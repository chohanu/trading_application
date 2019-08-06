package ca.jrvs.apps.trading.Service;

import ca.jrvs.apps.trading.dao.*;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Position;
import ca.jrvs.apps.trading.model.domain.Quote;
import ca.jrvs.apps.trading.model.domain.Trader;
import ca.jrvs.apps.trading.model.view.PortFolioView;
import ca.jrvs.apps.trading.model.view.TraderAccountView;

import java.util.List;

public class DashboardService {
    private Traderdao traderdao;
    private Quotedao quotedao;
    private PositionDao positiondao;
    private Accountdao accountdao;

    public DashboardService(Traderdao traderdao, Quotedao quotedao, PositionDao positiondao, Accountdao accountdao) {
        this.traderdao = traderdao;
        this.quotedao = quotedao;
        this.positiondao = positiondao;
        this.accountdao = accountdao;
    }


    public TraderAccountView getTraderAccount(Integer traderId) {
        Trader trader = null;
        Account account = null;
        try {
            trader = traderdao.findById(traderId);
            account = accountdao.findByTraderId(traderId);

        } catch (ResourceNotFoundException e) {
            e.getMessage();
        }
        TraderAccountView traderAccountView = new TraderAccountView();
        traderAccountView.setAccount(account);
        traderAccountView.setTrader(trader);
        return traderAccountView;
    }


    public PortFolioView getProfileViewByTraderId(Integer traderId) {
        Account account = accountdao.findByTraderId(traderId);
        List<Position> positions = positiondao.findallbyAccId(account.getId());
        PortFolioView portfolioView = new PortFolioView();
        for (Position pos : positions) {
            String ticker = pos.getTicker();
            Quote quote = quotedao.findById(ticker);
            portfolioView.addSecurityRow(pos, quote, ticker);
        }
        return portfolioView;
    }


} // end of class
