package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;

public class Securityorderdao implements CrdRepo<SecurityOrder, Integer> {

    private final String TABLE_NAME = "securityorderdao";
    private final String ID_COLUMN;

    private JdbcTemplate jdbctemplate;
    private SimpleJdbcInsert simplejdbcinsert;

    public Securityorderdao(DataSource datasource) {
        this.jdbctemplate = new JdbcTemplate(datasource);
        this.simplejdbcinsert = new SimpleJdbcInsert(datasource).withTableName("securityorderdao");
    }


    public SecurityOrder Save(SecurityOrder entity) {
        SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(entity);
        Number numberid = simplejdbcinsert.executeAndReturnKey(sqlParameterSource);
        entity.setId(numberid.intValue());
        return entity;
    }
}
