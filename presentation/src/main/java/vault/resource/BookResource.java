package vault.resource;

import com.codahale.metrics.annotation.Timed;
import vault.model.Book;
import vault.service.Lifecycle;
import vault.validation.annotations.UUID;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

import static vault.http.Headers.X_TOTAL_COUNT;
import static vault.resource.UriPathBuilder.buildUri;

@Path("/books")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BookResource {

    private final Lifecycle<Book> lifecycle;

    @Inject
    public BookResource(Lifecycle<Book> lifecycle) {
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

        return Response.ok(books).header(X_TOTAL_COUNT, books.size()).build();
    }

    @GET
    @Timed
    @Path("{id}")
    public Book getBook(@PathParam("id") @UUID String id) {
        return lifecycle.getOne(id);
    }

    @PUT
    @Timed
    @Path("{id}")
    public Response updateBook(@PathParam("id") @UUID String id, @Valid Book book, @Context UriInfo uriInfo) {
        boolean updated = lifecycle.update(id, book);

        if (!updated) {
            return Response.created(buildUri(uriInfo, book.id())).build();
        }

        return Response.noContent().build();
    }

    @DELETE
    @Timed
    @Path("{id}")
    public Response deleteBook(@PathParam("id") @UUID String id) {
        lifecycle.remove(id);

        return Response.noContent().build();
    }
}
