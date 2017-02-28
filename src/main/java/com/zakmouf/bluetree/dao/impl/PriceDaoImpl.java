package com.zakmouf.bluetree.dao.impl;

import java.sql.Types;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zakmouf.bluetree.dao.PriceDao;
import com.zakmouf.bluetree.dao.mapper.PriceRowMapper;
import com.zakmouf.bluetree.domain.Price;

@Component
public class PriceDaoImpl extends BaseDaoImpl implements PriceDao {

    @Override
    @Transactional(readOnly = true)
    public List<Price> findAll(Long stockId) {
	String sql = "select * from v_price p where p.stock_id = ? order by p.price_date";
	Object[] args = { stockId };
	int[] argTypes = { Types.NUMERIC };
	List<Price> prices = queryForList(sql, args, argTypes, new PriceRowMapper());
	return prices;
    }

    @Override
    @Transactional
    public void insert(Long stockId, Price price) {
	String sql = "insert into t_price (stock_id, f_date, f_value) values (?, ?, ?)";
	Object[] args = { stockId, price.getDate(), price.getValue() };
	int[] argTypes = { Types.NUMERIC, Types.DATE, Types.NUMERIC };
	insert(sql, args, argTypes);
    }

    @Override
    @Transactional
    public void deleteAll(Long stockId) {
	String sql = "delete from t_price where stock_id = ?";
	Object[] args = { stockId };
	int[] argTypes = { Types.NUMERIC };
	delete(sql, args, argTypes);
    }

}