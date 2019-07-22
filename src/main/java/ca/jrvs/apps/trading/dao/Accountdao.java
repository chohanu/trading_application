package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;

public class Accountdao implements CrdRepo<Account, Integer> {

    private final String TABLE_NAME = "accountdao";
    private final String ID_COLUMN;

    private JdbcTemplate jdbctemplate;
    private SimpleJdbcInsert simplejdbcinsert;

    public Accountdao(DataSource datasource) {
        this.jdbctemplate = new JdbcTemplate(datasource);
        this.simplejdbcinsert = new SimpleJdbcInsert(datasource).withTableName("accountdao");
    }

    public Account Save(Account entity) {
        SqlParameterSource sqlparametersource = new BeanPropertySqlParameterSource(entity);
        Number numberid = simplejdbcinsert.executeAndReturnKey(sqlparametersource);
        entity.setId(numberid.intValue());
        return entity;
    }


} // end of class
