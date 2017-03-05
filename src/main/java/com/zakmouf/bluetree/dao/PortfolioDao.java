package com.zakmouf.bluetree.dao;

import java.util.List;

import com.zakmouf.bluetree.domain.Holding;
import com.zakmouf.bluetree.domain.Portfolio;

public interface PortfolioDao {

    Portfolio findById(Long id);

    Portfolio findByName(String name);

    List<Portfolio> findAll();

    void insert(Portfolio portfolio);

    void update(Portfolio portfolio);

    void delete(Portfolio portfolio);

    List<Holding> findHoldings(Portfolio portfolio);

    void insertHolding(Portfolio portfolio, Holding holding);

    void deleteAllHoldings(Portfolio portfolio);

}