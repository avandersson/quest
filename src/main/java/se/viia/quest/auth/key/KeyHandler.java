package se.viia.quest.auth.key;

import java.security.Key;

/**
 * Managing a key for signing and verifying tokens.
 *
 * @author affe 2018-01-31
 */
public interface KeyHandler {

    /**
     * Gets the key used for signing and verifying tokens.
     *
     * @return the key.
     */
    Key getKey();
}
