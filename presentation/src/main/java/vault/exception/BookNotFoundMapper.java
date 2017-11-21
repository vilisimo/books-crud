package vault.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class BookNotFoundMapper implements ExceptionMapper<BookNotFoundException> {

    @Override
    public Response toResponse(BookNotFoundException exception) {
        ExceptionContext exceptionContext = new ExceptionContext(exception);

        return Response.status(Response.Status.NOT_FOUND)
                .entity(exceptionContext)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
