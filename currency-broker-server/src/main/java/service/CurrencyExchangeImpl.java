package service;

import com.codahale.metrics.Counter;
import com.codahale.metrics.Gauge;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import model.CurrencyCode;
import model.CurrencyExchange;
import model.CurrencyExchangeResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.DoubleAdder;

import static com.codahale.metrics.MetricRegistry.name;


/**
 * Created by pmasko on 10.04.2014.
 *
 */
@Service("currencyExchange")
public class CurrencyExchangeImpl implements CurrencyExchangeService {
    private List<CurrencyExchangeResult> exchangeHistory = Collections.synchronizedList(new LinkedList<>());
    private List<WeakReference<ExchangeRateHistory>> rateHistory = Collections.synchronizedList(new ArrayList<>());
    private DoubleAdder totalTrade = new DoubleAdder();

    private static class ExchangeRateHistory implements Serializable {
        private CurrencyCode from;
        private CurrencyCode to;
        private Instant time;
        private BigDecimal rate;

        private ExchangeRateHistory(CurrencyCode from, CurrencyCode to, BigDecimal rate) {
            this.from = from;
            this.to = to;
            this.time = Instant.now();
            this.rate = rate;
        }
    }

    @Autowired
    private MetricRegistry registry;
    private Timer timer;
    private Counter counter;

    @PostConstruct
    private void init() {
        totalTrade.reset();

        timer = registry.timer(name(CurrencyExchangeImpl.class, "messages", "duration"));
        counter = registry.counter(name(CurrencyExchangeImpl.class, "messages", "received"));

        registry.register(name(CurrencyExchangeImpl.class, "exchange", "total"), new Gauge<BigDecimal>() {
            @Override
            public BigDecimal getValue() {
                return BigDecimal.valueOf(totalTrade.doubleValue());
            }
        });
    }

    @Override
    public CurrencyExchangeResult exchangeCurrencies(CurrencyExchange currencyExchange) {
        Assert.notNull(currencyExchange, "Request cannot be null");
        final long now = System.nanoTime();

        Optional<BigDecimal> reduce = currencyExchange.getExchangeItemList()
                .stream()
                .map(s -> {
                    s.setAmount(
                            exchangeRate(s.getFrom(), s.getTo())
                                    .multiply(s.getCount())
                    );
                    return s.getAmount();
                })
                .reduce((a, b) -> a.add(b));

        if (reduce.isPresent()) {
            CurrencyExchangeResult currencyExchangeResult = new CurrencyExchangeResult(currencyExchange.getBrokerId(), reduce.get(), LocalDateTime.now());
            currencyExchangeResult.getExchangeItemList().addAll(currencyExchange.getExchangeItemList());

            // store in history
            exchangeHistory.add(currencyExchangeResult);
            totalTrade.add(reduce.get().doubleValue());

            timer.update(System.nanoTime() - now, TimeUnit.NANOSECONDS);
            return currencyExchangeResult;
        }

        throw new IllegalStateException("Cannot calculate reduction");
    }

    @Override
    public BigDecimal exchangeRate(CurrencyCode from, CurrencyCode to) {
        BigDecimal rate = BigDecimal.valueOf(ThreadLocalRandom.current().nextInt(20, 50) / 10.0);
        rateHistory.add(new WeakReference(new ExchangeRateHistory(from, to, rate)));
        return rate;
    }
}
