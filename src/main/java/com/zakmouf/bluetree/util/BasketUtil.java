package com.zakmouf.bluetree.util;

import java.util.List;

public abstract class BasketUtil {

    public static Double[] calculateReturns(List<Double[]> stockReturnsList, Integer[] keys) {
	Double[] stockReturns = stockReturnsList.get(keys[0]);
	Double[] basketReturns = new Double[stockReturns.length];
	for (int j = 0; j < basketReturns.length; j++) {
	    basketReturns[j] = stockReturns[j];
	}
	for (int i = 1; i < keys.length; i++) {
	    stockReturns = stockReturnsList.get(keys[0]);
	    for (int j = 0; j < basketReturns.length; j++) {
		basketReturns[j] = basketReturns[j] + stockReturns[j];
	    }
	}
	for (int j = 0; j < basketReturns.length; j++) {
	    basketReturns[j] = basketReturns[j] / keys.length;
	}
	return basketReturns;
    }

}