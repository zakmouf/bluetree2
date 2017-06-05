package com.zakmouf.bluetree.service;

import com.zakmouf.bluetree.domain.Stock;

import java.util.List;

public interface StockService {

    Stock getStock(Long id);

    Stock getStock(String symbol);

    List<Stock> getStocks();

    void saveStock(Stock stock);

    void saveStocks(List<Stock> stocks);

    void deleteStock(Stock stock);

    void deleteStocks(List<Stock> stocks);

}
