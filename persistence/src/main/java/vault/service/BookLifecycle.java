package vault.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vault.jdbi.BookDAO;

import javax.inject.Inject;
import java.util.UUID;

public class BookLifecycle {

    private static final Logger log = LoggerFactory.getLogger(BookLifecycle.class);

    private final BookDAO database;

    @Inject
    public BookLifecycle(BookDAO database) {
        this.database = database;
    }

    public String save(String book) {
        database.save(UUID.randomUUID().toString(), "test", "test", "test", "https://www.amazon.com/", "https://www.goodreads.com/");

        log.debug("Received a book: {}", book);

        return "placeholder id";
    }

    public String getAll() {
        log.debug("Received a request to get all books");

        return "Placeholder books";
    }

    public String getOne(String bookId) {
        log.debug("Received a request to get one book with ID={}", bookId);

        return "Placeholder book";
    }

    public void update(String book) {
        log.debug("Received a request to update a book: {}", book);
    }

    public void delete(String id) {
        log.debug("Received a request to delete a book: {}", id);
    }
}
