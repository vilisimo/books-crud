package vault.resource;

import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vault.model.Book;
import vault.validation.annotations.UUID;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.*;

@Path("/recommendations/books")
public class BookResource {

    private static final Logger LOG = LoggerFactory.getLogger(Book.class);

    private static final Map<String, Book> tempDatasource = new HashMap<>();

    @POST
    @Timed
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postSuggestion(@Valid Book book, @Context UriInfo uriInfo) {
        book.setId(java.util.UUID.randomUUID().toString());
        tempDatasource.put(book.id(), book);

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
        List<Book> books = new ArrayList<>(tempDatasource.values());

        return Response.ok(books).build();
    }

    @GET
    @Timed
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Optional<Book> getOne(@PathParam("id") @UUID String id) {
        return Optional.ofNullable(tempDatasource.get(id));
    }

    @PUT
    @Timed
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") @UUID String id, @Valid Book book, @Context UriInfo uriInfo) {
        book.setId(id);

        boolean created = !tempDatasource.containsKey(book.id());
        tempDatasource.put(id, book);

        if (created) {
            UriBuilder pathBuilder = uriInfo.getAbsolutePathBuilder();
            pathBuilder.path(book.id());

            return Response.created(pathBuilder.build())
                    .build();
        }

        return Response.noContent().build();
    }

    @DELETE
    @Timed
    @Path("/{id}")
    public Response delete(@PathParam("id") @UUID String id) {
        Book book = tempDatasource.remove(id);

        if (book != null) {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
