package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Entity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

public abstract class JdbcCrudDao<E extends Entity, ID> implements CrdRepo<E, ID> {


    abstract public JdbcTemplate getjdbctemplate();

    abstract public SimpleJdbcInsert getsimplejdbcinsert();

    abstract public String gettablename();

    abstract public String getIdname();

    abstract public Class getEntityclass();


    @Override
    public E save(E entity) {
        SqlParameterSource parametersource = new BeanPropertySqlParameterSource(entity);
        Number newId = getsimplejdbcinsert().executeAndReturnKey(parametersource);
        entity.setId(newId.intValue());
        return entity;
    }

    @Override
    public boolean existsbyId(ID id) {
        return existsbyId(getIdname(), id);
    }

    @Override
    public E findbyId(ID id) {
        return findbyId(getIdname(), id, false, getEntityclass());
    }

    @Override
    public void deletebyId(ID id) {
        return deletebyId(getIdname(), id);
    }


    public E findbyIdforupdate(ID id) {
        return findbyIdforupdate(getIdname(), id, true, getEntityclass());
    }


    public E findbyId(String idName, ID id, boolean forupdate, Class clazz) {
        E t = null;
        String selectsql = "SELEC * FROM " + gettablename() + "WHERE" + idName + "=?";
        if (forupdate) {
            selectsql += " for update";
        }

        //logger.info.(selectsql);


    }


}
