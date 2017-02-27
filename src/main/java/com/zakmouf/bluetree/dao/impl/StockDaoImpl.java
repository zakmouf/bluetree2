package com.zakmouf.bluetree.dao.impl;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.zakmouf.bluetree.dao.StockDao;
import com.zakmouf.bluetree.dao.mapper.PriceRowMapper;
import com.zakmouf.bluetree.dao.mapper.StockRowMapper;
import com.zakmouf.bluetree.domain.Price;
import com.zakmouf.bluetree.domain.Stock;

@Component
public class StockDaoImpl extends BaseDaoImpl implements StockDao {

    @Override
    public Stock findById(Long id) {
	Assert.notNull(id);
	//
	String sql = "select s.* from v_stock s where s.stock_id = ?";
	Object[] args = { id };
	int[] argTypes = { Types.NUMERIC };
	//
	Stock stock = queryForObject(sql, args, argTypes, new StockRowMapper());
	//
	return stock;
    }

    @Override
    public Stock findBySymbol(String symbol) {
	Assert.notNull(symbol);
	//
	String sql = "select s.* from v_stock s where s.stock_symbol = ?";
	Object[] args = { symbol };
	int[] argTypes = { Types.VARCHAR };
	//
	Stock stock = queryForObject(sql, args, argTypes, new StockRowMapper());
	//
	return stock;
    }

    @Override
    public List<Stock> findAll() {
	String sql = "select s.* from v_stock s";
	Object[] args = {};
	int[] argTypes = {};
	//
	List<Stock> stocks = queryForList(sql, args, argTypes, new StockRowMapper());
	//
	Collections.sort(stocks);
	//
	return stocks;
    }

    @Override
    public void insert(Stock stock) {
	Assert.notNull(stock);
	Assert.isNull(stock.getId());
	Assert.notNull(stock.getSymbol());
	//
	stock.setId(getNextId());
	//
	String sql = "insert into t_stock (f_id, f_symbol, f_name) values (?, ?, ?)";
	Object[] args = { stock.getId(), stock.getSymbol(), stock.getName() };
	int[] argTypes = { Types.NUMERIC, Types.VARCHAR, Types.VARCHAR };
	//
	insert(sql, args, argTypes);
    }

    @Override
    public void update(Stock stock) {
	Assert.notNull(stock);
	Assert.notNull(stock.getId());
	Assert.notNull(stock.getSymbol());
	//
	String sql = "update t_stock set f_symbol= ?, f_name = ? where f_id = ?";
	Object[] args = { stock.getSymbol(), stock.getName(), stock.getId() };
	int[] argTypes = { Types.VARCHAR, Types.VARCHAR, Types.NUMERIC };
	//
	update(sql, args, argTypes);
    }

    @Override
    public void delete(Stock stock) {
	Assert.notNull(stock);
	Assert.notNull(stock.getId());
	//
	String sql = "delete from t_stock where f_id = ?";
	Object[] args = { stock.getId() };
	int[] argTypes = { Types.NUMERIC };
	//
	delete(sql, args, argTypes);
    }

    @Override
    public Date findLastDate(Stock stock) {
	Assert.notNull(stock);
	Assert.notNull(stock.getId());
	//
	String sql = "select max(f_date) from t_price where stock_id = ?";
	Object[] args = { stock.getId() };
	int[] argTypes = { Types.NUMERIC };
	//
	Date lastDate = queryForObject(sql, args, argTypes, Date.class);
	//
	return lastDate;
    }

    @Override
    public List<Price> findPrices(Stock stock) {
	Assert.notNull(stock);
	Assert.notNull(stock.getId());
	//
	String sql = "select p.price_date, p.price_value from v_price p where p.stock_id = ?";
	Object[] args = { stock.getId() };
	int[] argTypes = { Types.NUMERIC };
	//
	List<Price> prices = queryForList(sql, args, argTypes, new PriceRowMapper());
	//
	Collections.sort(prices);
	//
	return prices;
    }

    @Override
    public List<Price> findPricesBetween(Stock stock, Date fromDate, Date toDate) {
	Assert.notNull(stock);
	Assert.notNull(stock.getId());
	Assert.notNull(fromDate);
	Assert.notNull(toDate);
	Assert.isTrue(fromDate.compareTo(toDate) <= 0);
	//
	List<Price> prices = findPrices(stock);
	//
	List<Price> filterPrices = new ArrayList<Price>();
	for (Price price : prices) {
	    if (price.getDate().compareTo(fromDate) >= 0 && price.getDate().compareTo(toDate) <= 0) {
		filterPrices.add(price);
	    }
	}
	//
	return filterPrices;
    }

    @Override
    public List<Price> findPricesBetweenInclusive(Stock stock, Date fromDate, Date toDate) {
	Assert.notNull(stock);
	Assert.notNull(stock.getId());
	Assert.notNull(fromDate);
	Assert.notNull(toDate);
	Assert.isTrue(fromDate.compareTo(toDate) <= 0);
	//
	List<Price> prices = findPrices(stock);
	//
	Date filterFromDate = null;
	Date filterToDate = null;
	for (Price price : prices) {
	    if (price.getDate().compareTo(fromDate) <= 0) {
		filterFromDate = price.getDate();
	    }
	    if (price.getDate().compareTo(toDate) >= 0) {
		if (filterToDate == null) {
		    filterToDate = price.getDate();
		}
	    }
	}
	//
	List<Price> filterPrices = new ArrayList<Price>();
	if (filterFromDate != null && filterToDate != null) {
	    for (Price price : prices) {
		if (price.getDate().compareTo(filterFromDate) >= 0 && price.getDate().compareTo(filterToDate) <= 0) {
		    filterPrices.add(price);
		}
	    }
	}
	//
	return filterPrices;
    }

    @Override
    public List<Price> findPricesFrom(Stock stock, Date fromDate) {
	Assert.notNull(stock);
	Assert.notNull(stock.getId());
	Assert.notNull(fromDate);
	//
	List<Price> prices = findPrices(stock);
	//
	List<Price> filterPrices = new ArrayList<Price>();
	for (Price price : prices) {
	    if (price.getDate().compareTo(fromDate) >= 0) {
		filterPrices.add(price);
	    }
	}
	//
	return filterPrices;
    }

    @Override
    public List<Price> findPricesFromInclusive(Stock stock, Date fromDate) {
	Assert.notNull(stock);
	Assert.notNull(stock.getId());
	Assert.notNull(fromDate);
	//
	List<Price> prices = findPrices(stock);
	//
	Date filterFromDate = null;
	for (Price price : prices) {
	    if (price.getDate().compareTo(fromDate) <= 0) {
		filterFromDate = price.getDate();
	    }
	}
	//
	List<Price> filterPrices = new ArrayList<Price>();
	if (filterFromDate != null) {
	    for (Price price : prices) {
		if (price.getDate().compareTo(filterFromDate) >= 0) {
		    filterPrices.add(price);
		}
	    }
	}
	//
	return filterPrices;
    }

    @Override
    public void insertPrices(Stock stock, List<Price> prices) {
	Assert.notNull(stock);
	Assert.notNull(stock.getId());
	Assert.notNull(prices);
	//
	for (Price price : prices) {
	    Assert.notNull(price);
	    Assert.notNull(price.getDate());
	    Assert.notNull(price.getValue());
	    //
	    String sql = "insert into t_price (stock_id, f_date, f_value) values (?, ?, ?)";
	    Object[] args = { stock.getId(), price.getDate(), price.getValue() };
	    int[] argTypes = { Types.NUMERIC, Types.DATE, Types.NUMERIC };
	    //
	    update(sql, args, argTypes);
	}
    }

    @Override
    public void deletePrices(Stock stock) {
	Assert.notNull(stock);
	Assert.notNull(stock.getId());
	//
	String sql = "delete from t_price where stock_id = ?";
	Object[] args = { stock.getId() };
	int[] argTypes = { Types.NUMERIC };
	//
	update(sql, args, argTypes);
    }

}