package vault.service;

import vault.model.Book;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class BookLifecycle implements RecommendationLifecycle<Book> {

    // TODO: needs to have another dependency that deals with sending beans to persistence layer
    // TODO: that dependency should be injected with Guice
    // TODO: most likely UUID will be returned by persistence layer

    private static final Map<String, Book> datasource = new ConcurrentHashMap<>();

    @Override
    public String save(Book book) {
        String uuid = UUID.randomUUID().toString();
        book.setId(uuid);

        datasource.put(uuid, book);

        return uuid;
    }
}
