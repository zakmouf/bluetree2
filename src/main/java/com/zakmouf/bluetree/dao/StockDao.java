package com.zakmouf.bluetree.dao;

import java.util.List;

import com.zakmouf.bluetree.domain.Stock;

public interface StockDao {

    Stock findById(Long id);

    Stock findBySymbol(String symbol);

    List<Stock> findAll();

    void insert(Stock stock);

    void update(Stock stock);

    void delete(Stock stock);

}