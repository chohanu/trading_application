package ca.jrvs.apps.trading.dao;

public interface CrdRepo<T, ID> {

    T FindbyId(ID id);

    void DeletebyId(ID id);

    T Save(T entity);

    boolean existsbyId(ID id);
}
