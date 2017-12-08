package vault.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

import java.util.UUID;

public class Book {

    private String id;

    @NotEmpty
    private String title;

    @NotEmpty
    private String author;

    @NotEmpty
    private String description;

    @NotEmpty
    private String reason;

    @URL
    private String amazon;

    @URL
    private String goodreads;

    private Book() {}

    public Book(String title, String author, String description, String reason, String amazon, String goodreads) {
        this.id = UUID.randomUUID().toString();
        this.amazon = amazon;
        this.goodreads = goodreads;
        this.title = title;
        this.author = author;
        this.description = description;
        this.reason = reason;
    }

    @JsonProperty
    public String id() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty
    public String title() {
        return title;
    }

    @JsonProperty
    public String author() {
        return author;
    }

    @JsonProperty
    public String amazon() {
        return amazon;
    }

    @JsonProperty
    public String goodreads() {
        return goodreads;
    }

    @JsonProperty
    public String description() {
        return description;
    }

    @JsonProperty
    public String reason() {
        return reason;
    }

    @Override
    public String toString() {
        return "Book[id=" + id() + "]";
    }
}
