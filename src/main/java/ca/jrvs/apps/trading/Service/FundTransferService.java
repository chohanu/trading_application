package ca.jrvs.apps.trading.Service;

import ca.jrvs.apps.trading.dao.Accountdao;
import ca.jrvs.apps.trading.model.domain.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class FundTransferService {

    private Accountdao accountdao;

    @Autowired
    public FundTransferService(Accountdao accountdao) {
        this.accountdao = accountdao;
    }


    public Account depositFund(Integer id, Double fund) {
        if (id == null || fund <= 0.0)
            throw new IllegalArgumentException("Illegal arguments passed.Check Id and fund ");

        Account account = accountdao.findByAccountId(id);
        account.setAmount(fund + account.getAmount());
        try {
            accountdao.updateAmountusingId(account.getAmount(), account.getId());
        } catch (IncorrectResultSizeDataAccessException e) {
            e.getLocalizedMessage();
        }

        return account;
    }


    public Account withdraw(Integer id, Double fund) {
        if (id == null || fund <= 0.0)
            throw new IllegalArgumentException("Illegal arguments passed.Check Id and fund ");
        Account account = accountdao.findByAccountId(id);
        if (fund > account.getAmount() || account.getAmount() < 0)
            throw new IllegalArgumentException(" Insufficient funds");
        accountdao.updateAmountusingId(account.getAmount() - fund, account.getId());
        return account;

    }

} // end of class
