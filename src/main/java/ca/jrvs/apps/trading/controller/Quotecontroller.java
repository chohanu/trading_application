package ca.jrvs.apps.trading.controller;

import ca.jrvs.apps.trading.Service.Quoteservice;
import ca.jrvs.apps.trading.dao.Marketdatadao;
import ca.jrvs.apps.trading.dao.Quotedao;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import ca.jrvs.apps.trading.model.domain.Quote;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/quote")
public class Quotecontroller {
    private Marketdatadao marketdatadao;
    private Quoteservice quoteservice;
    private Quotedao quotedao;


    public Quotecontroller(Marketdatadao marketdatadao, Quoteservice quoteservice, Quotedao quotedao) {
        this.marketdatadao = marketdatadao;
        this.quoteservice = quoteservice;
        this.quotedao = quotedao;
    } // end of contructor


    @RequestMapping(path = "/dailyList")
    public List<Quote> Dailylist() {

        try {
            // return quotedao.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @RequestMapping(path = "/iex/ticker/{ticker}")
    // @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public IexQuote iexquote(String ticker) {
        IexQuote quote = new IexQuote();


        try {
            quote = marketdatadao.findIexQuoteByTicker(ticker);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return quote;
    }


    @RequestMapping(path = "/tickerId/{tickerId}")
    //@ResponseStatus(HttpStatus)
    @ResponseBody
    public void addtickertoquotetable(String ticker) {
        try {
            quoteservice.initQuote(ticker);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //getbatchquote


} // end of class
