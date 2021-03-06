package com.zakmouf.bluetree.dao;

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

import java.util.Date;
import java.util.List;

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

        List<Price> prices = priceDao.findAll(stock);
        Assert.assertNotNull(prices);
        Assert.assertTrue(prices.isEmpty());

        Date date = parseDate("2012-12-31");
        Double value = 123.456D;

        Price price = new Price();
        price.setDate(date);
        price.setValue(value);

        priceDao.insert(stock, price);

        prices = priceDao.findAll(stock);
        Assert.assertNotNull(prices);
        Assert.assertTrue(prices.size() == 1);

        priceDao.deleteAll(stock);

        prices = priceDao.findAll(stock);
        Assert.assertNotNull(prices);
        Assert.assertTrue(prices.isEmpty());

    }

}
