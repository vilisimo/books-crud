package vault.service;

import java.util.List;

public interface RecommendationLifecycle<T> {
    String save(T entity);
    List<T> getAll();
    T getOne(String id);
    void update(String id, T entity);
}
