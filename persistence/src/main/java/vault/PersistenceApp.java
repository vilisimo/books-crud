package vault;

import com.google.inject.Guice;
import com.google.inject.Injector;
import io.dropwizard.Application;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Environment;
import org.skife.jdbi.v2.DBI;
import vault.jms.EndpointConsumer;
import vault.modules.JmsModule;

public class PersistenceApp extends Application<MainConfiguration> {

    public static void main(String[] args) throws Exception {
        new PersistenceApp().run(args);
    }

    @Override
    public void run(MainConfiguration configuration, Environment environment) {

        /* Resources */
        Injector resourceInjector = Guice.createInjector(new JmsModule());
        EndpointConsumer consumer = resourceInjector.getInstance(EndpointConsumer.class);
        environment.jersey().register(consumer);

        /* Datasource */
        final DBIFactory factory = new DBIFactory();
        final DBI jdbi = factory.build(environment, configuration.getDataSourceFactory(), "hsqldb");
    }
}
