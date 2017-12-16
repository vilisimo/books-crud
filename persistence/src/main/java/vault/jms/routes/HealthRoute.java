package vault.jms.routes;

import org.apache.camel.Endpoint;

public class HealthRoute extends BasicRoute {

    public HealthRoute(Endpoint endpoint) {
        super(endpoint);
    }

    @Override
    public void configure() {
        from(endpoint)
                .bean(HealthRoute.class, "health");
    }

    public static String health(String inquiry) {
        return inquiry;
    }
}
