package com.zakmouf.bluetree.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zakmouf.bluetree.dao.MarketDao;
import com.zakmouf.bluetree.domain.Market;
import com.zakmouf.bluetree.domain.Stock;
import com.zakmouf.bluetree.service.MarketService;

@Component
public class MarketServiceImpl extends BaseServiceImpl implements MarketService {

    @Autowired
    private MarketDao marketDao;

    @Override
    @Transactional(readOnly = true)
    public Market getMarket(Long id) {
	Market market = marketDao.findById(id);
	return market;
    }

    @Override
    @Transactional(readOnly = true)
    public Market getMarket(String name) {
	Market market = marketDao.findByName(name);
	return market;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Market> getMarkets() {
	List<Market> markets = marketDao.findAll();
	return markets;
    }

    @Override
    @Transactional
    public void saveMarket(Market market) {
	if (market.getId() == null) {
	    logger.info(msg("insert market=[%1$s]", market));
	    marketDao.insert(market);
	} else {
	    logger.info(msg("update market=[%1$s]", market));
	    marketDao.update(market);
	}
    }

    @Override
    @Transactional
    public void deleteMarket(Market market) {
	logger.info(msg("delete market=[%1$s]", market));
	marketDao.delete(market);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Stock> getStocks(Market market) {
	List<Stock> stocks = marketDao.findStocks(market);
	return stocks;
    }

    @Override
    @Transactional
    public void setStocks(Market market, List<Stock> stocks) {
	logger.info(msg("delete all stocks for market=[%1$s]", market));
	marketDao.deleteAllStocks(market);
	for (Stock stock : stocks) {
	    logger.info(msg("insert stock=[%1$s] for market=[%2$s]", stock, market));
	    marketDao.insertStock(market, stock);
	}
    }

}
