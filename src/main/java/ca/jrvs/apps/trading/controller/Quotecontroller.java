package ca.jrvs.apps.trading.controller;


import ca.jrvs.apps.trading.Service.Quoteservice;
import ca.jrvs.apps.trading.dao.Marketdatadao;
import ca.jrvs.apps.trading.dao.Quotedao;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import ca.jrvs.apps.trading.model.domain.Quote;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Quote> Dailylist() {

        try {
            return quotedao.findAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



    @RequestMapping(path = "/iex/ticker/{ticker}")
    @ResponseStatus(HttpStatus.OK)
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

    @PostMapping(path = "/tickerId/{tickerId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void createQuote(@PathVariable String tickerId) {
        try {
            quoteservice.initQuote(tickerId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @PutMapping(path = "/iexMarketData")
    @ResponseStatus(HttpStatus.OK)
    public void updateMarketData() {
        try {
            quoteservice.updateMarketData();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping(path = "/")
    @ResponseStatus(HttpStatus.OK)
    public void putQuote(@RequestBody Quote quote) {
        try {
            quotedao.updateQuote(quote);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



} // end of class


