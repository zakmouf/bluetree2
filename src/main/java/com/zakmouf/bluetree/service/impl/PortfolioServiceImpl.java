package com.zakmouf.bluetree.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zakmouf.bluetree.dao.PortfolioDao;
import com.zakmouf.bluetree.domain.Holding;
import com.zakmouf.bluetree.domain.Portfolio;
import com.zakmouf.bluetree.service.PortfolioService;

@Component
public class PortfolioServiceImpl extends BaseServiceImpl implements PortfolioService {

    @Autowired
    private PortfolioDao portfolioDao;

    @Override
    @Transactional(readOnly = true)
    public Portfolio getPortfolio(Long id) {
	Portfolio portfolio = portfolioDao.findById(id);
	return portfolio;
    }

    @Override
    @Transactional(readOnly = true)
    public Portfolio getPortfolio(String name) {
	Portfolio portfolio = portfolioDao.findByName(name);
	return portfolio;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Portfolio> getPortfolios() {
	List<Portfolio> portfolios = portfolioDao.findAll();
	return portfolios;
    }

    @Override
    @Transactional
    public void savePortfolio(Portfolio portfolio) {
	if (portfolio.getId() == null) {
	    logger.info(msg("insert portfolio=[%1$s]", portfolio));
	    portfolioDao.insert(portfolio);
	} else {
	    logger.info(msg("update portfolio=[%1$s]", portfolio));
	    portfolioDao.update(portfolio);
	}

    }

    @Override
    @Transactional
    public void deletePortfolio(Portfolio portfolio) {
	logger.info(msg("delete portfolio=[%1$s]", portfolio));
	portfolioDao.delete(portfolio);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Holding> getHoldings(Portfolio portfolio) {
	List<Holding> holdings = portfolioDao.findHoldings(portfolio);
	return holdings;
    }

    @Override
    @Transactional
    public void setHoldings(Portfolio portfolio, List<Holding> holdings) {
	// TODO Auto-generated method stub

    }

}
