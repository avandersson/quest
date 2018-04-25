package se.viia.quest.auth.token;

import com.google.common.collect.Lists;
import org.springframework.security.authentication.AbstractAuthenticationToken;

/**
 * @author affe 2018-04-25
 */
public class RefreshAuthToken extends AbstractAuthenticationToken {

    private final String refreshToken;

    public RefreshAuthToken(String refreshToken) {
        super(Lists.newArrayList());
        this.refreshToken = refreshToken;
    }

    @Override
    public String getCredentials() {
        return refreshToken;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }
}
