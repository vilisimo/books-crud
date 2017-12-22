package vault.jms;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vault.jms.exceptions.RouteAdditionException;
import vault.jms.routes.*;
import vault.service.BookLifecycle;

import javax.inject.Inject;

public class EndpointConsumer {

    private static final Logger log = LoggerFactory.getLogger(EndpointConsumer.class);

    private final CamelContext context;

    @Inject
    public EndpointConsumer(CamelContext context, EndpointSupplier endpoints, BookLifecycle lifecycle) {
        this.context = context;

        addRoute(new SaveRoute(endpoints.save(), lifecycle), "save");
        addRoute(new GetAllRoute(endpoints.getAll(), lifecycle), "getAll");
        addRoute(new GetOneRoute(endpoints.getOne(), lifecycle), "getOne");
        addRoute(new UpdateRoute(endpoints.update(), lifecycle), "update");
        addRoute(new DeleteRoute(endpoints.delete(), lifecycle), "delete");
        addRoute(new HealthRoute(endpoints.health(), lifecycle), "health");

        log.info("Created endpoint consumer");
    }

    private void addRoute(RouteBuilder route, String routeName) {
        try {
            context.addRoutes(route);
            log.info("Added {} route", routeName);
        } catch (Exception e) {
            throw new RouteAdditionException("Failed to add a '" + routeName + "' route to the context", e);
        }
    }
}
