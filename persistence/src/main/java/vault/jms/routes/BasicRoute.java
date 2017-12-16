package vault.jms.routes;

import org.apache.camel.Endpoint;
import org.apache.camel.builder.RouteBuilder;

abstract class BasicRoute extends RouteBuilder {

    Endpoint endpoint;

    BasicRoute(Endpoint endpoint) {
        this.endpoint = endpoint;
    }
}
