package vault.jms;

import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;

import javax.inject.Inject;

public class ActiveMqEndpointSupplier implements EndpointSupplier {

    private static final String ACTIVE_MQ_PREFIX = "activemq:";

    private CamelContext context;

    @Inject
    public ActiveMqEndpointSupplier(CamelContext context) {
        this.context = context;
    }

    @Override
    public Endpoint save() {
        return context.getEndpoint(ACTIVE_MQ_PREFIX + "persistence.save");
    }

    @Override
    public Endpoint getAll() {
        return context.getEndpoint(ACTIVE_MQ_PREFIX + "persistence.getall");
    }

    @Override
    public Endpoint getOne() {
        return context.getEndpoint(ACTIVE_MQ_PREFIX + "persistence.getone");
    }

    @Override
    public Endpoint update() {
        return context.getEndpoint(ACTIVE_MQ_PREFIX + "persistence.update");
    }

    @Override
    public Endpoint delete() {
        return context.getEndpoint(ACTIVE_MQ_PREFIX + "persistence.delete");
    }
}
