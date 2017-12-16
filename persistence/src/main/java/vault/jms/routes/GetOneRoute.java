package vault.jms.routes;

import org.apache.camel.Endpoint;
import vault.service.BookLifecycle;

public class GetOneRoute extends BasicRoute {

    public GetOneRoute(Endpoint endpoint) {
        super(endpoint);
    }

    @Override
    public void configure() throws Exception {
        from(endpoint)
                .bean(BookLifecycle.class, "getOne");
    }
}
