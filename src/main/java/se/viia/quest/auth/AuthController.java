package se.viia.quest.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.viia.quest.auth.token.RefreshAuthToken;
import se.viia.quest.auth.token.TokenHandler;
import se.viia.quest.domain.Account;

/**
 * @author affe 2018-04-25
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenHandler tokenHandler;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, TokenHandler tokenHandler) {
        this.authenticationManager = authenticationManager;
        this.tokenHandler = tokenHandler;
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authToken);
        Account account = Account.class.cast(authenticate.getPrincipal());
        return createResponse(account);
    }

    @PostMapping("/refresh")
    public AuthResponse refresh(@RequestBody String refreshToken) {
        RefreshAuthToken authToken = new RefreshAuthToken(refreshToken);
        Authentication auth = authenticationManager.authenticate(authToken);
        Account account = Account.class.cast(auth.getDetails());
        return createResponse(account);
    }

    private AuthResponse createResponse(Account account) {
        String accessToken = tokenHandler.createAccessToken(account);
        String refreshToken = tokenHandler.createRefreshToken(account);
        return new AuthResponse(accessToken, refreshToken);
    }
}
