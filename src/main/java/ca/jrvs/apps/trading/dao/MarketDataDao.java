package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.config.MarketDataConfig;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
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


public class MarketDataDao {
    // private Logger logger = LoggerFactory.getLogger(MarketDataDao.class);

    private final String BATCH_QUOTE_URL;
    private HttpClientConnectionManager httpClientConnectionManager;

    public MarketDataDao(HttpClientConnectionManager httpClientConnectionManager, MarketDataConfig marketDataConfig) {
        this.httpClientConnectionManager = httpClientConnectionManager;
        BATCH_QUOTE_URL = marketDataConfig.getHost() + "/stock/market/batch?symbols=%s&types=quote&token=" + marketDataConfig.getToken();
    }

    /**
     * @throws DataRetrievalFailureException if unable to get http response
     */


    public static void main(String[] args) {

        HttpClientConnectionManager cm = connectionmanager(20, 50);
        MarketDataConfig mdc = new MarketDataConfig();
        mdc.setHost("https://cloud.iexapis.com/");
        mdc.setToken("pk_baf8f07f7ad3441595a051b1a1d805e5");

        MarketDataDao marketdatadao = new MarketDataDao(cm, mdc);

        List<String> attach = Arrays.asList("aal", "aapl", "fb");
        String onestring = attach.stream().map(String::valueOf).collect(Collectors.joining(","));

        StringBuilder sb = new StringBuilder();
        sb.append("https://cloud.iexapis.com/stable/stock/market/batch?symbols=").append(onestring).append("&types=quote&token=").append(mdc.getToken());

        String response = marketdatadao.ParseResponse(marketdatadao.closeableHttpResponse(sb.toString()));

        unmarshalljson(response);


    }


    public static HttpClientConnectionManager connectionmanager(int defaultpoolconnection, int maxpoolconnection) {
        PoolingHttpClientConnectionManager pcm = new PoolingHttpClientConnectionManager();
        pcm.setMaxTotal(maxpoolconnection);
        pcm.setDefaultMaxPerRoute(defaultpoolconnection);
        return pcm;
    }

    public static void unmarshalljson(String response) {
        JSONObject jasonobject = new JSONObject(response);
        List<IexQuote> iexquotes = new ArrayList<>();
        Iterator<String> quotetostring = jasonobject.keys(); //gets hold off all the tickers
        while (quotetostring.hasNext()) {
            String stringquote = jasonobject.getJSONObject(quotetostring.next()).get("quotes").toString();
            // getting "quotes" row from each ticker and changing it to string

            try {
                IexQuote iexq = toObjectFromJson(stringquote, IexQuote.class);
                iexquotes.add(iexq);
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("IexQUOTE: " + iexquotes);
        }

    } // end of unmarshalljson

    private CloseableHttpClient getHttpClient() {
        return HttpClients.custom().setConnectionManager(httpClientConnectionManager).setConnectionManagerShared(true).build();

    }

    public CloseableHttpResponse closeableHttpResponse(String uri) {
        CloseableHttpClient client = getHttpClient();
        try {
            CloseableHttpResponse response = client.execute(new HttpGet(uri));
            return response;
        } catch (IOException e) {
            throw new DataRetrievalFailureException(" date could not be retrieved");
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



   /*
    public List<IexQuote>  findIexQuoteByTicker(List<String> tickerList)
    {

        // convert list into comma separated string
        logger.info("GET URI:" + uri);

        // get http response body in string
        String response = executeHttpGet(uri);

        // iex will skip invalid symbols/tickers.we need to check it

        //JSObject IexQuotes

        if(IexQuotesJson.length() != tickerList.size())
        {
            throw new IllegalArgumentException("Invalid ticker symbol");
        }




        return iexQuotes;
    }



    public IexQuote findIexQuoteByTicker(String ticker)
    {
        List<IexQuote> quotes = findIexQuoteByTicker(Arrays.asList(ticker));
        if (quotes == null || quotes.size() != 1) {
            throw new DataRetrievalFailureException("Unable to get data");
        }
        return quotes.get(0);
    }

    */


}





