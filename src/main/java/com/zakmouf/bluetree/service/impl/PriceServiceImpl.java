package com.zakmouf.bluetree.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zakmouf.bluetree.dao.PriceDao;
import com.zakmouf.bluetree.domain.Price;
import com.zakmouf.bluetree.domain.Stock;
import com.zakmouf.bluetree.service.PriceService;

@Component
public class PriceServiceImpl extends BaseServiceImpl implements PriceService {

    @Autowired
    private PriceDao priceDao;

    @Override
    @Transactional(readOnly = true)
    public List<Price> getPrices(Stock stock) {
	List<Price> prices = priceDao.findAll(stock.getId());
	return prices;
    }

    @Override
    @Transactional
    public void addPrices(Stock stock, List<Price> prices) {
	for (Price price : prices) {
	    priceDao.insert(stock.getId(), price);
	}
    }

    @Override
    @Transactional
    public void deleteAllPrices(Stock stock) {
	priceDao.deleteAll(stock.getId());
    }

}
