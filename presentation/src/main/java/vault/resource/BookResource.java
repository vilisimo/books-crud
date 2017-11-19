package vault.resource;

import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vault.model.Book;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.ArrayList;
import java.util.List;

@Path("/recommendations/books")
public class BookResource {

    private static final Logger LOG = LoggerFactory.getLogger(Book.class);

    @POST
    @Timed
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postSuggestion(@Valid Book book, @Context UriInfo uriInfo) {
        UriBuilder pathBuilder = uriInfo.getAbsolutePathBuilder();
        pathBuilder.path(book.id());

        LOG.trace("Received a suggestion: {}", book);

        return Response.created(pathBuilder.build())
                .entity(book)
                .build();
    }

    @GET
    @Timed
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        List<Book> books = new ArrayList<>();
        books.add(new Book("First suggestion", "Because"));
        books.add(new Book("Second suggestion", "I said so"));

        return Response.ok(books).build();
    }

    @GET
    @Timed
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOne(@PathParam("id") String id) {
        Book book = new Book("Just a placeholder", "Placeholder");

        return Response.ok(book).build();
    }

    @PUT
    @Timed
    @Path("/{id}")
    public Response update(@PathParam("id") String id) {
        return Response.noContent().build();
    }

    @DELETE
    @Timed
    @Path("/{id}")
    public Response delete(@PathParam("id") String id) {
        return Response.accepted().build();
    }
}
