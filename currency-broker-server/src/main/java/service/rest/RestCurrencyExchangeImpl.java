package service.rest;

import model.CurrencyCode;
import model.CurrencyExchange;
import model.CurrencyExchangeResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import service.CurrencyExchangeImpl;
import service.CurrencyExchangeService;

import java.math.BigDecimal;

/**
 * Created by pmasko on 21.04.2014.
 */
@RestController
@RequestMapping(value = "/broker")
public class RestCurrencyExchangeImpl implements CurrencyExchangeService {

    private static final Logger logger = LoggerFactory.getLogger(RestCurrencyExchangeImpl.class);

    @Autowired
    private CurrencyExchangeImpl service;

    @RequestMapping(value = "exchange", method = RequestMethod.POST,consumes = {"application/json"}, produces = {"application/json"})
    @Override
    public @ResponseBody CurrencyExchangeResult exchangeCurrencies(@RequestBody CurrencyExchange currencyExchange) {
        return service.exchangeCurrencies(currencyExchange);
    }


    @RequestMapping(value = "rate/{from}/{to}", method = RequestMethod.GET)
    @Override
    public @ResponseBody BigDecimal exchangeRate(@PathVariable CurrencyCode from, @PathVariable CurrencyCode to) {
        return service.exchangeRate(from, to);
    }
}
