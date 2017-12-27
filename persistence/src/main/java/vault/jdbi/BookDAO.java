package vault.jdbi;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import vault.model.Book;

import java.util.List;

@RegisterMapper(BookMapper.class)
public interface BookDAO {

    @SqlUpdate("INSERT INTO book(id, title, author, description, amazon, goodreads) " +
               "VALUES (:id, :title, :author, :description, :amazon, :goodreads)")
    void save(@BindBean Book book);

    @SqlQuery("SELECT * FROM book")
    List<Book> getAll();

    @SqlQuery("SELECT id, title, author, description, amazon, goodreads FROM book WHERE id = :id")
    Book findOne(@Bind("id") String bookId);

    @SqlUpdate("UPDATE book SET title = :title, author = :author, description = :description, amazon = :amazon, goodreads = :goodreads " +
               "WHERE id = :id")
    void update(@BindBean Book book);

    @SqlUpdate("DELETE FROM book WHERE id = :id")
    int delete(@Bind("id") String id);
}
