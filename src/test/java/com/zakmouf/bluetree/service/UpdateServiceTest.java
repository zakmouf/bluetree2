package com.zakmouf.bluetree.service;

import com.zakmouf.bluetree.BaseTest;
import com.zakmouf.bluetree.domain.Price;
import com.zakmouf.bluetree.domain.Stock;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UpdateServiceTest extends BaseTest {

    @Autowired
    private StockService stockService;

    @Autowired
    private PriceService priceService;

    @Autowired
    private UpdateService updateService;

    @Test
    @Transactional
    public void testName() {

        String symbol = "YHOO";

        Stock stock = stockService.getStock(symbol);
        if (stock == null) {
            stock = new Stock(symbol);
            stockService.saveStock(stock);
        } else {
            stock.setName(null);
            stockService.saveStock(stock);
        }

        stock = stockService.getStock(symbol);
        Assert.assertNull(stock.getName());

        List<Stock> stocks = new ArrayList<Stock>();
        stocks.add(stock);

        updateService.updateNames(stocks);

        stock = stocks.get(0);
        Assert.assertNotNull(stock.getName());

    }

    @Test
    @Transactional
    public void testPriceWithoutLast() {

        String symbol = "YHOO";

        Stock stock = stockService.getStock(symbol);
        if (stock == null) {
            stock = new Stock(symbol);
            stockService.saveStock(stock);
        }

        priceService.deleteAllPrices(stock);

        List<Price> prices = priceService.getPrices(stock);
        Assert.assertNotNull(prices);
        Assert.assertTrue(prices.isEmpty());

        List<Stock> stocks = new ArrayList<Stock>();
        stocks.add(stock);

        updateService.updatePrices(stocks);

    }

    @Test
    @Transactional
    public void testPriceWithLast() {

        String symbol = "YHOO";

        Stock stock = stockService.getStock(symbol);
        if (stock == null) {
            stock = new Stock(symbol);
            stockService.saveStock(stock);
        }

        priceService.deleteAllPrices(stock);

        Price price = new Price(parseDate("2013-01-01"), 123.456D);
        List<Price> prices = new ArrayList<Price>();
        prices.add(price);
        priceService.addPrices(stock, prices);

        List<Stock> stocks = new ArrayList<Stock>();
        stocks.add(stock);

        updateService.updatePrices(stocks);

    }

}
