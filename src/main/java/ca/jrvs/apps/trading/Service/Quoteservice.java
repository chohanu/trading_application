package ca.jrvs.apps.trading.Service;

import ca.jrvs.apps.trading.dao.MarketDataDao;
import ca.jrvs.apps.trading.dao.Quotedao;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import ca.jrvs.apps.trading.model.domain.Quote;

import java.util.List;

public class Quoteservice {


    private Quotedao quotedao;
    private MarketDataDao marketdatadao;

    public Quoteservice(Quotedao quotedao, MarketDataDao marketdatadao) {
        this.quotedao = quotedao;
        this.marketdatadao = marketdatadao;
    }

    public static Quote buildQuotefromIexquote(IexQuote iexquote) {

        Quote quote = new Quote();
        quote.setAskPrice(iexquote.getAskPrice());
        quote.setAskSize(iexquote.getAskSize());
        quote.setBidPrice(iexquote.getBidPrice());
        quote.setBidSize(iexquote.getBidSize());
        quote.setId(iexquote.getId());
        quote.setTicker(iexquote.getTicker());
        quote.setLastPrice(iexquote.getLastPrice());
        return quote;
    }

    public void initQuote(List<String> tickers) {

    }

    public void initQuote(String quote) {

    }


    public void updateMarketData() {

    }


} // end of class

