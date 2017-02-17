package com.zakmouf.bluetree.dao;

import java.util.Date;
import java.util.List;

import com.zakmouf.bluetree.domain.Price;
import com.zakmouf.bluetree.domain.Stock;

public interface StockDao {

    Stock findStock(Long id);

    Stock findStock(String symbol);

    List<Stock> getStocks();

    void insertStock(Stock stock);

    void updateStock(Stock stock);

    void deleteStock(Stock stock);

    List<Price> getPrices(Stock stock);

    void addPrices(Stock stock, List<Price> prices);

    void deletePrices(Stock stock);

    Date getLastDate(Stock stock);

    List<Price> getPrices(Stock stock, Date fromDate, Date toDate);

    List<Price> getPricesInclusive(Stock stock, Date fromDate, Date toDate);

    List<Price> getPricesInclusive(Stock stock, Date fromDate);

}