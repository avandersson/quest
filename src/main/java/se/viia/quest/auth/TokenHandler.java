package se.viia.quest.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import se.viia.quest.domain.Account;
import se.viia.quest.exception.InvalidTokenException;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

/**
 * @author affe 2018-04-25
 */
public class TokenHandler {
    private static final String TOKEN_PREFIX = "Bearer";
    private static final SecretKey key = new SecretKeySpec("TEST".getBytes(), "AES");
    private static long accessTokenExpiration = 10 * 60 * 1000;

    private TokenHandler() {}

    public static Token validateAndGet(String token) throws InvalidTokenException {
        if (token == null) {
            throw new InvalidTokenException("Missing token! No token was provided");
        }
        if (!Jwts.parser().isSigned(token)) {
            throw new InvalidTokenException("Invalid token! The provided token wasn't signed");
        }

        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody();
            return new Token(claims);
        } catch (JwtException e) {
            throw new InvalidTokenException("Unable to parse token: " + token, e);
        }
    }

    public static String createToken(Account account) {
        return Jwts.builder()
                .setSubject(account.getUsername())
                .claim("scopes", account.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpiration))
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }
}
