package com.zakmouf.bluetree.util;

import com.zakmouf.bluetree.BaseTest;
import com.zakmouf.bluetree.domain.Holding;
import com.zakmouf.bluetree.domain.Price;
import com.zakmouf.bluetree.domain.Stock;
import com.zakmouf.bluetree.service.PriceService;
import com.zakmouf.bluetree.service.StockService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PriceHolderTest extends BaseTest {

    @Autowired
    private StockService stockService;
    @Autowired
    private PriceService priceService;

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

        Date fromDate = parseDate("2014-01-01");
        Date toDate = parseDate("2015-12-31");
        logger.debug(msg("fromDate=[%1$tF] toDate=[%2$tF]", fromDate, toDate));

        List<Price> iprices = priceService.getPrices(indice);
        iprices = PriceUtils.filterBetween(iprices, fromDate, toDate);
        Assert.assertFalse(iprices.isEmpty());
        logger.debug(msg("indice=[%1$-10s] dates=[%2$d] ", indice.getSymbol(), iprices.size()));
        iprices = PriceUtils.rebase(iprices, 100D);

        fromDate = PriceUtils.firstDate(iprices);
        toDate = PriceUtils.lastDate(iprices);
        logger.debug(msg("fromDate=[%1$tF] toDate=[%2$tF]", fromDate, toDate));

        List<Holding> holdings = new ArrayList<Holding>();

        PriceHolder priceHolder = new PriceHolder(indice, iprices);
        List<Price> sprices;

        for (Stock stock : stocks) {

            sprices = priceService.getPrices(stock);
            sprices = PriceUtils.filterBetweenInclusive(sprices, fromDate, toDate);
            logger.debug(msg("stock=[%1$-10s] dates=[%2$d] ", stock.getSymbol(), sprices.size()));
            if (!sprices.isEmpty()) {
                priceHolder.addStock(stock, sprices);
                Double sfirstValue = PriceUtils.firstPrice(sprices).getValue();
                Double quantity = 100D / stocks.size() / sfirstValue;
                Holding holding = new Holding(stock);
                holding.setQuantity(quantity);
                holdings.add(holding);
            }

        }

        logger.debug(msg("****** quantity ******"));

        for (Holding holding : holdings) {
            Stock stock = holding.getStock();
            Double quantity = holding.getQuantity();
            logger.debug(msg("stock=[%1$-10s] quantity=[%2$10.5f]", stock.getSymbol(), quantity));
        }

        List<Price> pprices = priceHolder.valuate(holdings);
        Double pfirstValue = PriceUtils.firstValue(pprices);
        Double plastValue = PriceUtils.lastValue(pprices);

        logger.debug(msg("****** weight ******"));

        for (Holding holding : holdings) {
            Stock stock = holding.getStock();
            Double sfirstValue = priceHolder.getClosedPrice(stock, fromDate).getValue();
            Double sLastValue = priceHolder.getClosedPrice(stock, toDate).getValue();
            Double hfirstWeight = holding.getQuantity() * sfirstValue / pfirstValue;
            Double hlastWeight = holding.getQuantity() * sLastValue / plastValue;
            logger.debug(msg("stock=[%1$-10s] firstWeight=[%2$.2f] lastWeight=[%3$.2f]", stock.getSymbol(),
                    hfirstWeight * 100D, hlastWeight * 100D));
        }

        logger.debug(msg("****** prices ******"));

        for (Price pprice : pprices) {
            Date date = pprice.getDate();
            Price iprice = priceHolder.getClosedPrice(indice, date);
            logger.debug(msg("%1$tF    %2$10.5f    %3$10.5f", date, iprice.getValue(), pprice.getValue()));
        }

        logger.debug(msg("****** measure ******"));

        MeasureHolder measureHolder = new MeasureHolder(0D);
        measureHolder.setIndicePrices(iprices);
        measureHolder.setPortfolioPrices(pprices);
        logger.debug(msg("measure=[%1$s]", measureHolder.getCombinedMeasure()));

    }

}
