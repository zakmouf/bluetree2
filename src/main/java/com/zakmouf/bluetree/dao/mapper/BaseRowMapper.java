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
import com.zakmouf.bluetree.domain.Profile;
import com.zakmouf.bluetree.domain.Stock;
import com.zakmouf.bluetree.domain.User;

public abstract class BaseRowMapper {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected Long mapLong(ResultSet rs, int rowNum) throws SQLException {
	long value = rs.getLong(rowNum);
	return rs.wasNull() ? null : new Long(value);
    }

    protected Long mapLong(ResultSet rs, String columnLabel) throws SQLException {
	long value = rs.getLong(columnLabel);
	return rs.wasNull() ? null : new Long(value);
    }

    protected String mapString(ResultSet rs, int rowNum) throws SQLException {
	return rs.getString(rowNum);
    }

    protected String mapString(ResultSet rs, String columnLabel) throws SQLException {
	return rs.getString(columnLabel);
    }

    protected Double mapDouble(ResultSet rs, int rowNum) throws SQLException {
	double value = rs.getDouble(rowNum);
	return rs.wasNull() ? null : new Double(value);
    }

    protected Double mapDouble(ResultSet rs, String columnLabel) throws SQLException {
	double value = rs.getDouble(columnLabel);
	return rs.wasNull() ? null : new Double(value);
    }

    protected Integer mapInteger(ResultSet rs, int rowNum) throws SQLException {
	int value = rs.getInt(rowNum);
	return rs.wasNull() ? null : new Integer(value);
    }

    protected Integer mapInteger(ResultSet rs, String columnLabel) throws SQLException {
	int value = rs.getInt(columnLabel);
	return rs.wasNull() ? null : new Integer(value);
    }

    protected Boolean mapBoolean(ResultSet rs, int rowNum) throws SQLException {
	boolean value = rs.getBoolean(rowNum);
	return rs.wasNull() ? null : Boolean.valueOf(value);
    }

    protected Boolean mapBoolean(ResultSet rs, String columnLabel) throws SQLException {
	boolean value = rs.getBoolean(columnLabel);
	return rs.wasNull() ? null : Boolean.valueOf(value);
    }

    protected Date mapDate(ResultSet rs, int rowNum) throws SQLException {
	java.sql.Date date = rs.getDate(rowNum);
	return rs.wasNull() ? null : new Date(date.getTime());
    }

    protected Date mapDate(ResultSet rs, String columnLabel) throws SQLException {
	java.sql.Date date = rs.getDate(columnLabel);
	return rs.wasNull() ? null : new Date(date.getTime());
    }

    protected Date mapTimestamp(ResultSet rs, int rowNum) throws SQLException {
	Timestamp timestamp = rs.getTimestamp(rowNum);
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

    protected Profile mapProfile(ResultSet rs) throws SQLException {
	Profile profile = new Profile();
	profile.setId(mapLong(rs, "profile_id"));
	profile.setName(mapString(rs, "profile_name"));
	return profile;
    }

    protected Stock mapStock(ResultSet rs) throws SQLException {
	Stock stock = new Stock();
	stock.setId(mapLong(rs, "stock_id"));
	stock.setSymbol(mapString(rs, "stock_symbol"));
	stock.setName(mapString(rs, "stock_name"));
	return stock;
    }

    protected User mapUser(ResultSet rs) throws SQLException {
	User user = new User();
	user.setId(mapLong(rs, "user_id"));
	user.setLogin(mapString(rs, "user_login"));
	user.setPassword(mapString(rs, "user_password"));
	user.setMail(mapString(rs, "user_mail"));
	return user;
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
	portfolio.setExecuted(mapBoolean(rs, "portfolio_executed"));
	portfolio.setError(mapString(rs, "portfolio_error"));
	return portfolio;
    }

    protected Position mapPosition(ResultSet rs) throws SQLException {
	Position position = new Position();
	position.setStock(mapStock(rs));
	position.setWeight(mapDouble(rs, "pls_weight"));
	return position;
    }

}
