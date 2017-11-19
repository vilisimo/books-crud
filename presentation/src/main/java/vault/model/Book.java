package vault.model;

public class Book extends Recommendation {

    private Book() {}

    public Book(String description, String reason) {
        super(description, reason);
    }

    @Override
    public String toString() {
        return "Book[id=" + id() + "]";
    }
}
