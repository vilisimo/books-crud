package vault.jms.routes;

import org.apache.camel.Endpoint;
import vault.service.BookLifecycle;

public class GetAllRoute extends BasicRoute {

    public GetAllRoute(Endpoint endpoint, BookLifecycle lifecycle) {
        super(endpoint, lifecycle);
    }

    @Override
    public void configure() {
        from(endpoint)
                .bean(lifecycle, "getAll");
    }
}
