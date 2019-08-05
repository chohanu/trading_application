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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static ca.jrvs.apps.trading.Util.JasonUtil.toObjectFromJson;

@Repository
public class Marketdatadao {

    private final String firsthalf_url;
    private final String secondhalf_url;
    HttpClientConnectionManager httpClientconnectionmanager;

    @Autowired
    public Marketdatadao(HttpClientConnectionManager httpclientconnectionmanager, MarketDataConfig marketdataconfig) {
        this.httpClientconnectionmanager = httpclientconnectionmanager;
        firsthalf_url = marketdataconfig.getHost() + "/stable/stock/market/batch?symbols=";
        secondhalf_url = "&types=quote&token=" + marketdataconfig.getToken();

    }

    private CloseableHttpClient getHttpClient() {
        return HttpClients.custom().setConnectionManager(httpClientconnectionmanager).setConnectionManagerShared(true).build();

    }

    public CloseableHttpResponse closeableHttpResponse(String uri) {
        System.out.println(uri);
        CloseableHttpClient client = getHttpClient();
        System.out.println("2");
        try {

            HttpGet get = new HttpGet(uri);
            System.out.println("3");
            CloseableHttpResponse response = client.execute(get);
            System.out.println("4");
            return response;
        } catch (IOException e) {
            throw new DataRetrievalFailureException(" date coul not be retrieved");
        }
    }

    public String ParseResponse(CloseableHttpResponse response) {
        int status = response.getStatusLine().getStatusCode();
        System.out.println(status);
        switch (status) {
            case 200:
                try {
                    //System.out.println(EntityUtils.toString(response.getEntity()));
                    return EntityUtils.toString(response.getEntity());
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
        sburl.append(firsthalf_url).append(Tickers).append(secondhalf_url);

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

