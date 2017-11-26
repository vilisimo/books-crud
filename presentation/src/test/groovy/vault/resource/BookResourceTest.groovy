package vault.resource

import io.dropwizard.testing.junit.ResourceTestRule
import org.junit.Rule
import spock.lang.Specification
import vault.model.Book
import vault.service.BookLifecycle

import javax.ws.rs.client.Entity
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

class BookResourceTest extends Specification {

    BookLifecycle lifecycle = Stub()

    @Rule
    ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new BookResource(lifecycle))
            .build()

    def "posting JSON of a valid book creates a book"() {
        given: "a valid book JSON"
            def bookJson = "{" +
                    "\"author\": \"J.R.R. Tolkien\"," +
                    "\"title\": \"The Hobbit\"," +
                    "\"description\": \"A book about a hobbit's adventures\"," +
                    "\"reason\": \"It is a light-hearted, easy to read and yet immersive book\"," +
                    "\"amazon\": \"https://www.amazon.com/Hobbit-J-R-Tolkien/dp/054792822X\"," +
                    "\"goodreads\": \"https://www.goodreads.com/book/show/5907.The_Hobbit\"}"

        when: "book is posted"
            def response = resources.client().target("/recommendations/books")
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .post(Entity.json(bookJson))

        then: "lifecycle returns uuid"
            lifecycle.save(_ as Book) >> UUID.randomUUID().toString()

        and: "resource returns status CREATED"
            response.getStatusInfo() == Response.Status.CREATED

        and: "resource returns the book that was created"
            response.hasEntity()

        and: "resource returns a path to a newly created book"
            def uuid = response.location.toString().split("/").last()
            uuid.length() == 36
    }

    def "requesting all books returns a list of books"() {
        when: "a list of all books is requested"
            def response = resources.client().target("/recommendations/books")
                    .request(MediaType.APPLICATION_JSON)
                    .get()

        then: "lifecycle returns a list of books"
            lifecycle.getAll() >> [new Book(
                    "J.R.R. Tolkien",
                    "The Hobbit",
                    "A book about a hobbit's adventures",
                    "It is a light-hearted, easy to read and yet immersive book",
                    "https://www.amazon.com/Hobbit-J-R-Tolkien/dp/054792822X",
                    "https://www.goodreads.com/book/show/5907.The_Hobbit")]

        and: "resource returns status OK"
            response.getStatusInfo() == Response.Status.OK

        and: "resource returns a list of all books"
            response.hasEntity()
    }
}
