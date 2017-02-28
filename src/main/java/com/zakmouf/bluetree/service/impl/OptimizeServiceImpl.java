package com.zakmouf.bluetree.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zakmouf.bluetree.dao.MarketDao;
import com.zakmouf.bluetree.dao.PortfolioDao;
import com.zakmouf.bluetree.domain.Market;
import com.zakmouf.bluetree.domain.Portfolio;
import com.zakmouf.bluetree.domain.Position;
import com.zakmouf.bluetree.domain.Price;
import com.zakmouf.bluetree.domain.Stock;
import com.zakmouf.bluetree.service.OptimizeService;
import com.zakmouf.bluetree.service.PriceService;
import com.zakmouf.bluetree.util.BasketUtil;
import com.zakmouf.bluetree.util.PriceUtil;
import com.zakmouf.bluetree.util.Randomizer;
import com.zakmouf.bluetree.util.StatUtil;

@Component
public class OptimizeServiceImpl extends BaseService implements OptimizeService {

    private static final int PREVIEW_MAX_LOOPS = 5000;

    private static final double BETA_DELTA = 0.1;

    private static final long PREVIEW_MAX_TIME = 60L * 1000L;

    private static final int OPTIMIZE_UNSUCCESS = 40000;

    private static final long OPTIMIZE_MAX_TIME = 7L * 60L * 1000L;

    private static final int OPTIMIZE_SUCCESS = 300;

    @Autowired
    private PortfolioDao portfolioDao;
    @Autowired
    private MarketDao marketDao;
    @Autowired
    private PriceService priceService;

    @Override
    public List<Position> optimize(Portfolio portfolio) {

	logger.info(msg("optimize portfolio <{0}>", portfolio));

	//
	// read data
	//
	Market market = portfolioDao.getMarket(portfolio);
	Stock indice = market.getIndice();
	List<Stock> initialStocks = marketDao.getStocks(market);
	Date fromDate = portfolio.getFromDate();
	Date toDate = portfolio.getToDate();

	// read indice

	List<Price> indicePrices = priceService.getPrices(indice);
	indicePrices = PriceUtil.filterBetween(indicePrices, fromDate, toDate);
	if (indicePrices.size() < 10) {
	    throw new RuntimeException(msg("Indice <{0}> is empty", indice.getSymbol()));
	}
	logger.debug(msg("Indice <{0}> size <{1}> first <{2}> last <{3}>", indice.getSymbol(), indicePrices.size(),
		PriceUtil.firstDate(indicePrices), PriceUtil.lastDate(indicePrices)));

	// correct dates
	fromDate = PriceUtil.firstDate(indicePrices);
	toDate = PriceUtil.lastDate(indicePrices);

	// read stocks
	List<Stock> stocks = new ArrayList<Stock>();
	List<List<Price>> stockPricesList = new ArrayList<List<Price>>();
	for (Stock stock : initialStocks) {
	    List<Price> stockPrices = priceService.getPrices(stock);
	    stockPrices = PriceUtil.filterBetweenInclusive(stockPrices, fromDate, toDate);
	    if (stockPrices.isEmpty()) {
		logger.debug(msg("Stock <{0}> is empty", stock.getSymbol()));
	    } else {
		int diff = PriceUtil.matchPrices(indicePrices, stockPrices);
		logger.debug(msg("Stock <{0}> size <{1}> first <{2}> last <{3}> diff <{4}>", stock.getSymbol(),
			stockPrices.size(), PriceUtil.firstDate(stockPrices), PriceUtil.lastDate(stockPrices), diff));
		stocks.add(stock);
		stockPricesList.add(stockPrices);
	    }
	}

	// less stocks to use
	logger.info(msg("Use {0} / {1} stocks", stocks.size(), initialStocks.size()));
	if (stocks.size() < 10) {
	    throw new RuntimeException("Less than 10 stocks match chosen dates");
	}

	// calculate returns
	Double[] indiceReturns = PriceUtil.calculateReturns(indicePrices);
	List<Double[]> stockReturnsList = new ArrayList<Double[]>();
	for (List<Price> stockPrices : stockPricesList) {
	    Double[] stockReturns = PriceUtil.calculateReturns(stockPrices);
	    stockReturnsList.add(stockReturns);
	}

	Double beta = portfolio.getBeta();
	Double riskless = market.getRiskless() / 252.0D;

	// indice properties
	Double indiceMean = StatUtil.mean(indiceReturns);
	Double indiceStdev = StatUtil.stdev(indiceReturns);
	Double indiceSharp = StatUtil.sharp(indiceMean, riskless, indiceStdev);

	Integer size = stocks.size();

	Boolean ratioMaxFound = false;
	Randomizer randomizer = new Randomizer();
	Double ratioMax = Double.MIN_VALUE;

	Integer cpt = 0;
	Long start = System.currentTimeMillis();

	while (cpt < PREVIEW_MAX_LOOPS) {

	    if (System.currentTimeMillis() - start > PREVIEW_MAX_TIME) {
		break;
	    }

	    Integer[] keys = randomizer.keys(10, size);
	    Double[] basketReturns = BasketUtil.calculateReturns(stockReturnsList, keys);

	    Double basketMean = StatUtil.mean(basketReturns);
	    Double basketStdev = StatUtil.stdev(basketReturns);
	    Double basketSharp = StatUtil.sharp(basketMean, riskless, basketStdev);
	    Double basketRatio = StatUtil.ratio(indiceSharp, basketSharp);
	    Double basketBeta = StatUtil.beta(indiceReturns, basketReturns);

	    if (basketBeta >= beta - BETA_DELTA && basketBeta <= beta + BETA_DELTA) {
		ratioMaxFound = true;
		if (ratioMax < basketRatio) {
		    cpt = 0;
		    ratioMax = basketRatio;
		    logger.debug(msg("Ratio max <{0,number,0.00%}>", ratioMax));
		}
	    }
	    cpt++;
	}

	if (!ratioMaxFound) {
	    throw new RuntimeException("No ratio found for chosen beta");
	}

	// correct ratio max
	ratioMax += 0.05;
	logger.info(msg("Ratio max <{0,number,0.00%}>", ratioMax));

	// max weight
	Double maxWeight = (1 - Math.exp(-0.08)) / (1 - Math.exp(-0.08 * size));
	logger.info(msg("Max weight <{0,number,0.00%}>", maxWeight));

	Integer[] points = new Integer[size];
	for (int i = 0; i < size; i++) {
	    points[i] = 0;
	}
	Integer pointTotal = 0;

	randomizer = new Randomizer();

	int success = 0;
	int unsuccess = 0;

	logger.debug(msg("Ratio <{0,number,0.00%}> Success <{1}>", ratioMax, success));

	logger.info("Start optimization");
	start = System.currentTimeMillis();

	while (success < OPTIMIZE_SUCCESS) {

	    if (System.currentTimeMillis() - start > OPTIMIZE_MAX_TIME) {
		throw new RuntimeException("No portfolio found (time exceed)");
	    }

	    Integer[] keys = randomizer.keys(5, size);
	    Double[] baskerReturns = BasketUtil.calculateReturns(stockReturnsList, keys);

	    Double basketMean = StatUtil.mean(baskerReturns);
	    Double basketStdev = StatUtil.stdev(baskerReturns);
	    Double basketSharp = StatUtil.sharp(basketMean, riskless, basketStdev);
	    Double basketRatio = StatUtil.ratio(indiceSharp, basketSharp);
	    Double basketBeta = StatUtil.beta(indiceReturns, baskerReturns);

	    Boolean weightOk = true;
	    // check weights after 10% of success
	    if (success > (OPTIMIZE_SUCCESS / 10)) {
		for (int i = 0; weightOk && i < keys.length; i++) {
		    double weight = points[keys[i]] + 1;
		    weight /= (pointTotal + keys.length);
		    weightOk = weight < maxWeight;
		}
	    }

	    if (weightOk && basketBeta >= beta - BETA_DELTA && basketBeta <= beta + BETA_DELTA
		    && basketRatio >= ratioMax) {

		for (int i = 0; i < keys.length; i++) {
		    points[keys[i]] += 1;
		}
		pointTotal += keys.length;

		success++;
		unsuccess = 0;
		logger.debug(msg("Ratio <{0,number,0.00%}> Success <{1}>", ratioMax, success));
	    } else {
		unsuccess++;
		if (unsuccess == OPTIMIZE_UNSUCCESS) {
		    unsuccess = 0;
		    ratioMax -= 0.005;
		    logger.debug(msg("Ratio <{0,number,0.00%}> Success <{1}>", ratioMax, success));
		}
	    }

	}
	logger.info("Finish optimization");

	List<Position> positions = new ArrayList<Position>();
	for (int i = 0; i < points.length; i++) {
	    if (points[i] != 0) {
		Stock stock = stocks.get(i);
		Double weight = (points[i] + 0.0D) / pointTotal;
		Position position = new Position();
		position.setStock(stock);
		position.setWeight(weight);
		positions.add(position);
	    }
	}
	Collections.sort(positions, new Comparator<Position>() {
	    @Override
	    public int compare(Position o1, Position o2) {
		return o2.getWeight().compareTo(o1.getWeight());
	    }
	});

	for (Position position : positions) {
	    logger.debug(msg("Stock <{0}> Weight <{1,number,0.00%}>", position.getStock().getSymbol(),
		    position.getWeight()));
	}

	size = portfolio.getSize().intValue();

	if (positions.size() > size) {

	    Position position = (Position) positions.get(size - 1);
	    double weight = position.getWeight().doubleValue();

	    Iterator<Position> iterator = positions.iterator();
	    while (iterator.hasNext()) {
		position = iterator.next();
		if (position.getWeight().doubleValue() < weight) {
		    iterator.remove();
		}
	    }

	    double total = 0.0;
	    iterator = positions.iterator();
	    while (iterator.hasNext()) {
		position = iterator.next();
		total += position.getWeight().doubleValue();
	    }

	    iterator = positions.iterator();
	    while (iterator.hasNext()) {
		position = iterator.next();
		weight = position.getWeight().doubleValue() / total;
		position.setWeight(new Double(weight));
	    }

	}

	for (Position position : positions) {
	    logger.debug(msg("Stock <{0}> Weight <{1,number,0.00%}>", position.getStock().getSymbol(),
		    position.getWeight()));
	}

	portfolio.setFromDate(fromDate);
	portfolio.setToDate(toDate);

	return positions;

    }

}
