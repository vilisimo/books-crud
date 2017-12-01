package vault.jms;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;

public class TestConsumer {

    private CamelContext context;

    public TestConsumer() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        connectionFactory.setTrustAllPackages(true);
        context = new DefaultCamelContext();
        context.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));

        try {
            context.addRoutes(new TestRoute());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            context.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
