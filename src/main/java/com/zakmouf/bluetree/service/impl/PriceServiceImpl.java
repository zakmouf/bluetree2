package com.zakmouf.bluetree.service.impl;

import com.zakmouf.bluetree.dao.PriceDao;
import com.zakmouf.bluetree.domain.Price;
import com.zakmouf.bluetree.domain.Stock;
import com.zakmouf.bluetree.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class PriceServiceImpl extends BaseServiceImpl implements PriceService {

    @Autowired
    private PriceDao priceDao;

    @Override
    @Transactional(readOnly = true)
    public List<Price> getPrices(Stock stock) {
        List<Price> prices = priceDao.findAll(stock);
        return prices;
    }

    @Override
    @Transactional
    public void addPrice(Stock stock, Price price) {
        logger.debug(msg("insert price=[%1$s] for stock=[%2$s]", price, stock));
        priceDao.insert(stock, price);

    }

    @Override
    @Transactional
    public void addPrices(Stock stock, List<Price> prices) {
        for (Price price : prices) {
            addPrice(stock, price);
        }
    }

    @Override
    @Transactional
    public void deleteAllPrices(Stock stock) {
        logger.info(msg("delete all prices for stock=[%1$s]", stock));
        priceDao.deleteAll(stock);
    }

    @Override
    @Transactional
    public void deleteAllPrices(List<Stock> stocks) {
        for (Stock stock : stocks) {
            deleteAllPrices(stock);
        }
    }

}
