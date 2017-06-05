package com.zakmouf.bluetree.dao;

import com.zakmouf.bluetree.BaseTest;
import com.zakmouf.bluetree.domain.Stock;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StockDaoTest extends BaseTest {

    @Autowired
    private StockDao stockDao;

    @Test
    @Transactional
    public void doTest() {

        String symbol = "symbol";
        String name = "name";

        Stock stock = new Stock();
        stock.setSymbol(symbol);
        stock.setName(name);

        Assert.assertNull(stockDao.findBySymbol(symbol));

        stockDao.insert(stock);
        Long id = stock.getId();
        Assert.assertNotNull(id);

        stock = stockDao.findById(id);
        Assert.assertNotNull(stock);

        stock = stockDao.findBySymbol(symbol);
        Assert.assertNotNull(stock);

        List<Stock> stocks = stockDao.findAll();
        Assert.assertTrue(stocks.contains(stock));

        symbol = "#" + symbol + "#";
        name = "#" + name + "#";

        stock.setSymbol(symbol);
        stock.setName(name);

        stockDao.update(stock);
        stock = stockDao.findBySymbol(symbol);
        Assert.assertNotNull(stock);

        stockDao.delete(stock);
        Assert.assertNull(stockDao.findById(id));
        Assert.assertNull(stockDao.findBySymbol(symbol));

    }

}
