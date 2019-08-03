package ca.jrvs.apps.trading.Service;

import ca.jrvs.apps.trading.dao.Quotedao;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import ca.jrvs.apps.trading.model.domain.Quote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Quoteservice {


    private Quotedao quotedao;

    //private Marketdatadao marketdatadao;
    @Autowired
    public Quoteservice(Quotedao quotedao) {
        this.quotedao = quotedao;
        //this.marketdatadao = marketdatadao;
    }

    public Quote buildQuotefromIexquote(IexQuote iexquote) {

        Quote quote = new Quote();
        quote.setAskPrice(iexquote.getIexAskPrice());
        quote.setAskSize(iexquote.getIexAskSize());
        quote.setBidPrice(iexquote.getIexBidPrice());
        quote.setBidSize(iexquote.getIexBidSize());
        quote.setId(iexquote.getSymbol());
        quote.setTicker(iexquote.getSymbol());
        quote.setLastPrice(iexquote.getLatestPrice());
        return quote;
    }

 /*   public void initQuote(List<String> tickers) {
        for (String ticker : tickers) {
            IexQuote iexquote = marketdatadao.findIexQuoteByTicker(ticker);
            Quote quote = buildQuotefromIexquote(iexquote);
            quotedao.save(quote);
        }

    }





    public void initQuote(String ticker) {
        initQuote(Collections.singletonList(ticker));

    }

  */

    public void initQuote(Quote quote) {
        quotedao.save(quote);
    }



    public void updateMarketData() {

    }


} // end of class

