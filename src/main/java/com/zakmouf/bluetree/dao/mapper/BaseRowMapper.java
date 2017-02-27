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

    protected Long getLong(ResultSet rs, int columnIndex) throws SQLException {
	long value = rs.getLong(columnIndex);
	return rs.wasNull() ? null : new Long(value);
    }

    protected Long getLong(ResultSet rs, String columnLabel) throws SQLException {
	long value = rs.getLong(columnLabel);
	return rs.wasNull() ? null : new Long(value);
    }

    protected String getString(ResultSet rs, int columnIndex) throws SQLException {
	return rs.getString(columnIndex);
    }

    protected String getString(ResultSet rs, String columnLabel) throws SQLException {
	return rs.getString(columnLabel);
    }

    protected Double getDouble(ResultSet rs, int columnIndex) throws SQLException {
	double value = rs.getDouble(columnIndex);
	return rs.wasNull() ? null : new Double(value);
    }

    protected Double getDouble(ResultSet rs, String columnLabel) throws SQLException {
	double value = rs.getDouble(columnLabel);
	return rs.wasNull() ? null : new Double(value);
    }

    protected Integer getInteger(ResultSet rs, int columnIndex) throws SQLException {
	int value = rs.getInt(columnIndex);
	return rs.wasNull() ? null : new Integer(value);
    }

    protected Integer getInteger(ResultSet rs, String columnLabel) throws SQLException {
	int value = rs.getInt(columnLabel);
	return rs.wasNull() ? null : new Integer(value);
    }

    protected Boolean getBoolean(ResultSet rs, int columnIndex) throws SQLException {
	boolean value = rs.getBoolean(columnIndex);
	return rs.wasNull() ? null : Boolean.valueOf(value);
    }

    protected Boolean getBoolean(ResultSet rs, String columnLabel) throws SQLException {
	boolean value = rs.getBoolean(columnLabel);
	return rs.wasNull() ? null : Boolean.valueOf(value);
    }

    protected Date getDate(ResultSet rs, int columnIndex) throws SQLException {
	java.sql.Date date = rs.getDate(columnIndex);
	return rs.wasNull() ? null : new Date(date.getTime());
    }

    protected Date getDate(ResultSet rs, String columnLabel) throws SQLException {
	java.sql.Date date = rs.getDate(columnLabel);
	return rs.wasNull() ? null : new Date(date.getTime());
    }

    protected Date getTimestamp(ResultSet rs, int columnIndex) throws SQLException {
	Timestamp timestamp = rs.getTimestamp(columnIndex);
	return rs.wasNull() ? null : new Date(timestamp.getTime());
    }

    protected Date getTimestamp(ResultSet rs, String columnLabel) throws SQLException {
	Timestamp timestamp = rs.getTimestamp(columnLabel);
	return rs.wasNull() ? null : new Date(timestamp.getTime());
    }

    protected Market mapMarket(ResultSet rs) throws SQLException {
	return mapMarket(rs, "market");
    }

    protected Market mapMarket(ResultSet rs, String prefix) throws SQLException {
	Market market = new Market();
	market.setId(getLong(rs, prefix + "_id"));
	market.setName(getString(rs, prefix + "_name"));
	market.setRiskless(getDouble(rs, prefix + "_riskless"));
	Stock indice = mapStock(rs, "indice");
	market.setIndice(indice);
	return market;
    }

    protected Stock mapStock(ResultSet rs) throws SQLException {
	return mapStock(rs, "stock");
    }

    protected Stock mapStock(ResultSet rs, String prefix) throws SQLException {
	Stock stock = new Stock();
	stock.setId(getLong(rs, prefix + "_id"));
	stock.setSymbol(getString(rs, prefix + "_symbol"));
	stock.setName(getString(rs, prefix + "_name"));
	stock.setDateCount(getInteger(rs, prefix + "_date_count"));
	stock.setFirstDate(getDate(rs, prefix + "_first_date"));
	stock.setLastDate(getDate(rs, prefix + "_last_date"));
	return stock;
    }

    protected Price mapPrice(ResultSet rs) throws SQLException {
	return mapPrice(rs, "price");
    }

    protected Price mapPrice(ResultSet rs, String prefix) throws SQLException {
	Price price = new Price();
	price.setDate(getDate(rs, prefix + "_date"));
	price.setValue(getDouble(rs, prefix + "_value"));
	return price;
    }

    protected Portfolio mapPortfolio(ResultSet rs) throws SQLException {
	Portfolio portfolio = new Portfolio();
	portfolio.setId(getLong(rs, "portfolio_id"));
	portfolio.setName(getString(rs, "portfolio_name"));
	portfolio.setFromDate(getDate(rs, "portfolio_from_date"));
	portfolio.setToDate(getDate(rs, "portfolio_to_date"));
	portfolio.setBeta(getDouble(rs, "portfolio_beta"));
	portfolio.setSize(getInteger(rs, "portfolio_size"));
	return portfolio;
    }

    protected Position mapPosition(ResultSet rs) throws SQLException {
	Position position = new Position();
	position.setStock(mapStock(rs));
	position.setWeight(getDouble(rs, "pls_weight"));
	return position;
    }

}
