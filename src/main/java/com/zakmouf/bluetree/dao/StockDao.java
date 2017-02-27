package com.zakmouf.bluetree.dao;

import java.util.Date;
import java.util.List;

import com.zakmouf.bluetree.domain.Price;
import com.zakmouf.bluetree.domain.Stock;

public interface StockDao {

    Stock findById(Long id);

    Stock findBySymbol(String symbol);

    List<Stock> findAll();

    void insert(Stock stock);

    void update(Stock stock);

    void delete(Stock stock);

    Date findLastDate(Stock stock);

    List<Price> findPrices(Stock stock);

    List<Price> findPricesBetween(Stock stock, Date fromDate, Date toDate);

    List<Price> findPricesBetweenInclusive(Stock stock, Date fromDate, Date toDate);

    List<Price> findPricesFrom(Stock stock, Date fromDate);

    List<Price> findPricesFromInclusive(Stock stock, Date fromDate);

    void insertPrices(Stock stock, List<Price> prices);

    void deletePrices(Stock stock);

}