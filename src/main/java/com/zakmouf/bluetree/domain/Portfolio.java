package com.zakmouf.bluetree.domain;

import java.util.Date;

public class Portfolio extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String name;

    private Date fromDate;

    private Date toDate;

    private Double beta;

    private Integer size;

    private Market market;

    public Double getBeta() {
	return beta;
    }

    public void setBeta(Double beta) {
	this.beta = beta;
    }

    public Date getFromDate() {
	return fromDate;
    }

    public void setFromDate(Date fromDate) {
	this.fromDate = fromDate;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public Integer getSize() {
	return size;
    }

    public void setSize(Integer size) {
	this.size = size;
    }

    public Date getToDate() {
	return toDate;
    }

    public void setToDate(Date toDate) {
	this.toDate = toDate;
    }

    public Market getMarket() {
	return market;
    }

    public void setMarket(Market market) {
	this.market = market;
    }

    @Override
    public String toString() {
	return msg("[{0},{1}]", id, name);
    }

}
