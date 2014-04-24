package model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by pmasko on 09.04.2014.
 */
public class CurrencyExchangeItem implements Serializable {
    CurrencyCode from;
    CurrencyCode to;
    BigDecimal count;
    BigDecimal amount = BigDecimal.ZERO;

    public CurrencyCode getFrom() {
        return from;
    }

    public void setFrom(CurrencyCode from) {
        this.from = from;
    }

    public CurrencyCode getTo() {
        return to;
    }

    public void setTo(CurrencyCode to) {
        this.to = to;
    }

    public BigDecimal getCount() {
        return count;
    }

    public void setCount(BigDecimal count) {
        this.count = count;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public CurrencyExchangeItem(CurrencyCode from, CurrencyCode to, BigDecimal count) {
        this.from = from;
        this.to = to;
        this.count = count;
    }

    @Override
    public String toString() {
        return "CurrencyExchangeItem{" +
                "from=" + from +
                ", to=" + to +
                ", count=" + count +
                ", amount=" + amount +
                '}';
    }

    public CurrencyExchangeItem() {
    }

}
