package com.zakmouf.bluetree.dao.impl;

import java.sql.Types;
import java.util.Collections;
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
    public Market findById(Long id) {
	String sql = "select * from v_market m where m.market_id = ?";
	Object[] args = { id };
	int[] argTypes = { Types.NUMERIC };
	return queryForObject(sql, args, argTypes, new MarketRowMapper());
    }

    @Override
    public Market findByName(String name) {
	String sql = "select * from v_market m where m.market_name = ?";
	Object[] args = { name };
	int[] argTypes = { Types.VARCHAR };
	return queryForObject(sql, args, argTypes, new MarketRowMapper());
    }

    @Override
    public List<Market> findAll() {
	String sql = "select * from v_market m order by m.market_name";
	Object[] args = {};
	int[] argTypes = {};
	return queryForList(sql, args, argTypes, new MarketRowMapper());
    }

    @Override
    public void insert(Market market) {
	market.setId(getNextId());
	String sql = "insert into t_market (f_id, f_name, f_riskless, indice_id) values (?, ?, ?, ?)";
	Object[] args = { market.getId(), market.getName(), market.getRiskless(), market.getIndice().getId() };
	int[] argTypes = { Types.NUMERIC, Types.VARCHAR, Types.NUMERIC, Types.NUMERIC };
	insert(sql, args, argTypes);
    }

    @Override
    public void update(Market market) {
	String sql = "update t_market set f_name = ?, f_riskless = ?, indice_id = ? where f_id = ?";
	Object[] args = { market.getName(), market.getRiskless(), market.getIndice().getId(), market.getId() };
	int[] argTypes = { Types.VARCHAR, Types.NUMERIC, Types.NUMERIC, Types.NUMERIC };
	update(sql, args, argTypes);
    }

    @Override
    public void delete(Market market) {
	String sql = "delete from t_market where f_id = ?";
	Object[] args = { market.getId() };
	int[] argTypes = { Types.NUMERIC };
	delete(sql, args, argTypes);
    }

    @Override
    public List<Stock> getStocks(Market market) {
	String sql = "select s.* from t_market_stock ms join v_stock s on ms.stock_id = s.stock_id where ms.market_id = ?";
	Object[] args = { market.getId() };
	int[] argTypes = { Types.NUMERIC };
	List<Stock> stocks = queryForList(sql, args, argTypes, new StockRowMapper());
	Collections.sort(stocks);
	return stocks;
    }

    @Override
    public void setStocks(Market market, List<Stock> stocks) {
	//
	String sql = "delete from t_market_stock where market_id = ?";
	Object[] args = { market.getId() };
	int[] argTypes = { Types.NUMERIC };
	update(sql, args, argTypes);
	//
	for (Stock stock : stocks) {
	    sql = "insert into t_market_stock (market_id, stock_id) values (?, ?)";
	    args = new Object[] { market.getId(), stock.getId() };
	    argTypes = new int[] { Types.NUMERIC, Types.NUMERIC };
	    update(sql, args, argTypes);
	}
    }

}