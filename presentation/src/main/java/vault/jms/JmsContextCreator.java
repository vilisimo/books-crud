package vault.jms;

import org.apache.camel.CamelContext;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;
import vault.jms.exceptions.ContextStartupException;

import javax.jms.ConnectionFactory;

public class JmsContextCreator implements ContextCreator {

    private static final String JMS_COMPONENT = "jms";

    private static CamelContext context;

    public JmsContextCreator(ConnectionFactory factory) {
        if (context == null) {
            context = new DefaultCamelContext();
            context.addComponent(JMS_COMPONENT, JmsComponent.jmsComponentAutoAcknowledge(factory));

            startContext();
        }
    }

    private void startContext() {
        try {
            context.start();
        } catch (Exception e) {
            throw new ContextStartupException("Starting context with JMS connection factory has failed", e);
        }
    }

    @Override
    public CamelContext getContext() {
        return context;
    }
}
