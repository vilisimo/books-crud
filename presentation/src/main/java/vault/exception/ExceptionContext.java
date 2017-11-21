package vault.exception;

public final class ExceptionContext {

    private final int code;
    private final String message;
    private final String resourceId;

    ExceptionContext(ResourceException exception) {
        this.code = exception.getStatus();
        this.message = exception.getMessage();
        this.resourceId = exception.getResourceId();
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getResourceId() {
        return resourceId;
    }
}
