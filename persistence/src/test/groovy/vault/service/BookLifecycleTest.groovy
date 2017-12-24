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
            1 * database.save(book.id(), book.title(), book.author(), book.description(), book.amazon(), book.goodreads())
    }
}
