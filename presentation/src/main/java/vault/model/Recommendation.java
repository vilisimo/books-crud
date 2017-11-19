package vault.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public abstract class Recommendation {

    private final String id = UUID.randomUUID().toString();

    @NotNull
    private String description;

    @NotNull
    private String reason;

    Recommendation() {}

    Recommendation(String description, String reason) {
        this.description = description;
        this.reason = reason;
    }

    @JsonProperty
    public String id() {
        return id;
    }

    @JsonProperty
    public String description() {
        return description;
    }

    @JsonProperty
    public String reason() {
        return reason;
    }
}
