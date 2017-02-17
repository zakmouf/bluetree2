package com.zakmouf.bluetree.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.zakmouf.bluetree.domain.Price;

public abstract class PriceUtil {

    public static Integer matchPrices(List<Price> indicePrices, List<Price> stockPrices) {
	List<Price> matchPrices = new ArrayList<Price>();
	matchPrices.clear();
	int size = indicePrices.size();
	int i = 0;
	int j = 0;
	int diff = 0;
	while (i < size) {
	    Price indicePrice = indicePrices.get(i);
	    Price stockPrice = stockPrices.get(j);
	    int cmp = indicePrice.getDate().compareTo(stockPrice.getDate());
	    if (cmp == 0) {
		matchPrices.add(stockPrice);
		i++;
		j++;
	    } else if (cmp < 0) {
		Price before = stockPrices.get(j - 1);
		double value = before.getValue() + ((stockPrice.getValue() - before.getValue())
			/ DateUtil.daysBetween(stockPrice.getDate(), before.getDate()))
			* DateUtil.daysBetween(indicePrice.getDate(), before.getDate());
		Price price = new Price();
		price.setDate(indicePrice.getDate());
		price.setValue(value);
		matchPrices.add(price);
		i++;
		diff++;
	    } else {
		j++;
	    }
	}
	stockPrices.clear();
	stockPrices.addAll(matchPrices);
	return diff;
    }

    public static Date firstDate(List<Price> prices) {
	return prices.get(0).getDate();
    }

    public static Date lastDate(List<Price> prices) {
	return prices.get(prices.size() - 1).getDate();
    }

    public static Double[] calculateReturns(List<Price> prices) {
	Double[] returns = new Double[prices.size() - 1];
	Iterator<Price> iterator = prices.iterator();
	Price oldPrice = iterator.next();
	for (int i = 0; i < returns.length; i++) {
	    Price price = iterator.next();
	    returns[i] = (price.getValue() / oldPrice.getValue()) - 1.0;
	    oldPrice = price;
	}
	return returns;
    }

}
