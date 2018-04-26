package se.viia.quest.api;

import se.viia.quest.auth.token.RefreshToken;

import java.util.Date;
import java.util.UUID;

/**
 * @author affe 2018-04-26
 */
public class TokenResponse {

    private final UUID id;
    private final String username;
    private final Date issuedAt;
    private final Date expiresAt;
    private final boolean revoked;

    public TokenResponse(RefreshToken token) {
        this.id = token.getId();
        this.username = token.getUsername();
        this.issuedAt = token.getIssuedAt();
        this.expiresAt = token.getExpiresAt();
        this.revoked = token.isRevoked();
    }

    /**
     * @return value of id
     */
    public UUID getId() {
        return id;
    }

    /**
     * @return value of username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return value of issuedAt
     */
    public Date getIssuedAt() {
        return issuedAt;
    }

    /**
     * @return value of expiresAt
     */
    public Date getExpiresAt() {
        return expiresAt;
    }

    /**
     * @return value of revoked
     */
    public boolean isRevoked() {
        return revoked;
    }
}
