package vault.resource;

import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vault.api.Suggestion;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Path("/suggestions")
public class SuggestionsResource {

    private static final Logger LOG = LoggerFactory.getLogger(Suggestion.class);

    @POST
    @Timed
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postSuggestion(@Valid Suggestion suggestion, @Context UriInfo uriInfo) {
        String id = UUID.randomUUID().toString();
        suggestion.setId(id);

        UriBuilder pathBuilder = uriInfo.getAbsolutePathBuilder();
        pathBuilder.path(id);

        LOG.info("Received a suggestion: {}", suggestion);

        return Response.created(pathBuilder.build())
                .entity(suggestion)
                .build();
    }

    @GET
    @Timed
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        List<Suggestion> suggestions = new ArrayList<>();
        suggestions.add(new Suggestion("First suggestion"));
        suggestions.add(new Suggestion("Second suggestion"));

        return Response.ok(suggestions).build();
    }

    @GET
    @Timed
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOne(@PathParam("id") String id) {
        Suggestion suggestion = new Suggestion("Just a placeholder");

        return Response.ok(suggestion).build();
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
