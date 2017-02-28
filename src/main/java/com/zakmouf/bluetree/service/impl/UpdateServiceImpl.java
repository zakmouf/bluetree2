package com.zakmouf.bluetree.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zakmouf.bluetree.domain.Price;
import com.zakmouf.bluetree.domain.Stock;
import com.zakmouf.bluetree.service.PriceService;
import com.zakmouf.bluetree.service.StockService;
import com.zakmouf.bluetree.service.UpdateService;
import com.zakmouf.bluetree.util.PriceUtil;

@Component
public class UpdateServiceImpl extends BaseServiceImpl implements UpdateService {

    @Autowired
    private StockService stockService;
    @Autowired
    private PriceService priceService;

    private String nameUrlPattern;
    private Date priceStartDate;
    private Integer priceIncrement;
    private String priceUrlPattern;

    public UpdateServiceImpl() throws ParseException {
	nameUrlPattern = "http://finance.yahoo.com/d/quotes.csv?s=%1$s&f=n";
	priceStartDate = DateUtils.parseDate("2012-12-01", "yyyy-MM-dd");
	priceIncrement = 200;
	priceUrlPattern = "http://ichart.finance.yahoo.com/table.csv?s=%1$s&a=%2$d&b=%3$d&c=%4$d&d=%5$d&e=%6$d&f=%7$d&g=d&ignore=.csv";
    }

    @Override
    @Transactional
    public void updateNames(Stock stock) {
	String url = msg(nameUrlPattern, stock.getSymbol());
	List<String> lines = loadUrl(url);
	String name = null;
	if (!lines.isEmpty()) {
	    name = lines.get(0);
	    name = StringUtils.replace(name, "\"", "");
	    stock.setName(name);
	    stockService.saveStock(stock);
	}
    }

    @Override
    @Transactional
    public void updateNames(List<Stock> stocks) {
	for (Stock stock : stocks) {
	    updateNames(stock);
	}
    }

    @Override
    @Transactional
    public void updatePrices(Stock stock) {

	Date toDate = new Date();
	toDate = DateUtils.truncate(toDate, Calendar.DATE);
	toDate = DateUtils.addDays(toDate, -1);

	Date fromDate = priceStartDate;

	List<Price> prices = priceService.getPrices(stock);
	Date lastDate = PriceUtil.lastDate(prices);
	if (lastDate != null) {
	    fromDate = DateUtils.addDays(lastDate, 1);
	}

	prices = new ArrayList<Price>();

	Date fromDateStep = fromDate;

	while (fromDateStep.compareTo(toDate) <= 0) {

	    Date toDateStep = DateUtils.addDays(fromDateStep, priceIncrement);
	    if (toDateStep.compareTo(toDate) > 0) {
		toDateStep = toDate;
	    }

	    List<Price> pricesStep = loadPrices(stock, fromDateStep, toDateStep);
	    logger.debug(msg("update stock=[%1$s] from=[%2$tF] to=[%3$tF] : prices=[%4$d]", stock.getSymbol(),
		    fromDateStep, toDateStep, pricesStep.size()));

	    prices.addAll(pricesStep);
	    fromDateStep = DateUtils.addDays(toDateStep, 1);

	}

	logger.info(msg("update stock=[%1$s] from=[%2$tF] to=[%3$tF] : prices=[%4$d]", stock.getSymbol(), fromDate,
		toDate, prices.size()));
	priceService.addPrices(stock, prices);

    }

    @Override
    @Transactional
    public void updatePrices(List<Stock> stocks) {
	for (Stock stock : stocks) {
	    updatePrices(stock);
	}
    }

    private List<Price> loadPrices(Stock stock, Date fromDate, Date toDate) {

	Calendar fromCalendar = DateUtils.toCalendar(fromDate);
	Calendar toCalendar = DateUtils.toCalendar(toDate);

	String url = msg(priceUrlPattern, stock.getSymbol(), fromCalendar.get(Calendar.MONTH),
		fromCalendar.get(Calendar.DATE), fromCalendar.get(Calendar.YEAR), toCalendar.get(Calendar.MONTH),
		toCalendar.get(Calendar.DATE), toCalendar.get(Calendar.YEAR));

	List<String> lines = loadUrl(url);
	if (!lines.isEmpty()) {
	    lines = lines.subList(1, lines.size());
	}

	List<Price> prices = new ArrayList<Price>();

	for (String line : lines) {
	    Price price = new Price();
	    String[] tokens = StringUtils.split(line, ",");
	    String dateAsString = tokens[0];
	    try {
		price.setDate(DateUtils.parseDate(dateAsString, "yyyy-MM-dd"));
	    } catch (ParseException ex) {
		throw new IllegalArgumentException(msg("failed to parse date [%1$s]", dateAsString), ex);
	    }
	    String valueAsString = tokens[6];
	    try {
		price.setValue(Double.valueOf(valueAsString));
	    } catch (NumberFormatException ex) {
		throw new IllegalArgumentException(msg("failed to parse double [%1$s]", valueAsString), ex);
	    }
	    prices.add(price);
	}

	Collections.sort(prices);

	logger.debug(msg("load stock=[%1$s] from=[%2$tF] to=[%3$tF] : prices=[%4$d]", stock.getSymbol(), fromDate,
		toDate, prices.size()));

	return prices;

    }

    private List<String> loadUrl(String url) {

	InputStream input = null;
	List<String> lines = new ArrayList<String>();

	try {
	    input = new URL(url).openStream();
	    InputStreamReader inputReader = new InputStreamReader(input);
	    BufferedReader buffReader = new BufferedReader(inputReader);
	    String line = buffReader.readLine();
	    while (line != null) {
		lines.add(line);
		line = buffReader.readLine();
	    }
	} catch (IOException ex) {
	    logger.warn(msg("failed to load url=[%1$s]", url));
	} finally {
	    try {
		if (input != null) {
		    input.close();
		}
	    } catch (IOException ex) {
		// ignore
	    }
	}

	logger.debug(msg("load url=[%1$s] : lines=[%2$d]", url, lines.size()));

	return lines;

    }

}
