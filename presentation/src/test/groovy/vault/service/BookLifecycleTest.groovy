package vault.service

import spock.lang.Specification
import vault.exception.BookNotFoundException
import vault.jms.PersistenceClient
import vault.model.Book

class BookLifecycleTest extends Specification {

    def storage = Mock(PersistenceClient)
    def lifecycle = new BookLifecycle(storage)

    def "book is saved"() {
        given: "a book"
            def book = new Book(
                    "Terry Pratchett",
                    "The Colour of Magic",
                    "A book about a wizard",
                    "https://www.amazon.co.uk/Colour-Magic-Discworld-Novel-Novels/dp/0552166596",
                    "https://www.goodreads.com/book/show/34497.The_Color_of_Magic")

        when: "a book is saved"
            String id = lifecycle.save(book)

        then: "persistence client returns a book id"
            storage.save(book) >> UUID.randomUUID().toString()

        and: "an id of saved book is returned"
            id != null

        and: "id is a valid UUID"
            UUID.fromString(id)
    }

    def "saved book is assigned an id"() {
        given: "a book"
            def book = new Book(
                    "Terry Pratchett",
                    "The Colour of Magic",
                    "A book about a wizard",
                    "https://www.amazon.co.uk/Colour-Magic-Discworld-Novel-Novels/dp/0552166596",
                    "https://www.goodreads.com/book/show/34497.The_Color_of_Magic")

        when: "the book is saved"
            lifecycle.save(book)

        then: "persistence client returns an id"
            storage.save(book) >> UUID.randomUUID().toString()

        and: "the book is assigned an id"
            book.id() != null
    }

    def "all saved books can be retrieved from the lifecycle"() {
        given: "several books are saved"
            def tcom = new Book(
                    "Terry Pratchett",
                    "The Colour of Magic",
                    "A book about a wizard",
                    "https://www.amazon.co.uk/Colour-Magic-Discworld-Novel-Novels/dp/0552166596",
                    "https://www.goodreads.com/book/show/34497.The_Color_of_Magic")
            def tlf = new Book(
                    "Terry Pratchett",
                    "The Light Fantastic",
                    "A book about a wizard",
                    "https://www.amazon.co.uk/Light-Fantastic-Discworld-Novel-Novels/dp/055216660X/",
                    "https://www.goodreads.com/book/show/34506.The_Light_Fantastic")

        when: "the lifecycle is queried for all books"
            def books = lifecycle.getAll()

        then: "persistence client returns a list of books"
            storage.getAll() >> [tcom, tlf]

        and: "all books are returned"
            !books.isEmpty()
            books.size() > 1
    }

    def "when no books are saved, empty collection is returned"() {
        when: "all books are queried"
            def books = lifecycle.getAll()

        then: "persistence client returns an empty list of books"
            storage.getAll() >> []

        and: "an empty collection is returned"
            books.isEmpty()
    }

    def "saved book can be retrieved from the lifecycle"() {
        given: "a book is saved"
            def book = new Book(
                    "Terry Pratchett",
                    "The Colour of Magic",
                    "A book about a wizard",
                    "https://www.amazon.co.uk/Colour-Magic-Discworld-Novel-Novels/dp/0552166596",
                    "https://www.goodreads.com/book/show/34497.The_Color_of_Magic")

        when: "the lifecycle is queried by the book's id"
            def retrievedBook = lifecycle.getOne("book")

        then: "persistence client returns a book"
            storage.getOne("book") >> book

        and: "the book is returned"
            book == retrievedBook
    }

    def "non-existent book cannot be retrieved"() {
        when: "a book is requested"
            lifecycle.getOne("non-existent id")

        then: "persistence client reports that book does not exist"
            storage.getOne("non-existent id") >> null

        and: "an exception is thrown to indicate its absence"
            thrown(BookNotFoundException)
    }

    def "successful update is reported by the lifecycle"() {
        when: "user updates the book"
            def tlf = new Book(
                    "Terry Pratchett",
                    "The Light Fantastic",
                    "A book about a wizard",
                    "https://www.amazon.co.uk/Light-Fantastic-Discworld-Novel-Novels/dp/055216660X/",
                    "https://www.goodreads.com/book/show/34506.The_Light_Fantastic")
            def updated = lifecycle.update("book", tlf)

        then: "persistence layer reports that a book has been updated"
            storage.update(_ as Book) >> Boolean.TRUE

        and: "lifecycle reports that the book was updated"
            updated
    }

    def "updating non-existent book reports a books creation"() {
        when: "non-existent book is updated"
            def book = new Book(
                    "Terry Pratchett",
                    "The Colour of Magic",
                    "A book about a wizard",
                    "https://www.amazon.co.uk/Colour-Magic-Discworld-Novel-Novels/dp/0552166596",
                    "https://www.goodreads.com/book/show/34497.The_Color_of_Magic")
            def updated = lifecycle.update("book", book)

        then: "persistence client updates a book"
            storage.update(_ as Book) >> Boolean.FALSE

        and: "lifecycle reports that the book was created"
            !updated
    }

    def "delete removes a book"() {
        when: "a book is deleted"
            lifecycle.remove("book")

        then: "persistence layer is asked to delete a book"
            1 * storage.delete("book") >> true
    }

    def "non-existent book cannot be deleted"() {
        when: "a non-existent book is deleted"
            lifecycle.remove("non-existent id")

        then: "persistence client reports that the book has not been deleted"
            storage.delete("non-existent id") >> Boolean.FALSE

        and:
            thrown(BookNotFoundException)
    }
}
