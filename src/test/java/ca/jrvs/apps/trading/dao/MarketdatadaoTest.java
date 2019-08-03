package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.config.MarketDataConfig;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MarketdatadaoTest {

    @Test
    public void Testing() {
        MarketDataConfig mdconfig = new MarketDataConfig();
        mdconfig.setHost("https://cloud.iexapis.com/");
        mdconfig.setToken("pk_baf8f07f7ad3441595a051b1a1d805e5");
        HttpClientConnectionManager httpclientconnection = new PoolingHttpClientConnectionManager();
        Marketdatadao mdao = new Marketdatadao(httpclientconnection, mdconfig);
        List<IexQuote> iexquotes = new ArrayList<>();
        List<String> tickers = Arrays.asList("fb", "amzn");
        iexquotes = mdao.findIexQuoteByTicker(tickers);


    }
}