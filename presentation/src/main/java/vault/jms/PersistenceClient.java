package vault.jms;

import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.apache.camel.ProducerTemplate;
import vault.converter.Converter;
import vault.model.Book;

import javax.inject.Inject;

public class PersistenceClient {

    private static final String TEST_ENDPOINT = "activemq:foo.bar?exchangePattern=InOut";
    private static final String TEST_SIMPLE_ENDPOINT = "activemq:foo.bar.simple";

    private final ProducerTemplate template;

    private final Converter converter;


    @Inject
    public PersistenceClient(CamelContext context, Converter converter) {
        this.converter = converter;

        template = context.createProducerTemplate();
        Endpoint testEndpoint = context.getEndpoint(TEST_ENDPOINT);
        template.setDefaultEndpoint(testEndpoint);

    }

    public void send() {
        template.sendBody(TEST_SIMPLE_ENDPOINT, "Test payload");
        System.err.println("Poked persistence layer");
    }

    public void save(Book book) {
        String bookString = converter.asString(book);
        template.sendBody(bookString);
    }
}
