package se.viia.quest.auth.provider;

import com.google.common.base.Strings;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import se.viia.quest.account.AccountService;
import se.viia.quest.auth.token.RefreshAuthToken;
import se.viia.quest.auth.token.RefreshToken;
import se.viia.quest.auth.token.Token;
import se.viia.quest.auth.token.TokenHandler;
import se.viia.quest.domain.Account;
import se.viia.quest.exception.InvalidTokenException;

import java.util.UUID;

/**
 * @author affe 2018-04-25
 */
public class RefreshAuthProvider implements AuthenticationProvider {
    
    private final TokenHandler tokenHandler;
    private final AccountService accountService;
    private final UserDetailsChecker userDetailsChecker = new AccountStatusUserDetailsChecker();

    public RefreshAuthProvider(TokenHandler tokenHandler, AccountService accountService) {
        this.tokenHandler = tokenHandler;
        this.accountService = accountService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        RefreshAuthToken authToken = RefreshAuthToken.class.cast(authentication);
        String refreshTokenString = authToken.getCredentials();
        RefreshToken refreshToken = null;
        try {
            Token token = tokenHandler.validateAndGet(refreshTokenString);
            String id = token.getId();
            if (Strings.isNullOrEmpty(id)) {
                throw new InvalidTokenException("The refresh token didn't contain any id.");
            }
            refreshToken = tokenHandler.getRefreshToken(UUID.fromString(id))
                    .orElseThrow(() -> new InvalidTokenException("Unable to find the token in the store."));

            if (refreshToken.isRevoked()) {
                throw new InvalidTokenException("The refresh token was revoked!");
            }

            Account account = accountService.loadUserByUsername(refreshToken.getUsername());
            userDetailsChecker.check(account);
            authToken.setDetails(account);

            return authToken;
        } catch (InvalidTokenException e) {
            throw new BadCredentialsException("Invalid refresh token!", e);
        } finally {
            if (refreshToken != null) {
                tokenHandler.revoke(refreshToken);
            }
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(RefreshAuthToken.class);
    }
}
