package vault.service;

import com.fasterxml.jackson.core.type.TypeReference;
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

    public String save(String bookString) {
        Book book = converter.asObject(bookString, new TypeReference<Book>() {});
        String bookId = UUID.randomUUID().toString();
        book.setId(bookId);

        database.save(book);

        log.debug("Saved a book: {}", book);

        return bookId;
    }

    public String getAll() {
        List<Book> books = database.getAll();
        String bookStrings = converter.asString(books);

        log.debug("Retrieved all (n={}) books", books.size());

        return bookStrings;
    }

    public String getOne(String bookId) {
        Book book = database.findOne(bookId);

        log.debug("Retrieved a book [{}]", book);

        if (book == null) {
            return "null";
        } else {
            return converter.asString(book);
        }
    }

    public Boolean update(String bookString) {
        Book book = converter.asObject(bookString, new TypeReference<Book>() {});
        boolean updated = updateOrInsert(book);

        log.debug("Updated a book: {}", book);

        return updated;
    }

    private boolean updateOrInsert(Book book) {
        Book instance = database.findOne(book.getId());

        if (instance != null) {
            database.update(book);
            return true;
        } else {
            database.save(book);
            return false;
        }
    }

    public boolean delete(String id) {
        int deleted = database.delete(id);

        log.debug("Deleted a book: {}", id);

        return deleted == 1;
    }
}
