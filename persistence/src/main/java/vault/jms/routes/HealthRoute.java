package vault.jms.routes;

import org.apache.camel.builder.RouteBuilder;
import vault.jms.EndpointSupplier;

public class HealthRoute extends RouteBuilder {

    private EndpointSupplier endpoints;

    public HealthRoute(EndpointSupplier endpoints) {
        this.endpoints = endpoints;
    }

    @Override
    public void configure() {
        from(endpoints.health())
                .bean(HealthRoute.class, "health");
    }

    public static String health(String inquiry) {
        return inquiry;
    }
}
