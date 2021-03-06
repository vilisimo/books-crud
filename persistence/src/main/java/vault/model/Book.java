package vault.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

import java.util.Objects;
import java.util.UUID;

public class Book {

    private String id;

    @NotEmpty
    private String title;

    @NotEmpty
    private String author;

    @NotEmpty
    private String description;

    @URL
    private String amazon;

    @URL
    private String goodreads;

    private Book() {}

    public Book(String title, String author, String description, String amazon, String goodreads) {
        this(UUID.randomUUID().toString(), title, author, description, amazon, goodreads);
    }

    public Book(String id, String title, String author, String description, String amazon, String goodreads) {
        this.id = id;
        this.amazon = amazon;
        this.goodreads = goodreads;
        this.title = title;
        this.author = author;
        this.description = description;
    }

    @JsonProperty
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty
    public String getTitle() {
        return title;
    }

    @JsonProperty
    public String getAuthor() {
        return author;
    }

    @JsonProperty
    public String getAmazon() {
        return amazon;
    }

    @JsonProperty
    public String getGoodreads() {
        return goodreads;
    }

    @JsonProperty
    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Book[getId=" + getId() + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id) &&
                Objects.equals(title, book.title) &&
                Objects.equals(author, book.author) &&
                Objects.equals(description, book.description) &&
                Objects.equals(amazon, book.amazon) &&
                Objects.equals(goodreads, book.goodreads);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, title, author, description, amazon, goodreads);
    }
}
