package vault.jms;

import org.apache.camel.CamelContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

public class EndpointConsumer {

    private static final Logger log = LoggerFactory.getLogger(EndpointConsumer.class);

    private final CamelContext context;

    @Inject
    public EndpointConsumer(CamelContext context) {
        this.context = context;

        log.info("Created endpoint consumer"); // todo: configure logging levels and set to trace
    }
}
