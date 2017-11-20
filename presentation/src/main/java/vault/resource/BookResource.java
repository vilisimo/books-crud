package vault.resource;

import com.codahale.metrics.annotation.Timed;
import vault.exception.BookNotFoundException;
import vault.model.Book;
import vault.validation.annotations.UUID;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.*;

import static vault.resource.UriPathBuilder.buildUri;

@Path("/recommendations/books")
public class BookResource {

    private static final Map<String, Book> tempDatasource = new HashMap<>();

    @POST
    @Timed
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postBook(@Valid Book book, @Context UriInfo uriInfo) {
        book.setId(java.util.UUID.randomUUID().toString());
        tempDatasource.put(book.id(), book);

        return Response.created(buildUri(uriInfo, book.id()))
                .entity(book)
                .build();
    }

    @GET
    @Timed
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllBooks() {
        List<Book> books = new ArrayList<>(tempDatasource.values());

        return Response.ok(books).build();
    }

    @GET
    @Timed
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Optional<Book> getBook(@PathParam("id") @UUID String id) {
        return Optional.ofNullable(tempDatasource.get(id));
    }

    @PUT
    @Timed
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateBook(@PathParam("id") @UUID String id, @Valid Book book, @Context UriInfo uriInfo) {
        book.setId(id);

        boolean created = !tempDatasource.containsKey(book.id());
        tempDatasource.put(id, book);

        if (created) {
            return Response.created(buildUri(uriInfo, book.id())).build();
        }

        return Response.noContent().build();
    }

    @DELETE
    @Timed
    @Path("{id}")
    public Response deleteBook(@PathParam("id") @UUID String id) {
        Book book = tempDatasource.remove(id);

        if (book != null) {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
