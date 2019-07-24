package ca.jrvs.apps.trading.dao;

public interface CrdRepo<T, ID> {

    T findbyId(ID id);

    void deletebyId(ID id);

    T save(T entity);

    boolean existsbyId(ID id);
}
