package com.zakmouf.bluetree.dao;

import java.util.List;

import com.zakmouf.bluetree.domain.Price;
import com.zakmouf.bluetree.domain.Stock;

public interface PriceDao {

    List<Price> findAll(Stock stock);

    void insert(Stock stock, Price price);

    void deleteAll(Stock stock);

}