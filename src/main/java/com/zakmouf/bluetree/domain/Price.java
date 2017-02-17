package com.zakmouf.bluetree.domain;

import java.util.Date;

public class Price extends BaseObject {

    private static final long serialVersionUID = 1L;

    private Date date;

    private Double value;

    public Date getDate() {
	return date;
    }

    public void setDate(Date date) {
	this.date = date;
    }

    public Double getValue() {
	return value;
    }

    public void setValue(Double value) {
	this.value = value;
    }

    @Override
    public String toString() {
	return msg("[{0},{1}]", date, value);
    }

}