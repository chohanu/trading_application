package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Trader;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;

public class Traderdao implements CrdRepo<Trader, Integer> {


    private final String TABLE_NAME = "trader";
    private final String ID_COLUMN = "ticker";
    private JdbcTemplate jdbctemplate;
    private SimpleJdbcInsert simplejdbcinsert;

    public Traderdao(DataSource datasource) {
        this.jdbctemplate = new JdbcTemplate(datasource);
        this.simplejdbcinsert = new SimpleJdbcInsert(datasource).withTableName(TABLE_NAME);
    }

    public Trader Save(Trader entity) {
        SqlParameterSource sqlparametersource = new BeanPropertySqlParameterSource(entity);
        Number numberid = simplejdbcinsert.executeAndReturnKey(sqlparametersource);
        // entity.setId(numberid.intValue());
        return entity;

    }


} // end of class
