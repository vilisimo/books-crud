package vault.service;

import vault.exception.BookNotFoundException;
import vault.jms.PersistenceClient;
import vault.model.Book;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.List;

import static java.util.Optional.ofNullable;

public class BookLifecycle implements Lifecycle<Book> {
    
    private final PersistenceClient storage;

    @Inject
    public BookLifecycle(PersistenceClient storage) {
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
