package vault.jms.routes;

import org.apache.camel.Endpoint;
import org.apache.camel.builder.RouteBuilder;
import vault.service.BookLifecycle;

abstract class BasicRoute extends RouteBuilder {

    Endpoint endpoint;
    BookLifecycle lifecycle;

    BasicRoute(Endpoint endpoint, BookLifecycle lifecycle) {
        this.endpoint = endpoint;
        this.lifecycle = lifecycle;
    }
}
