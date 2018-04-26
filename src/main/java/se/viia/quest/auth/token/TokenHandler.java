package se.viia.quest.auth.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.viia.quest.auth.key.KeyHandler;
import se.viia.quest.domain.Account;
import se.viia.quest.exception.InvalidTokenException;
import se.viia.quest.repo.TokenStore;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

/**
 * @author affe 2018-04-25
 */
@Service
public class TokenHandler {
    private static final String TOKEN_PREFIX = "Bearer";
    private static long accessTokenExpiration = 10 * 60 * 1000;
    private static long refreshTokenExpiration = 60 * 60 * 1000;

    private final TokenStore tokenStore;
    private final KeyHandler keyHandler;

    @Autowired
    public TokenHandler(TokenStore tokenStore, KeyHandler keyHandler) {
        this.tokenStore = tokenStore;
        this.keyHandler = keyHandler;
    }

    public Token validateAndGet(String token) throws InvalidTokenException {
        if (token == null) {
            throw new InvalidTokenException("Missing token! No token was provided");
        }
        if (!Jwts.parser().isSigned(token)) {
            throw new InvalidTokenException("Invalid token! The provided token wasn't signed");
        }

        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(keyHandler.getKey())
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody();
            return new Token(claims);
        } catch (JwtException e) {
            throw new InvalidTokenException("Unable to parse token: " + token, e);
        }
    }

    public String createAccessToken(Account account) {
        return Jwts.builder()
                .setSubject(account.getUsername())
                .claim("scopes", account.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpiration))
                .signWith(SignatureAlgorithm.HS512, keyHandler.getKey())
                .compact();
    }

    public String createRefreshToken(Account account) {
        UUID id = UUID.randomUUID();
        Date issuedAt = new Date();
        Date expires = new Date(System.currentTimeMillis() + refreshTokenExpiration);
        RefreshToken refreshToken = new RefreshToken(id, account.getUsername(), issuedAt, expires);
        tokenStore.save(refreshToken);

        return Jwts.builder()
                .setId(id.toString())
                .setIssuedAt(issuedAt)
                .setExpiration(expires)
                .signWith(SignatureAlgorithm.HS512, keyHandler.getKey())
                .compact();
    }

    public Optional<RefreshToken> getRefreshToken(UUID id) {
        return tokenStore.findById(id);
    }

    public void revoke(RefreshToken refreshToken) {
        refreshToken.setRevoked(true);
        tokenStore.save(refreshToken);
    }

    public Iterable<RefreshToken> getRefreshTokens() {
        return tokenStore.findAll();
    }
}
