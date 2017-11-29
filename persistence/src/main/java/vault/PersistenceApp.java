package vault;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

public class PersistenceApp extends Application<MainConfiguration> {

    public static void main(String[] args) throws Exception {
        new PersistenceApp().run(args);
    }

    @Override
    public void run(MainConfiguration configuration, Environment environment) throws Exception {

    }
}
