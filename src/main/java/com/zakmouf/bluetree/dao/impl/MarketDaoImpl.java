package com.zakmouf.bluetree.dao.impl;

import java.sql.Types;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zakmouf.bluetree.dao.MarketDao;
import com.zakmouf.bluetree.dao.mapper.MarketRowMapper;
import com.zakmouf.bluetree.dao.mapper.StockRowMapper;
import com.zakmouf.bluetree.domain.Market;
import com.zakmouf.bluetree.domain.Stock;

@Component
public class MarketDaoImpl extends BaseDaoImpl implements MarketDao {

    @Override
    @Transactional(readOnly = true)
    public Market findById(Long id) {
	String sql = "select * from v_market m where m.market_id = ?";
	Object[] args = { id };
	int[] argTypes = { Types.NUMERIC };
	Market market = queryForObject(sql, args, argTypes, new MarketRowMapper());
	return market;
    }

    @Override
    @Transactional(readOnly = true)
    public Market findByName(String name) {
	String sql = "select * from v_market m where m.market_name = ?";
	Object[] args = { name };
	int[] argTypes = { Types.VARCHAR };
	Market market = queryForObject(sql, args, argTypes, new MarketRowMapper());
	return market;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Market> findAll() {
	String sql = "select * from v_market m order by m.market_name";
	Object[] args = {};
	int[] argTypes = {};
	List<Market> markets = queryForList(sql, args, argTypes, new MarketRowMapper());
	return markets;
    }

    @Override
    @Transactional
    public void insert(Market market) {
	market.setId(getNextId());
	String sql = "insert into t_market (f_id, f_name, f_riskless, indice_id) values (?, ?, ?, ?)";
	Object[] args = { market.getId(), market.getName(), market.getRiskless(), market.getIndice().getId() };
	int[] argTypes = { Types.NUMERIC, Types.VARCHAR, Types.NUMERIC, Types.NUMERIC };
	insert(sql, args, argTypes);
    }

    @Override
    @Transactional
    public void update(Market market) {
	String sql = "update t_market set f_name = ?, f_riskless = ?, indice_id = ? where f_id = ?";
	Object[] args = { market.getName(), market.getRiskless(), market.getIndice().getId(), market.getId() };
	int[] argTypes = { Types.VARCHAR, Types.NUMERIC, Types.NUMERIC, Types.NUMERIC };
	update(sql, args, argTypes);
    }

    @Override
    @Transactional
    public void delete(Market market) {
	String sql = "delete from t_market where f_id = ?";
	Object[] args = { market.getId() };
	int[] argTypes = { Types.NUMERIC };
	delete(sql, args, argTypes);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Stock> findStocks(Long marketId) {
	String sql = "select s.* from t_market_stock ms join v_stock s on ms.stock_id = s.stock_id where ms.market_id = ? order by s.stock_symbol";
	Object[] args = { marketId };
	int[] argTypes = { Types.NUMERIC };
	List<Stock> stocks = queryForList(sql, args, argTypes, new StockRowMapper());
	return stocks;
    }

    @Override
    @Transactional
    public void insertStock(Long marketId, Long stockId) {
	String sql = "insert into t_market_stock (market_id, stock_id) values (?, ?)";
	Object[] args = new Object[] { marketId, stockId };
	int[] argTypes = new int[] { Types.NUMERIC, Types.NUMERIC };
	insert(sql, args, argTypes);
    }

    @Override
    @Transactional
    public void deleteStocks(Long marketId) {
	String sql = "delete from t_market_stock where market_id = ?";
	Object[] args = { marketId };
	int[] argTypes = { Types.NUMERIC };
	delete(sql, args, argTypes);
    }

}