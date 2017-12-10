package vault.jms;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.apache.camel.ProducerTemplate;
import vault.model.Book;

import javax.inject.Inject;

public class StorageIntegration {

    private static final String TEST_ENDPOINT = "activemq:foo.bar?exchangePattern=InOut";
    private static final String TEST_SIMPLE_ENDPOINT = "activemq:foo.bar.simple";
    private static final ObjectMapper mapper = new ObjectMapper();

    private final ProducerTemplate template;

    @Inject
    public StorageIntegration(CamelContext context) {
        template = context.createProducerTemplate();
        Endpoint testEndpoint = context.getEndpoint(TEST_ENDPOINT);
        template.setDefaultEndpoint(testEndpoint);
    }

    public void send() {
        template.sendBody(TEST_SIMPLE_ENDPOINT, "Test payload");
        System.err.println("Poked persistence layer");
    }

    public void save(Book book) {
        String bookString = null;
        try {
            bookString = mapper.writeValueAsString(book);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        template.sendBody(bookString);
    }
}
