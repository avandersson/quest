package se.viia.quest.account;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import se.viia.quest.domain.Account;
import se.viia.quest.repo.AccountRepo;
import se.viia.quest.util.SecurityUtils;

import java.util.Optional;

/**
 * @author affe 2018-04-25
 */
@Service
public class AccountService implements UserDetailsService {

    private final AccountRepo accountRepo;

    @Autowired
    public AccountService(AccountRepo accountRepo) {
        this.accountRepo = accountRepo;
        String pw = SecurityUtils.passwordEncoder().encode("admin");
        Account account = new Account("admin", "admin", pw, Lists.newArrayList(new SimpleGrantedAuthority("USER"), new SimpleGrantedAuthority("ADMIN")), true);
        accountRepo.save(account);
    }

    @Override
    public Account loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> account = accountRepo.findByUsername(username);
        return account.orElseThrow(() -> new UsernameNotFoundException(username));
    }

    public Iterable<Account> getAccounts() {
        return accountRepo.findAll();
    }
}
