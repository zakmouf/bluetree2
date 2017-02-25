package com.zakmouf.bluetree.dao.impl;

import java.sql.Types;
import java.util.List;

import org.springframework.stereotype.Component;

import com.zakmouf.bluetree.dao.MarketDao;
import com.zakmouf.bluetree.dao.mapper.MarketRowMapper;
import com.zakmouf.bluetree.dao.mapper.StockRowMapper;
import com.zakmouf.bluetree.domain.Market;
import com.zakmouf.bluetree.domain.Stock;

@Component
public class MarketDaoImpl extends BaseDaoImpl implements MarketDao {

    @Override
    public Market findMarket(Long id) {
	String sql = "select * from markets where market_id=?";
	Object[] args = { id };
	int[] argTypes = { Types.NUMERIC };
	return queryForObject(sql, args, argTypes, new MarketRowMapper());
    }

    @Override
    public Market findMarket(String name) {
	String sql = "select * from markets where market_name=?";
	Object[] args = { name };
	int[] argTypes = { Types.VARCHAR };
	return queryForObject(sql, args, argTypes, new MarketRowMapper());
    }

    @Override
    public List<Market> getMarkets() {
	String sql = "select * from markets order by market_name";
	Object[] args = {};
	int[] argTypes = {};
	return queryForList(sql, args, argTypes, new MarketRowMapper());
    }

    @Override
    public void insertMarket(Market market) {
	String sql = "insert into markets values (null, ?, ?)";
	Object[] args = { market.getName(), market.getRiskless() };
	int[] argTypes = { Types.VARCHAR, Types.NUMERIC };
	insert(sql, args, argTypes, market);
    }

    @Override
    public void updateMarket(Market market) {
	String sql = "update markets set market_name=?, market_riskless=? where market_id=?";
	Object[] args = { market.getName(), market.getRiskless(), market.getId() };
	int[] argTypes = { Types.VARCHAR, Types.NUMERIC, Types.NUMERIC };
	update(sql, args, argTypes);
    }

    @Override
    public void deleteMarket(Market market) {
	//
	String sql = "";
	Object[] args = { market.getId() };
	int[] argTypes = { Types.NUMERIC };
	//
	sql = "select count(1) from portfolio_lnk_market where plm_market_id=?";
	if (queryForInteger(sql, args, argTypes) > 0) {
	    return;
	}
	//
	sql = "delete from markets where market_id=?";
	update(sql, args, argTypes);
	//
	sql = "delete from market_lnk_indice where mli_market_id=?";
	update(sql, args, argTypes);
	//
	sql = "delete from market_lnk_stock where mls_market_id=?";
	update(sql, args, argTypes);
    }

    @Override
    public Stock getIndice(Market market) {
	String sql = "select * from market_lnk_indice, stocks where mli_market_id=? and mli_indice_id=stock_id";
	Object[] args = { market.getId() };
	int[] argTypes = { Types.NUMERIC };
	return queryForObject(sql, args, argTypes, new StockRowMapper());
    }

    @Override
    public void setIndice(Market market, Stock indice) {
	//
	String sql = "delete from market_lnk_indice where mli_market_id=?";
	Object[] args = { market.getId() };
	int[] argTypes = { Types.NUMERIC };
	update(sql, args, argTypes);
	//
	sql = "insert into market_lnk_indice values (?, ?)";
	args = new Object[] { market.getId(), indice.getId() };
	argTypes = new int[] { Types.NUMERIC, Types.NUMERIC };
	update(sql, args, argTypes);
    }

    @Override
    public List<Stock> getStocks(Market market) {
	String sql = "select * from market_lnk_stock, stocks where mls_market_id=? and mls_stock_id=stock_id order by stock_symbol";
	Object[] args = { market.getId() };
	int[] argTypes = { Types.NUMERIC };
	return queryForList(sql, args, argTypes, new StockRowMapper());
    }

    @Override
    public void setStocks(Market market, List<Stock> stocks) {
	//
	String sql = "delete from market_lnk_stock where mls_market_id=?";
	Object[] args = { market.getId() };
	int[] argTypes = { Types.NUMERIC };
	update(sql, args, argTypes);
	//
	for (Stock stock : stocks) {
	    sql = "insert into market_lnk_stock values (?, ?)";
	    args = new Object[] { market.getId(), stock.getId() };
	    argTypes = new int[] { Types.NUMERIC, Types.NUMERIC };
	    update(sql, args, argTypes);
	}
    }

}