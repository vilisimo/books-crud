package vault;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import vault.exception.ResourceExceptionMapper;
import vault.resource.BookResource;
import vault.service.BookLifecycle;

import java.util.concurrent.ConcurrentHashMap;

public class PresentationApp extends Application<MainConfiguration> {

    public static void main(String[] args) throws Exception {
        new PresentationApp().run(args);
    }

    @Override
    public void run(MainConfiguration configuration, Environment environment) throws Exception {
        /* Resources */
        final BookResource suggestions = new BookResource(new BookLifecycle(new ConcurrentHashMap<>()));
        environment.jersey().register(suggestions);

        /* Exception mappers */
        final ResourceExceptionMapper bookMapper = new ResourceExceptionMapper();
        environment.jersey().register(bookMapper);
    }
}
