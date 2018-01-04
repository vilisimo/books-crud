package vault.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ResourceExceptionMapper implements ExceptionMapper<ResourceException> {

    @Override
    public Response toResponse(ResourceException exception) {
        ExceptionContext exceptionContext = new ExceptionContext(exception);

        return Response.status(exceptionContext.getCode())
                .entity(exceptionContext)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
