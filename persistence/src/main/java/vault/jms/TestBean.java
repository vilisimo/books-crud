package vault.jms;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.Consume;
import vault.model.Book;

import java.io.IOException;
import java.util.Optional;

public class TestBean {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Consume(uri = "activemq:foo.bar")
    public String report(String stuff) {
        Optional<Book> book = Optional.empty();

        try {
            book = Optional.ofNullable(mapper.readValue(stuff, Book.class));
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Consuming book: " + book.map(Book::toString).orElse("nothing"));
        return "tested";
    }

    public String placeholder() {
        System.err.println("Placeholder");
        return "placeholder";
    }
}
