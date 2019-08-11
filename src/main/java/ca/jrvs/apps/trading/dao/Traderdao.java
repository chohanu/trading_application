package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Trader;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class Traderdao extends JdbcCrudDao<Trader, Integer> {


    private final String TABLE_NAME = "trader";
    private final String ID_COLUMN = "ticker";
    private JdbcTemplate jdbctemplate;
    private SimpleJdbcInsert simplejdbcinsert;

    public Traderdao(DataSource datasource) {
        this.jdbctemplate = new JdbcTemplate(datasource);
        this.simplejdbcinsert = new SimpleJdbcInsert(datasource).withTableName(TABLE_NAME);
    }

    @Override
    public JdbcTemplate getJdbcTemplate() {
        return jdbctemplate;
    }

    @Override
    public SimpleJdbcInsert getSimpleJdbcInsert() {
        return simplejdbcinsert;
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public Class getIdClass() {
        return Integer.class;
    }

    @Override
    public String getIdName() {
        return ID_COLUMN;
    }

    @Override
    public Class getEntityClass() {
        return Traderdao.class;
    }


} // end of class


