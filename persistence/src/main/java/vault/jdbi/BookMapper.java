package vault.jdbi;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;
import vault.model.Book;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookMapper implements ResultSetMapper<Book> {

    @Override
    public Book map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        return new Book(
                r.getString("id"),
                r.getString("title"),
                r.getString("author"),
                r.getString("description"),
                r.getString("amazon"),
                r.getString("goodreads"));
    }
}
