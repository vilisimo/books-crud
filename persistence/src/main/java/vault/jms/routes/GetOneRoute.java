package vault.jms.routes;

import org.apache.camel.builder.RouteBuilder;
import vault.jms.EndpointSupplier;
import vault.service.BookLifecycle;

public class GetOneRoute extends RouteBuilder {

    private EndpointSupplier endpoints;

    public GetOneRoute(EndpointSupplier endpoints) {
        this.endpoints = endpoints;
    }

    @Override
    public void configure() throws Exception {
        from(endpoints.getOne())
                .bean(BookLifecycle.class, "getOne");
    }
}
