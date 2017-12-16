package vault.jms.routes;

import org.apache.camel.Endpoint;
import vault.service.BookLifecycle;

public class DeleteRoute extends BasicRoute {

    public DeleteRoute(Endpoint endpoint) {
        super(endpoint);
    }

    @Override
    public void configure() {
        from(endpoint)
                .bean(BookLifecycle.class, "delete");
    }
}
