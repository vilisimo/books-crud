package vault.resource;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

import static java.util.Objects.requireNonNull;

public final class UriPathBuilder {

    private UriPathBuilder() {
        throw new AssertionError("The class should not be instantiated");
    }

    public static URI buildUri(UriInfo uriInfo, String path) {
        requireNonNull(uriInfo, "UriInfo cannot be null");

        UriBuilder pathBuilder = uriInfo.getAbsolutePathBuilder();
        pathBuilder.path(path);

        return pathBuilder.build();
    }
}
