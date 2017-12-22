package vault.jms.routes;

import org.apache.camel.Endpoint;
import vault.service.BookLifecycle;

public class DeleteRoute extends BasicRoute {

    public DeleteRoute(Endpoint endpoint, BookLifecycle lifecycle) {
        super(endpoint, lifecycle);
    }

    @Override
    public void configure() {
        from(endpoint)
                .bean(lifecycle, "delete");
    }
}
