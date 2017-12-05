package vault.jms.exceptions;

public class ContextStartupException extends RuntimeException {

    public ContextStartupException(String message, Throwable exception) {
        super(message, exception);
    }
}
