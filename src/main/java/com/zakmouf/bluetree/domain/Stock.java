package com.zakmouf.bluetree.domain;

public class Stock extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String symbol;

    private String name;

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

    @Override
    public String toString() {
	return msg("[{0},{1},{2}]", id, symbol, name);
    }

}