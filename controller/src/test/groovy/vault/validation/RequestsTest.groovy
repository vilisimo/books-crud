package vault.validation

import spock.lang.Specification
import vault.exception.NullBodyException

import static vault.validation.Requests.requireNonNullBody

class RequestsTest extends Specification {
    
    def "validates that a body is not null"() {
        given: "a null body"
            def body = null

        when: "the body is validated"
            requireNonNullBody(body)

        then: "an exception is thrown to indicate a null body"
            thrown(NullBodyException.class)
    }
    
    def "lets the non-null body through"() {
        given: "a valid body"
           def body = "something"

        when: "the body is validated"
            requireNonNullBody(body)

        then:
            noExceptionThrown()
    }
}
