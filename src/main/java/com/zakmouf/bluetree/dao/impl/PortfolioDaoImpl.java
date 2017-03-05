package com.zakmouf.bluetree.dao.impl;

import java.sql.Types;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zakmouf.bluetree.dao.PortfolioDao;
import com.zakmouf.bluetree.dao.mapper.HoldingRowMapper;
import com.zakmouf.bluetree.dao.mapper.PortfolioRowMapper;
import com.zakmouf.bluetree.domain.Holding;
import com.zakmouf.bluetree.domain.Portfolio;

@Component
public class PortfolioDaoImpl extends BaseDaoImpl implements PortfolioDao {

    @Override
    @Transactional(readOnly = true)
    public Portfolio findById(Long id) {
	String sql = "select p.* from v_portfolio p where p.portfolio_id = ?";
	Object[] args = { id };
	int[] argTypes = { Types.NUMERIC };
	Portfolio portfolio = queryForObject(sql, args, argTypes, new PortfolioRowMapper());
	return portfolio;
    }

    @Override
    @Transactional(readOnly = true)
    public Portfolio findByName(String name) {
	String sql = "select p.* from v_portfolio p where p.portfolio_name = ?";
	Object[] args = { name };
	int[] argTypes = { Types.VARCHAR };
	Portfolio portfolio = queryForObject(sql, args, argTypes, new PortfolioRowMapper());
	return portfolio;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Portfolio> findAll() {
	String sql = "select * from portfolios order by portfolio_name";
	Object[] args = {};
	int[] argTypes = {};
	List<Portfolio> portfolios = queryForList(sql, args, argTypes, new PortfolioRowMapper());
	return portfolios;
    }

    @Override
    @Transactional
    public void insert(Portfolio portfolio) {
	portfolio.setId(getNextId());
	String sql = "insert into t_portfolio values (?, ?, ?, ?, ?, ?, ?)";
	Object[] args = { portfolio.getId(), portfolio.getName(), portfolio.getFromDate(), portfolio.getToDate(),
		portfolio.getBeta(), portfolio.getSize(), portfolio.getMarket().getId() };
	int[] argTypes = { Types.NUMERIC, Types.VARCHAR, Types.DATE, Types.DATE, Types.NUMERIC, Types.NUMERIC,
		Types.NUMERIC };
	insert(sql, args, argTypes);
    }

    @Override
    @Transactional
    public void update(Portfolio portfolio) {
	String sql = "update t_portfolio set f_name = ?, f_from_date = ?, f_to_date = ?, f_beta = ?, f_size = ?, market_id = ? where f_id = ?";
	Object[] args = { portfolio.getName(), portfolio.getFromDate(), portfolio.getToDate(), portfolio.getBeta(),
		portfolio.getSize(), portfolio.getMarket().getId(), portfolio.getId() };
	int[] argTypes = { Types.VARCHAR, Types.DATE, Types.DATE, Types.NUMERIC, Types.NUMERIC, Types.NUMERIC,
		Types.NUMERIC };
	update(sql, args, argTypes);
    }

    @Override
    @Transactional
    public void delete(Portfolio portfolio) {
	String sql = "delete from t_portfolio where f_id = ?";
	Object[] args = { portfolio.getId() };
	int[] argTypes = { Types.NUMERIC };
	delete(sql, args, argTypes);
    }

    @Override
    public List<Holding> findHoldings(Portfolio portfolio) {
	String sql = "select h.* from v_holding where h.portfolio_id = ? order by h.holding_weight desc";
	Object[] args = { portfolio.getId() };
	int[] argTypes = { Types.NUMERIC };
	return queryForList(sql, args, argTypes, new HoldingRowMapper());
    }

    @Override
    public void insertHolding(Portfolio portfolio, Holding holding) {
	String sql = "insert into t_holding values (?, ?, ?)";
	Object[] args = new Object[] { portfolio.getId(), holding.getStock().getId(), holding.getWeight() };
	int[] argTypes = new int[] { Types.NUMERIC, Types.NUMERIC, Types.NUMERIC };
	insert(sql, args, argTypes);
    }

    @Override
    public void deleteAllHoldings(Portfolio portfolio) {
	String sql = "delete from t_holding where portfolio_id = ?";
	Object[] args = { portfolio.getId() };
	int[] argTypes = { Types.NUMERIC };
	delete(sql, args, argTypes);
    }

}