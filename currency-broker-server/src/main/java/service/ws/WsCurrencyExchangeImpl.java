package service.ws;

import model.CurrencyCode;
import model.CurrencyExchange;
import model.CurrencyExchangeResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import service.CurrencyExchangeService;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.math.BigDecimal;

/**
 * Created by pmasko on 27.04.2014.
 */
@WebService(serviceName = "brokerws")
public class WsCurrencyExchangeImpl implements CurrencyExchangeService {

    @Autowired
    @Qualifier("currencyExchange")
    CurrencyExchangeService currencyExchangeService;

    @WebMethod(operationName = "exchange")
    @Override
    public CurrencyExchangeResult exchangeCurrencies(CurrencyExchange currencyExchange) {
        return currencyExchangeService.exchangeCurrencies(currencyExchange);
    }

    @WebMethod(operationName = "rate")
    @Override
    public BigDecimal exchangeRate(CurrencyCode from, CurrencyCode to) {
        return currencyExchangeService.exchangeRate(from, to);
    }
}
