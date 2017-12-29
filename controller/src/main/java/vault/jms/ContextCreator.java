package vault.jms;

import org.apache.camel.CamelContext;

public interface ContextCreator {
    CamelContext getContext();
}
