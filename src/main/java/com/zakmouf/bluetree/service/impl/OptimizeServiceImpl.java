package com.zakmouf.bluetree.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.zakmouf.bluetree.util.PortfolioValuator;
import com.zakmouf.bluetree.util.PriceHolder;
import com.zakmouf.bluetree.util.PriceUtils;
import com.zakmouf.bluetree.util.Randomizer;

@Component
public class OptimizeServiceImpl extends BaseServiceImpl implements OptimizeService {

    private static final int PREVIEW_BASKET_SIZE = 10;

    private static final int PREVIEW_MAX_LOOPS = 20000;

    private static final double BETA_DELTA = 0.1;

    private static final int OPTIMIZE_UNSUCCESS = 40000;

    private static final long OPTIMIZE_MAX_TIME = 7L * 60L * 1000L;

    private static final int OPTIMIZE_SUCCESS = 300;

    @Autowired
    private MarketService marketService;
    @Autowired
    private PriceService priceService;

    @Override
    public List<Holding> optimize(Portfolio portfolio) {

	logger.info(msg("optimize portfolio=[%1$s]", portfolio));

	//
	// read data
	//
	Market market = portfolio.getMarket();
	Stock indice = market.getIndice();
	List<Stock> marketStocks = marketService.getStocks(market);
	Date fromDate = portfolio.getFromDate();
	Date toDate = portfolio.getToDate();

	// read indice

	List<Price> indicePrices = priceService.getPrices(indice);
	indicePrices = PriceUtils.filterBetween(indicePrices, fromDate, toDate);
	logger.info(msg("indice=[%1$s] prices=[%2$d] fromDate=[%3$tF] toDate=[%4$tF]", indice.getSymbol(),
		indicePrices.size(), fromDate, toDate));

	// validate indice prices

	if (indicePrices.size() < 10) {
	    throw new RuntimeException(msg("indice=[%1$s] prices=[%2$d] fromDate=[%3$tF] toDate=[%4$tF] : needed=[10]",
		    indice.getSymbol(), indicePrices.size(), fromDate, toDate));
	}

	// effective from date and to date

	Date effectiveFromDate = PriceUtils.firstDate(indicePrices);
	Date effectiveToDate = PriceUtils.lastDate(indicePrices);
	logger.info(msg("fromDate=[%1$tF] : effectiveFromDate=[%2$tF]", fromDate, effectiveFromDate));
	logger.info(msg("toDate=[%1$tF] : effectiveToDate=[%2$tF]", toDate, effectiveToDate));

	PriceHolder priceHolder = new PriceHolder();
	priceHolder.addPrices(indice, indicePrices);

	// read stocks

	List<Stock> optimizeStocks = new ArrayList<Stock>();
	for (Stock stock : marketStocks) {
	    List<Price> stockPrices = priceService.getPrices(stock);
	    stockPrices = PriceUtils.filterBetweenInclusive(stockPrices, effectiveFromDate, effectiveToDate);
	    logger.info(msg("stock=[%1$s] prices=[%2$d] fromDate=[%3$tF] toDate=[%4$tF]", stock.getSymbol(),
		    stockPrices.size(), effectiveFromDate, effectiveToDate));
	    if (!stockPrices.isEmpty()) {
		optimizeStocks.add(stock);
		priceHolder.addPrices(stock, stockPrices);
	    }
	}

	//

	logger.info(msg("marketStocks=[%1$d] optimizeStocks=[%2$d]", marketStocks.size(), optimizeStocks.size()));
	if (optimizeStocks.size() < 10) {
	    throw new RuntimeException(msg("marketStocks=[%1$d] optimizeStocks=[%2$d] : needed [10]",
		    marketStocks.size(), optimizeStocks.size()));
	}

	//

	Randomizer randomizer = new Randomizer(PREVIEW_BASKET_SIZE, optimizeStocks.size());
	MeasureHolder measureHolder = new MeasureHolder(market.getRiskless());
	measureHolder.setIndicePrices(indicePrices);

	Double ratioMax = null;
	List<Holding> holdings;

	for (int i = 0; i < PREVIEW_MAX_LOOPS; i++) {

	    int[] keys = randomizer.nextBasket();

	    holdings = new ArrayList<Holding>();

	    for (int j = 0; j < keys.length; j++) {
		Stock stock = optimizeStocks.get(keys[j]);
		Price firstPrice = priceHolder.getPrice(stock, effectiveFromDate);
		Double firstValue = firstPrice.getValue();
		Double quantity = 100.0D / keys.length / firstValue;
		Holding holding = new Holding(stock);
		holding.setQuantity(quantity);
		holdings.add(holding);
	    }

	    List<Price> portfolioPrices = PortfolioValuator.valuate(indice, holdings, effectiveFromDate,
		    effectiveToDate, priceHolder);
	    measureHolder.setPortfolioPrices(portfolioPrices);

	    CombinedMeasure combinedMeasure = measureHolder.getCombinedMeasure();
	    Double beta = combinedMeasure.getRegressionMeasure().getBeta();
	    Double ratio = combinedMeasure.getRegressionMeasure().getDecisionRatio();

	    if (beta >= portfolio.getBeta() - BETA_DELTA && beta <= portfolio.getBeta() + BETA_DELTA) {
		if (ratioMax == null || ratioMax < ratio) {
		    logger.debug(msg("preview=[%1$5d] : beta=[%2$.5f] ratio=[%3$.5f]", i, beta, ratio * 100.0D));
		    ratioMax = ratio;
		}
	    }

	}

	if (ratioMax == null) {
	    throw new RuntimeException("ratio max not found");
	}
	Double ratioStart = 0.01D * ((int) (ratioMax * 100D + 5.0d));
	logger.info(msg("ratioMax=[%1$.5f] : ratioStart=[%2$.5f]", ratioMax, ratioStart));

	// correct ratio max
	// ratioMax += 0.05;

	// max weight

	return new ArrayList<Holding>();

    }

}
