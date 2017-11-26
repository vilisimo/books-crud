package vault.service;

import java.util.List;

public interface RecommendationLifecycle<T> {

    String save(T entity);

    List<T> getAll();

    T getOne(String id);

    boolean update(String id, T entity);

    void remove(String id);
}
