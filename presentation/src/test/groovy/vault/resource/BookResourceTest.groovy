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

    def "Posting a book returns 201"() {
        when: "book is posted"
            def book = "{" +
                    "\"author\": \"J.R.R. Tolkien\"," +
                    "\"title\": \"The Hobbit\"," +
                    "\"description\": \"A book about a hobbit's adventures\"," +
                    "\"reason\": \"It is a light-hearted, easy to read and yet immersive book\"," +
                    "\"amazon\": \"https://www.amazon.com/Hobbit-J-R-Tolkien/dp/054792822X\"," +
                    "\"goodreads\": \"https://www.goodreads.com/book/show/5907.The_Hobbit\"}"
            def response = resources.client().target("/recommendations/books")
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .post(Entity.json(book))

        then: "201 is returned"
            response.getStatusInfo() == Response.Status.CREATED
    }

}
