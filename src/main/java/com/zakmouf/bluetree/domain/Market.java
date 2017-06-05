package com.zakmouf.bluetree.domain;

public class Market extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String name;

    private Double riskless;

    private Stock indice;

    public Market() {

    }

    public Market(String name) {
        setName(name);
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

    public Stock getIndice() {
        return indice;
    }

    public void setIndice(Stock indice) {
        this.indice = indice;
    }

    @Override
    public String toString() {
        return msg("id=%1$d,name=%2$s,riskless=%3$.2f", id, name, riskless);
    }

}
