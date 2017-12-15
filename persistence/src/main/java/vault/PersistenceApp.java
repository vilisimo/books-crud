package vault;

import com.google.inject.Guice;
import com.google.inject.Injector;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
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
    }
}
