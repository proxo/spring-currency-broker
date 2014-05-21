package service;


import model.CurrencyCode;
import model.CurrencyExchange;
import model.CurrencyExchangeItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by pmasko on 22.04.2014.
 */
public class BrokerServiceTester implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(BrokerServiceTester.class);

    private Map<String, CurrencyExchangeService> exchangeServiceMap;
    private List<Integer> items = new ArrayList<>();

    public BrokerServiceTester(Map<String, CurrencyExchangeService> exchangeServiceMap) {
        this.exchangeServiceMap = exchangeServiceMap;
    }

    public void run() {
        if (exchangeServiceMap != null) {
            for (Map.Entry<String, CurrencyExchangeService> entry : exchangeServiceMap.entrySet()) {
                logger.info("--------------------------------------------------");
                logger.info("Testing service {}", entry.getKey());
                long startTime = System.nanoTime();
                String serviceName = entry.getKey();
                CurrencyExchangeService service = entry.getValue();
                for (Integer cnt : items) {
                    testService(service, cnt);
                }
                long endTime = System.nanoTime() - startTime;
                logger.info("Service name: {} took time: {} ms", entry.getKey() , (endTime / 1000_000));
            }

        } else {
            logger.info("No services to test");
        }

    }

    private void testService(CurrencyExchangeService service, Integer cnt) {
        Random r = new Random();
        CurrencyExchange req = new CurrencyExchange("service#" + cnt);
        r.ints().limit(cnt).forEach(i -> {
            List<CurrencyExchangeItem> itemList = req.getExchangeItemList();
            itemList.add(new CurrencyExchangeItem(CurrencyCode.EURO, CurrencyCode.PLN, BigDecimal.valueOf(Math.abs(i + 1))));
        });

        logger.debug("sending {} to service: {}", req, service.toString());

        long startTime = System.nanoTime();
        service.exchangeCurrencies(req);
        long endTime = System.nanoTime() - startTime;
        long endMsTime = endTime / 1000_000;
        logger.info("Called service with #{} items, took time: {} ms, on avg: {} ns", cnt, endMsTime, (endTime / cnt));
    }

    public List<Integer> getItems() {
        return items;
    }

    public void setItems(List<Integer> items) {
        this.items = items;
    }

    public Map<String, CurrencyExchangeService> getExchangeServiceMap() {
        return exchangeServiceMap;
    }

    public void setExchangeServiceMap(Map<String, CurrencyExchangeService> exchangeServiceMap) {
        this.exchangeServiceMap = exchangeServiceMap;
    }

}
