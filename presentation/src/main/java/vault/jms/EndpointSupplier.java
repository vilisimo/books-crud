package vault.jms;

import org.apache.camel.Endpoint;

public interface EndpointSupplier {
    Endpoint save();
    Endpoint getAll();
    Endpoint getOne();
    Endpoint update();
    Endpoint remove();
}
