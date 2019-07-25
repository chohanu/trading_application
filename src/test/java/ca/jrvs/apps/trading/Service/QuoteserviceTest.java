package ca.jrvs.apps.trading.Service;

import ca.jrvs.apps.trading.model.domain.IexQuote;
import ca.jrvs.apps.trading.model.domain.Quote;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class QuoteserviceTest {

    @Test
    public void buildquotefromiexquotetest() {

        IexQuote iextest = new IexQuote();
        iextest.setIexAskPrice(40.0);
        iextest.setIexAskSize((long) 45.0);
        iextest.setIexBidPrice(20.0);
        iextest.setIexBidSize((long) 10.0);
        iextest.setSymbol("bb");
        iextest.setLatestPrice(45.0);




        Quote actualquote = new Quote();
        actualquote.setBidSize((long) 10.0);
        actualquote.setBidPrice(20.0);
        actualquote.setAskPrice(40.0);
        actualquote.setAskSize((long) 45);
        actualquote.setId("yo");
        actualquote.setTicker("bb");
        actualquote.setLastPrice(45.0);


        Quote expectedquote = Quoteservice.buildQuotefromIexquote(iextest);
        assertEquals(actualquote, expectedquote);


    }

}