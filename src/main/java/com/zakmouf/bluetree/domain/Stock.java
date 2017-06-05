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
        return msg("id=%1$d,symbol=%2$s,name=%3$s", id, symbol, name);
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