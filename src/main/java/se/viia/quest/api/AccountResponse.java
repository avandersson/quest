package se.viia.quest.api;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.springframework.data.annotation.Id;
import org.springframework.security.core.GrantedAuthority;
import se.viia.quest.domain.Account;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * @author affe 2018-04-26
 */
public class AccountResponse {

    private final String name;
    private final String username;
    private final List<? extends GrantedAuthority> authorities;
    private final boolean active;

    public AccountResponse(Account account) {
        Preconditions.checkArgument(account != null, "Argument account should not be null!");
        this.name = account.getName();
        this.username = account.getUsername();
        this.authorities = Optional.ofNullable(account.getAuthorities()).map(Lists::newArrayList).orElse(Lists.newArrayList());
        this.active = account.isEnabled();
    }

    /**
     * @return value of name
     */
    public String getName() {
        return name;
    }

    /**
     * @return value of username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return value of authorities
     */
    public List<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    /**
     * @return value of active
     */
    public boolean isActive() {
        return active;
    }
}
