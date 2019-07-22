package ca.jrvs.apps.trading.Util;

import ca.jrvs.apps.trading.dao.MarketDataDao;
import ca.jrvs.apps.trading.model.config.MarketDataConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.springframework.dao.DataRetrievalFailureException;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class ConnectionManager {
    HttpClientConnectionManager cm;

    public ConnectionManager(HttpClientConnectionManager cm, MarketDataConfig dataconfig) {
        this.cm = cm;

    }

    public static void main(String[] args) {
        HttpClientConnectionManager cm = Clientconnmanager(20, 40);
        MarketDataConfig marketdataconfig = new MarketDataConfig();
        marketdataconfig.setHost("https://cloud.iexapis.com/");
        marketdataconfig.setToken("pk_baf8f07f7ad3441595a051b1a1d805e5");

        MarketDataDao marketDataRao = new MarketDataDao(cm, marketdataconfig);
        ConnectionManager connectmanager = new ConnectionManager(cm, marketdataconfig); // pass the constructor


        List<String> attach = Arrays.asList("aal", "aapl", "fb");
        String finalstring = attach.stream().map(String::valueOf).collect(Collectors.joining(","));

        StringBuilder sb = new StringBuilder();
        sb.append("https://cloud.iexapis.com/stable/stock/market/batch?symbols=").append(finalstring).append("&types=quote&token=").append(marketdataconfig.getToken());


        System.out.println(connectmanager.ParseResponse(connectmanager.GetHTTPClientandResponse(sb.toString())));


    } // end of main

    public static HttpClientConnectionManager Clientconnmanager(int defaultpoolconnection, int maxpoolconnection) {
        PoolingHttpClientConnectionManager connmanager = new PoolingHttpClientConnectionManager();
        connmanager.setDefaultMaxPerRoute(defaultpoolconnection);
        connmanager.setMaxTotal(maxpoolconnection);
        return connmanager;
    }


    private CloseableHttpClient getHttpClient() {
        return HttpClients.custom().setConnectionManager(cm).setConnectionManagerShared(true).build();

    }

    public CloseableHttpResponse GetHTTPClientandResponse(String url) {


        try {
            CloseableHttpClient client = getHttpClient();
            CloseableHttpResponse response = client.execute(new HttpGet(url));
            return response;
        } catch (IOException e) {
            throw new DataRetrievalFailureException(" data retrieval failure from HTTPclientandResponse");
        }
    }


    public String ParseResponse(CloseableHttpResponse response) {

        int status = response.getStatusLine().getStatusCode();
        switch (status) {
            case 200:
                try {
                    return EntityUtils.toString(response.getEntity());
                    //return Optional.ofNullable(body).orElseThrow(() -> new IOException("Unexpected empty http response body"));
                } catch (IOException e) {
                    e.printStackTrace();

                }

                //  case 404:
                //    throw new ResourceNotFoundException("not found");

            default:
                throw new DataRetrievalFailureException("unexpected status" + response.getStatusLine().getStatusCode());

        }
    }

} // end of class

