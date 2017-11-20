package vault.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

public abstract class Recommendation {

    String id;

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

    public void setId(String id) {
        this.id = id;
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
