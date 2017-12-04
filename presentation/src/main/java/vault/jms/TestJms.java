package vault.jms;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;

public class TestJms {

    private CamelContext context;

    private ProducerTemplate template;

    public TestJms() {

        context = new DefaultCamelContext();
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        connectionFactory.setTrustAllPackages(true);
        context.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));

        template = context.createProducerTemplate();
        template.setDefaultEndpoint(context.getEndpoint("activemq:foo.bar?exchangePattern=InOut"));

        try {
            context.start();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void sendStuff() {
        Object response = template.requestBody("Test");
        System.err.println("Response: " + response);
    }

}
