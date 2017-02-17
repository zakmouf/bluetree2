package com.zakmouf.bluetree.domain;

public class Position extends BaseObject {

    private static final long serialVersionUID = 1L;

    private Stock stock;

    private Double weight;

    public Stock getStock() {
	return stock;
    }

    public void setStock(Stock stock) {
	this.stock = stock;
    }

    public Double getWeight() {
	return weight;
    }

    public void setWeight(Double weight) {
	this.weight = weight;
    }

    @Override
    public String toString() {
	return msg("[{0},{1}]", stock, weight);
    }

}
