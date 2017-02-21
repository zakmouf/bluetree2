package com.zakmouf.bluetree.dao.impl;

import java.sql.Types;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.zakmouf.bluetree.dao.StockDao;
import com.zakmouf.bluetree.dao.mapper.PriceRowMapper;
import com.zakmouf.bluetree.dao.mapper.StockRowMapper;
import com.zakmouf.bluetree.domain.Price;
import com.zakmouf.bluetree.domain.Stock;

@Component
public class StockDaoImpl extends BaseDaoImpl implements StockDao {

    @Override
    public Stock findStock(Long id) {
	String sql = "select * from stocks where stock_id=?";
	Object[] args = { id };
	int[] argTypes = { Types.NUMERIC };
	return queryForObject(sql, args, argTypes, new StockRowMapper());
    }

    @Override
    public Stock findStock(String symbol) {
	String sql = "select * from stocks where stock_symbol=?";
	Object[] args = { symbol };
	int[] argTypes = { Types.VARCHAR };
	return queryForObject(sql, args, argTypes, new StockRowMapper());
    }

    @Override
    public List<Stock> getStocks() {
	String sql = "select * from stocks order by stock_symbol";
	Object[] args = {};
	int[] argTypes = {};
	return queryForList(sql, args, argTypes, new StockRowMapper());
    }

    @Override
    public void insertStock(Stock stock) {
	String sql = "insert into stocks values (null, ?, ?)";
	Object[] args = { stock.getSymbol(), stock.getName() };
	int[] argTypes = { Types.VARCHAR, Types.VARCHAR };
	insert(sql, args, argTypes, stock);
    }

    @Override
    public void updateStock(Stock stock) {
	String sql = "update stocks set stock_name=? where stock_id=?";
	Object[] args = { stock.getName(), stock.getId() };
	int[] argTypes = { Types.VARCHAR, Types.NUMERIC };
	update(sql, args, argTypes);
    }

    @Override
    public void deleteStock(Stock stock) {
	//
	String sql = "";
	Object[] args = { stock.getId() };
	int[] argTypes = { Types.NUMERIC };
	//
	sql = "select count(*) from market_lnk_indice where mli_indice_id=?";
	if (queryForInteger(sql, args, argTypes) > 0) {
	    return;
	}
	//
	sql = "select count(*) from market_lnk_stock where mls_stock_id=?";
	if (queryForInteger(sql, args, argTypes) > 0) {
	    return;
	}
	//
	sql = "select count(*) from portfolio_lnk_stock where pls_stock_id=?";
	if (queryForInteger(sql, args, argTypes) > 0) {
	    return;
	}
	//
	sql = "delete from stocks where stock_id=?";
	update(sql, args, argTypes);
	//
	sql = "delete from prices where price_stock_id=?";
	update(sql, args, argTypes);
    }

    @Override
    public List<Price> getPrices(Stock stock) {
	String sql = "select * from prices where price_stock_id=? order by price_date";
	Object[] args = { stock.getId() };
	int[] argTypes = { Types.NUMERIC };
	return queryForList(sql, args, argTypes, new PriceRowMapper());
    }

    @Override
    public void addPrices(Stock stock, List<Price> prices) {
	for (Price price : prices) {
	    String sql = "insert into prices values (?, ?, ?)";
	    Object[] args = { stock.getId(), price.getDate(), price.getValue() };
	    int[] argTypes = { Types.NUMERIC, Types.DATE, Types.NUMERIC };
	    update(sql, args, argTypes);
	}
    }

    @Override
    public void deletePrices(Stock stock) {
	String sql = "delete from prices where price_stock_id=?";
	Object[] args = { stock.getId() };
	int[] argTypes = { Types.NUMERIC };
	update(sql, args, argTypes);
    }

    @Override
    public Date getLastDate(Stock stock) {
	String sql = "select max(price_date) as last_date from prices where price_stock_id=?";
	Object[] args = { stock.getId() };
	int[] argTypes = { Types.NUMERIC };
	return queryForDate(sql, args, argTypes);
    }

    @Override
    public List<Price> getPrices(Stock stock, Date fromDate, Date toDate) {
	String sql = "select * from prices where price_stock_id=? and price_date>=? and price_date<=? order by price_date";
	Object[] args = { stock.getId(), fromDate, toDate };
	int[] argTypes = { Types.NUMERIC, Types.DATE, Types.DATE };
	return queryForList(sql, args, argTypes, new PriceRowMapper());
    }

    @Override
    public List<Price> getPricesInclusive(Stock stock, Date fromDate, Date toDate) {
	String sql = "select * from prices where price_stock_id=? "
		+ "and price_date>=(select max(price_date) from prices where price_stock_id=? and price_date<=?) "
		+ "and price_date<=(select min(price_date) from prices where price_stock_id=? and price_date>=?) "
		+ "order by price_date";
	Object[] args = { stock.getId(), stock.getId(), fromDate, stock.getId(), toDate };
	int[] argTypes = { Types.NUMERIC, Types.NUMERIC, Types.DATE, Types.NUMERIC, Types.DATE };
	return queryForList(sql, args, argTypes, new PriceRowMapper());
    }

    @Override
    public List<Price> getPricesInclusive(Stock stock, Date fromDate) {
	String sql = "select * from prices where price_stock_id=? "
		+ "and price_date>=(select max(price_date) from prices where price_stock_id=? and price_date<=?) "
		+ "order by price_date";
	Object[] args = { stock.getId(), stock.getId(), fromDate };
	int[] argTypes = { Types.NUMERIC, Types.NUMERIC, Types.DATE };
	return queryForList(sql, args, argTypes, new PriceRowMapper());
    }

}