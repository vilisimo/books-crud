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

    def "posting a valid book JSON creates a book"() {
        given: "a valid book JSON"
            def book = "{" +
                    "\"author\": \"J.R.R. Tolkien\"," +
                    "\"title\": \"The Hobbit\"," +
                    "\"description\": \"A book about a hobbit's adventures\"," +
                    "\"reason\": \"It is a light-hearted, easy to read and yet immersive book\"," +
                    "\"amazon\": \"https://www.amazon.com/Hobbit-J-R-Tolkien/dp/054792822X\"," +
                    "\"goodreads\": \"https://www.goodreads.com/book/show/5907.The_Hobbit\"}"

        when: "book is posted"
            def response = resources.client().target("/recommendations/books")
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .post(Entity.json(book))

        then: "status code is 201"
            response.getStatusInfo() == Response.Status.CREATED

        then: "a created book is returned"
            response.hasEntity()

        then: "a path to a new book is returned"
            def uuid = response.location.toString().split("/").last()
            uuid.length() == 36
    }

    def "requesting all books returns the books"() {
        when: "a list of all books is requested"
            def response = resources.client().target("/recommendations/books")
                    .request(MediaType.APPLICATION_JSON)
                    .get()

        then: "status code 200 is returned"
            response.getStatusInfo() == Response.Status.OK
            response.hasEntity()

        then: "queried books are returned"
            response.hasEntity()
    }
}
