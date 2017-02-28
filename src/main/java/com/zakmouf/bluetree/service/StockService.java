package com.zakmouf.bluetree.service;

import java.util.List;

import com.zakmouf.bluetree.domain.Stock;

public interface StockService {
    
    Stock getStock(Long id);
    
    Stock getStock(String symbol);
    
    List<Stock> getStocks();
    
    void saveStock(Stock stock);
    
    void deleteStock(Stock stock);

}
