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
import com.zakmouf.bluetree.domain.Price;
import com.zakmouf.bluetree.domain.Stock;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PriceDaoTest extends BaseTest {

    @Autowired
    private StockDao stockDao;
    @Autowired
    private PriceDao priceDao;

    @Test
    @Transactional
    public void doTest() {

	Stock stock = new Stock("symbol");
	stockDao.insert(stock);
	Long stockId = stock.getId();

	List<Price> prices = priceDao.findAll(stockId);
	Assert.assertNotNull(prices);
	Assert.assertTrue(prices.isEmpty());

    }

}
