package com.zakmouf.bluetree.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zakmouf.bluetree.domain.Market;
import com.zakmouf.bluetree.domain.Portfolio;
import com.zakmouf.bluetree.domain.Position;
import com.zakmouf.bluetree.domain.Price;
import com.zakmouf.bluetree.domain.Stock;

public abstract class BaseRowMapper {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected Long mapLong(ResultSet rs, int columnIndex) throws SQLException {
	long value = rs.getLong(columnIndex);
	return rs.wasNull() ? null : new Long(value);
    }

    protected Long mapLong(ResultSet rs, String columnLabel) throws SQLException {
	long value = rs.getLong(columnLabel);
	return rs.wasNull() ? null : new Long(value);
    }

    protected String mapString(ResultSet rs, int columnIndex) throws SQLException {
	return rs.getString(columnIndex);
    }

    protected String mapString(ResultSet rs, String columnLabel) throws SQLException {
	return rs.getString(columnLabel);
    }

    protected Double mapDouble(ResultSet rs, int columnIndex) throws SQLException {
	double value = rs.getDouble(columnIndex);
	return rs.wasNull() ? null : new Double(value);
    }

    protected Double mapDouble(ResultSet rs, String columnLabel) throws SQLException {
	double value = rs.getDouble(columnLabel);
	return rs.wasNull() ? null : new Double(value);
    }

    protected Integer mapInteger(ResultSet rs, int columnIndex) throws SQLException {
	int value = rs.getInt(columnIndex);
	return rs.wasNull() ? null : new Integer(value);
    }

    protected Integer mapInteger(ResultSet rs, String columnLabel) throws SQLException {
	int value = rs.getInt(columnLabel);
	return rs.wasNull() ? null : new Integer(value);
    }

    protected Boolean mapBoolean(ResultSet rs, int columnIndex) throws SQLException {
	boolean value = rs.getBoolean(columnIndex);
	return rs.wasNull() ? null : Boolean.valueOf(value);
    }

    protected Boolean mapBoolean(ResultSet rs, String columnLabel) throws SQLException {
	boolean value = rs.getBoolean(columnLabel);
	return rs.wasNull() ? null : Boolean.valueOf(value);
    }

    protected Date mapDate(ResultSet rs, int columnIndex) throws SQLException {
	java.sql.Date date = rs.getDate(columnIndex);
	return rs.wasNull() ? null : new Date(date.getTime());
    }

    protected Date mapDate(ResultSet rs, String columnLabel) throws SQLException {
	java.sql.Date date = rs.getDate(columnLabel);
	return rs.wasNull() ? null : new Date(date.getTime());
    }

    protected Date mapTimestamp(ResultSet rs, int columnIndex) throws SQLException {
	Timestamp timestamp = rs.getTimestamp(columnIndex);
	return rs.wasNull() ? null : new Date(timestamp.getTime());
    }

    protected Date mapTimestamp(ResultSet rs, String columnLabel) throws SQLException {
	Timestamp timestamp = rs.getTimestamp(columnLabel);
	return rs.wasNull() ? null : new Date(timestamp.getTime());
    }

    protected Market mapMarket(ResultSet rs) throws SQLException {
	Market market = new Market();
	market.setId(mapLong(rs, "market_id"));
	market.setName(mapString(rs, "market_name"));
	market.setRiskless(mapDouble(rs, "market_riskless"));
	return market;
    }

    protected Stock mapStock(ResultSet rs) throws SQLException {
	Stock stock = new Stock();
	stock.setId(mapLong(rs, "stock_id"));
	stock.setSymbol(mapString(rs, "stock_symbol"));
	stock.setName(mapString(rs, "stock_name"));
	return stock;
    }

    protected Price mapPrice(ResultSet rs) throws SQLException {
	Price price = new Price();
	price.setDate(mapDate(rs, "price_date"));
	price.setValue(mapDouble(rs, "price_value"));
	return price;
    }

    protected Portfolio mapPortfolio(ResultSet rs) throws SQLException {
	Portfolio portfolio = new Portfolio();
	portfolio.setId(mapLong(rs, "portfolio_id"));
	portfolio.setName(mapString(rs, "portfolio_name"));
	portfolio.setFromDate(mapDate(rs, "portfolio_from_date"));
	portfolio.setToDate(mapDate(rs, "portfolio_to_date"));
	portfolio.setBeta(mapDouble(rs, "portfolio_beta"));
	portfolio.setSize(mapInteger(rs, "portfolio_size"));
	return portfolio;
    }

    protected Position mapPosition(ResultSet rs) throws SQLException {
	Position position = new Position();
	position.setStock(mapStock(rs));
	position.setWeight(mapDouble(rs, "pls_weight"));
	return position;
    }

}
