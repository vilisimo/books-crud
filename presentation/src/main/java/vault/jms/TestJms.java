package vault.jms;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;

public class TestJms {

    private CamelContext context;

    private ProducerTemplate template;

    public TestJms(ContextFactory factory) {

        context = factory.getContext();
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
