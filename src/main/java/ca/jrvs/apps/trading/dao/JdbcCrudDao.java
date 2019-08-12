package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.util.List;

public abstract class JdbcCrudDao<E extends Entity, ID> implements CrdRepo<E, ID> {


    public static final Logger logger = LoggerFactory.getLogger(JdbcCrudDao.class);

    abstract public JdbcTemplate getJdbcTemplate();

    abstract public SimpleJdbcInsert getSimpleJdbcInsert();

    abstract public String getTableName();

    abstract public String getIdName();

    abstract public Class getEntityClass();

    abstract public Class getIdClass();



    @Override
    public E save(E entity) {
        SqlParameterSource parametersource = new BeanPropertySqlParameterSource(entity);
        Number newId = getSimpleJdbcInsert().executeAndReturnKey(parametersource);
        entity.setId(newId.intValue());
        return entity;
    }

    @Override
    public boolean existsById(ID id) {
        return existsById(getIdName(), id);
    }

    @Override
    public E findById(ID id) {
        return findById(getIdName(), id, false, getEntityClass());
    }


    @Override
    public void deleteById(ID id) {
        deleteById(getIdName(), id);
    }


    @SuppressWarnings("unchecked")
    public E findById(String idName, ID id, boolean forupdate, Class clazz) {
        E entityobject = null;
        String selectSql = "SELECT * FROM " + getTableName() + " WHERE " + idName + " =?";

        if (forupdate) {
            selectSql += " for update";
        }
        logger.info(selectSql);

        try {
            entityobject = (E) getJdbcTemplate().queryForObject(selectSql, BeanPropertyRowMapper.newInstance(clazz), id);
        } catch (EmptyResultDataAccessException e) {
            logger.debug("Can't find quote id:" + id, e);
        }
        if (entityobject == null) {
            throw new ResourceNotFoundException("Er:Resource not found");
        }
        return entityobject;
    }


    public boolean existsById(String idName, ID id) {
        if (id == null) {
            throw new IllegalArgumentException("ID can't be null");
        }
        String selectSql = "SELECT count(*) FROM " + getTableName() + " WHERE " + idName + " =?";
        logger.info(selectSql);
        Integer count = getJdbcTemplate().queryForObject(selectSql, Integer.class, id);
        return count != 0;
    }

    public void deleteById(String idName, ID id) {
        if (id == null) {
            throw new IllegalArgumentException("ID can't be null");
        }
        String deleteSql = "DELETE FROM " + getTableName() + " WHERE " + idName + " =?";
        logger.info(deleteSql);
        getJdbcTemplate().update(deleteSql, id);
    }

    // fetches all of the IDs/tickers from the ID column
    public List<ID> returnalltickers() {
        List<ID> returnTickers = getJdbcTemplate().queryForList("select " + getIdName() + " from " + getTableName(), getIdClass());
        return returnTickers;
    }

    public List<E> findAll() {
        String sql = "select * from " + getTableName();
        logger.debug(sql);
        List<E> list = getJdbcTemplate().query(sql, BeanPropertyRowMapper.newInstance(getEntityClass()));
        return list;
    }

}





