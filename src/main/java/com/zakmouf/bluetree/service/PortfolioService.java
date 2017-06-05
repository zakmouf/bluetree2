package com.zakmouf.bluetree.service;

import com.zakmouf.bluetree.domain.Holding;
import com.zakmouf.bluetree.domain.Portfolio;

import java.util.List;

public interface PortfolioService {

    Portfolio getPortfolio(Long id);

    Portfolio getPortfolio(String name);

    List<Portfolio> getPortfolios();

    void savePortfolio(Portfolio portfolio);

    void deletePortfolio(Portfolio portfolio);

    List<Holding> getHoldings(Portfolio portfolio);

    void setHoldings(Portfolio portfolio, List<Holding> holdings);

}
