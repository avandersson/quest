package se.viia.quest.auth.token;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.UUID;

/**
 * @author affe 2018-04-25
 */
@Document
public class RefreshToken {
    @Id
    private final UUID id;
    private final String username;
    private final Date issuedAt;
    private final Date expiresAt;
    private boolean revoked = false;

    public RefreshToken(UUID id, String username, Date issuedAt, Date expiresAt) {
        this.id = id;
        this.username = username;
        this.issuedAt = issuedAt;
        this.expiresAt = expiresAt;
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

    public void setRevoked(boolean revoked) {
        this.revoked = revoked;
    }
}
