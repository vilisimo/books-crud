package vault;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import vault.exception.BookNotFoundMapper;
import vault.resource.BookResource;

public class PresentationApp extends Application<MainConfiguration> {

    public static void main(String[] args) throws Exception {
        new PresentationApp().run(args);
    }

    @Override
    public void run(MainConfiguration configuration, Environment environment) throws Exception {
        /* Resources */
        final BookResource suggestions = new BookResource();
        environment.jersey().register(suggestions);

        /* Exception mappers */
        final BookNotFoundMapper bookMapper = new BookNotFoundMapper();
        environment.jersey().register(bookMapper);
    }
}
