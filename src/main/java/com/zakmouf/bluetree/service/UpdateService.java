package com.zakmouf.bluetree.service;

import java.util.List;

import com.zakmouf.bluetree.domain.Stock;

public interface UpdateService {

    void updateNames(Stock stock);

    void updateNames(List<Stock> stocks);

    void updatePrices(Stock stock);

    void updatePrices(List<Stock> stocks);

}
