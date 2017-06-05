package com.zakmouf.bluetree.service;

import com.zakmouf.bluetree.domain.Stock;

import java.util.List;

public interface UpdateService {

    void updateNames(Stock stock);

    void updateNames(List<Stock> stocks);

    void updatePrices(Stock stock);

    void updatePrices(List<Stock> stocks);

}
