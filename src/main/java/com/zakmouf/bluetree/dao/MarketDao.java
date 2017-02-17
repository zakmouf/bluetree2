package com.zakmouf.bluetree.dao;

import java.util.List;

import com.zakmouf.bluetree.domain.Market;
import com.zakmouf.bluetree.domain.Profile;
import com.zakmouf.bluetree.domain.Stock;

public interface MarketDao {

    Market findMarket(Long id);

    Market findMarket(String name);

    List<Market> getMarkets();

    void insertMarket(Market market);

    void updateMarket(Market market);

    void deleteMarket(Market market);

    Stock getIndice(Market market);

    void setIndice(Market market, Stock indice);

    List<Stock> getStocks(Market market);

    void setStocks(Market market, List<Stock> stocks);

    List<Profile> getProfiles(Market market);

    void setProfiles(Market market, List<Profile> profiles);

}