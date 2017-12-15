package vault.jms.routes;

import org.apache.camel.builder.RouteBuilder;
import vault.jms.EndpointSupplier;
import vault.service.BookLifecycle;

public class GetAllRoute extends RouteBuilder {

    private EndpointSupplier endpoints;

    public GetAllRoute(EndpointSupplier endpoints) {
        this.endpoints = endpoints;
    }

    @Override
    public void configure() {
        from(endpoints.getAll())
                .bean(BookLifecycle.class, "getAll");
    }
}
