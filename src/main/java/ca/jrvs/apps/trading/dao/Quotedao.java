package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Quote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class Quotedao extends JdbcCrudDao<Quote, String> {

    private final static String TABLE_NAME = "quote";
    private final static String ID_COLUMN = "ticker";
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public Quotedao(DataSource datasource) {
        this.jdbcTemplate = new JdbcTemplate(datasource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(datasource).withTableName(TABLE_NAME);
    }

    @Override
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    @Override
    public SimpleJdbcInsert getSimpleJdbcInsert() {
        return simpleJdbcInsert;
    }

    @Override
    public Class getEntityClass() {
        return Quote.class;
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }


    @Override
    public String getIdName() {
        return ID_COLUMN;
    }

    @Override
    public Class getIdClass() {
        return String.class;
    }

    @Override
    public Quote save(Quote quote) {
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(quote);
        simpleJdbcInsert.execute(parameterSource);
        return quote;
    }

    public void updateQuote(Quote quote) {
        String ticker = quote.getTicker();
        String sql = "UPDATE " + TABLE_NAME + " SET bid_size = ?, last_price = ?, bid_price = ?, ask_size = ?, " + "ask_price = ? where " + ID_COLUMN + " = ?";
        logger.debug(sql + ", " + quote.getBidSize() + ", " + quote.getLastPrice() + ", " + quote.getBidPrice() + ", " + quote.getAskSize() + ", " + quote.getAskPrice() + ", " + ticker);
        jdbcTemplate.update(sql, quote.getBidSize(), quote.getLastPrice(), quote.getBidPrice(), quote.getAskSize(), quote.getAskPrice(), ticker);
    }


} // end of class
