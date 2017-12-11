package vault.service

import spock.lang.Specification
import vault.exception.BookNotFoundException
import vault.jms.PersistenceClient
import vault.model.Book

class BookLifecycleTest extends Specification {

    def datasource = new HashMap<String, Book>()
    def storage = Mock(PersistenceClient)
    def lifecycle = new BookLifecycle(datasource, storage)

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

        then: "an id of saved book is returned"
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

        then: "the book is assigned an id"
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

        datasource.put("first", tcom)
        datasource.put("second", tlf)

        when: "the lifecycle is queried for all books"
            def books = lifecycle.getAll()

        then: "all books are returned"
            !books.isEmpty()
            books.size() > 1
    }

    def "when no books are saved, empty collection is returned"() {
        given: "an empty datasource"
            assert datasource.isEmpty()

        when: "all books are queried"
            def books = lifecycle.getAll()

        then: "an empty collection is returned"
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
            datasource.put("book", book)

        when: "the lifecycle is queried by the book's id"
            def retrievedBook = lifecycle.getOne("book")

        then: "the book is returned"
            book == retrievedBook
    }

    def "non-existent book cannot be retrieved"() {
        given: "an empty datasource"
            assert datasource.isEmpty()

        when: "a book is requested"
            lifecycle.getOne("non-existent id")

        then: "an exception is thrown to indicate its absence"
            thrown(BookNotFoundException)
    }

    def "saved book can be updated"() {
        given: "a book that exists in a datasource"
            def book = new Book(
                    "Terry Pratchett",
                    "The Colour of Magic",
                    "A book about a wizard",
                    "https://www.amazon.co.uk/Colour-Magic-Discworld-Novel-Novels/dp/0552166596",
                    "https://www.goodreads.com/book/show/34497.The_Color_of_Magic")
            datasource.put("book", book)

        when: "user updates the book"
            def tlf = new Book(
                    "Terry Pratchett",
                    "The Light Fantastic",
                    "A book about a wizard",
                    "https://www.amazon.co.uk/Light-Fantastic-Discworld-Novel-Novels/dp/055216660X/",
                    "https://www.goodreads.com/book/show/34506.The_Light_Fantastic")
            def updated = lifecycle.update("book", tlf)

        then: "lifecycle reports that the book was updated"
            updated

        and: "book's details are updated"
            def updatedBook = datasource.get("book")
            datasource.size() == 1
            updatedBook.title() == tlf.title()
    }

    def "updating non-existent book creates it"() {
        given: "an empty datasource"
            assert datasource.isEmpty()

        when: "non-existent book is updated"
            def book = new Book(
                    "Terry Pratchett",
                    "The Colour of Magic",
                    "A book about a wizard",
                    "https://www.amazon.co.uk/Colour-Magic-Discworld-Novel-Novels/dp/0552166596",
                    "https://www.goodreads.com/book/show/34497.The_Color_of_Magic")
            def updated = lifecycle.update("book", book)

        then: "a book is saved"
            datasource.size() == 1

        and: "lifecycle reports that the book was not updated"
            !updated
    }

    def "delete removes book from the datasource"() {
        given: "a book exists in data source"
            def book = new Book(
                    "Terry Pratchett",
                    "The Colour of Magic",
                    "A book about a wizard",
                    "https://www.amazon.co.uk/Colour-Magic-Discworld-Novel-Novels/dp/0552166596",
                    "https://www.goodreads.com/book/show/34497.The_Color_of_Magic")
            datasource.put("book", book)

        when: "the book is deleted"
            datasource.remove("book")

        then: "datasource does not contain the book anymore"
            datasource.isEmpty()
    }

    def "non-existent book cannot be deleted"() {
        given: "an empty datasource"
            assert datasource.isEmpty()

        when: "a non-existent book is deleted"
            lifecycle.remove("non-existent id")

        then:
            thrown(BookNotFoundException)
    }
}
