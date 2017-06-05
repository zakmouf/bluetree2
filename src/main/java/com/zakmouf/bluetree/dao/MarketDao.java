package com.zakmouf.bluetree.dao;

import com.zakmouf.bluetree.domain.Market;
import com.zakmouf.bluetree.domain.Stock;

import java.util.List;

public interface MarketDao {

    Market findById(Long id);

    Market findByName(String name);

    List<Market> findAll();

    void insert(Market market);

    void update(Market market);

    void delete(Market market);

    List<Stock> findStocks(Market market);

    void insertStock(Market market, Stock stock);

    void deleteAllStocks(Market market);

}