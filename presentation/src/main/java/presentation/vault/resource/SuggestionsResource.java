package presentation.vault.resource;

import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import presentation.vault.api.Suggestion;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;
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
}
