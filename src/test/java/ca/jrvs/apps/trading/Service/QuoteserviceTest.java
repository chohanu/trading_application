package ca.jrvs.apps.trading.Service;

import ca.jrvs.apps.trading.model.domain.IexQuote;
import ca.jrvs.apps.trading.model.domain.Quote;
import org.junit.Test;

public class QuoteserviceTest {

    @Test
    public void buildquotefromiexquotetest() {

        IexQuote iextest = new IexQuote();
        // iextest.setIexAskPrice();


        Quote actualquote = new Quote();
        actualquote.setBidSize((long) 10);
        actualquote.setBidPrice((long) 20);
        actualquote.setAskPrice((long) 40);
        actualquote.setAskSize((long) 45);
        actualquote.setId("yo");
        actualquote.setTicker("bb");
        actualquote.setLastPrice((long) 45);


        // Quote expectedquote = Quoteservice.buildQuotefromIexquote(iexquote);
        // assertEquals(actualquote,expectedquote);


    }

}