package vault.jdbi

import org.skife.jdbi.v2.StatementContext
import spock.lang.Specification
import vault.model.Book

import java.sql.ResultSet

class BookMapperTest extends Specification {
    
    def resultSet = Mock(ResultSet.class)

    def context = Mock(StatementContext.class)

    def "ResultSet is mapped to a book"() {
        given: "a BookMapper instance"
            def bookMapper = new BookMapper()

        when: "mapping function is called"
            def book = bookMapper.map(0, resultSet, context)

        then: "ResultSet returns book values"
            resultSet.getString("id") >> "bookId"
            resultSet.getString("title") >> "title"
            resultSet.getString("author") >> "author"
            resultSet.getString("description") >> "description"
            resultSet.getString("amazon") >> "amazon"
            resultSet.getString("goodreads") >> "goodreads"

        and: "a book is created"
            book == new Book("bookId", "title", "author", "description", "amazon", "goodreads")
    }
}
