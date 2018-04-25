package se.viia.quest.exception;

/**
 * @author affe 2018-04-25
 */
public class InvalidTokenException extends Exception {

    public InvalidTokenException(String message) {
        super(message);
    }

    public InvalidTokenException(String message, Throwable cause) {
        super(message, cause);
    }
}
