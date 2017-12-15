package vault.jms.routes;

import org.apache.camel.builder.RouteBuilder;
import vault.jms.EndpointSupplier;
import vault.service.BookLifecycle;

public class DeleteRoute extends RouteBuilder {

    private EndpointSupplier endpoints;

    public DeleteRoute(EndpointSupplier endpoints) {
        this.endpoints = endpoints;
    }

    @Override
    public void configure() {
        from(endpoints.delete())
                .bean(BookLifecycle.class, "delete");
    }
}
