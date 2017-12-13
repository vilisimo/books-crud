package vault.jms;

import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;

public class ActiveMqEndpointSupplier implements EndpointSupplier {

    private static final String ACTIVE_MQ_PREFIX = "activemq:";
    private static final String EXCHANGE_PATTERN_PREFIX = "?exchangePattern=";
    private static final String REQUEST_REPLY_PATTERN = "InOut";

    private CamelContext context;

    public ActiveMqEndpointSupplier(CamelContext context) {
        this.context = context;
    }

    @Override
    public Endpoint save() {
        return context.getEndpoint(ACTIVE_MQ_PREFIX + "persistence.save" + EXCHANGE_PATTERN_PREFIX + REQUEST_REPLY_PATTERN);
    }

    @Override
    public Endpoint getAll() {
        return context.getEndpoint(ACTIVE_MQ_PREFIX + "persistence.getall" + EXCHANGE_PATTERN_PREFIX + REQUEST_REPLY_PATTERN);
    }

    @Override
    public Endpoint getOne() {
        return context.getEndpoint(ACTIVE_MQ_PREFIX + "persistence.getone" + EXCHANGE_PATTERN_PREFIX + REQUEST_REPLY_PATTERN);
    }

    @Override
    public Endpoint update() {
        return context.getEndpoint(ACTIVE_MQ_PREFIX + "persistence.update" + EXCHANGE_PATTERN_PREFIX + REQUEST_REPLY_PATTERN);
    }

    @Override
    public Endpoint remove() {
        return context.getEndpoint(ACTIVE_MQ_PREFIX + "persistence.remove" + EXCHANGE_PATTERN_PREFIX + REQUEST_REPLY_PATTERN);
    }
}
