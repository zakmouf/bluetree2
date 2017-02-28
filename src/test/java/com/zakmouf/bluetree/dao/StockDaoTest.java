package com.zakmouf.bluetree.dao;

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
public class StockDaoTest extends BaseTest {

    @Autowired
    private StockDao stockDao;

    @Test
    @Transactional
    public void doTest() {

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

}
