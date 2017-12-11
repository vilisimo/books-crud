package vault.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import vault.jms.JmsContextCreator;
import vault.jms.ContextCreator;
import vault.jms.PersistenceClient;

import javax.jms.ConnectionFactory;

public class PersistenceClientModule extends AbstractModule {

    private static final String ACTIVE_MQ_ADDRESS = "tcp://localhost:61616";

    /** Configures {@link PersistenceClient} */
    @Override
    protected void configure() {}

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
