package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.config.MarketDataConfig;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.dao.DataRetrievalFailureException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static ca.jrvs.apps.trading.Util.JasonUtil.toObjectFromJson;

public class Marketdatadao {

    private final String BASE_URL;
    private final String TOKEN_URL;
    HttpClientConnectionManager httpClientconnectionmanager;

    public Marketdatadao(HttpClientConnectionManager httpclientconnectionmanager, MarketDataConfig marketdataconfig) {
        this.httpClientconnectionmanager = httpclientconnectionmanager;
        BASE_URL = marketdataconfig.getHost() + "/stock/market/batch?symbols=";
        TOKEN_URL = "&types=quote&token=" + marketdataconfig.getToken();

    }

    private CloseableHttpClient getHttpClient() {
        return HttpClients.custom().setConnectionManager(httpClientconnectionmanager).setConnectionManagerShared(true).build();

    }

    public CloseableHttpResponse closeableHttpResponse(String uri) {
        CloseableHttpClient client = getHttpClient();
        try {

            HttpGet get = new HttpGet(uri);
            CloseableHttpResponse response = client.execute(get);
            return response;
        } catch (IOException e) {
            throw new DataRetrievalFailureException(" date coul not be retrieved");
        }
    }

    public String ParseResponse(CloseableHttpResponse response) {
        int status = response.getStatusLine().getStatusCode();
        switch (status) {
            case 200:
                try {
                    EntityUtils.toString(response.getEntity());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            default:
                throw new DataRetrievalFailureException("data not retrieved." + response.getStatusLine().getStatusCode());

        }


    }


    public List<IexQuote> findIexQuoteByTicker(List<String> tickers) {
        String Tickers = tickers.stream().map(String::valueOf).collect(Collectors.joining(","));
        StringBuilder sburl = new StringBuilder();
        sburl.append(BASE_URL).append(Tickers).append(TOKEN_URL);

        //passing URL in and sending http request and getting a response
        String response = ParseResponse(closeableHttpResponse(sburl.toString()));

        JSONObject json = new JSONObject(response);
        List<IexQuote> iexquotes = new ArrayList<>();
        Iterator<String> Tickerlist = json.keys();
        while (Tickerlist.hasNext()) {
            String quote = json.getJSONObject(Tickerlist.next()).get("quote").toString();
            try {
                IexQuote iexquote = toObjectFromJson(quote, IexQuote.class);
                iexquotes.add(iexquote);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return iexquotes;
    }

    public IexQuote findIexQuoteByTicker(String ticker) {
        List<IexQuote> quotes = findIexQuoteByTicker(Arrays.asList(ticker));
        if (quotes == null || quotes.size() != 1) {
            throw new DataRetrievalFailureException("Unable to get data");
        }
        return quotes.get(0);
    }


} // end of class

