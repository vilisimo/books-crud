package vault.service;

import com.fasterxml.jackson.core.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vault.jdbi.BookDAO;
import vault.model.Book;

import javax.inject.Inject;
import java.util.List;

public class BookLifecycle {

    private static final Logger log = LoggerFactory.getLogger(BookLifecycle.class);

    private final BookDAO database;

    private final Converter converter;

    @Inject
    public BookLifecycle(BookDAO database, Converter converter) {
        this.database = database;
        this.converter = converter;
    }

    public String save(String bookString) {
        Book book = converter.asObject(bookString, new TypeReference<Book>() {});

        database.save(book.id(), book.title(), book.author(), book.description(), book.amazon(), book.goodreads());

        log.info("Saved a book: {}", book);

        return book.id();
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
