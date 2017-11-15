package presentation.vault.resource

import io.dropwizard.testing.junit.ResourceTestRule
import org.junit.ClassRule
import org.junit.Rule
import spock.lang.Specification

import javax.ws.rs.client.Entity
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

class SuggestionsResourceTest extends Specification {

    @Rule
    ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new SuggestionsResource())
            .build()

    def "Posting a suggestion returns 201"() {
        when: "suggestion is posted"
            def response = resources.client().target("/suggestions")
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .post(Entity.json("{\"description\": \"test\"}"))

        then: "201 is returned"
            response.getStatusInfo() == Response.Status.CREATED
    }

}
