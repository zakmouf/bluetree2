package com.zakmouf.bluetree.service;

import java.util.List;

import com.zakmouf.bluetree.domain.Holding;
import com.zakmouf.bluetree.domain.Portfolio;

public interface PortfolioService {

    Portfolio getPortfolio(Long id);

    Portfolio getPortfolio(String name);

    List<Portfolio> getPortfolios();

    void savePortfolio(Portfolio portfolio);

    void deletePortfolio(Portfolio portfolio);

    List<Holding> getHoldings(Portfolio portfolio);

    void setHoldings(Portfolio portfolio, List<Holding> holdings);

}
