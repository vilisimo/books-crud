package vault.model;

public class Book extends Recommendation {

    public Book() {}

    public Book(String description, String reason) {
        super(description, reason);
    }

    @Override
    public String toString() {
        return "Book{" +
                "description='" + description + '\'' +
                ", reason='" + reason + '\'' +
                '}';
    }
}
