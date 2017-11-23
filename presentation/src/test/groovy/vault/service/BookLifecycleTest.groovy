package vault.service

import spock.lang.Shared
import spock.lang.Specification
import vault.model.Book

class BookLifecycleTest extends Specification {

    @Shared
    def datasource = new HashMap<String, Book>()

    @Shared
    def lifecycle = new BookLifecycle(datasource)

    def "book is saved"() {
        given: "a book"
            def book = new Book(
                    "The Colour of Magic ",
                    "Terry Pratchett",
                    "A book about guards",
                    "Good laugh",
                    "https://www.amazon.co.uk/Guards-Discworld-Novel-Novels/dp/0552166669",
                    "https://www.goodreads.com/book/show/64216.Guards_Guards_")

        when: "a book is saved"
            String id = lifecycle.save(book)

        then: "an id of saved book is returned"
            id != null

        and: "id is a valid UUID"
            UUID.fromString(id)
    }

    def "saved book is assigned an id"() {
        given: "a book"
            def book = new Book(
                    "The Colour of Magic ",
                    "Terry Pratchett",
                    "A book about guards",
                    "Good laugh",
                    "https://www.amazon.co.uk/Guards-Discworld-Novel-Novels/dp/0552166669",
                    "https://www.goodreads.com/book/show/64216.Guards_Guards_")

        when: "the book is saved"
            lifecycle.save(book)

        then: "the book is assigned an id"
            book.id() != null
    }

    def "saved book can be retrieved from the lifecycle"() {
        given: "a book is saved"
            def book = new Book(
                    "The Colour of Magic ",
                    "Terry Pratchett",
                    "A book about guards",
                    "Good laugh",
                    "https://www.amazon.co.uk/Guards-Discworld-Novel-Novels/dp/0552166669",
                    "https://www.goodreads.com/book/show/64216.Guards_Guards_")
            datasource.put("book", book)

        when: "the persistence layer is queried by the book's id"
            def retrievedBook = lifecycle.getOne("book")

        then: "a book is returned"
            book == retrievedBook
    }
}
