package com.zakmouf.bluetree.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.zakmouf.bluetree.dao.StockDao;
import com.zakmouf.bluetree.domain.Price;
import com.zakmouf.bluetree.domain.Stock;
import com.zakmouf.bluetree.service.PriceService;

@Controller
@RequestMapping("/stock")
public class StockController extends BaseController {

    @Autowired
    private StockDao stockDao;
    @Autowired
    private PriceService priceService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getList() {
	List<Stock> stocks = stockDao.findAll();
	ModelAndView mav = new ModelAndView("stockList");
	mav.getModel().put("stocks", stocks);
	return mav;
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public ModelAndView getNew() {
	StockForm form = new StockForm();
	ModelAndView mav = new ModelAndView("stockNew");
	mav.getModel().put("form", form);
	return mav;
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public ModelAndView submitNew(@ModelAttribute StockForm form) {
	String symbol = form.getSymbol();
	if (stockDao.findBySymbol(symbol) != null) {
	    ModelAndView mav = new ModelAndView("stockNew");
	    mav.getModel().put("form", form);
	    return mav;
	}
	Stock stock = new Stock();
	stock.setSymbol(symbol);
	stock.setName(form.getName());
	logger.info(msg("insert stock <{0}>", stock));
	stockDao.insert(stock);
	ModelAndView mav = new ModelAndView("redirect:/stock/view?stock=" + stock.getId());
	return mav;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView getEdit(@RequestParam("stock") Long stockId) {
	Stock stock = stockDao.findById(stockId);
	StockForm form = new StockForm();
	form.setSymbol(stock.getSymbol());
	form.setName(stock.getName());
	ModelAndView mav = new ModelAndView("stockEdit");
	mav.getModel().put("form", form);
	return mav;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ModelAndView submitEdit(@RequestParam("stock") Long stockId, @ModelAttribute StockForm form) {
	String symbol = form.getSymbol();
	Stock stock = stockDao.findBySymbol(symbol);
	if (stock != null) {
	    if (!stock.getId().equals(stockId)) {
		ModelAndView mav = new ModelAndView("stockEdit");
		mav.getModel().put("form", form);
		return mav;
	    }
	}
	stock = stockDao.findById(stockId);
	stock.setSymbol(symbol);
	stock.setName(form.getName());
	logger.info(msg("update stock <{0}>", stock));
	stockDao.update(stock);
	ModelAndView mav = new ModelAndView("redirect:/stock/view?stock=" + stock.getId());
	return mav;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ModelAndView doDelete(@RequestParam("stock") Long stockId) {
	Stock stock = stockDao.findById(stockId);
	logger.info(msg("delete stock <{0}>", stock));
	stockDao.delete(stock);
	ModelAndView mav = new ModelAndView("redirect:/stock");
	return mav;
    }

    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public ModelAndView getView(@RequestParam("stock") Long stockId, HttpServletRequest request) {
	Stock stock = stockDao.findById(stockId);
	List<Price> prices = priceService.getPrices(stock);
	generateChart(request, "stock", prices);
	ModelAndView mav = new ModelAndView("stockView");
	mav.getModel().put("stock", stock);
	return mav;
    }

}
