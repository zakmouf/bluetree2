package com.zakmouf.bluetree.dao;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.zakmouf.bluetree.domain.Price;
import com.zakmouf.bluetree.domain.Stock;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StockDaoTest extends BaseDaoTest {

    @Autowired
    private StockDao stockDao;

    @Test
    @Transactional
    public void testStock() {

	Stock stock = new Stock("symbol", "name");

	Assert.assertNull(stockDao.findBySymbol(stock.getSymbol()));

	stockDao.insert(stock);
	Assert.assertNotNull(stock.getId());

	stock = stockDao.findById(stock.getId());
	Assert.assertNotNull(stock);

	stock = stockDao.findBySymbol(stock.getSymbol());
	Assert.assertNotNull(stock);

	List<Stock> stocks = stockDao.findAll();
	Assert.assertTrue(stocks.contains(stock));

	stock.setSymbol("#" + stock.getSymbol() + "#");
	stock.setName("#" + stock.getName() + "#");
	stockDao.update(stock);
	stock = stockDao.findBySymbol(stock.getSymbol());
	Assert.assertNotNull(stock);

	stockDao.delete(stock);
	Assert.assertNull(stockDao.findById(stock.getId()));
	Assert.assertNull(stockDao.findBySymbol(stock.getSymbol()));

    }

    @Test
    @Transactional
    public void testPrice() {

	Stock stock = new Stock("symbol");
	stockDao.insert(stock);

	Date date0 = parseDate("2012-12-14");
	Date date1 = parseDate("2012-12-15");
	Date date2 = parseDate("2012-12-16");
	Date date3 = parseDate("2012-12-17");
	Date date4 = parseDate("2012-12-18");
	Date date5 = parseDate("2012-12-19");
	Date date6 = parseDate("2012-12-20");

	Price price1 = new Price(date1, 1.1d);
	Price price3 = new Price(date3, 3.3d);
	Price price5 = new Price(date5, 5.5d);

	List<Price> prices;

	prices = stockDao.findPrices(stock);
	Assert.assertTrue(prices.isEmpty());

	prices.add(price1);
	prices.add(price3);
	prices.add(price5);
	stockDao.insertPrices(stock, prices);
	prices = stockDao.findPrices(stock);
	Assert.assertFalse(prices.isEmpty());
	Assert.assertTrue(prices.contains(price1));
	Assert.assertTrue(prices.contains(price3));
	Assert.assertTrue(prices.contains(price5));

	prices = stockDao.findPricesFrom(stock, date0);
	Assert.assertEquals(prices.size(), 3);
	Assert.assertTrue(prices.contains(price1));
	Assert.assertTrue(prices.contains(price3));
	Assert.assertTrue(prices.contains(price5));
	prices = stockDao.findPricesFrom(stock, date1);
	Assert.assertEquals(prices.size(), 3);
	Assert.assertTrue(prices.contains(price1));
	Assert.assertTrue(prices.contains(price3));
	Assert.assertTrue(prices.contains(price5));
	prices = stockDao.findPricesFrom(stock, date2);
	Assert.assertEquals(prices.size(), 2);
	Assert.assertTrue(prices.contains(price3));
	Assert.assertTrue(prices.contains(price5));
	prices = stockDao.findPricesFrom(stock, date3);
	Assert.assertEquals(prices.size(), 2);
	Assert.assertTrue(prices.contains(price3));
	Assert.assertTrue(prices.contains(price5));
	prices = stockDao.findPricesFrom(stock, date4);
	Assert.assertEquals(prices.size(), 1);
	Assert.assertTrue(prices.contains(price5));
	prices = stockDao.findPricesFrom(stock, date5);
	Assert.assertEquals(prices.size(), 1);
	Assert.assertTrue(prices.contains(price5));
	prices = stockDao.findPricesFrom(stock, date6);
	Assert.assertTrue(prices.isEmpty());

	prices = stockDao.findPricesFromInclusive(stock, date0);
	Assert.assertTrue(prices.isEmpty());
	prices = stockDao.findPricesFromInclusive(stock, date1);
	Assert.assertEquals(prices.size(), 3);
	Assert.assertTrue(prices.contains(price1));
	Assert.assertTrue(prices.contains(price3));
	Assert.assertTrue(prices.contains(price5));
	prices = stockDao.findPricesFromInclusive(stock, date2);
	Assert.assertEquals(prices.size(), 3);
	Assert.assertTrue(prices.contains(price1));
	Assert.assertTrue(prices.contains(price3));
	Assert.assertTrue(prices.contains(price5));
	prices = stockDao.findPricesFromInclusive(stock, date3);
	Assert.assertEquals(prices.size(), 2);
	Assert.assertTrue(prices.contains(price3));
	Assert.assertTrue(prices.contains(price5));
	prices = stockDao.findPricesFromInclusive(stock, date4);
	Assert.assertEquals(prices.size(), 2);
	Assert.assertTrue(prices.contains(price3));
	Assert.assertTrue(prices.contains(price5));
	prices = stockDao.findPricesFromInclusive(stock, date5);
	Assert.assertEquals(prices.size(), 1);
	Assert.assertTrue(prices.contains(price5));
	prices = stockDao.findPricesFromInclusive(stock, date6);
	Assert.assertEquals(prices.size(), 1);
	Assert.assertTrue(prices.contains(price5));

	prices = stockDao.findPricesBetween(stock, date0, date0);
	Assert.assertTrue(prices.isEmpty());
	prices = stockDao.findPricesBetween(stock, date0, date6);
	Assert.assertEquals(prices.size(), 3);
	Assert.assertTrue(prices.contains(price1));
	Assert.assertTrue(prices.contains(price3));
	Assert.assertTrue(prices.contains(price5));
	prices = stockDao.findPricesBetween(stock, date5, date5);
	Assert.assertEquals(prices.size(), 1);
	Assert.assertTrue(prices.contains(price5));
	prices = stockDao.findPricesBetween(stock, date5, date6);
	Assert.assertEquals(prices.size(), 1);
	Assert.assertTrue(prices.contains(price5));
	prices = stockDao.findPricesBetween(stock, date6, date6);
	Assert.assertTrue(prices.isEmpty());

	prices = stockDao.findPricesBetweenInclusive(stock, date0, date0);
	Assert.assertTrue(prices.isEmpty());
	prices = stockDao.findPricesBetweenInclusive(stock, date0, date1);
	Assert.assertTrue(prices.isEmpty());
	prices = stockDao.findPricesBetweenInclusive(stock, date0, date2);
	Assert.assertTrue(prices.isEmpty());
	prices = stockDao.findPricesBetweenInclusive(stock, date0, date3);
	Assert.assertTrue(prices.isEmpty());
	prices = stockDao.findPricesBetweenInclusive(stock, date0, date4);
	Assert.assertTrue(prices.isEmpty());
	prices = stockDao.findPricesBetweenInclusive(stock, date0, date5);
	Assert.assertTrue(prices.isEmpty());
	prices = stockDao.findPricesBetweenInclusive(stock, date0, date6);
	Assert.assertTrue(prices.isEmpty());
	prices = stockDao.findPricesBetweenInclusive(stock, date1, date5);
	Assert.assertEquals(prices.size(), 3);
	Assert.assertTrue(prices.contains(price1));
	Assert.assertTrue(prices.contains(price3));
	Assert.assertTrue(prices.contains(price5));
	prices = stockDao.findPricesBetweenInclusive(stock, date2, date4);
	Assert.assertEquals(prices.size(), 3);
	Assert.assertTrue(prices.contains(price1));
	Assert.assertTrue(prices.contains(price3));
	Assert.assertTrue(prices.contains(price5));
	prices = stockDao.findPricesBetweenInclusive(stock, date3, date5);
	Assert.assertEquals(prices.size(), 2);
	Assert.assertTrue(prices.contains(price3));
	Assert.assertTrue(prices.contains(price5));
	prices = stockDao.findPricesBetweenInclusive(stock, date4, date5);
	Assert.assertEquals(prices.size(), 2);
	Assert.assertTrue(prices.contains(price3));
	Assert.assertTrue(prices.contains(price5));
	prices = stockDao.findPricesBetweenInclusive(stock, date5, date5);
	Assert.assertEquals(prices.size(), 1);
	Assert.assertTrue(prices.contains(price5));
	prices = stockDao.findPricesBetweenInclusive(stock, date0, date6);
	Assert.assertTrue(prices.isEmpty());
	prices = stockDao.findPricesBetweenInclusive(stock, date1, date6);
	Assert.assertTrue(prices.isEmpty());
	prices = stockDao.findPricesBetweenInclusive(stock, date2, date6);
	Assert.assertTrue(prices.isEmpty());
	prices = stockDao.findPricesBetweenInclusive(stock, date3, date6);
	Assert.assertTrue(prices.isEmpty());
	prices = stockDao.findPricesBetweenInclusive(stock, date4, date6);
	Assert.assertTrue(prices.isEmpty());
	prices = stockDao.findPricesBetweenInclusive(stock, date5, date6);
	Assert.assertTrue(prices.isEmpty());
	prices = stockDao.findPricesBetweenInclusive(stock, date6, date6);
	Assert.assertTrue(prices.isEmpty());

	stockDao.deletePrices(stock);
	prices = stockDao.findPrices(stock);
	Assert.assertTrue(prices.isEmpty());

    }

}
