package vault.jms.routes;

import org.apache.camel.builder.RouteBuilder;
import vault.jms.EndpointSupplier;
import vault.service.BookLifecycle;

public class SaveRoute extends RouteBuilder {

    private EndpointSupplier endpoints;

    public SaveRoute(EndpointSupplier endpoints) {
        this.endpoints = endpoints;
    }

    @Override
    public void configure() {
        from(endpoints.save())
                .bean(BookLifecycle.class, "save");
    }
}
