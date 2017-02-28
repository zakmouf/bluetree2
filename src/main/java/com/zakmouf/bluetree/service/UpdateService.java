package com.zakmouf.bluetree.service;

import java.util.List;

import com.zakmouf.bluetree.domain.Stock;

public interface UpdateService {

    void updateNames(List<Stock> stocks);

    void updatePrices(List<Stock> stocks);

}
