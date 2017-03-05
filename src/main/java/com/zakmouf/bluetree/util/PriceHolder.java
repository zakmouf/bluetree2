package com.zakmouf.bluetree.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.commons.lang3.time.DateUtils;

import com.zakmouf.bluetree.domain.Price;
import com.zakmouf.bluetree.domain.Stock;

public class PriceHolder {

    private TreeMap<String, TreeMap<Date, Double>> stockMap = new TreeMap<String, TreeMap<Date, Double>>();

    public void addPrices(Stock stock, List<Price> prices) {
	TreeMap<Date, Double> priceMap = new TreeMap<Date, Double>();
	for (Price price : prices) {
	    priceMap.put(price.getDate(), price.getValue());
	}
	stockMap.put(stock.getName(), priceMap);
    }

    public List<Price> getPrices(Stock stock) {
	List<Price> prices = new ArrayList<Price>();
	TreeMap<Date, Double> priceMap = stockMap.get(stock.getName());
	for (Entry<Date, Double> entry : priceMap.entrySet()) {
	    prices.add(new Price(entry.getKey(), entry.getValue()));
	}
	return prices;
    }

    public Price getPrice(Stock stock, Date date) {
	TreeMap<Date, Double> priceMap = stockMap.get(stock.getName());
	Date firstDate = priceMap.firstKey();
	Double value = priceMap.get(date);
	while (value == null && !date.before(firstDate)) {
	    date = DateUtils.addDays(date, -1);
	    value = priceMap.get(date);
	}
	return new Price(date, value);
    }

}
