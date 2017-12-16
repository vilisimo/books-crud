package vault.health;

import com.codahale.metrics.health.HealthCheck;
import org.apache.camel.ProducerTemplate;
import vault.jms.EndpointSupplier;

import javax.inject.Inject;

public class PersistenceHealthCheck extends HealthCheck {

    private ProducerTemplate template;
    private EndpointSupplier endpoints;

    @Inject
    public PersistenceHealthCheck(ProducerTemplate template, EndpointSupplier endpoints) {
        this.template = template;
        this.endpoints = endpoints;
    }


    @Override
    protected Result check() {
        String up = (String) template.requestBody(endpoints.health(), "UP");

        if (up != null) {
            return Result.healthy();
        }

        return Result.unhealthy("Persistence layer is not responding");
    }
}
