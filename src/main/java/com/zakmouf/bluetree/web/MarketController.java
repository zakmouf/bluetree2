package com.zakmouf.bluetree.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
    public ModelAndView getList(ModelMap model) {
	List<Market> markets = marketService.getMarkets();
	ModelAndView mav = new ModelAndView("marketList");
	mav.getModel().put("markets", markets);
	return mav;
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public ModelAndView getNew() {

	List<Stock> stocks = stockService.getStocks();

	MarketForm form = new MarketForm();
	form.setName("name");
	form.setRiskless(0.0D);
	if (!stocks.isEmpty()) {
	    form.setIndiceId(stocks.get(0).getId());
	}

	ModelAndView mav = new ModelAndView("marketNew");
	mav.getModel().put("stocks", stocks);
	mav.getModel().put("form", form);
	return mav;

    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public ModelAndView submitNew(@ModelAttribute MarketForm form) {
	String name = form.getName();
	if (marketService.getMarket(name) != null) {
	    List<Stock> stocks = stockService.getStocks();
	    ModelAndView mav = new ModelAndView("marketNew");
	    mav.getModel().put("stocks", stocks);
	    mav.getModel().put("form", form);
	    return mav;
	}
	Market market = new Market();
	market.setName(name);
	market.setRiskless(form.getRiskless());
	Stock indice = stockService.getStock(form.getIndiceId());
	market.setIndice(indice);
	logger.info(msgOld("insert market <{0}>", market));
	marketService.saveMarket(market);
	ModelAndView mav = new ModelAndView("redirect:/market/view?market=" + market.getId());
	return mav;
    }

    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public ModelAndView getView(@RequestParam("market") Long marketId, HttpServletRequest request) {
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

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView getEdit(@RequestParam("market") Long marketId) {

	Market market = marketService.getMarket(marketId);

	List<Stock> stocks = stockService.getStocks();

	MarketForm form = new MarketForm();
	form.setName(market.getName());
	form.setRiskless(market.getRiskless());
	Stock indice = market.getIndice();
	form.setIndiceId(indice.getId());

	ModelAndView mav = new ModelAndView("marketEdit");
	mav.getModel().put("stocks", stocks);
	mav.getModel().put("form", form);
	return mav;

    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ModelAndView submitEdit(@RequestParam("market") Long marketId, @ModelAttribute MarketForm form) {
	String name = form.getName();
	Market market = marketService.getMarket(name);
	if (market != null) {
	    if (!market.getId().equals(marketId)) {
		List<Stock> stocks = stockService.getStocks();
		ModelAndView mav = new ModelAndView("marketEdit");
		mav.getModel().put("stocks", stocks);
		mav.getModel().put("form", form);
		return mav;
	    }
	}
	market = marketService.getMarket(marketId);
	market.setName(name);
	market.setRiskless(form.getRiskless());
	Stock indice = stockService.getStock(form.getIndiceId());
	market.setIndice(indice);
	logger.info(msgOld("update market <{0}>", market));
	marketService.saveMarket(market);
	ModelAndView mav = new ModelAndView("redirect:/market/view?market=" + market.getId());
	return mav;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ModelAndView doDelete(@RequestParam("market") Long marketId) {
	Market market = marketService.getMarket(marketId);
	logger.info(msgOld("delete market <{0}>", market));
	marketService.deleteMarket(market);
	ModelAndView mav = new ModelAndView("redirect:/market");
	return mav;
    }

    @RequestMapping(value = "/stock", method = RequestMethod.GET)
    public ModelAndView getStock(@RequestParam("market") Long marketId) {
	Market market = marketService.getMarket(marketId);
	MarketStockForm form = new MarketStockForm();
	form.setText("symbol");
	ModelAndView mav = new ModelAndView("marketStock");
	mav.getModel().put("market", market);
	mav.getModel().put("form", form);
	return mav;
    }

    @RequestMapping(value = "/stock", method = RequestMethod.POST)
    public ModelAndView submitStock(@RequestParam("market") Long marketId, @ModelAttribute MarketStockForm form) {
	Market market = marketService.getMarket(marketId);
	String[] symbols = StringUtils.split(form.getText());
	List<Stock> stocks = new ArrayList<Stock>();
	for (String symbol : symbols) {
	    Stock stock = stockService.getStock(symbol);
	    if (stock == null) {
		stock = new Stock();
		stock.setSymbol(symbol);
		stock.setName("undefined");
		logger.info(msgOld("insert stock <{0}>", stock));
		stockService.saveStock(stock);
	    }
	    stocks.add(stock);
	}
	marketService.setStocks(market, stocks);
	logger.info(msgOld("market stock <{0}>", market));
	ModelAndView mav = new ModelAndView("redirect:/market/view?market=" + market.getId());
	return mav;
    }

    @RequestMapping(value = "/names", method = RequestMethod.GET)
    public ModelAndView updateNames(@RequestParam("market") Long marketId) {
	Market market = marketService.getMarket(marketId);
	List<Stock> stocks = new ArrayList<Stock>();
	stocks.add(market.getIndice());
	stocks.addAll(marketService.getStocks(market));
	updateService.updateNames(stocks);
	ModelAndView mav = new ModelAndView("redirect:/market/view?market=" + market.getId());
	return mav;
    }

    @RequestMapping(value = "/prices", method = RequestMethod.GET)
    public ModelAndView updatePrices(@RequestParam("market") Long marketId) {
	Market market = marketService.getMarket(marketId);
	List<Stock> stocks = new ArrayList<Stock>();
	stocks.add(market.getIndice());
	stocks.addAll(marketService.getStocks(market));
	updateService.updatePrices(stocks);
	ModelAndView mav = new ModelAndView("redirect:/market/view?market=" + market.getId());
	return mav;
    }

}
