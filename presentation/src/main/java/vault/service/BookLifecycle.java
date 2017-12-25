package vault.service;

import vault.exception.BookNotFoundException;
import vault.jms.PersistenceClient;
import vault.model.Book;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

import static java.util.Optional.ofNullable;

public class BookLifecycle implements Lifecycle<Book> {

    // TODO: most likely UUID will be returned by persistence layer

    private final Map<String, Book> datasource;
    private final PersistenceClient storage;

    @Inject
    public BookLifecycle(Map<String, Book> datasource, PersistenceClient storage) {
        this.datasource = datasource;
        this.storage = storage;
    }

    @Override
    public String save(Book book) {
        String id = storage.save(book);
        book.setId(id);

        return id;
    }

    @Override
    public List<Book> getAll() {
        return storage.getAll();
    }

    @Override
    public Book getOne(String id) {
        Book book = storage.getOne(id);

        return ofNullable(book)
                .orElseThrow(() -> new BookNotFoundException(Response.Status.NOT_FOUND, id));
    }

    @Override
    public boolean update(String id, Book book) {
        book.setId(id);

        return storage.update(book);
    }

    @Override
    public void remove(String id) {
        Boolean deleted = storage.delete(id);

        if (!deleted) {
            throw new BookNotFoundException(Response.Status.NOT_FOUND, id);
        }
    }
}
