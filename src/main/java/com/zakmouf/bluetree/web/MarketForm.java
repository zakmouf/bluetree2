package com.zakmouf.bluetree.web;

import java.util.List;

import com.zakmouf.bluetree.domain.Stock;

public class MarketForm {

    private List<Stock> stocks;

    public List<Stock> getStocks() {
	return stocks;
    }

    public void setStocks(List<Stock> stocks) {
	this.stocks = stocks;
    }

    private String name;

    private Double riskless;

    private Long indiceId;

    public Long getIndiceId() {
	return indiceId;
    }

    public void setIndiceId(Long indiceId) {
	this.indiceId = indiceId;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public Double getRiskless() {
	return riskless;
    }

    public void setRiskless(Double riskless) {
	this.riskless = riskless;
    }

}
