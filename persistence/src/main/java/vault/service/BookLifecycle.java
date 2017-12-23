package vault.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vault.jdbi.BookDAO;
import vault.model.Book;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

public class BookLifecycle {

    private static final Logger log = LoggerFactory.getLogger(BookLifecycle.class);

    private final BookDAO database;

    private final Converter converter;

    @Inject
    public BookLifecycle(BookDAO database, Converter converter) {
        this.database = database;
        this.converter = converter;
    }

    public String save(String book) {
        database.save(UUID.randomUUID().toString(), "test", "test", "test", "https://www.amazon.com/", "https://www.goodreads.com/");

        log.info("Saved a book: {}", book);

        return "placeholder id";
    }

    public String getAll() {
        List<Book> books = database.getAll();

        log.info("Retrieved all (n={}) books", books.size());

        return "Placeholder books";
    }

    public String getOne(String bookId) {
        Book book = database.findOne(bookId);

        log.info("Retrieved a book [{}]", book);

        return "Placeholder book";
    }

    public void update(String book) {
        database.update("3085d7d9-88a8-4ce4-a536-2e9efb0d2788", "secondTest", "secondTest", "secondTest", "secondTest", "secondTest");

        log.info("Updated a book: {}", book);
    }

    public void delete(String id) {
        database.delete(id);

        log.info("Deleted a book: {}", id);
    }
}
