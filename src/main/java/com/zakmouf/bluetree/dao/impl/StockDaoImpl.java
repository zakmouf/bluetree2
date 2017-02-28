package com.zakmouf.bluetree.dao.impl;

import java.sql.Types;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zakmouf.bluetree.dao.StockDao;
import com.zakmouf.bluetree.dao.mapper.StockRowMapper;
import com.zakmouf.bluetree.domain.Stock;

@Component
public class StockDaoImpl extends BaseDaoImpl implements StockDao {

    @Override
    @Transactional(readOnly = true)
    public Stock findById(Long id) {
	String sql = "select s.* from v_stock s where s.stock_id = ?";
	Object[] args = { id };
	int[] argTypes = { Types.NUMERIC };
	Stock stock = queryForObject(sql, args, argTypes, new StockRowMapper());
	return stock;
    }

    @Override
    @Transactional(readOnly = true)
    public Stock findBySymbol(String symbol) {
	String sql = "select s.* from v_stock s where s.stock_symbol = ?";
	Object[] args = { symbol };
	int[] argTypes = { Types.VARCHAR };
	Stock stock = queryForObject(sql, args, argTypes, new StockRowMapper());
	return stock;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Stock> findAll() {
	String sql = "select s.* from v_stock s order by s.stock_symbol";
	Object[] args = {};
	int[] argTypes = {};
	List<Stock> stocks = queryForList(sql, args, argTypes, new StockRowMapper());
	Collections.sort(stocks);
	return stocks;
    }

    @Override
    @Transactional
    public void insert(Stock stock) {
	stock.setId(getNextId());
	String sql = "insert into t_stock (f_id, f_symbol, f_name) values (?, ?, ?)";
	Object[] args = { stock.getId(), stock.getSymbol(), stock.getName() };
	int[] argTypes = { Types.NUMERIC, Types.VARCHAR, Types.VARCHAR };
	insert(sql, args, argTypes);
    }

    @Override
    @Transactional
    public void update(Stock stock) {
	String sql = "update t_stock set f_symbol= ?, f_name = ? where f_id = ?";
	Object[] args = { stock.getSymbol(), stock.getName(), stock.getId() };
	int[] argTypes = { Types.VARCHAR, Types.VARCHAR, Types.NUMERIC };
	update(sql, args, argTypes);
    }

    @Override
    @Transactional
    public void delete(Stock stock) {
	String sql = "delete from t_stock where f_id = ?";
	Object[] args = { stock.getId() };
	int[] argTypes = { Types.NUMERIC };
	delete(sql, args, argTypes);
    }

}