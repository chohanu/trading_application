package ca.jrvs.apps.trading.controller;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class Appcontroller {

    @GetMapping(path = "/health")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public String health() {
        return "I am healthy";
    }
}
