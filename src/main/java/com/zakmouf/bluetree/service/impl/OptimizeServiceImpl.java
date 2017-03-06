package com.zakmouf.bluetree.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zakmouf.bluetree.domain.Holding;
import com.zakmouf.bluetree.domain.Market;
import com.zakmouf.bluetree.domain.Portfolio;
import com.zakmouf.bluetree.domain.Price;
import com.zakmouf.bluetree.domain.Stock;
import com.zakmouf.bluetree.service.MarketService;
import com.zakmouf.bluetree.service.OptimizeService;
import com.zakmouf.bluetree.service.PriceService;
import com.zakmouf.bluetree.util.CombinedMeasure;
import com.zakmouf.bluetree.util.MeasureHolder;
import com.zakmouf.bluetree.util.PriceHolder;
import com.zakmouf.bluetree.util.PriceUtils;
import com.zakmouf.bluetree.util.Randomizer;

@Component
public class OptimizeServiceImpl extends BaseServiceImpl implements OptimizeService {

    private static final int PREVIEW_BASKET_SIZE = 10;

    private static final int PREVIEW_UNSUCCESS = 10000;

    private static final double BETA_DELTA = 0.1;

    private static final int OPTIMIZE_BASKET_SIZE = 5;

    private static final int OPTIMIZE_UNSUCCESS = 10000;

    private static final int OPTIMIZE_SUCCESS = 50;

    private static final double RATIO_LOOP_DECREASE = 0.025D;

    @Autowired
    private MarketService marketService;
    @Autowired
    private PriceService priceService;

    @Override
    public List<Holding> optimize(Portfolio portfolio) {

	logger.info(msg("optimize portfolio=[%1$s]", portfolio));

	//

	Market market = portfolio.getMarket();
	Stock indice = market.getIndice();
	List<Stock> initialStocks = marketService.getStocks(market);
	Date fromDate = portfolio.getFromDate();
	Date toDate = portfolio.getToDate();

	//

	logger.info(msg("fromDate=[%1$tF] toDate=[%2$tF]", fromDate, toDate));

	//

	List<Price> indicePrices = priceService.getPrices(indice);
	indicePrices = PriceUtils.filterBetween(indicePrices, fromDate, toDate);
	logger.info(msg("indice=[%1$s] prices=[%2$d]", indice.getSymbol(), indicePrices.size()));

	//

	if (indicePrices.size() < 10) {
	    throw new RuntimeException(
		    msg("indice=[%1$s] prices=[%2$d] : needed=[10]", indice.getSymbol(), indicePrices.size()));
	}

	//

	indicePrices = PriceUtils.rebase(indicePrices, 100D);
	fromDate = PriceUtils.firstDate(indicePrices);
	toDate = PriceUtils.lastDate(indicePrices);
	logger.info(msg("fromDate=[%1$tF] toDate=[%2$tF]", fromDate, toDate));

	//

	PriceHolder priceHolder = new PriceHolder(indice, indicePrices);

	List<Stock> stocks = new ArrayList<Stock>();
	for (Stock stock : initialStocks) {
	    List<Price> stockPrices = priceService.getPrices(stock);
	    stockPrices = PriceUtils.filterBetweenInclusive(stockPrices, fromDate, toDate);
	    logger.info(msg("stock=[%1$s] prices=[%2$d]", stock.getSymbol(), stockPrices.size()));
	    if (!stockPrices.isEmpty()) {
		stocks.add(stock);
		priceHolder.addStock(stock, stockPrices);
	    }
	}

	//

	logger.info(msg("stocks=[%1$d/%2$d]", stocks.size(), initialStocks.size()));
	if (stocks.size() < 10) {
	    throw new RuntimeException(msg("stocks=[%1$d/%2$d] : needed [10]", stocks.size(), initialStocks.size()));
	}

	//

	MeasureHolder measureHolder = new MeasureHolder(market.getRiskless());
	measureHolder.setIndicePrices(indicePrices);

	//

	Randomizer randomizer = new Randomizer(PREVIEW_BASKET_SIZE, stocks.size());

	int success = 0;
	int unsuccess = 0;

	Double ratioMax = 0D;

	while (unsuccess < PREVIEW_UNSUCCESS) {

	    int[] keys = randomizer.nextBasket();

	    List<Holding> holdings = new ArrayList<Holding>();

	    for (int j = 0; j < keys.length; j++) {
		Stock stock = stocks.get(keys[j]);
		Price firstPrice = priceHolder.getClosedPrice(stock, fromDate);
		Double firstValue = firstPrice.getValue();
		Double quantity = 100D / keys.length / firstValue;
		Holding holding = new Holding(stock);
		holding.setQuantity(quantity);
		holdings.add(holding);
	    }

	    List<Price> portfolioPrices = priceHolder.valuate(holdings);
	    measureHolder.setPortfolioPrices(portfolioPrices);

	    CombinedMeasure combinedMeasure = measureHolder.getCombinedMeasure();
	    Double beta = combinedMeasure.getRegressionMeasure().getBeta();
	    Double ratio = combinedMeasure.getRegressionMeasure().getDecisionRatio();

	    if (beta >= portfolio.getBeta() - BETA_DELTA && beta <= portfolio.getBeta() + BETA_DELTA
		    && ratioMax < ratio) {
		success++;
		unsuccess = 0;
		logger.debug(msg("preview=[%1$5d] : beta=[%2$.5f] ratio=[%3$.5f]", success, beta, ratio * 100D));
		ratioMax = ratio;
	    } else {
		unsuccess++;
	    }

	}

	Double ratioLoop = 0.01D * ((int) (ratioMax * 100D + 5D));
	logger.info(msg("ratioMax=[%1$.5f] : ratioStart=[%2$.5f]", ratioMax * 100D, ratioLoop * 100D));

	TreeMap<String, Double> weights = new TreeMap<String, Double>();
	for (Stock stock : stocks) {
	    weights.put(stock.getSymbol(), 0D);
	}

	randomizer = new Randomizer(OPTIMIZE_BASKET_SIZE, stocks.size());

	success = 0;
	unsuccess = 0;

	logger.debug(msg("success=[%1$d/%2$d] ratio=[%3$.5f]", success, OPTIMIZE_SUCCESS, ratioLoop * 100D));

	while (success < OPTIMIZE_SUCCESS) {

	    int[] keys = randomizer.nextBasket();

	    List<Holding> holdings = new ArrayList<Holding>();

	    for (int j = 0; j < keys.length; j++) {
		Stock stock = stocks.get(keys[j]);
		Price firstPrice = priceHolder.getClosedPrice(stock, fromDate);
		Double firstValue = firstPrice.getValue();
		Double quantity = 100D / keys.length / firstValue;
		Holding holding = new Holding(stock);
		holding.setQuantity(quantity);
		holdings.add(holding);
	    }

	    List<Price> portfolioPrices = priceHolder.valuate(holdings);
	    measureHolder.setPortfolioPrices(portfolioPrices);

	    CombinedMeasure combinedMeasure = measureHolder.getCombinedMeasure();
	    Double beta = combinedMeasure.getRegressionMeasure().getBeta();
	    Double ratio = combinedMeasure.getRegressionMeasure().getDecisionRatio();

	    if (beta >= portfolio.getBeta() - BETA_DELTA && beta <= portfolio.getBeta() + BETA_DELTA
		    && ratio >= ratioLoop) {
		success++;
		unsuccess = 0;
		logger.debug(msg("success=[%1$d/%2$d] ratio=[%3$.5f]", success, OPTIMIZE_SUCCESS, ratioLoop * 100D));

		for (Holding holding : holdings) {
		    Stock stock = holding.getStock();
		    Double weight = weights.get(stock.getSymbol());
		    weight = weight + 1D;
		    weights.put(stock.getSymbol(), weight);
		}

	    } else {
		unsuccess++;
		if (unsuccess >= OPTIMIZE_UNSUCCESS) {
		    ratioLoop = ratioLoop - RATIO_LOOP_DECREASE;
		    unsuccess = 0;
		    logger.debug(msg("decrease ratio=[%1$.5f]", ratioLoop * 100D));
		}
	    }

	}

	Double totalWeight = 0D;
	for (Double weight : weights.values()) {
	    totalWeight = totalWeight + weight;
	}
	
	List<Holding> holdings = new ArrayList<Holding>();
	 

	return holdings;

    }

}
