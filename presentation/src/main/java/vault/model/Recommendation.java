package vault.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.UUID;

public abstract class Recommendation {

    private final String id = UUID.randomUUID().toString();

    @NotEmpty
    private String description;

    @NotEmpty
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
