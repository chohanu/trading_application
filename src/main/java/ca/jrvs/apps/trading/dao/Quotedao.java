package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Quote;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;


public class Quotedao implements CrdRepo<Quote, Integer> {

    private final static String TABLE_NAME = "quote";
    private final static String ID_COLUMN = "ticker";

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    public Quotedao(DataSource datasource) {
        this.jdbcTemplate = new JdbcTemplate(datasource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(datasource).withTableName(TABLE_NAME);
    }

    public Quote Save(Quote entity) {
        SqlParameterSource sqlparametersource = new BeanPropertySqlParameterSource(entity);
        Number numberid = simpleJdbcInsert.executeAndReturnKey(sqlparametersource);
        // entity.setId(numberid.intValue());
        return entity;

    }

    public Quote FindbyId(String id) {
        return null;
    }

    public void DeletebyId(String id) {

    }

    public boolean existsbyId(String id) {
        return true;
    }
} // end of class
