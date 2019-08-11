package ca.jrvs.apps.trading.Service;

import ca.jrvs.apps.trading.Util.StringUtil;
import ca.jrvs.apps.trading.dao.Accountdao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.Quotedao;
import ca.jrvs.apps.trading.dao.Securityorderdao;
import ca.jrvs.apps.trading.model.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;

import static java.lang.Math.abs;

@Service
@Transactional
public class OrderService {


    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    private Accountdao accountdao;
    private Securityorderdao securityorderdao;
    private Quotedao quotedao;
    private PositionDao positiondao;

    @Autowired
    public OrderService(Accountdao accountDao, Securityorderdao securityOrderDao,
                        Quotedao quoteDao, PositionDao positionDao) {
        this.accountdao = accountDao;
        this.securityorderdao = securityOrderDao;
        this.quotedao = quoteDao;
        this.positiondao = positionDao;
    }


    public SecurityOrder executeMarketOrder(Marketorderdto marketorderdto) {
        IfValid(marketorderdto);
        String ticker = marketorderdto.getTicker();
        int orderSize = marketorderdto.getSize();
        int accountId = marketorderdto.getAccountId();
        SecurityOrder securityOrder = new SecurityOrder();
        securityOrder.setAccountId(accountId);
        securityOrder.setSize(orderSize);
        securityOrder.setTicker(ticker);
        Account account = accountdao.findByAccountId(accountId);

        if (orderSize > 0) {
            return buy(securityOrder, account);
        } else {
            return sell(securityOrder, account);
        }
    }


    private void IfValid(Marketorderdto mod) {
        if (mod == null) {
            throw new IllegalArgumentException("Order cannot be null.");
        }
        int orderSize = mod.getSize();
        if (orderSize == 0) {
            throw new IllegalArgumentException("Order size cannot be 0.");
        }
        mod.setTicker(mod.getTicker().trim());
        String ticker = mod.getTicker();
        if (StringUtil.isEmpty(Collections.singletonList(ticker))) {
            throw new IllegalArgumentException("Ticker cannot be empty.");
        }
    }


    private SecurityOrder buy(SecurityOrder securityOrder, Account account) {
        double price = quotedao.findById(securityOrder.getTicker()).getAskPrice();
        securityOrder.setPrice(price);
        double totalPrice = price * securityOrder.getSize();
        if (totalPrice > account.getAmount()) {
            securityOrder.setStatus(OrderStatus.CANCELLED);
            securityOrder.setNotes("Insufficient fund.");
            securityorderdao.save(securityOrder);
            throw new IllegalArgumentException("Insufficient fund. Required: " + totalPrice + ", available: " + account.getAmount());
        } else {
            securityOrder.setStatus(OrderStatus.FILLED);
            accountdao.updateAmountusingId(account.getAmount() - totalPrice, account.getId());
            securityorderdao.save(securityOrder);
        }

        return securityOrder;
    }


    private SecurityOrder sell(SecurityOrder securityOrder, Account account) {
        double price = quotedao.findById(securityOrder.getTicker()).getBidPrice();
        securityOrder.setPrice(price);
        double totalPrice = price * abs(securityOrder.getSize());
        Position position = positiondao.findbyAccIdandTicker(account.getId(), securityOrder.getTicker());
        if (position.getPosition() < abs(securityOrder.getSize())) {
            securityOrder.setStatus(OrderStatus.CANCELLED);
            securityOrder.setNotes("Insufficient position.");
            securityorderdao.save(securityOrder);
            throw new IllegalArgumentException("Not enough position, only " + position.getPosition() + " available.");
        } else {
            securityOrder.setStatus(OrderStatus.FILLED);
            accountdao.updateAmountusingId(account.getAmount() + totalPrice, account.getId());
            securityorderdao.save(securityOrder);
        }
        return securityOrder;
    }


} // end of class
