package vault.jms;

import org.apache.camel.ProducerTemplate;
import vault.converter.Converter;
import vault.model.Book;

import javax.inject.Inject;

public class PersistenceClient {

    private static final String TEST_ENDPOINT = "activemq:foo.bar?exchangePattern=InOut";
    private static final String TEST_SIMPLE_ENDPOINT = "activemq:foo.bar.simple";

    private final ProducerTemplate template;

    private final Converter converter;

    private final EndpointSupplier endpoints;

    @Inject
    public PersistenceClient(ProducerTemplate template, EndpointSupplier endpoints, Converter converter) {
        this.converter = converter;
        this.endpoints = endpoints;
        this.template = template;
    }

    public void send() {
        template.sendBody(TEST_SIMPLE_ENDPOINT, "Test payload");
        System.err.println("Poked persistence layer");
    }

    public void save(Book book) {
        String bookString = converter.asString(book);
        template.sendBody(TEST_ENDPOINT, bookString);
    }
}
