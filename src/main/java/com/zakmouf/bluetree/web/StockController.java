package com.zakmouf.bluetree.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.zakmouf.bluetree.domain.Price;
import com.zakmouf.bluetree.domain.Stock;
import com.zakmouf.bluetree.service.PriceService;
import com.zakmouf.bluetree.service.StockService;
import com.zakmouf.bluetree.service.UpdateService;

@Controller
@RequestMapping("/stock")
public class StockController extends BaseController {

    @Autowired
    private StockService stockService;
    @Autowired
    private PriceService priceService;
    @Autowired
    private UpdateService updateService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView whenList() {
	List<Stock> stocks = stockService.getStocks();
	ModelAndView mav = new ModelAndView("stockList");
	mav.getModel().put("stocks", stocks);
	return mav;
    }

    @RequestMapping(value = "/{stockId}/view", method = RequestMethod.GET)
    public ModelAndView whenView(@PathVariable("stockId") Long stockId, HttpServletRequest request) {
	Stock stock = stockService.getStock(stockId);
	List<Price> prices = priceService.getPrices(stock);
	generateChart(request, "stock", prices);
	ModelAndView mav = new ModelAndView("stockView");
	mav.getModel().put("stock", stock);
	return mav;
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public ModelAndView whenNew() {
	StockForm form = new StockForm();
	ModelAndView mav = new ModelAndView("stockNew");
	mav.getModel().put("form", form);
	return mav;
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public ModelAndView whenSubmitNew(@ModelAttribute StockForm form) {
	Stock stock = new Stock();
	stock.setSymbol(form.getSymbol());
	stock.setName(form.getName());
	stockService.saveStock(stock);
	ModelAndView mav = new ModelAndView(msg("redirect:/stock/%1$d/view", stock.getId()));
	return mav;
    }

    @RequestMapping(value = "/{stockId}/edit", method = RequestMethod.GET)
    public ModelAndView whenEdit(@PathVariable("stockId") Long stockId) {
	Stock stock = stockService.getStock(stockId);
	StockForm form = new StockForm();
	form.setSymbol(stock.getSymbol());
	form.setName(stock.getName());
	ModelAndView mav = new ModelAndView("stockEdit");
	mav.getModel().put("form", form);
	mav.getModel().put("stock", stock);
	return mav;
    }

    @RequestMapping(value = "/{stockId}/edit", method = RequestMethod.POST)
    public ModelAndView whenSubmitEdit(@PathVariable("stockId") Long stockId, @ModelAttribute StockForm form) {
	Stock stock = stockService.getStock(stockId);
	stock.setSymbol(form.getSymbol());
	stock.setName(form.getName());
	stockService.saveStock(stock);
	ModelAndView mav = new ModelAndView(msg("redirect:/stock/%1$d/view", stock.getId()));
	return mav;
    }

    @RequestMapping(value = "/{stockId}/delete", method = RequestMethod.GET)
    public ModelAndView whenDelete(@PathVariable("stockId") Long stockId) {
	Stock stock = stockService.getStock(stockId);
	stockService.deleteStock(stock);
	ModelAndView mav = new ModelAndView("redirect:/stock");
	return mav;
    }

    @RequestMapping(value = "/{stockId}/delprices", method = RequestMethod.GET)
    public ModelAndView whenDeletePrices(@PathVariable("stockId") Long stockId) {
	Stock stock = stockService.getStock(stockId);
	priceService.deleteAllPrices(stock);
	ModelAndView mav = new ModelAndView(msg("redirect:/stock/%1$d/view", stock.getId()));
	return mav;
    }

    @RequestMapping(value = "/{stockId}/updnames", method = RequestMethod.GET)
    public ModelAndView whenUpdateNames(@PathVariable("stockId") Long stockId) {
	Stock stock = stockService.getStock(stockId);
	updateService.updateNames(stock);
	ModelAndView mav = new ModelAndView(msg("redirect:/stock/%1$d/view", stock.getId()));
	return mav;
    }

    @RequestMapping(value = "/{stockId}/updprices", method = RequestMethod.GET)
    public ModelAndView whenUpdatePrices(@PathVariable("stockId") Long stockId) {
	Stock stock = stockService.getStock(stockId);
	updateService.updatePrices(stock);
	ModelAndView mav = new ModelAndView(msg("redirect:/stock/%1$d/view", stock.getId()));
	return mav;
    }

}
