package vault.jms.routes;

import org.apache.camel.Endpoint;
import vault.service.BookLifecycle;

public class UpdateRoute extends BasicRoute {

    public UpdateRoute(Endpoint endpoint) {
        super(endpoint);
    }

    @Override
    public void configure() {
        from(endpoint)
                .bean(BookLifecycle.class, "update");
    }
}
