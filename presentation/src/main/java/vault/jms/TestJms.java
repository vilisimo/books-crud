package vault.jms;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;

public class TestJms {

    private CamelContext context;

    private ProducerTemplate template;

    // TODO: provide context instead of factory? Use Guice to resolve it
    public TestJms(ContextFactory factory) {
        context = factory.getContext();
        template = context.createProducerTemplate();
        template.setDefaultEndpoint(context.getEndpoint("activemq:foo.bar?exchangePattern=InOut"));
    }

    public void send() {
        Object response = template.requestBody("Test");
        System.err.println("Response: " + response);
    }
}
