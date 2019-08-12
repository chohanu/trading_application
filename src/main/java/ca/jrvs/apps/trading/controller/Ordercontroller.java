package ca.jrvs.apps.trading.controller;
import ca.jrvs.apps.trading.Service.OrderService;
import ca.jrvs.apps.trading.model.domain.Marketorderdto;
import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/order")
public class Ordercontroller {

    private OrderService orderservice;

    @Autowired
    public Ordercontroller(OrderService orderservice) {
        this.orderservice = orderservice;
    }


    @PostMapping(path = "/MarketOrder")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody

    public SecurityOrder MarketOrder(Marketorderdto marketorderdto) {
        try {
            return orderservice.executeMarketOrder(marketorderdto);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

} // end of class
