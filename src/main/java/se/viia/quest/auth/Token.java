package se.viia.quest.auth;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import se.viia.quest.util.ApiUtils;

import java.util.Date;
import java.util.List;

/**
 * @author affe 2018-04-25
 */
public class Token {

    private final Claims claims;

    public Token(Claims claims) {
        Preconditions.checkArgument(claims != null);
        this.claims = claims;
    }

    /**
     * @return value of issuer
     */
    public String getIssuer() {
        return claims.getIssuer();
    }

    /**
     * @return value of subject
     */
    public String getSubject() {
        return claims.getSubject();
    }

    /**
     * @return value of audience
     */
    public String getAudience() {
        return claims.getAudience();
    }

    /**
     * @return value of expiration
     */
    public Date getExpiration() {
        return claims.getExpiration();
    }

    /**
     * @return value of issuedAt
     */
    public Date getIssuedAt() {
        return claims.getIssuedAt();
    }

    /**
     * @return value of id
     */
    public String getId() {
        return claims.getId();
    }

    /**
     * @return value of username
     */
    public String getUsername() {
        return claims.getSubject();
    }

    /**
     * @return value of scopes
     */
    public List<String> getScopes() {
        List<?> scopes = claims.get("scopes", List.class);
        return scopes != null ? ApiUtils.transform(scopes, Object::toString) : Lists.newArrayList();
    }

    public List<GrantedAuthority> getAuthorities() {
        return ApiUtils.transform(getScopes(), SimpleGrantedAuthority::new);
    }
}
