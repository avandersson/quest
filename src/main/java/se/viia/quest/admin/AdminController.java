package se.viia.quest.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.viia.quest.account.AccountService;
import se.viia.quest.api.AccountResponse;
import se.viia.quest.api.TokenResponse;
import se.viia.quest.auth.token.TokenHandler;
import se.viia.quest.util.ApiUtils;

import java.util.List;

/**
 * @author affe 2018-04-26
 */
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AccountService accountService;
    private final TokenHandler tokenHandler;

    @Autowired
    public AdminController(AccountService accountService, TokenHandler tokenHandler) {
        this.accountService = accountService;
        this.tokenHandler = tokenHandler;
    }

    @GetMapping("/accounts")
    public List<AccountResponse> getAccounts() {
        return ApiUtils.transform(accountService.getAccounts(), AccountResponse::new);
    }

    @GetMapping("/tokens")
    public List<TokenResponse> getTokens() {
        return ApiUtils.transform(tokenHandler.getRefreshTokens(), TokenResponse::new);
    }
}
