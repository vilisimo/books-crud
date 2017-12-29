package vault.service;

import com.fasterxml.jackson.core.type.TypeReference;

public interface Converter {
    String asString(Object object);
    <T> T asObject(String object, TypeReference<T> typeToken);
}
