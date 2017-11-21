package vault.service;

public interface RecommendationLifecycle<T> {
    String save(T entity);
}
