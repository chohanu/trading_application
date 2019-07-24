package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Quote;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;


public class Quotedao implements CrdRepo<Quote, String> {

    private final static String TABLE_NAME = "quote";
    private final static String ID_COLUMN = "ticker";

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    public Quotedao(DataSource datasource) {
        this.jdbcTemplate = new JdbcTemplate(datasource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(datasource).withTableName(TABLE_NAME);
    }

    @Override
    public Quote save(Quote entity) {
        SqlParameterSource sqlparametersource = new BeanPropertySqlParameterSource(entity);
        Number numberid = simpleJdbcInsert.executeAndReturnKey(sqlparametersource);
        // entity.setId(numberid.intValue());
        return entity;

    }

    @Override
    public Quote findbyId(String id) {
        return null;
    }

    @Override
    public void deletebyId(String id) {

    }

    @Override
    public boolean existsbyId(String id) {
        return true;
    }
} // end of class
