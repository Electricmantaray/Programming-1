package exceptions;

public class AgeLimitException extends RuntimeException {
    public AgeLimitException(String message) {
        super(message);
    }
}
