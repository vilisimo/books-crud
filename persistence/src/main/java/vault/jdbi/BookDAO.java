package vault.jdbi;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;

public interface BookDAO {
    // TODO: try @BindBean
    @SqlUpdate("INSERT INTO book(id, title, author, description, amazon, goodreads) VALUES (:id, :title, :author, :description, :amazon, :goodreads)")
    void save(
            @Bind("id") String id,
            @Bind("title") String title,
            @Bind("author") String author,
            @Bind("description") String description,
            @Bind("amazon") String amazon,
            @Bind("goodreads") String goodreads);
}
