package vault.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

import javax.annotation.concurrent.Immutable;
import java.util.UUID;

@Immutable
public class Book {

    private String id;

    @NotEmpty
    private String author;

    @NotEmpty
    private String title;

    @NotEmpty
    private String description;

    @URL
    private String amazon;

    @URL
    private String goodreads;

    private Book() {}

    public Book(String author, String title, String description, String amazon, String goodreads) {
        this.id = UUID.randomUUID().toString();
        this.amazon = amazon;
        this.goodreads = goodreads;
        this.title = title;
        this.author = author;
        this.description = description;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty
    public String id() {
        return id;
    }

    @JsonProperty
    public String author() {
        return author;
    }

    @JsonProperty
    public String title() {
        return title;
    }

    @JsonProperty
    public String description() {
        return description;
    }

    @JsonProperty
    public String amazon() {
        return amazon;
    }

    @JsonProperty
    public String goodreads() {
        return goodreads;
    }

    @Override
    public String toString() {
        return "Book[id=" + id() + "]";
    }
}
