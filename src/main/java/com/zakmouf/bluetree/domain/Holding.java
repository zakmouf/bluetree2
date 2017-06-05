package com.zakmouf.bluetree.domain;

public class Holding extends BaseObject {

    private static final long serialVersionUID = 1L;

    private Stock stock;

    private Double weight;

    private Double quantity;

    public Holding() {

    }

    public Holding(Stock stock) {
        setStock(stock);
    }

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

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return msg("weight=%1$.2f,stock=[%2$s]", weight * 100.0D, stock);
    }

}
