package vault.service;

import vault.exception.BookNotFoundException;
import vault.jms.PersistenceClient;
import vault.model.Book;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
        String uuid = UUID.randomUUID().toString();
        book.setId(uuid);

        storage.save(book);
        datasource.put(uuid, book);

        return uuid;
    }

    @Override
    public List<Book> getAll() {
        storage.getAll();

        return new ArrayList<>(datasource.values());
    }

    @Override
    public Book getOne(String id) {
        storage.getOne(id);

        return ofNullable(datasource.get(id))
                .orElseThrow(() -> new BookNotFoundException(Response.Status.NOT_FOUND, id));
    }

    @Override
    public boolean update(String id, Book book) {
        book.setId(id);

        storage.update(book);

        return datasource.put(id, book) != null;
    }

    @Override
    public void remove(String id) {
        storage.delete(id);

        if (datasource.remove(id) == null) {
            throw new BookNotFoundException(Response.Status.NOT_FOUND, id);
        }
    }
}
