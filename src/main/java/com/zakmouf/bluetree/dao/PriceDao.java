package com.zakmouf.bluetree.dao;

import java.util.List;

import com.zakmouf.bluetree.domain.Price;

public interface PriceDao {

    List<Price> findAll(Long stockId);

    void insert(Long stockId, Price price);

    void deleteAll(Long stockId);

}