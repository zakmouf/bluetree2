package com.zakmouf.bluetree.domain;

import java.util.Date;

public class Portfolio extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String name;

    private Date fromDate;

    private Date toDate;

    private Double beta;

    private Integer size;

    private Boolean executed;

    private String error;

    public Double getBeta() {
	return beta;
    }

    public void setBeta(Double beta) {
	this.beta = beta;
    }

    public String getError() {
	return error;
    }

    public void setError(String error) {
	this.error = error;
    }

    public Boolean getExecuted() {
	return executed;
    }

    public void setExecuted(Boolean executed) {
	this.executed = executed;
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

    @Override
    public String toString() {
	return msg("[{0},{1}]", id, name);
    }

}
