package vault.service;

import vault.model.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

public class BookLifecycle implements RecommendationLifecycle<Book> {

    // TODO: needs to have another dependency that deals with sending beans to persistence layer
    // TODO: that dependency should be injected with Guice
    // TODO: most likely UUID will be returned by persistence layer

    private Map<String, Book> datasource;

    public BookLifecycle(Map<String, Book> datasource) {
        requireNonNull(datasource);

        this.datasource = datasource;
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
        return new ArrayList<>(datasource.values());
    }

    @Override
    public Book getOne(String id) {
        return datasource.get(id);
    }

    @Override
    public void update(String id, Book book) {
        book.setId(id);
        datasource.put(id, book);
    }
}
