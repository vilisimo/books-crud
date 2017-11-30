package vault.service;

import vault.exception.BookNotFoundException;
import vault.jms.TestJms;
import vault.model.Book;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static java.util.Optional.ofNullable;

public class BookLifecycle implements Lifecycle<Book> {

    // TODO: needs to have another dependency that deals with sending beans to persistence layer
    // TODO: most likely UUID will be returned by persistence layer

    private final Map<String, Book> datasource;
    private final TestJms testJms;

    @Inject
    public BookLifecycle(Map<String, Book> datasource) {
        this.datasource = datasource;
        this.testJms = new TestJms();
    }

    @Override
    public String save(Book book) {
        String uuid = UUID.randomUUID().toString();
        book.setId(uuid);

        datasource.put(uuid, book);

        return uuid;
    }

    @Override
    public List<Book> getAll() {
        testJms.sendStuff();
        return new ArrayList<>(datasource.values());
    }

    @Override
    public Book getOne(String id) {
        return ofNullable(datasource.get(id))
                .orElseThrow(() -> new BookNotFoundException(Response.Status.NOT_FOUND, id));
    }

    @Override
    public boolean update(String id, Book book) {
        book.setId(id);

        return datasource.put(id, book) != null;
    }

    @Override
    public void remove(String id) {
        if (datasource.remove(id) == null) {
            throw new BookNotFoundException(Response.Status.NOT_FOUND, id);
        }
    }
}
