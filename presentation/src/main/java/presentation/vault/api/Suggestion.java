package presentation.vault.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public class Suggestion {

    private String id;

    @NotNull
    private String description;

    public Suggestion() {}

    public Suggestion(String description) {
        this.description = description;
    }

    @JsonProperty
    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Suggestion{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
