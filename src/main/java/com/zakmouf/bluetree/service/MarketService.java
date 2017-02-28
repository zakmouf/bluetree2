package com.zakmouf.bluetree.service;

import java.util.List;

import com.zakmouf.bluetree.domain.Market;
import com.zakmouf.bluetree.domain.Stock;

public interface MarketService {

    Market getMarket(Long id);

    Market getMarket(String name);

    List<Market> getMarkets();

    void saveMarket(Market market);

    void deleteMarket(Market market);

    List<Stock> getStocks(Market market);

    void setStocks(Market market, List<Stock> stocks);

}
