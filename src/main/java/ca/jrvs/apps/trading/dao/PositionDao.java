package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Position;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class PositionDao {
    private final static String TABLE_NAME = "position";
    private final Logger logger = LoggerFactory.getLogger(PositionDao.class);
    private JdbcTemplate jdbctemplate;

    @Autowired
    public PositionDao(JdbcTemplate jdbctemplate) {
        this.jdbctemplate = jdbctemplate;
    }

    public List<Position> findallbyAccId(Integer accountid) {
        if (accountid == null)
            throw new IllegalArgumentException("invalid Id");
        String sql = "select * from " + TABLE_NAME + " where account_id =?";
        logger.debug(sql + ", " + accountid);
        return jdbctemplate.query(sql, BeanPropertyRowMapper.newInstance(Position.class), accountid);
    }

    public Position findbyAccIdandTicker(Integer accountid, String ticker) {
        if (accountid == null)
            throw new IllegalArgumentException("invalid Id");


        String sql = "select * from " + TABLE_NAME + " where account_id =? and ticker =?";
        logger.debug(sql + ", " + accountid + ", " + ticker);
        return jdbctemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(Position.class), accountid, ticker);
    }

} // end of class
