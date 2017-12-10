package vault.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import vault.exception.MappingException;

import java.io.IOException;

import static java.util.Objects.requireNonNull;

public class StringConverter implements Converter {

    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public String asString(Object object) {
        requireNonNull(object, "Null cannot be converted to a string");

        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new MappingException("Conversion of an object to a String has failed", e);
        }
    }

    @Override
    public <T> T asObject(String object, TypeReference<T> typeToken) {
        requireNonNull(object, "Null cannot be converted to a target object");

        if (object.isEmpty()) {
            throw new IllegalArgumentException("Object string should not be empty");
        }

        try {
            return mapper.readValue(object, typeToken);
        } catch (IOException e) {
            throw new MappingException("Conversion of a string to an object has failed", e);
        }
    }
}
