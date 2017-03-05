package com.zakmouf.bluetree.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.zakmouf.bluetree.domain.Holding;
import com.zakmouf.bluetree.domain.Market;
import com.zakmouf.bluetree.domain.Portfolio;
import com.zakmouf.bluetree.service.MarketService;
import com.zakmouf.bluetree.service.OptimizeService;
import com.zakmouf.bluetree.service.PortfolioService;

@Controller
@RequestMapping("/portfolio")
public class PortfolioController extends BaseController {

    @Autowired
    private PortfolioService portfolioService;
    @Autowired
    private MarketService marketService;
    @Autowired
    private OptimizeService optimizeService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getList(ModelMap model) {
	List<Portfolio> portfolios = portfolioService.getPortfolios();
	ModelAndView mav = new ModelAndView("portfolioList");
	mav.getModel().put("portfolios", portfolios);
	return mav;
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public ModelAndView getNew() {
	PortfolioForm form = new PortfolioForm();
	List<Market> markets = marketService.getMarkets();
	form.setMarkets(markets);
	form.setName("name");
	form.setFromDateStr("2013-01-01");
	form.setToDateStr("2014-12-31");
	form.setBeta(0.8D);
	form.setSize(10);
	if (!markets.isEmpty()) {
	    form.setMarketId(markets.get(0).getId());
	}
	ModelAndView mav = new ModelAndView("portfolioNew");
	mav.getModel().put("form", form);
	return mav;
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public ModelAndView submitNew(@ModelAttribute PortfolioForm form) {
	Portfolio portfolio = new Portfolio();
	portfolio.setName(form.getName());
	portfolio.setFromDate(parseDate(form.getFromDateStr()));
	portfolio.setToDate(parseDate(form.getToDateStr()));
	portfolio.setBeta(form.getBeta());
	portfolio.setSize(form.getSize());
	Market market = marketService.getMarket(form.getMarketId());
	portfolio.setMarket(market);
	portfolioService.savePortfolio(portfolio);
	ModelAndView mav = new ModelAndView("redirect:/portfolio/view?portfolio=" + portfolio.getId());
	return mav;
    }

    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public ModelAndView getView(@RequestParam("portfolio") Long portfolioId) {
	Portfolio portfolio = portfolioService.getPortfolio(portfolioId);
	List<Holding> holdings = portfolioService.getHoldings(portfolio);
	ModelAndView mav = new ModelAndView("portfolioView");
	mav.getModel().put("portfolio", portfolio);
	mav.getModel().put("holdings", holdings);
	return mav;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ModelAndView doDelete(@RequestParam("portfolio") Long portfolioId) {
	Portfolio portfolio = portfolioService.getPortfolio(portfolioId);
	portfolioService.deletePortfolio(portfolio);
	ModelAndView mav = new ModelAndView("redirect:/portfolio");
	return mav;
    }

    @RequestMapping(value = "/optimize", method = RequestMethod.GET)
    public ModelAndView getStock(@RequestParam("portfolio") Long portfolioId) {
	Portfolio portfolio = portfolioService.getPortfolio(portfolioId);
	List<Holding> holdings = optimizeService.optimize(portfolio);
	portfolioService.setHoldings(portfolio, holdings);
	ModelAndView mav = new ModelAndView("redirect:/portfolio/view?portfolio=" + portfolio.getId());
	return mav;
    }

}
