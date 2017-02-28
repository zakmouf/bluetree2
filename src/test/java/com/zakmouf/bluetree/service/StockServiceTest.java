package com.zakmouf.bluetree.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.zakmouf.bluetree.BaseTest;
import com.zakmouf.bluetree.domain.Stock;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StockServiceTest extends BaseTest {

    @Autowired
    private StockService stockService;

    @Test
    @Transactional
    public void doTest() {

	String symbol = "symbol";
	String name = "name";

	Stock stock = new Stock();
	stock.setSymbol(symbol);
	stock.setName(name);

	Assert.assertNull(stockService.getStock(symbol));

	stockService.saveStock(stock);
	Long id = stock.getId();
	Assert.assertNotNull(id);

	stock = stockService.getStock(id);
	Assert.assertNotNull(stock);

	stock = stockService.getStock(symbol);
	Assert.assertNotNull(stock);

	List<Stock> stocks = stockService.getStocks();
	Assert.assertTrue(stocks.contains(stock));

	symbol = "#" + symbol + "#";
	name = "#" + name + "#";

	stock.setSymbol(symbol);
	stock.setName(name);

	stockService.saveStock(stock);
	stock = stockService.getStock(symbol);
	Assert.assertNotNull(stock);

	stockService.deleteStock(stock);
	Assert.assertNull(stockService.getStock(id));
	Assert.assertNull(stockService.getStock(symbol));

    }

}
