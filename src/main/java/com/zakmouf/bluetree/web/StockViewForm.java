package com.zakmouf.bluetree.web;

import java.util.Date;

import com.zakmouf.bluetree.domain.Stock;

public class StockViewForm {

    private Stock stock;

    public Stock getStock() {
	return stock;
    }

    public void setStock(Stock stock) {
	this.stock = stock;
    }

    private Date firstDate;

    private Date lastDate;

    private Integer count;

    public Integer getCount() {
	return count;
    }

    public void setCount(Integer count) {
	this.count = count;
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

    private String fromDate;

    private String toDate;

    public String getFromDate() {
	return fromDate;
    }

    public void setFromDate(String fromDate) {
	this.fromDate = fromDate;
    }

    public String getToDate() {
	return toDate;
    }

    public void setToDate(String toDate) {
	this.toDate = toDate;
    }

}
