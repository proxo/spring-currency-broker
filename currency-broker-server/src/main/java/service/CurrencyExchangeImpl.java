package service;

import model.CurrencyCode;
import model.CurrencyExchange;
import model.CurrencyExchangeItem;
import model.CurrencyExchangeResult;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
import java.util.function.BinaryOperator;

/**
 * Created by pmasko on 10.04.2014.
 */
@Service("currencyExchange")
public class CurrencyExchangeImpl implements CurrencyExchangeService {


    @Override
    public CurrencyExchangeResult exchangeCurrencies(CurrencyExchange currencyExchange) {
        Assert.notNull(currencyExchange, "Request cannot be null");
        Optional<BigDecimal> reduce = currencyExchange.getExchangeItemList()
                .stream()
                .map(s -> exchangeRate(s.getFrom(), s.getTo()).multiply(s.getCount()))
                .reduce((a, b) -> a.add(b));

        if (reduce.isPresent()) {
            CurrencyExchangeResult currencyExchangeResult = new CurrencyExchangeResult(currencyExchange.getBrokerId(), reduce.get(), LocalDateTime.now());
            currencyExchangeResult.getExchangeItemList().addAll(currencyExchange.getExchangeItemList());

            return currencyExchangeResult;
        }

        throw new IllegalStateException("Cannot calculate reduction");
    }

    @Override
    public BigDecimal exchangeRate(CurrencyCode from, CurrencyCode to) {
        return BigDecimal.valueOf(new Random().nextInt(50) / 10.0);
    }
}
