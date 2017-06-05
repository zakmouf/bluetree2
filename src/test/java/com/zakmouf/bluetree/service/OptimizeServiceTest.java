package com.zakmouf.bluetree.service;

import com.zakmouf.bluetree.BaseTest;
import com.zakmouf.bluetree.domain.Holding;
import com.zakmouf.bluetree.domain.Market;
import com.zakmouf.bluetree.domain.Portfolio;
import com.zakmouf.bluetree.domain.Stock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OptimizeServiceTest extends BaseTest {

    @Autowired
    private StockService stockService;
    @Autowired
    private MarketService marketService;
    @Autowired
    private PortfolioService portfolioService;
    @Autowired
    private OptimizeService optimizeService;

    @Test
    @Transactional
    public void doTest() {

        Stock indice = stockService.getStock("^GDAXI");

        List<Stock> stocks = new ArrayList<Stock>();
        stocks.add(stockService.getStock("VOW3.DE"));
        stocks.add(stockService.getStock("ALV.DE"));
        stocks.add(stockService.getStock("FRE.DE"));
        stocks.add(stockService.getStock("BAS.DE"));
        stocks.add(stockService.getStock("DAI.DE"));
        stocks.add(stockService.getStock("SAP.DE"));
        stocks.add(stockService.getStock("HEI.DE"));
        stocks.add(stockService.getStock("BMW.DE"));
        stocks.add(stockService.getStock("EOAN.DE"));
        stocks.add(stockService.getStock("LHA.DE"));
        stocks.add(stockService.getStock("SIE.DE"));
        stocks.add(stockService.getStock("FME.DE"));
        stocks.add(stockService.getStock("HEN3.DE"));
        stocks.add(stockService.getStock("BEI.DE"));
        stocks.add(stockService.getStock("TKA.DE"));
        stocks.add(stockService.getStock("BAYN.DE"));
        stocks.add(stockService.getStock("RWE.DE"));
        stocks.add(stockService.getStock("MUV2.DE"));
        stocks.add(stockService.getStock("DTE.DE"));
        stocks.add(stockService.getStock("IFX.DE"));
        stocks.add(stockService.getStock("PSM.DE"));
        stocks.add(stockService.getStock("VNA.DE"));
        stocks.add(stockService.getStock("CON.DE"));
        stocks.add(stockService.getStock("DPW.DE"));
        stocks.add(stockService.getStock("ADS.DE"));
        stocks.add(stockService.getStock("MRK.DE"));
        stocks.add(stockService.getStock("DBK.DE"));
        stocks.add(stockService.getStock("LIN.DE"));
        stocks.add(stockService.getStock("DB1.DE"));
        stocks.add(stockService.getStock("CBK.DE"));

        Market market = new Market("DAX market");
        market.setRiskless(0.0D);
        market.setIndice(indice);

        marketService.saveMarket(market);
        marketService.setStocks(market, stocks);

        Portfolio portfolio = new Portfolio("DAX portfolio");
        portfolio.setBeta(0.8D);
        portfolio.setSize(10);
        portfolio.setFromDate(parseDate("2014-01-01"));
        portfolio.setToDate(parseDate("2015-12-31"));
        portfolio.setMarket(market);

        portfolioService.savePortfolio(portfolio);

        List<Holding> holdings = optimizeService.optimize(portfolio);

        portfolioService.setHoldings(portfolio, holdings);

    }

}
