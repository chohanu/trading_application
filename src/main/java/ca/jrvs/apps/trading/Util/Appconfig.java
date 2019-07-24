package ca.jrvs.apps.trading.Util;


import ca.jrvs.apps.trading.model.config.MarketDataConfig;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class Appconfig {

    @Bean
    public MarketDataConfig MarketDataConfig() {
        MarketDataConfig marketdata = new MarketDataConfig();
        marketdata.setHost("https://cloud.iexapis.com/");
        marketdata.setToken("pk_baf8f07f7ad3441595a051b1a1d805e5");
        return marketdata;
    }

    @Bean
    public HttpClientConnectionManager httpClientConnectionManager() {
        PoolingHttpClientConnectionManager httpconnectionmanager = new PoolingHttpClientConnectionManager();
        httpconnectionmanager.setMaxTotal(50);
        httpconnectionmanager.setDefaultMaxPerRoute(20);
        return httpconnectionmanager;

    }

    @Bean
    public DataSource datasource() {
        // String jdbcurl;
        // String user;
        //String password;

        // jdbcurl = System.getenv();
        // user = System.getenv();
        // password = System.getenv();

        BasicDataSource basicdatasource = new BasicDataSource();
        basicdatasource.setDriverClassName("org.postgresql.Driver");
        basicdatasource.setUrl(" ");
        basicdatasource.setUsername("user");
        basicdatasource.setPassword("password");
        return basicdatasource;

    }
}
