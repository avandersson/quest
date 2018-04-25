package se.viia.quest.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author affe 2018-04-25
 */
public class SecurityUtils {
    private static final BCryptPasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    private SecurityUtils() {}

    public static PasswordEncoder passwordEncoder() {
        return PASSWORD_ENCODER;
    }
}
