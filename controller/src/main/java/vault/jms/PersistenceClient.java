package vault.jms;

import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.camel.ProducerTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vault.model.Book;
import vault.service.Converter;

import javax.inject.Inject;
import java.util.List;

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

    public String save(Book book) {
        String bookString = converter.asString(book);
        String bookId = (String) template.requestBody(endpoints.save(), bookString);

        log.debug("Sent a book to 'save' endpoint. Reply: {}", bookId);

        return bookId;
    }

    public List<Book> getAll() {
        String bookStrings = (String) template.requestBody(endpoints.getAll(), "placeholder");
        List<Book> books = converter.asObject(bookStrings, new TypeReference<List<Book>>() {});

        log.debug("Requested books from 'getAll' endpoint. Reply: {}", books);

        return books;
    }

    public Book getOne(String bookId) {
        String bookString = (String) template.requestBody(endpoints.getOne(), bookId);

        if (bookString.equals("null")) {
            // TODO: camel fails on null, it cannot be through it
            return null;
        }

        Book book = converter.asObject(bookString, new TypeReference<Book>() {});

        log.debug("Requested a book form 'getOne' endpoint. Reply: {}", book);

        return book;
    }

    public Boolean update(Book book) {
        String bookString = converter.asString(book);
        Boolean updated = (Boolean) template.requestBody(endpoints.update(), bookString);

        log.debug("Issued an update of a book request to 'update' endpoint");

        return updated;
    }

    public Boolean delete(String id) {
        Boolean deleted = (Boolean) template.requestBody(endpoints.delete(), id);

        log.debug("Issued a delete request to 'delete' endpoint");

        return deleted;
    }
}
