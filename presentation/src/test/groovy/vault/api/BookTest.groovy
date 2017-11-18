package vault.api

import spock.lang.Specification
import vault.model.Book

class BookTest extends Specification {

    def "reference test: setter sets id"() {
        given:
            def suggestion = new Book()
        when:
            suggestion.setId("id")
        then:
            suggestion.getId() == "id"
    }
}
