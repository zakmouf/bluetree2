package com.zakmouf.bluetree.dao;

import com.zakmouf.bluetree.domain.Stock;

import java.util.List;

public interface StockDao {

    Stock findById(Long id);

    Stock findBySymbol(String symbol);

    List<Stock> findAll();

    void insert(Stock stock);

    void update(Stock stock);

    void delete(Stock stock);

}