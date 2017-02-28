package com.zakmouf.bluetree.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zakmouf.bluetree.dao.StockDao;
import com.zakmouf.bluetree.domain.Stock;
import com.zakmouf.bluetree.service.StockService;

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
	    stockDao.insert(stock);
	} else {
	    stockDao.update(stock);
	}

    }

    @Override
    @Transactional
    public void deleteStock(Stock stock) {
	stockDao.delete(stock);
    }

}
