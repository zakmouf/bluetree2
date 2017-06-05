package com.zakmouf.bluetree.service;

import com.zakmouf.bluetree.domain.Price;
import com.zakmouf.bluetree.domain.Stock;

import java.util.List;

public interface PriceService {

    List<Price> getPrices(Stock stock);

    void addPrice(Stock stock, Price price);

    void addPrices(Stock stock, List<Price> prices);

    void deleteAllPrices(List<Stock> stocks);

    void deleteAllPrices(Stock stock);

}
