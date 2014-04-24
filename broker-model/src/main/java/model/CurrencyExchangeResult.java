package model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

/**
 * Created by pmasko on 09.04.2014.
 */
public class CurrencyExchangeResult extends CurrencyExchange implements Serializable{
    private BigDecimal totalAmount;
    private Date conversionDate;

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Date getConversionDate() {
        return conversionDate;
    }

    public void setConversionDate(Date conversionDate) {
        this.conversionDate = conversionDate;
    }

    public CurrencyExchangeResult(String brokerId, BigDecimal totalAmount, LocalDateTime conversionDate) {
        super(brokerId);
        this.totalAmount = totalAmount;
        this.conversionDate = Date.from(conversionDate.toInstant(ZoneOffset.UTC));
    }

    public CurrencyExchangeResult() {
    }
}
