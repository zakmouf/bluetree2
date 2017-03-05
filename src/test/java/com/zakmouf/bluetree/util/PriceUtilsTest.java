package com.zakmouf.bluetree.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.zakmouf.bluetree.BaseTest;
import com.zakmouf.bluetree.domain.Price;

public class PriceUtilsTest extends BaseTest {

    @Test
    public void doTest() {

	Date date0 = parseDate("2012-12-14");
	Date date1 = parseDate("2012-12-15");
	Date date2 = parseDate("2012-12-16");
	Date date3 = parseDate("2012-12-17");
	Date date4 = parseDate("2012-12-18");
	Date date5 = parseDate("2012-12-19");
	Date date6 = parseDate("2012-12-20");

	Price price1 = new Price(date1, 1.1d);
	Price price3 = new Price(date3, 3.3d);
	Price price5 = new Price(date5, 5.5d);

	List<Price> prices = new ArrayList<Price>();
	prices.add(price1);
	prices.add(price3);
	prices.add(price5);

	List<Price> filterPrices;

	filterPrices = PriceUtils.filterFrom(prices, date0);
	Assert.assertEquals(filterPrices.size(), 3);
	Assert.assertTrue(filterPrices.contains(price1));
	Assert.assertTrue(filterPrices.contains(price3));
	Assert.assertTrue(filterPrices.contains(price5));
	filterPrices = PriceUtils.filterFrom(prices, date1);
	Assert.assertEquals(filterPrices.size(), 3);
	Assert.assertTrue(filterPrices.contains(price1));
	Assert.assertTrue(filterPrices.contains(price3));
	Assert.assertTrue(filterPrices.contains(price5));
	filterPrices = PriceUtils.filterFrom(prices, date2);
	Assert.assertEquals(filterPrices.size(), 2);
	Assert.assertTrue(filterPrices.contains(price3));
	Assert.assertTrue(filterPrices.contains(price5));
	filterPrices = PriceUtils.filterFrom(prices, date3);
	Assert.assertEquals(filterPrices.size(), 2);
	Assert.assertTrue(filterPrices.contains(price3));
	Assert.assertTrue(filterPrices.contains(price5));
	filterPrices = PriceUtils.filterFrom(prices, date4);
	Assert.assertEquals(filterPrices.size(), 1);
	Assert.assertTrue(filterPrices.contains(price5));
	filterPrices = PriceUtils.filterFrom(prices, date5);
	Assert.assertEquals(filterPrices.size(), 1);
	Assert.assertTrue(filterPrices.contains(price5));
	filterPrices = PriceUtils.filterFrom(prices, date6);
	Assert.assertTrue(filterPrices.isEmpty());

	filterPrices = PriceUtils.filterFromInclusive(prices, date0);
	Assert.assertTrue(filterPrices.isEmpty());
	filterPrices = PriceUtils.filterFromInclusive(prices, date1);
	Assert.assertEquals(filterPrices.size(), 3);
	Assert.assertTrue(filterPrices.contains(price1));
	Assert.assertTrue(filterPrices.contains(price3));
	Assert.assertTrue(filterPrices.contains(price5));
	filterPrices = PriceUtils.filterFromInclusive(prices, date2);
	Assert.assertEquals(filterPrices.size(), 3);
	Assert.assertTrue(filterPrices.contains(price1));
	Assert.assertTrue(filterPrices.contains(price3));
	Assert.assertTrue(filterPrices.contains(price5));
	filterPrices = PriceUtils.filterFromInclusive(prices, date3);
	Assert.assertEquals(filterPrices.size(), 2);
	Assert.assertTrue(filterPrices.contains(price3));
	Assert.assertTrue(filterPrices.contains(price5));
	filterPrices = PriceUtils.filterFromInclusive(prices, date4);
	Assert.assertEquals(filterPrices.size(), 2);
	Assert.assertTrue(filterPrices.contains(price3));
	Assert.assertTrue(filterPrices.contains(price5));
	filterPrices = PriceUtils.filterFromInclusive(prices, date5);
	Assert.assertEquals(filterPrices.size(), 1);
	Assert.assertTrue(filterPrices.contains(price5));
	filterPrices = PriceUtils.filterFromInclusive(prices, date6);
	Assert.assertEquals(filterPrices.size(), 1);
	Assert.assertTrue(filterPrices.contains(price5));

	filterPrices = PriceUtils.filterBetween(prices, date0, date0);
	Assert.assertTrue(filterPrices.isEmpty());
	filterPrices = PriceUtils.filterBetween(prices, date0, date6);
	Assert.assertEquals(filterPrices.size(), 3);
	Assert.assertTrue(filterPrices.contains(price1));
	Assert.assertTrue(filterPrices.contains(price3));
	Assert.assertTrue(filterPrices.contains(price5));
	filterPrices = PriceUtils.filterBetween(prices, date5, date5);
	Assert.assertEquals(filterPrices.size(), 1);
	Assert.assertTrue(filterPrices.contains(price5));
	filterPrices = PriceUtils.filterBetween(prices, date5, date6);
	Assert.assertEquals(filterPrices.size(), 1);
	Assert.assertTrue(filterPrices.contains(price5));
	filterPrices = PriceUtils.filterBetween(prices, date6, date6);
	Assert.assertTrue(filterPrices.isEmpty());

	filterPrices = PriceUtils.filterBetweenInclusive(prices, date0, date0);
	Assert.assertTrue(filterPrices.isEmpty());
	filterPrices = PriceUtils.filterBetweenInclusive(prices, date0, date1);
	Assert.assertTrue(filterPrices.isEmpty());
	filterPrices = PriceUtils.filterBetweenInclusive(prices, date0, date2);
	Assert.assertTrue(filterPrices.isEmpty());
	filterPrices = PriceUtils.filterBetweenInclusive(prices, date0, date3);
	Assert.assertTrue(filterPrices.isEmpty());
	filterPrices = PriceUtils.filterBetweenInclusive(prices, date0, date4);
	Assert.assertTrue(filterPrices.isEmpty());
	filterPrices = PriceUtils.filterBetweenInclusive(prices, date0, date5);
	Assert.assertTrue(filterPrices.isEmpty());
	filterPrices = PriceUtils.filterBetweenInclusive(prices, date0, date6);
	Assert.assertTrue(filterPrices.isEmpty());
	filterPrices = PriceUtils.filterBetweenInclusive(prices, date1, date5);
	Assert.assertEquals(filterPrices.size(), 3);
	Assert.assertTrue(filterPrices.contains(price1));
	Assert.assertTrue(filterPrices.contains(price3));
	Assert.assertTrue(filterPrices.contains(price5));
	filterPrices = PriceUtils.filterBetweenInclusive(prices, date2, date4);
	Assert.assertEquals(filterPrices.size(), 3);
	Assert.assertTrue(filterPrices.contains(price1));
	Assert.assertTrue(filterPrices.contains(price3));
	Assert.assertTrue(filterPrices.contains(price5));
	filterPrices = PriceUtils.filterBetweenInclusive(prices, date3, date5);
	Assert.assertEquals(filterPrices.size(), 2);
	Assert.assertTrue(filterPrices.contains(price3));
	Assert.assertTrue(filterPrices.contains(price5));
	filterPrices = PriceUtils.filterBetweenInclusive(prices, date4, date5);
	Assert.assertEquals(filterPrices.size(), 2);
	Assert.assertTrue(filterPrices.contains(price3));
	Assert.assertTrue(filterPrices.contains(price5));
	filterPrices = PriceUtils.filterBetweenInclusive(prices, date5, date5);
	Assert.assertEquals(filterPrices.size(), 1);
	Assert.assertTrue(filterPrices.contains(price5));
	filterPrices = PriceUtils.filterBetweenInclusive(prices, date0, date6);
	Assert.assertTrue(filterPrices.isEmpty());
	filterPrices = PriceUtils.filterBetweenInclusive(prices, date1, date6);
	Assert.assertTrue(filterPrices.isEmpty());
	filterPrices = PriceUtils.filterBetweenInclusive(prices, date2, date6);
	Assert.assertTrue(filterPrices.isEmpty());
	filterPrices = PriceUtils.filterBetweenInclusive(prices, date3, date6);
	Assert.assertTrue(filterPrices.isEmpty());
	filterPrices = PriceUtils.filterBetweenInclusive(prices, date4, date6);
	Assert.assertTrue(filterPrices.isEmpty());
	filterPrices = PriceUtils.filterBetweenInclusive(prices, date5, date6);
	Assert.assertTrue(filterPrices.isEmpty());
	filterPrices = PriceUtils.filterBetweenInclusive(prices, date6, date6);
	Assert.assertTrue(filterPrices.isEmpty());

    }

}
