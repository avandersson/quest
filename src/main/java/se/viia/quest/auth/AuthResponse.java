package se.viia.quest.auth;

/**
 * @author affe 2018-04-25
 */
public class AuthResponse {

    private final String accessToken;
    private final String refreshToken;

    public AuthResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    /**
     * @return value of accessToken
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * @return value of refreshToken
     */
    public String getRefreshToken() {
        return refreshToken;
    }
}
