package com.zakmouf.bluetree.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.zakmouf.bluetree.domain.Market;
import com.zakmouf.bluetree.domain.Price;
import com.zakmouf.bluetree.domain.Stock;
import com.zakmouf.bluetree.service.MarketService;
import com.zakmouf.bluetree.service.PriceService;
import com.zakmouf.bluetree.service.StockService;
import com.zakmouf.bluetree.service.UpdateService;

@Controller
@RequestMapping("/market")
public class MarketController extends BaseController {

    @Autowired
    private MarketService marketService;
    @Autowired
    private StockService stockService;
    @Autowired
    private PriceService priceService;
    @Autowired
    private UpdateService updateService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView whenList(ModelMap model) {
	List<Market> markets = marketService.getMarkets();
	ModelAndView mav = new ModelAndView("marketList");
	mav.getModel().put("markets", markets);
	return mav;
    }

    @RequestMapping(value = "/{marketId}/view", method = RequestMethod.GET)
    public ModelAndView whenView(@PathVariable("marketId") Long marketId, HttpServletRequest request) {
	Market market = marketService.getMarket(marketId);
	Stock indice = market.getIndice();
	List<Price> prices = priceService.getPrices(indice);
	generateChart(request, "market", prices);
	List<Stock> stocks = marketService.getStocks(market);
	ModelAndView mav = new ModelAndView("marketView");
	mav.getModel().put("market", market);
	mav.getModel().put("stocks", stocks);
	return mav;
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public ModelAndView whenNew() {
	MarketForm form = new MarketForm();
	List<Stock> stocks = stockService.getStocks();
	if (!stocks.isEmpty()) {
	    form.setIndiceId(stocks.get(0).getId());
	}
	ModelAndView mav = new ModelAndView("marketNew");
	mav.getModel().put("stocks", stocks);
	mav.getModel().put("form", form);
	return mav;
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public ModelAndView whenSubmitNew(@ModelAttribute MarketForm form) {
	Market market = new Market();
	market.setName(form.getName());
	market.setRiskless(form.getRiskless());
	Stock indice = stockService.getStock(form.getIndiceId());
	market.setIndice(indice);
	marketService.saveMarket(market);
	ModelAndView mav = new ModelAndView(msg("redirect:/market/%1$d/view", market.getId()));
	return mav;
    }

    @RequestMapping(value = "/{marketId}/edit", method = RequestMethod.GET)
    public ModelAndView whenEdit(@PathVariable("marketId") Long marketId) {
	Market market = marketService.getMarket(marketId);
	List<Stock> stocks = stockService.getStocks();
	MarketForm form = new MarketForm();
	form.setName(market.getName());
	form.setRiskless(market.getRiskless());
	Stock indice = market.getIndice();
	form.setIndiceId(indice.getId());
	ModelAndView mav = new ModelAndView("marketEdit");
	mav.getModel().put("market", market);
	mav.getModel().put("stocks", stocks);
	mav.getModel().put("form", form);
	return mav;
    }

    @RequestMapping(value = "/{marketId}/edit", method = RequestMethod.POST)
    public ModelAndView whenSubmitEdit(@PathVariable("marketId") Long marketId, @ModelAttribute MarketForm form) {
	Market market = marketService.getMarket(marketId);
	market.setName(form.getName());
	market.setRiskless(form.getRiskless());
	Stock indice = stockService.getStock(form.getIndiceId());
	market.setIndice(indice);
	marketService.saveMarket(market);
	ModelAndView mav = new ModelAndView(msg("redirect:/market/%1$d/view", market.getId()));
	return mav;
    }

    @RequestMapping(value = "/{marketId}/delete", method = RequestMethod.GET)
    public ModelAndView whenDelete(@PathVariable("marketId") Long marketId) {
	Market market = marketService.getMarket(marketId);
	marketService.deleteMarket(market);
	ModelAndView mav = new ModelAndView("redirect:/market");
	return mav;
    }

    @RequestMapping(value = "/{marketId}/setstocks", method = RequestMethod.GET)
    public ModelAndView whenSetStocks(@PathVariable("marketId") Long marketId) {
	Market market = marketService.getMarket(marketId);
	MarketStockForm form = new MarketStockForm();
	ModelAndView mav = new ModelAndView("marketStock");
	mav.getModel().put("market", market);
	mav.getModel().put("form", form);
	return mav;
    }

    @RequestMapping(value = "/{marketId}/setstocks", method = RequestMethod.POST)
    public ModelAndView whenSubmitSetStocks(@PathVariable("marketId") Long marketId,
	    @ModelAttribute MarketStockForm form) {
	Market market = marketService.getMarket(marketId);
	String[] symbols = StringUtils.split(form.getText());
	List<Stock> stocks = new ArrayList<Stock>();
	for (String symbol : symbols) {
	    Stock stock = stockService.getStock(symbol);
	    if (stock == null) {
		stock = new Stock();
		stock.setSymbol(symbol);
		stockService.saveStock(stock);
	    }
	    stocks.add(stock);
	}
	marketService.setStocks(market, stocks);
	ModelAndView mav = new ModelAndView(msg("redirect:/market/%1$d/view", market.getId()));
	return mav;
    }

    @RequestMapping(value = "/{marketId}/delprices", method = RequestMethod.GET)
    public ModelAndView whenDeletePrices(@PathVariable("marketId") Long marketId) {
	Market market = marketService.getMarket(marketId);
	List<Stock> stocks = new ArrayList<Stock>();
	stocks.add(market.getIndice());
	stocks.addAll(marketService.getStocks(market));
	priceService.deleteAllPrices(stocks);
	ModelAndView mav = new ModelAndView(msg("redirect:/market/%1$d/view", market.getId()));
	return mav;
    }

    @RequestMapping(value = "/{marketId}/updnames", method = RequestMethod.GET)
    public ModelAndView whenUpdateNames(@PathVariable("marketId") Long marketId) {
	Market market = marketService.getMarket(marketId);
	List<Stock> stocks = new ArrayList<Stock>();
	stocks.add(market.getIndice());
	stocks.addAll(marketService.getStocks(market));
	updateService.updateNames(stocks);
	ModelAndView mav = new ModelAndView(msg("redirect:/market/%1$d/view", market.getId()));
	return mav;
    }

    @RequestMapping(value = "/{marketId}/updprices", method = RequestMethod.GET)
    public ModelAndView whenUpdatePrices(@PathVariable("marketId") Long marketId) {
	Market market = marketService.getMarket(marketId);
	List<Stock> stocks = new ArrayList<Stock>();
	stocks.add(market.getIndice());
	stocks.addAll(marketService.getStocks(market));
	updateService.updatePrices(stocks);
	ModelAndView mav = new ModelAndView(msg("redirect:/market/%1$d/view", market.getId()));
	return mav;
    }

}
