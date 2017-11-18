package vault;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import vault.resource.BookResource;

public class PresentationApp extends Application<MainConfiguration> {

    public static void main(String[] args) throws Exception {
        new PresentationApp().run(args);
    }

    @Override
    public void run(MainConfiguration configuration, Environment environment) throws Exception {
        final BookResource suggestions = new BookResource();
        environment.jersey().register(suggestions);
    }
}
