package ca.jrvs.apps.trading.dao;


import ca.jrvs.apps.trading.model.domain.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;


public class Accountdao extends JdbcCrudDao<Account, Integer> {
    private final static String TABLE_NAME = "account";
    private final static String ID_NAME = "id";
    private JdbcTemplate jdbctemplate;
    private SimpleJdbcInsert simplejdbcinsert;

    public Accountdao(DataSource datasource) {
        this.jdbctemplate = new JdbcTemplate(datasource);
        this.simplejdbcinsert = new SimpleJdbcInsert(datasource).withTableName(TABLE_NAME).usingGeneratedKeyColumns(ID_NAME);
    }

    public Account findByTraderId(int traderid) {
        return super.findById("trader_id", traderid, false, Account.class);

    }

    public void updateAmountusingId(Integer Id, Double fund) {
        String SQL = "UPDATE " + TABLE_NAME + " SET amount = ? WHERE " + ID_NAME + " = ?";
        logger.debug(SQL + ", " + fund + ", " + Id);
        jdbctemplate.update(SQL, fund, Id);


    }

    @Override
    public Class getIdClass() {
        return Integer.class;
    }


    public Account findByAccountId(int accountid) {
        return super.findById(ID_NAME, accountid, true, Account.class);
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
    public String getIdName() {
        return ID_NAME;
    }

    @Override
    public Class getEntityClass() {
        return Account.class;
    }



} // end of class


