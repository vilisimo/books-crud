package vault.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import vault.jms.ActiveMqEndpointSupplier;
import vault.jms.ContextCreator;
import vault.jms.EndpointSupplier;
import vault.jms.JmsContextCreator;

import javax.jms.ConnectionFactory;

public class JmsModule extends AbstractModule {

    private static final String ACTIVE_MQ_ADDRESS = "tcp://localhost:61616";

    /** Configures {@link vault.jms.EndpointConsumer} and RouteBuilders in {@link vault.jms.routes} */
    @Override
    protected void configure() {
        bind(EndpointSupplier.class).to(ActiveMqEndpointSupplier.class);
    }

    @Provides
    @Singleton
    private ConnectionFactory getConnectionFactory() {
        return new ActiveMQConnectionFactory(ACTIVE_MQ_ADDRESS);
    }

    @Provides
    @Singleton
    private CamelContext getCamelContext(ConnectionFactory connectionFactory) {
        ContextCreator creator = new JmsContextCreator(connectionFactory);

        return creator.getContext();
    }
}
