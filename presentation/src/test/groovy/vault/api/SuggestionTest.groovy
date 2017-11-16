package vault.api

import spock.lang.Specification

class SuggestionTest extends Specification {

    def "reference test: setter sets id"() {
        given:
            def suggestion = new Suggestion()
        when:
            suggestion.setId("id")
        then:
            suggestion.getId() == "id"
    }
}
