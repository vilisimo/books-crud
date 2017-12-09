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

    static final String booksEndpoint = "/books/"

    BookLifecycle lifecycle = Stub()

    @Rule
    ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new BookResource(lifecycle))
            .build()

    def "posting JSON of a valid book creates a book"() {
        given: "a book"
            def book = new Book(
                    "J.R.R. Tolkien",
                    "The Hobbit",
                    "A book about a hobbit's adventures",
                    "https://www.amazon.com/Hobbit-J-R-Tolkien/dp/054792822X",
                    "https://www.goodreads.com/book/show/5907.The_Hobbit")

        when: "book is posted"
            def response = resources.client().target(booksEndpoint)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .post(Entity.json(book))

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
            def response = resources.client().target(booksEndpoint)
                    .request(MediaType.APPLICATION_JSON)
                    .get()

        then: "lifecycle returns a list of books"
            lifecycle.getAll() >> [new Book(
                    "J.R.R. Tolkien",
                    "The Hobbit",
                    "A book about a hobbit's adventures",
                    "https://www.amazon.com/Hobbit-J-R-Tolkien/dp/054792822X",
                    "https://www.goodreads.com/book/show/5907.The_Hobbit")]

        and: "resource returns status OK"
            response.getStatusInfo() == Response.Status.OK

        and: "resource returns a list of all books"
            response.hasEntity()
    }

    def "requesting all books includes X-Total-Count header"() {
        when: "a list of all books is requested"
            def response = resources.client().target(booksEndpoint)
                    .request(MediaType.APPLICATION_JSON)
                    .get()

        then: "lifecycle returns a list of 2 books"
            lifecycle.getAll() >> [Mock(Book.class), Mock(Book.class)]

        and: "resource returns status OK"
            response.getStatusInfo() == Response.Status.OK

        and: "a header specifying total size is included"
            response.getHeaders().containsKey("X-Total-Count")
            def totalCount = response.getHeaders().get("X-Total-Count")
            totalCount.get(0) == "2"
    }

    def "requesting a book by id returns a book"() {
        when: "a book is requested by its id"
            def response = resources.client().target(booksEndpoint + UUID.randomUUID().toString())
                    .request(MediaType.APPLICATION_JSON)
                    .get()

        then: "lifecycle returns a corresponding book"
            lifecycle.getOne(_ as String) >> new Book(
                    "J.R.R. Tolkien",
                    "The Hobbit",
                    "A book about a hobbit's adventures",
                    "https://www.amazon.com/Hobbit-J-R-Tolkien/dp/054792822X",
                    "https://www.goodreads.com/book/show/5907.The_Hobbit")

        and: "resource returns status OK"
            response.getStatusInfo() == Response.Status.OK

        and: "resource returns requested book"
            response.hasEntity()
    }

    def "updating a non-existent book creates it"() {
        when: "a non-existent book is updated"
            def book = new Book(
                    "J.R.R. Tolkien",
                    "The Hobbit",
                    "A book about a hobbit's adventures",
                    "https://www.amazon.com/Hobbit-J-R-Tolkien/dp/054792822X",
                    "https://www.goodreads.com/book/show/5907.The_Hobbit")
            def response = resources.client().target(booksEndpoint + UUID.randomUUID().toString())
                    .request(MediaType.APPLICATION_JSON)
                    .put(Entity.json(book))

        then: "lifecycle indicates that the entity was created"
            lifecycle.update(_ as String, _ as Book) >> false

        and: "resource returns status CREATED"
            response.getStatusInfo() == Response.Status.CREATED
    }

    def "updating an existing book updates it"() {
        when: "an existing book is updated"
            def book = new Book(
                    "J.R.R. Tolkien",
                    "The Hobbit",
                    "A book about a hobbit's adventures",
                    "https://www.amazon.com/Hobbit-J-R-Tolkien/dp/054792822X",
                    "https://www.goodreads.com/book/show/5907.The_Hobbit")
            def response = resources.client().target(booksEndpoint + UUID.randomUUID().toString())
                        .request(MediaType.APPLICATION_JSON)
                        .put(Entity.json(book))

        then: "lifecycle indicates that the entity was updated"
            lifecycle.update(_ as String, _ as Book) >> true

        and: "resource returns status NO CONTENT"
            response.getStatusInfo() == Response.Status.NO_CONTENT
    }

    def "deleting a book removes it"() {
        when: "a book is deleted"
            def response = resources.client().target(booksEndpoint + UUID.randomUUID().toString())
                        .request(MediaType.APPLICATION_JSON)
                        .delete()

        then: "resource returns status NO CONTENT"
            response.getStatusInfo() == Response.Status.NO_CONTENT
    }
}
