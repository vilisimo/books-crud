package vault.jms;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;
import vault.jms.exceptions.ContextStartupException;

import javax.jms.ConnectionFactory;

public class ActiveMqContextFactory implements ContextFactory {

    private static final String ACTIVE_MQ_ADDRESS = "tcp://localhost:61616";
    private static final String JMS_COMPONENT = "jms";

    private static CamelContext context;

    // TODO: provide connection factory via Guice instead of creating it
    public ActiveMqContextFactory() {
        if (context == null) {
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ACTIVE_MQ_ADDRESS);
            context = new DefaultCamelContext();
            context.addComponent(JMS_COMPONENT, JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));

            startContext();
        }
    }

    private void startContext() {
        try {
            context.start();
        } catch (Exception e) {
            throw new ContextStartupException("Starting context with ActiveMQ connection factory has failed", e);
        }
    }

    @Override
    public CamelContext getContext() {
        return context;
    }
}
