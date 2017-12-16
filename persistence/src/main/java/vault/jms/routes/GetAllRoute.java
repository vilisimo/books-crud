package vault.jms.routes;

import org.apache.camel.Endpoint;
import vault.service.BookLifecycle;

public class GetAllRoute extends BasicRoute {

    public GetAllRoute(Endpoint endpoint) {
        super(endpoint);
    }

    @Override
    public void configure() {
        from(endpoint)
                .bean(BookLifecycle.class, "getAll");
    }
}
