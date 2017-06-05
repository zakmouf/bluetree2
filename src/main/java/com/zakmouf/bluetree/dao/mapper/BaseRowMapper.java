package com.zakmouf.bluetree.dao.mapper;

import com.zakmouf.bluetree.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

public abstract class BaseRowMapper {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected String msg(String pattern, Object... arguments) {
        return String.format(pattern, arguments);
    }

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
        Price price = new Price();
        price.setDate(getDate(rs, "price_date"));
        price.setValue(getDouble(rs, "price_value"));
        return price;
    }

    protected Market mapMarket(ResultSet rs) throws SQLException {
        Market market = new Market();
        market.setId(getLong(rs, "market_id"));
        market.setName(getString(rs, "market_name"));
        market.setRiskless(getDouble(rs, "market_riskless"));
        Stock indice = mapStock(rs, "indice");
        market.setIndice(indice);
        return market;
    }

    protected Portfolio mapPortfolio(ResultSet rs) throws SQLException {
        Portfolio portfolio = new Portfolio();
        portfolio.setId(getLong(rs, "portfolio_id"));
        portfolio.setName(getString(rs, "portfolio_name"));
        portfolio.setFromDate(getDate(rs, "portfolio_from_date"));
        portfolio.setToDate(getDate(rs, "portfolio_to_date"));
        portfolio.setBeta(getDouble(rs, "portfolio_beta"));
        portfolio.setSize(getInteger(rs, "portfolio_size"));
        Market market = mapMarket(rs);
        portfolio.setMarket(market);
        return portfolio;
    }

    protected Holding mapHolding(ResultSet rs) throws SQLException {
        Holding holding = new Holding();
        Stock stock = mapStock(rs, "stock");
        holding.setStock(stock);
        holding.setWeight(getDouble(rs, "holding_weight"));
        return holding;
    }

}
