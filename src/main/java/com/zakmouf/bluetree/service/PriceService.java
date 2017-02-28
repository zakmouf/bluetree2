package com.zakmouf.bluetree.service;

import java.util.List;

import com.zakmouf.bluetree.domain.Price;
import com.zakmouf.bluetree.domain.Stock;

public interface PriceService {

    List<Price> getPrices(Stock stock);

    void addPrice(Stock stock, Price price);

    void addPrices(Stock stock, List<Price> prices);

    void deleteAllPrices(Stock stock);

}
