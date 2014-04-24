package service;

import model.CurrencyCode;
import model.CurrencyExchange;
import model.CurrencyExchangeResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by pmasko on 22.04.2014.
 */
public class RestCurrencyExchangeClient implements CurrencyExchangeService {

    @Autowired
    private RestTemplate restTemplate;

    private String baseUrl;

    @Override
    public CurrencyExchangeResult exchangeCurrencies(CurrencyExchange currencyExchange) {
        RestTemplate restTemplate = new RestTemplate();
        CurrencyExchangeResult result = restTemplate
                .postForObject(baseUrl + "/exchange", currencyExchange, CurrencyExchangeResult.class);
        return result;
    }

    @Override
    public BigDecimal exchangeRate(CurrencyCode from, CurrencyCode to) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String,String> params = new HashMap<>();
        params.put("from", from.name());
        params.put("to", to.name());
        BigDecimal rate = restTemplate.getForObject(baseUrl + "/rate/{from}/{to}", BigDecimal.class, params);
        return rate;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }
}
