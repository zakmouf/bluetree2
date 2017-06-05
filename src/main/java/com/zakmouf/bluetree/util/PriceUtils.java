package com.zakmouf.bluetree.util;

import com.zakmouf.bluetree.domain.Price;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class PriceUtils {

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

    public static Double firstValue(List<Price> prices) {
        Price price = firstPrice(prices);
        return price == null ? null : price.getValue();
    }

    public static Double lastValue(List<Price> prices) {
        Price price = lastPrice(prices);
        return price == null ? null : price.getValue();
    }

    public static List<Price> rebase(List<Price> prices, Double base) {
        List<Price> basedPrices = new ArrayList<Price>();
        if (!prices.isEmpty()) {
            Double firstValue = firstValue(prices);
            for (Price price : prices) {
                basedPrices.add(new Price(price.getDate(), price.getValue() * base / firstValue));
            }
        }
        return basedPrices;
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

}
