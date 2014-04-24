package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pmasko on 09.04.2014.
 */
public class CurrencyExchange implements Serializable {
    private String brokerId;
    private List<CurrencyExchangeItem> exchangeItemList = new ArrayList<>();

    public CurrencyExchange(String brokerId) {
        this.brokerId = brokerId;
    }

    public List<CurrencyExchangeItem> getExchangeItemList() {
        return exchangeItemList;
    }

    public String getBrokerId() {
        return brokerId;
    }

    public CurrencyExchange() {
    }

    public void setBrokerId(String brokerId) {
        this.brokerId = brokerId;
    }

    public void setExchangeItemList(List<CurrencyExchangeItem> exchangeItemList) {
        this.exchangeItemList = exchangeItemList;
    }

    @Override
    public String toString() {
        return "CurrencyExchange[brokerId=" + brokerId + ",items=" + getExchangeItemList() + "]";
    }
}
