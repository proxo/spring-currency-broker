package service;

import model.CurrencyCode;
import model.CurrencyExchangeResult;

import java.math.BigDecimal;

/**
 * Created by pmasko on 09.04.2014.
 */
public interface CurrencyExchangeService {

    CurrencyExchangeResult exchangeCurrencies(model.CurrencyExchange currencyExchange);

    BigDecimal exchangeRate(CurrencyCode from, CurrencyCode to);
}
