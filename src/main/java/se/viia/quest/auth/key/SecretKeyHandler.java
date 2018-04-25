package se.viia.quest.auth.key;

import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

/**
 * Simple secret key implementation.
 *
 * @author affe 2018-01-31
 */
@Component
public class SecretKeyHandler implements KeyHandler {

    private static final String SECRET = "TEST!";
    private final SecretKey key = new SecretKeySpec(SECRET.getBytes(), "AES");

    public Key getKey() {
        return key;
    }
}
