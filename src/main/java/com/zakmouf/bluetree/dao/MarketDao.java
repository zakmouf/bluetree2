package com.zakmouf.bluetree.dao;

import java.util.List;

import com.zakmouf.bluetree.domain.Market;
import com.zakmouf.bluetree.domain.Stock;

public interface MarketDao {

    Market findById(Long id);

    Market findByName(String name);

    List<Market> findAll();

    void insert(Market market);

    void update(Market market);

    void delete(Market market);

    List<Stock> getStocks(Market market);

    void setStocks(Market market, List<Stock> stocks);

}