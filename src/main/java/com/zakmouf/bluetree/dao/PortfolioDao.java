package com.zakmouf.bluetree.dao;

import java.util.List;

import com.zakmouf.bluetree.domain.Market;
import com.zakmouf.bluetree.domain.Portfolio;
import com.zakmouf.bluetree.domain.Position;

public interface PortfolioDao {

    Portfolio findPortfolio(Long id);

    Portfolio findPortfolio(String name);

    List<Portfolio> getPortfolios();

    void insertPortfolio(Portfolio portfolio);

    void updatePortfolio(Portfolio portfolio);

    void deletePortfolio(Portfolio portfolio);

    Market getMarket(Portfolio portfolio);

    void setMarket(Portfolio portfolio, Market market);

    List<Position> getPositions(Portfolio portfolio);

    void setPositions(Portfolio portfolio, List<Position> positions);

}