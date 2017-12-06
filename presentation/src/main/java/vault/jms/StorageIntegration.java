package vault.jms;

import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.apache.camel.ProducerTemplate;

import javax.inject.Inject;

public class StorageIntegration {

    private static final String TEST_ENDPOINT = "activemq:foo.bar?exchangePattern=InOut";

    private ProducerTemplate template;

    @Inject
    public StorageIntegration(CamelContext context) {
        template = context.createProducerTemplate();
        Endpoint testEndpoint = context.getEndpoint(TEST_ENDPOINT);
        template.setDefaultEndpoint(testEndpoint);
    }

    public void send() {
        Object response = template.requestBody("Test");
        System.err.println("Response: " + response);
    }
}
