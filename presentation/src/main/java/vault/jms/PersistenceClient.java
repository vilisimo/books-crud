package vault.jms;

import org.apache.camel.ProducerTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vault.converter.Converter;
import vault.model.Book;

import javax.inject.Inject;

public class PersistenceClient {

    private static final Logger log = LoggerFactory.getLogger(PersistenceClient.class);

    private final ProducerTemplate template;

    private final Converter converter;

    private final EndpointSupplier endpoints;

    @Inject
    public PersistenceClient(ProducerTemplate template, EndpointSupplier endpoints, Converter converter) {
        this.converter = converter;
        this.endpoints = endpoints;
        this.template = template;
    }

    public void save(Book book) {
        String bookString = converter.asString(book);
        String bookId = (String) template.requestBody(endpoints.save(), bookString);

        log.info("Sent a book to 'save' endpoint. Reply: {}", bookId);
    }

    public void getAll() {
        String books = (String) template.requestBody(endpoints.getAll(), "placeholder");

        log.info("Requested books from 'getAll' endpoint. Reply: {}", books);
    }

    public void getOne(String bookId) {
        String book = (String) template.requestBody(endpoints.getOne(), bookId);

        log.info("Requested a book form 'getOne' endpoint. Reply: {}", book);
    }

    public void update(Book book) {
        String bookString = converter.asString(book);
        template.sendBody(endpoints.update(), bookString);

        log.info("Issued an update of a book request to 'update' endpoint");
    }

    public void delete(String id) {
        template.sendBody(endpoints.delete(), id);

        log.info("Issued a delete request to 'delete' endpoint");
    }
}
