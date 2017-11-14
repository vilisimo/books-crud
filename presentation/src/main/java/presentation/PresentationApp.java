package presentation;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import presentation.vault.resource.SuggestionsResource;

public class PresentationApp extends Application<MainConfiguration> {

    public static void main(String[] args) throws Exception {
        new PresentationApp().run(args);
    }

    @Override
    public void run(MainConfiguration configuration, Environment environment) throws Exception {
        final SuggestionsResource suggestions = new SuggestionsResource();
        environment.jersey().register(suggestions);
    }
}
