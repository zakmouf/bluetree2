package com.zakmouf.bluetree.dao.impl;

import java.sql.Types;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zakmouf.bluetree.dao.PriceDao;
import com.zakmouf.bluetree.dao.mapper.PriceRowMapper;
import com.zakmouf.bluetree.domain.Price;
import com.zakmouf.bluetree.domain.Stock;

@Component
public class PriceDaoImpl extends BaseDaoImpl implements PriceDao {

    @Override
    @Transactional(readOnly = true)
    public List<Price> findAll(Stock stock) {
	String sql = "select * from v_price p where p.stock_id = ? order by p.price_date";
	Object[] args = { stock.getId() };
	int[] argTypes = { Types.NUMERIC };
	List<Price> prices = queryForList(sql, args, argTypes, new PriceRowMapper());
	return prices;
    }

    @Override
    @Transactional
    public void insert(Stock stock, Price price) {
	String sql = "insert into t_price (stock_id, f_date, f_value) values (?, ?, ?)";
	Object[] args = { stock.getId(), price.getDate(), price.getValue() };
	int[] argTypes = { Types.NUMERIC, Types.DATE, Types.NUMERIC };
	insert(sql, args, argTypes);
    }

    @Override
    @Transactional
    public void deleteAll(com.zakmouf.bluetree.domain.Stock stock) {
	String sql = "delete from t_price where stock_id = ?";
	Object[] args = { stock.getId() };
	int[] argTypes = { Types.NUMERIC };
	delete(sql, args, argTypes);
    }

}