package com.zakmouf.bluetree.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import org.apache.commons.lang3.time.DateUtils;

import com.zakmouf.bluetree.domain.Holding;
import com.zakmouf.bluetree.domain.Price;
import com.zakmouf.bluetree.domain.Stock;

public class PriceHolder {

    private String indiceSymbol;
    private TreeMap<String, TreeMap<Date, Double>> stockMap = new TreeMap<String, TreeMap<Date, Double>>();

    public PriceHolder(Stock indice, List<Price> indicePrices) {
	indiceSymbol = indice.getSymbol();
	addStock(indice, indicePrices);
    }

    public void addStock(Stock stock, List<Price> prices) {
	TreeMap<Date, Double> priceMap = new TreeMap<Date, Double>();
	for (Price price : prices) {
	    priceMap.put(price.getDate(), price.getValue());
	}
	stockMap.put(stock.getSymbol(), priceMap);
    }

    public Price getClosedPrice(Stock stock, Date date) {
	TreeMap<Date, Double> priceMap = stockMap.get(stock.getSymbol());
	Date firstDate = priceMap.firstKey();
	Double value = priceMap.get(date);
	while (value == null && !date.before(firstDate)) {
	    date = DateUtils.addDays(date, -1);
	    value = priceMap.get(date);
	}
	return new Price(date, value);
    }

    public List<Price> valuate(List<Holding> holdings) {

	List<Price> portfolioPrices = new ArrayList<Price>();

	for (Date date : stockMap.get(indiceSymbol).keySet()) {
	    Double portfolioValue = 0D;
	    for (Holding holding : holdings) {
		Stock stock = holding.getStock();
		Price stockPrice = getClosedPrice(stock, date);
		Double holdingValue = holding.getQuantity() * stockPrice.getValue();
		portfolioValue += holdingValue;
	    }
	    portfolioPrices.add(new Price(date, portfolioValue));
	}

	return portfolioPrices;

    }

}
