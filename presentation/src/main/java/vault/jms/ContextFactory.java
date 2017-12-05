package vault.jms;

import org.apache.camel.CamelContext;

public interface ContextFactory {
    CamelContext getContext();
}
