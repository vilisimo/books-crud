package vault.jms.routes;

import org.apache.camel.Endpoint;
import vault.service.BookLifecycle;

public class HealthRoute extends BasicRoute {

    public HealthRoute(Endpoint endpoint, BookLifecycle lifecycle) {
        super(endpoint, lifecycle);
    }

    @Override
    public void configure() {
        from(endpoint)
                .bean(this, "health");
    }

    public static String health(String inquiry) {
        return inquiry;
    }
}
