package com.zakmouf.bluetree.web;

public class StockForm {

    private String symbol;
    private String name;

    public StockForm() {
	symbol = "symbol";
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

}
