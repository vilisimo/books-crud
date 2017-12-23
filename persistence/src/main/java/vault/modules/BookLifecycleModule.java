package vault.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import org.skife.jdbi.v2.DBI;
import vault.jdbi.BookDAO;
import vault.service.Converter;
import vault.service.ObjectConverter;

public class BookLifecycleModule extends AbstractModule {

    private final DBI jdbi;

    public BookLifecycleModule(DBI jdbi) {
        this.jdbi = jdbi;
    }

    @Override
    protected void configure() {
        bind(Converter.class).to(ObjectConverter.class);
    }

    @Provides
    public BookDAO getBookLifecycle() {
        return jdbi.onDemand(BookDAO.class);
    }
}
