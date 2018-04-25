package se.viia.quest.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * @author affe 2018-04-13
 */
@Document
public class Account implements UserDetails {

    private String name;
    private int credit;

    @Id
    private String username;
    private String password;
    private List<GrantedAuthority> authorities;
    private boolean active;

    public Account() {
    }

    public Account(String name, String username, String password, List<GrantedAuthority> authorities, boolean active) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.active = active;
    }

    /**
     * @return value of name
     */
    public String getName() {
        return name;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    /**
     * @return value of username
     */
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }
}
