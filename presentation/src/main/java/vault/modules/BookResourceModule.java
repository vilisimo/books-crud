package vault.modules;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import vault.model.Book;
import vault.service.BookLifecycle;
import vault.service.RecommendationLifecycle;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BookResourceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(new TypeLiteral<Map<String, Book>>() {}).toInstance(new ConcurrentHashMap<>());
        bind(new TypeLiteral<RecommendationLifecycle<Book>>() {}).to(BookLifecycle.class);
    }
}
