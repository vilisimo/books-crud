package vault.service

import com.fasterxml.jackson.core.type.TypeReference
import spock.lang.Specification
import vault.jdbi.BookDAO
import vault.model.Book

class BookLifecycleTest extends Specification {

    def database = Mock(BookDAO.class)
    def converter = Mock(Converter.class)
    def lifecycle = new BookLifecycle(database, converter)

    def "Save passes object innards towards database"() {
        def book = new Book("Lord of the Rings", "J.R.R. Tolkien", "A book about an evil ring", null, null)

        when: "an object is saved"
            lifecycle.save("placeholder")

        then: "converter returns a book"
            converter.asObject(_ as String, _ as TypeReference) >> book

        and: "database is passed a book instance"
            1 * database.save(_ as Book)
    }

    def "Get all returns a list of books"() {
        when: "a list of books is requested"
            def books = lifecycle.getAll()

        then: "database returns a list of books"
            database.getAll() >> ["list of books"]

        and: "converter returns a list of books"
            converter.asString(_ as Object) >> "list of books"

        and: "a list of books is returned"
            books == "list of books"
    }

    def "Get one returns a book"() {
        when: "a book is requested"
            def book = lifecycle.getOne("14a66898-22f9-4db3-ba7c-ba4b74788c9f")

        then: "database returns a book"
            database.findOne(_ as String) >> new Book("title", "author", "description", "amazon", "goodreads")

        and: "converter converts a book into a string"
            converter.asString(_ as Object) >> "a string representing a book"

        and: "a book is returned"
            book == "a string representing a book"
    }

    def "Get one returns null string when book is not found"() {
        when: "a book is requested"
            def book = lifecycle.getOne("14a66898-22f9-4db3-ba7c-ba4b74788c9f")

        then: "database cannot find a book and returns a null"
            database.findOne(_ as String) >> null

        and: "converter is not called"
            0 * converter.asString(_ as Object)

        and: "a null string is returned"
            book == "null"
    }

    def "Update updates a book in a database when it exists"() {
        def book = new Book("title", "author", "description", "amazon", "goodreads")

        when: "a book is updated"
            lifecycle.update("a book")

        then: "converter returns a converted book"
            converter.asObject(_ as String,_ as TypeReference) >> book

        and: "database confirms the existence of a book"
            database.findOne(_ as String) >> book

        and: "database updates a book"
            1 * database.update(_ as Book)
    }

    def "Creates a book in a database when it does not exist"() {
        def book = new Book("title", "author", "description", "amazon", "goodreads")

        when: "a book is updated"
            lifecycle.update("a book")

        then: "converter returns a converted book"
            converter.asObject(_ as String,_ as TypeReference) >> book

        and: "database confirms the existence of a book"
            database.findOne(_ as String) >> null

        and: "database updates a book"
            1 * database.save(_ as Book)
    }

    def "Delete removes a book from a database"() {
        when: "a book is deleted"
            def deleted = lifecycle.delete("bookId")

        then: "database deletes a book"
            1 * database.delete("bookId") >> 1

        and: "database reports it as deleted"
            deleted
    }
}
