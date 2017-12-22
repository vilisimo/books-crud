package vault;

import com.google.inject.Guice;
import com.google.inject.Injector;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.skife.jdbi.v2.DBI;
import vault.jms.EndpointConsumer;
import vault.modules.BookLifecycleModule;
import vault.modules.JmsModule;

public class PersistenceApp extends Application<MainConfiguration> {

    private static final String MIGRATIONS_FILE_NAME = "migrations.yaml";
    private static final String HSQLDB = "hsqldb";

    public static void main(String[] args) throws Exception {
        new PersistenceApp().run(args);
    }

    @Override
    public void initialize(Bootstrap<MainConfiguration> bootstrap) {
        bootstrap.addBundle(new MigrationsBundle<MainConfiguration>() {
            @Override
            public DataSourceFactory getDataSourceFactory(MainConfiguration configuration) {
                return configuration.getDataSourceFactory();
            }

            @Override
            public String getMigrationsFileName() {
                return MIGRATIONS_FILE_NAME;
            }
        });
    }

    @Override
    public void run(MainConfiguration configuration, Environment environment) {
        /* Datasource */
        final DBIFactory factory = new DBIFactory();
        final DBI jdbi = factory.build(environment, configuration.getDataSourceFactory(), HSQLDB);

        /* Resources */
        Injector resourceInjector = Guice.createInjector(new JmsModule(), new BookLifecycleModule(jdbi));
        EndpointConsumer consumer = resourceInjector.getInstance(EndpointConsumer.class);
        environment.jersey().register(consumer);
    }
}
