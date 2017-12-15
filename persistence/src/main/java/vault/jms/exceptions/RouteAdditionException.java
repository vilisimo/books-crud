package vault.jms.exceptions;

public class RouteAdditionException extends RuntimeException {

    public RouteAdditionException(String message, Throwable exception) {
        super(message, exception);
    }
}
