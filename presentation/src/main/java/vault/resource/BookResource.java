package vault.resource;

import com.codahale.metrics.annotation.Timed;
import vault.exception.BookNotFoundException;
import vault.model.Book;
import vault.service.RecommendationLifecycle;
import vault.validation.annotations.UUID;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.*;

import static java.util.Optional.ofNullable;
import static vault.resource.UriPathBuilder.buildUri;

@Path("/recommendations/books")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BookResource {

    // TODO: remove this when Book lifecycle is fully introduced: use Guice
    private static final Map<String, Book> tempDatasource = new HashMap<>();

    private final RecommendationLifecycle<Book> lifecycle;

    public BookResource(RecommendationLifecycle<Book> lifecycle) {
        this.lifecycle = lifecycle;
    }

    @POST
    @Timed
    public Response postBook(@Valid Book book, @Context UriInfo uriInfo) {
        String uuid = lifecycle.save(book);

        return Response.created(buildUri(uriInfo, uuid))
                .entity(book)
                .build();
    }

    @GET
    @Timed
    public Response getAllBooks() {
        List<Book> books = lifecycle.getAll();

        return Response.ok(books).build();
    }

    @GET
    @Timed
    @Path("{id}")
    public Book getBook(@PathParam("id") @UUID String id) {
        return ofNullable(tempDatasource.get(id))
                .orElseThrow(() -> new BookNotFoundException(Response.Status.NOT_FOUND, id));
    }

    @PUT
    @Timed
    @Path("{id}")
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

        return ofNullable(book)
                .map(it -> Response.noContent().build())
                .orElseThrow(() -> new BookNotFoundException(Response.Status.NOT_FOUND, id));
    }
}
