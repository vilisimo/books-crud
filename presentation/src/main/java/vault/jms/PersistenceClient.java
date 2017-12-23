package vault.jms;

import org.apache.camel.ProducerTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vault.model.Book;
import vault.service.Converter;

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

        log.debug("Sent a book to 'save' endpoint. Reply: {}", bookId);
    }

    public void getAll() {
        String books = (String) template.requestBody(endpoints.getAll(), "placeholder");

        log.debug("Requested books from 'getAll' endpoint. Reply: {}", books);
    }

    public void getOne(String bookId) {
        String book = (String) template.requestBody(endpoints.getOne(), bookId);

        log.debug("Requested a book form 'getOne' endpoint. Reply: {}", book);
    }

    public void update(Book book) {
        String bookString = converter.asString(book);
        template.sendBody(endpoints.update(), bookString);

        log.debug("Issued an update of a book request to 'update' endpoint");
    }

    public void delete(String id) {
        template.sendBody(endpoints.delete(), id);

        log.debug("Issued a delete request to 'delete' endpoint");
    }
}
