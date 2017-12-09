package vault.jms;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.Consume;
import vault.model.Book;

import java.io.IOException;
import java.util.Optional;

public class TestBean {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Consume(uri = "activemq:foo.bar")
    public String report(String payload) {
        Optional<Book> book = Optional.empty();

        try {
            book = Optional.ofNullable(mapper.readValue(payload, Book.class));
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Consuming book: " + book.map(Book::toString).orElse("nothing"));
        return "successful";
    }

    @Consume(uri = "activemq:foo.bar.simple")
    public void consumeSimple(String payload) {
        System.err.println("Consuming payload: " + payload);
    }
}
