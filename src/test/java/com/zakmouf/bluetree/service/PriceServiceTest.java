package com.zakmouf.bluetree.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.zakmouf.bluetree.BaseTest;
import com.zakmouf.bluetree.domain.Price;
import com.zakmouf.bluetree.domain.Stock;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PriceServiceTest extends BaseTest {

    @Autowired
    private StockService stockService;
    @Autowired
    private PriceService priceService;

    @Test
    @Transactional
    public void doTest() {

	Stock stock = new Stock("symbol");
	stockService.saveStock(stock);

	List<Price> prices = priceService.getPrices(stock);
	Assert.assertNotNull(prices);
	Assert.assertTrue(prices.isEmpty());

	Date date = parseDate("2012-12-31");
	Double value = 123.456D;

	Price price = new Price();
	price.setDate(date);
	price.setValue(value);

	prices = new ArrayList<Price>();
	prices.add(price);
	priceService.addPrices(stock, prices);

	prices = priceService.getPrices(stock);
	Assert.assertNotNull(prices);
	Assert.assertTrue(prices.size() == 1);

	priceService.deleteAllPrices(stock);

	prices = priceService.getPrices(stock);
	Assert.assertNotNull(prices);
	Assert.assertTrue(prices.isEmpty());

    }

}
