package vault.jms.routes;

import org.apache.camel.builder.RouteBuilder;
import vault.jms.EndpointSupplier;
import vault.service.BookLifecycle;

public class UpdateRoute extends RouteBuilder {

    private EndpointSupplier endpoints;

    public UpdateRoute(EndpointSupplier endpoints) {
        this.endpoints = endpoints;
    }

    @Override
    public void configure() {
        from(endpoints.update())
                .bean(BookLifecycle.class, "update");
    }
}
