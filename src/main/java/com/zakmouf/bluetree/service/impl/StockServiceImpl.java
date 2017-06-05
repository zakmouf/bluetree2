package com.zakmouf.bluetree.service.impl;

import com.zakmouf.bluetree.dao.StockDao;
import com.zakmouf.bluetree.domain.Stock;
import com.zakmouf.bluetree.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class StockServiceImpl extends BaseServiceImpl implements StockService {

    @Autowired
    private StockDao stockDao;

    @Override
    @Transactional(readOnly = true)
    public Stock getStock(Long id) {
        return stockDao.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Stock getStock(String symbol) {
        return stockDao.findBySymbol(symbol);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Stock> getStocks() {
        List<Stock> stocks = stockDao.findAll();
        return stocks;
    }

    @Override
    @Transactional
    public void saveStock(Stock stock) {
        if (stock.getId() == null) {
            logger.info(msg("insert stock=[%1$s]", stock));
            stockDao.insert(stock);
        } else {
            logger.info(msg("update stock=[%1$s]", stock));
            stockDao.update(stock);
        }
    }

    @Override
    @Transactional
    public void saveStocks(List<Stock> stocks) {
        for (Stock stock : stocks) {
            saveStock(stock);
        }
    }

    @Override
    @Transactional
    public void deleteStock(Stock stock) {
        logger.info(msg("delete stock=[%1$s]", stock));
        stockDao.delete(stock);
    }

    @Override
    @Transactional
    public void deleteStocks(List<Stock> stocks) {
        for (Stock stock : stocks) {
            deleteStock(stock);
        }
    }

}
