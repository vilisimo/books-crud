package vault.jms;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.DefaultProducerTemplate;

public class TestJms {

    private CamelContext context = new DefaultCamelContext();

    private ProducerTemplate template = new DefaultProducerTemplate(context);

    public TestJms() {

        context = new DefaultCamelContext();
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        connectionFactory.setTrustAllPackages(true);
        context.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));

        template = context.createProducerTemplate();
        template.setDefaultEndpoint(context.getEndpoint("activemq:foo.bar"));

        try {
            context.start();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void sendStuff() {
        template.sendBody("Test");
    }

}
