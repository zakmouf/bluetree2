package com.zakmouf.bluetree.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.zakmouf.bluetree.domain.Price;

public abstract class PriceUtil {

    public static Price firstPrice(List<Price> prices) {
	return prices.isEmpty() ? null : prices.get(0);
    }

    public static Price lastPrice(List<Price> prices) {
	return prices.isEmpty() ? null : prices.get(prices.size() - 1);
    }

    public static Date firstDate(List<Price> prices) {
	Price price = firstPrice(prices);
	return price == null ? null : price.getDate();
    }

    public static Date lastDate(List<Price> prices) {
	Price price = lastPrice(prices);
	return price == null ? null : price.getDate();
    }

    public static List<Price> filterFrom(List<Price> prices, Date fromDate) {
	List<Price> filterPrices = new ArrayList<Price>();
	//
	for (Price price : prices) {
	    if (price.getDate().compareTo(fromDate) >= 0) {
		filterPrices.add(price);
	    }
	}
	//
	return filterPrices;
    }

    public static List<Price> filterFromInclusive(List<Price> prices, Date fromDate) {
	List<Price> filterPrices = new ArrayList<Price>();
	//
	Date filterFromDate = null;
	for (Price price : prices) {
	    if (price.getDate().compareTo(fromDate) <= 0) {
		filterFromDate = price.getDate();
	    }
	}
	//
	if (filterFromDate != null) {
	    for (Price price : prices) {
		if (price.getDate().compareTo(filterFromDate) >= 0) {
		    filterPrices.add(price);
		}
	    }
	}
	//
	return filterPrices;
    }

    public static List<Price> filterBetween(List<Price> prices, Date fromDate, Date toDate) {
	List<Price> filterPrices = new ArrayList<Price>();
	//
	for (Price price : prices) {
	    if (price.getDate().compareTo(fromDate) >= 0 && price.getDate().compareTo(toDate) <= 0) {
		filterPrices.add(price);
	    }
	}
	//
	return filterPrices;
    }

    public static List<Price> filterBetweenInclusive(List<Price> prices, Date fromDate, Date toDate) {
	List<Price> filterPrices = new ArrayList<Price>();
	//
	Date filterFromDate = null;
	Date filterToDate = null;
	for (Price price : prices) {
	    if (price.getDate().compareTo(fromDate) <= 0) {
		filterFromDate = price.getDate();
	    }
	    if (price.getDate().compareTo(toDate) >= 0) {
		if (filterToDate == null) {
		    filterToDate = price.getDate();
		}
	    }
	}
	//
	if (filterFromDate != null && filterToDate != null) {
	    for (Price price : prices) {
		if (price.getDate().compareTo(filterFromDate) >= 0 && price.getDate().compareTo(filterToDate) <= 0) {
		    filterPrices.add(price);
		}
	    }
	}
	//
	return filterPrices;
    }

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
