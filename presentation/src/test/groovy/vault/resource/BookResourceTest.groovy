package vault.resource

import io.dropwizard.testing.junit.ResourceTestRule
import org.junit.Rule
import spock.lang.Specification

import javax.ws.rs.client.Entity
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

class BookResourceTest extends Specification {

    @Rule
    ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new BookResource())
            .build()

    def "Posting a suggestion returns 201"() {
        when: "suggestion is posted"
            def book = "{\"description\":\"test\",\"reason\":\"historic\"}"
            def response = resources.client().target("/recommendations/books")
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .post(Entity.json(book))

        then: "201 is returned"
            response.getStatusInfo() == Response.Status.CREATED
    }

}
