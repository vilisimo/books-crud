package vault.exception;

import javax.ws.rs.core.Response;

public abstract class ResourceException extends RuntimeException {

    private int status;
    private String resourceId;

    ResourceException(Response.Status status, String errorMessage, String resourceId) {
        super(errorMessage);
        this.status = status.getStatusCode();
        this.resourceId = resourceId;
    }

    public int getStatus() {
        return status;
    }

    public String getResourceId() {
        return resourceId;
    }
}
