package vault.model;

public class Book extends Recommendation {

    private String id;

    public Book() {}

    public Book(String description, String reason) {
        super(description, reason);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", reason='" + reason + '\'' +
                '}';
    }
}
