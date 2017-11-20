package vault.resource

import spock.lang.Specification

class UriPathBuilderTest extends Specification {

    def "requires UriInfo to be non null"() {
        when: "null UriInfo is supplied"
            UriPathBuilder.buildUri(null, "placeholder")
        then: "an exception is thrown"
            thrown(NullPointerException)
    }
}
