package ca.jrvs.apps.trading.Service;

import ca.jrvs.apps.trading.dao.*;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Position;
import ca.jrvs.apps.trading.model.domain.Trader;
import ca.jrvs.apps.trading.model.view.TraderAccountView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegisterService {

    private Traderdao traderDao;
    private Accountdao accountDao;
    private PositionDao positionDao;
    private Securityorderdao securityOrderDao;

    @Autowired
    public RegisterService(Traderdao traderDao, Accountdao accountDao,
                           PositionDao positionDao, Securityorderdao securityOrderDao) {
        this.traderDao = traderDao;
        this.accountDao = accountDao;
        this.positionDao = positionDao;
        this.securityOrderDao = securityOrderDao;
    }

    public TraderAccountView createTraderAndAccount(Trader trader) {
        IsValid(trader);
        trader = traderDao.save(trader);
        Account account = new Account();
        account.setTraderId(trader.getId());
        account = accountDao.save(account);

        TraderAccountView tav = new TraderAccountView();
        tav.setAccount(account);
        tav.setTrader(trader);
        return tav;

    }


    public void IsValid(Trader trader) {
        if (trader == null) {
            throw new IllegalArgumentException("Trader is null");
        }

        Boolean firstname = trader.getFirstName() != null;
        Boolean lastname = trader.getLastName() != null;
        Boolean country = trader.getCountry() != null;
        Boolean date = trader.getDob() != null;
        Boolean email = trader.getEmail() != null;

        if (firstname && lastname && country && date && email == null) {
            throw new IllegalArgumentException("trade attributes cannot be null");
        }

    }

    /**
     * A trader can be deleted iff no open position and no cash balance.
     * - validate traderID
     * - get trader account by traderId and check account balance
     * - get positions by accountId and check positions
     * - delete all securityOrders, account, trader (in this order)
     */

    public void deleteTraderById(Integer traderId) {
        if (traderId == null) {
            throw new IllegalArgumentException("TraderId is not valid");
        }

        if (!(traderDao.existsById(traderId))) {
            throw new ResourceNotFoundException("NThere is no such trader Id.Trader doesnt exist");
        }

        Account account = accountDao.findByTraderId(traderId);
        if (account.getAmount() > 0) {
            throw new IllegalArgumentException("Trader " + traderId + " has balance, cannot be deleted");
        }

        List<Position> positions = positionDao.findallbyAccId(account.getId());

        if (positions.size() != 0) {
            throw new IllegalArgumentException("Trader has open positions, cannot be deleted");
        }

        securityOrderDao.DeletebyaccID(account.getId());
        accountDao.deleteById(account.getId());
        traderDao.deleteById(traderId);
    }
} // end of class
