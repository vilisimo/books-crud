package vault;

import com.google.inject.Guice;
import com.google.inject.Injector;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import vault.exception.ResourceExceptionMapper;
import vault.modules.BookResourceModule;
import vault.modules.JmsModule;
import vault.resource.BookResource;

public class PresentationApp extends Application<MainConfiguration> {

    public static void main(String[] args) throws Exception {
        new PresentationApp().run(args);
    }

    @Override
    public void run(MainConfiguration configuration, Environment environment) {

        /* Resources */
        Injector resourceInjector = Guice.createInjector(new BookResourceModule(), new JmsModule());
        BookResource resource = resourceInjector.getInstance(BookResource.class);
        environment.jersey().register(resource);

        /* Exception mappers */
        final ResourceExceptionMapper bookMapper = new ResourceExceptionMapper();
        environment.jersey().register(bookMapper);
    }
}
