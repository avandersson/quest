package se.viia.quest.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.viia.quest.account.AccountService;
import se.viia.quest.domain.Account;

/**
 * @author affe 2018-04-25
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final AccountService accountService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, AccountService accountService) {
        this.authenticationManager = authenticationManager;
        this.accountService = accountService;
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authToken);
        Account account = Account.class.cast(authenticate.getPrincipal());
        String accessToken = TokenHandler.createToken(account);
        return new AuthResponse(accessToken, null);
    }
}
