package com.zakmouf.bluetree.web;

public class PortfolioForm {

    private String name;
    private Long marketId;
    private String fromDateStr;
    private String toDateStr;
    private Double beta;
    private Integer size;

    public PortfolioForm() {
        name = "name";
        fromDateStr = "2013-01-01";
        toDateStr = "2014-12-31";
        beta = 0.8D;
        size = 10;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getMarketId() {
        return marketId;
    }

    public void setMarketId(Long marketId) {
        this.marketId = marketId;
    }

    public String getFromDateStr() {
        return fromDateStr;
    }

    public void setFromDateStr(String fromDateStr) {
        this.fromDateStr = fromDateStr;
    }

    public String getToDateStr() {
        return toDateStr;
    }

    public void setToDateStr(String toDateStr) {
        this.toDateStr = toDateStr;
    }

    public Double getBeta() {
        return beta;
    }

    public void setBeta(Double beta) {
        this.beta = beta;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

}
