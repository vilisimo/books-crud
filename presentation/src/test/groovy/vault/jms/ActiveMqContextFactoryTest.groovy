package vault.jms

import spock.lang.Specification

class ActiveMqContextFactoryTest extends Specification {

    def "Creates a context"() {
        given: "a context factory"
            def contextFactory = new ActiveMqContextFactory()

        when: "a context is requested"
            def context = contextFactory.getContext()

        then: "a context is created"
            context
    }
}
