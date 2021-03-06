package vault.jms.routes;

import org.apache.camel.Endpoint;
import vault.service.BookLifecycle;

public class SaveRoute extends BasicRoute {

    public SaveRoute(Endpoint endpoint, BookLifecycle lifecycle) {
        super(endpoint, lifecycle);
    }

    @Override
    public void configure() {
        from(endpoint)
                .bean(lifecycle, "save");
    }
}
