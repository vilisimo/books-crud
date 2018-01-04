package vault.exception;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

public class NullBodyException extends ResourceException {

    private static final String errorMessage = "Body was null";

    public NullBodyException() {
        super(BAD_REQUEST, errorMessage, "none");
    }

}
