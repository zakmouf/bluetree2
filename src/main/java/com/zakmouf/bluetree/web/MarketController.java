package com.zakmouf.bluetree.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.zakmouf.bluetree.dao.MarketDao;
import com.zakmouf.bluetree.dao.StockDao;
import com.zakmouf.bluetree.domain.Market;
import com.zakmouf.bluetree.domain.Stock;

@Controller
@RequestMapping("/market")
public class MarketController extends BaseController {

    private MarketDao marketDao;
    private StockDao stockDao;

    @Autowired
    public void setMarketDao(MarketDao marketDao) {
	this.marketDao = marketDao;
    }

    @Autowired
    public void setStockDao(StockDao stockDao) {
	this.stockDao = stockDao;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getList(ModelMap model) {
	List<Market> markets = marketDao.getMarkets();
	ModelAndView mav = new ModelAndView("marketList");
	mav.getModel().put("markets", markets);
	return mav;
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public ModelAndView getNew() {
	MarketForm form = new MarketForm();
	List<Stock> stocks = stockDao.getStocks();
	form.setStocks(stocks);
	form.setName("name");
	form.setRiskless(0.0D);
	if (stocks.isEmpty()) {
	    form.setIndiceId(stocks.get(0).getId());
	}
	ModelAndView mav = new ModelAndView("marketNew");
	mav.getModel().put("form", form);
	return mav;
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public ModelAndView submitNew(@ModelAttribute MarketForm form) {
	String name = form.getName();
	Double riskless = form.getRiskless();
	Long indiceId = form.getIndiceId();
	if (marketDao.findMarket(name) != null) {
	    ModelAndView mav = new ModelAndView("marketNew");
	    List<Stock> stocks = stockDao.getStocks();
	    form.setStocks(stocks);
	    mav.getModel().put("form", form);
	    return mav;
	}
	Market market = new Market();
	market.setName(name);
	market.setRiskless(riskless);
	Stock indice = stockDao.findStock(indiceId);
	marketDao.insertMarket(market);
	marketDao.setIndice(market, indice);
	ModelAndView mav = new ModelAndView("redirect:/market");
	return mav;
    }

    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public ModelAndView getView(@RequestParam("market") Long marketId, HttpServletRequest request) {
	Market market = marketDao.findMarket(marketId);
	Stock indice = marketDao.getIndice(market);
	List<Stock> stocks = marketDao.getStocks(market);
	ModelAndView mav = new ModelAndView("marketView");
	mav.getModel().put("market", market);
	mav.getModel().put("indice", indice);
	mav.getModel().put("stocks", stocks);
	return mav;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView getEdit(@RequestParam("market") Long marketId) {
	Market market = marketDao.findMarket(marketId);
	MarketForm form = new MarketForm();
	form.setName(market.getName());
	form.setRiskless(market.getRiskless());
	Stock indice = marketDao.getIndice(market);
	form.setIndiceId(indice.getId());
	List<Stock> stocks = stockDao.getStocks();
	form.setStocks(stocks);
	ModelAndView mav = new ModelAndView("marketEdit");
	mav.getModel().put("form", form);
	return mav;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ModelAndView submitEdit(@RequestParam("market") Long marketId, @ModelAttribute MarketForm form) {
	String name = form.getName();
	Double riskless = form.getRiskless();
	Long indiceId = form.getIndiceId();
	Market market = marketDao.findMarket(name);
	if (market != null) {
	    if (!market.getId().equals(marketId)) {
		List<Stock> stocks = stockDao.getStocks();
		form.setStocks(stocks);
		ModelAndView mav = new ModelAndView("marketEdit");
		mav.getModel().put("form", form);
		return mav;
	    }
	}
	market = marketDao.findMarket(marketId);
	market.setName(name);
	market.setRiskless(riskless);
	Stock indice = stockDao.findStock(indiceId);
	marketDao.setIndice(market, indice);
	logger.info(msg("update market <{0}>", market));
	marketDao.updateMarket(market);
	marketDao.setIndice(market, indice);
	ModelAndView mav = new ModelAndView("redirect:/market");
	return mav;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ModelAndView doDelete(@RequestParam("market") Long marketId) {
	Market market = marketDao.findMarket(marketId);
	logger.info(msg("delete market <{0}>", market));
	marketDao.deleteMarket(market);
	ModelAndView mav = new ModelAndView("redirect:/market");
	return mav;
    }

}
