package vault.jms

import org.apache.activemq.ActiveMQConnectionFactory
import spock.lang.Specification

class JmsContextCreatorTest extends Specification {

    def "Creates a context"() {
        given: "a context factory"
            def contextFactory = new JmsContextCreator(Mock(ActiveMQConnectionFactory))

        when: "a context is requested"
            def context = contextFactory.getContext()

        then: "a context is created"
            context
    }
}
