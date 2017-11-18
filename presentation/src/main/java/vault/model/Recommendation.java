package vault.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public abstract class Recommendation {

    @NotNull
    protected String description;

    @NotNull
    protected String reason;

    protected Recommendation() {}

    protected Recommendation(String description, String reason) {
        this.description = description;
        this.reason = reason;
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
