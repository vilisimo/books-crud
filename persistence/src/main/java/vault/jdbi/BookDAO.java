package vault.jdbi;

import org.skife.jdbi.v2.sqlobject.SqlUpdate;

public interface BookDAO {
    @SqlUpdate("CREATE TABLE book (id VARCHAR (36) PRIMARY KEY, title VARCHAR (100), author VARCHAR (100), description VARCHAR (500), amazon VARCHAR(100), goodreads VARCHAR (100))")
    void createTable();
}
