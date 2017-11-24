package vault.service

import spock.lang.Shared
import spock.lang.Specification
import vault.model.Book

class BookLifecycleTest extends Specification {

    @Shared
    def datasource = new HashMap<String, Book>()

    @Shared
    def lifecycle = new BookLifecycle(datasource)

    def cleanup() {
        datasource.clear()
    }

    def "book is saved"() {
        given: "a book"
            def book = new Book(
                    "The Colour of Magic",
                    "Terry Pratchett",
                    "A book about a wizard",
                    "Good laugh",
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
                    "The Colour of Magic",
                    "Terry Pratchett",
                    "A book about a wizard",
                    "Good laugh",
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
                    "The Colour of Magic",
                    "Terry Pratchett",
                    "A book about a wizard",
                    "Good laugh",
                    "https://www.amazon.co.uk/Colour-Magic-Discworld-Novel-Novels/dp/0552166596",
                    "https://www.goodreads.com/book/show/34497.The_Color_of_Magic")
            def tlf = new Book(
                    "The Light Fantastic",
                    "Terry Pratchett",
                    "A book about a wizard",
                    "Good laugh",
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

    def "saved book can be retrieved from the lifecycle"() {
        given: "a book is saved"
            def book = new Book(
                    "The Colour of Magic",
                    "Terry Pratchett",
                    "A book about a wizard",
                    "Good laugh",
                    "https://www.amazon.co.uk/Colour-Magic-Discworld-Novel-Novels/dp/0552166596",
                    "https://www.goodreads.com/book/show/34497.The_Color_of_Magic")
            datasource.put("book", book)

        when: "the lifecycle is queried by the book's id"
            def retrievedBook = lifecycle.getOne("book")

        then: "a book is returned"
            book == retrievedBook
    }

    def "saved book can be updated"() {
        given: "a book that exists in a datasource"
            def book = new Book(
                    "The Colour of Magic",
                    "Terry Pratchett",
                    "A book about a wizard",
                    "Good laugh",
                    "https://www.amazon.co.uk/Colour-Magic-Discworld-Novel-Novels/dp/0552166596",
                    "https://www.goodreads.com/book/show/34497.The_Color_of_Magic")
            datasource.put("book", book)
        
        when: "user updates a book"
            def tlf = new Book(
                    "The Light Fantastic",
                    "Terry Pratchett",
                    "A book about a wizard",
                    "Good laugh",
                    "https://www.amazon.co.uk/Light-Fantastic-Discworld-Novel-Novels/dp/055216660X/",
                    "https://www.goodreads.com/book/show/34506.The_Light_Fantastic")
            datasource.put("book", tlf)

        then: "book is updated"
            def updated = datasource.get("book")
            datasource.size() == 1
            updated.title() == tlf.title()
    }
}
