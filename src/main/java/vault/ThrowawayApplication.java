package vault;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import vault.health.TemplateHealthCheck;
import vault.resource.ThrowawayResource;

public class ThrowawayApplication extends Application<ThrowawayConfiguration> {

    public static void main(String[] args) throws Exception {
        new ThrowawayApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<ThrowawayConfiguration> bootstrap) {}

    @Override
    public void run(ThrowawayConfiguration configuration, Environment environment) {
        final ThrowawayResource resource = new ThrowawayResource(configuration.getTemplate(), configuration.getDefaultName());
        environment.jersey().register(resource);

        final TemplateHealthCheck healthCheck = new TemplateHealthCheck(configuration.getTemplate());
        environment.healthChecks().register("template", healthCheck);
    }
}
