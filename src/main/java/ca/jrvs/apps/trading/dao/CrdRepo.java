package ca.jrvs.apps.trading.dao;

public interface CrdRepo<T, ID> {

    T findById(ID id);

    void deleteById(ID id);

    T save(T entity);

    boolean existsById(ID id);
}
