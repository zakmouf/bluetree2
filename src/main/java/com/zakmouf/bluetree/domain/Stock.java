package com.zakmouf.bluetree.domain;

import java.util.Date;

public class Stock extends BaseEntity implements Comparable<Stock> {

    private static final long serialVersionUID = 1L;

    private String symbol;
    private String name;
    private Integer dateCount;
    private Date firstDate;
    private Date lastDate;

    public Stock() {

    }

    public Stock(String symbol) {
	setSymbol(symbol);
    }

    public Stock(String symbol, String name) {
	setSymbol(symbol);
	setName(name);
    }

    public String getSymbol() {
	return symbol;
    }

    public void setSymbol(String symbol) {
	this.symbol = symbol;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public Integer getDateCount() {
	return dateCount;
    }

    public void setDateCount(Integer dateCount) {
	this.dateCount = dateCount;
    }

    public Date getFirstDate() {
	return firstDate;
    }

    public void setFirstDate(Date firstDate) {
	this.firstDate = firstDate;
    }

    public Date getLastDate() {
	return lastDate;
    }

    public void setLastDate(Date lastDate) {
	this.lastDate = lastDate;
    }

    @Override
    public String toString() {
	StringBuffer buf = new StringBuffer();
	buf.append(msg("id={0,number,0}", id));
	buf.append(msg(",symbol=\"{0}\"", symbol));
	buf.append(msg(",name=\"{0}\"", name));
	buf.append(msg(",dateCount={0}", dateCount));
	buf.append(msg(",firstDate={0,date,yyyy-MM-dd}", firstDate));
	buf.append(msg(",lastDate={0,date,yyyy-MM-dd}", lastDate));
	return buf.toString();
    }

    @Override
    public boolean equals(Object other) {
	return symbol.equals(((Stock) other).getSymbol());
    }

    @Override
    public int compareTo(Stock other) {
	return symbol.compareTo(other.getSymbol());
    }

    @Override
    public int hashCode() {
	return symbol.hashCode();
    }

}