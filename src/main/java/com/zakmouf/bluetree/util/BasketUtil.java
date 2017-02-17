package com.zakmouf.bluetree.util;

import org.apache.commons.collections4.map.LinkedMap;

public abstract class BasketUtil {

    public static Double[] calculateReturns(LinkedMap<Integer, Double[]> returns, Integer[] keys) {
	Double[] stockReturns = returns.getValue(keys[0]);
	Double[] basketReturns = new Double[stockReturns.length];
	for (int j = 0; j < basketReturns.length; j++) {
	    basketReturns[j] = stockReturns[j];
	}
	for (int i = 1; i < keys.length; i++) {
	    stockReturns = returns.getValue(keys[0]);
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