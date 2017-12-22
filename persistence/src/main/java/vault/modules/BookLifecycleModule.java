package vault.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import org.skife.jdbi.v2.DBI;
import vault.jdbi.BookDAO;

public class BookLifecycleModule extends AbstractModule {

    private final DBI jdbi;

    public BookLifecycleModule(DBI jdbi) {
        this.jdbi = jdbi;
    }

    @Override
    protected void configure() {

    }

    @Provides
    public BookDAO getBookLifecycle() {
        return jdbi.onDemand(BookDAO.class);
    }
}
