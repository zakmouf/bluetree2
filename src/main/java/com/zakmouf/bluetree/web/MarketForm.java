package com.zakmouf.bluetree.web;

public class MarketForm {

    private String name;
    private Double riskless;
    private Long indiceId;

    public MarketForm() {
        name = "name";
        riskless = 0.0D;
    }

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
