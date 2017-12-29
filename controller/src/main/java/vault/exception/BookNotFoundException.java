package vault.exception;

import javax.ws.rs.core.Response;

public class BookNotFoundException extends ResourceException {

    private static final String errorMessage = "Specified book was not found";

    public BookNotFoundException(Response.Status status, String resourceId) {
        super(status, errorMessage, resourceId);
    }
}
