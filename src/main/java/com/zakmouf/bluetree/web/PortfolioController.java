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

import com.zakmouf.bluetree.dao.PortfolioDao;
import com.zakmouf.bluetree.domain.Market;
import com.zakmouf.bluetree.domain.Portfolio;
import com.zakmouf.bluetree.domain.Position;
import com.zakmouf.bluetree.service.MarketService;
import com.zakmouf.bluetree.service.OptimizeService;

@Controller
@RequestMapping("/portfolio")
public class PortfolioController extends BaseController {

    @Autowired
    private PortfolioDao portfolioDao;
    @Autowired
    private MarketService marketService;
    @Autowired
    private OptimizeService optimizeService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getList(ModelMap model) {
	List<Portfolio> portfolios = portfolioDao.getPortfolios();
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
	String name = form.getName();
	if (portfolioDao.findPortfolio(name) != null) {
	    List<Market> markets = marketService.getMarkets();
	    form.setMarkets(markets);
	    ModelAndView mav = new ModelAndView("portfolioNew");
	    mav.getModel().put("form", form);
	    return mav;
	}
	Portfolio portfolio = new Portfolio();
	portfolio.setName(name);
	portfolio.setFromDate(parseDate(form.getFromDateStr()));
	portfolio.setToDate(parseDate(form.getToDateStr()));
	portfolio.setBeta(form.getBeta());
	portfolio.setSize(form.getSize());
	Market market = marketService.getMarket(form.getMarketId());
	logger.info(msgOld("insert portfolio <{0}>", portfolio));
	portfolioDao.insertPortfolio(portfolio);
	portfolioDao.setMarket(portfolio, market);
	ModelAndView mav = new ModelAndView("redirect:/portfolio/view?portfolio=" + portfolio.getId());
	return mav;
    }

    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public ModelAndView getView(@RequestParam("portfolio") Long portfolioId) {
	Portfolio portfolio = portfolioDao.findPortfolio(portfolioId);
	Market market = portfolioDao.getMarket(portfolio);
	List<Position> positions = portfolioDao.getPositions(portfolio);
	ModelAndView mav = new ModelAndView("portfolioView");
	mav.getModel().put("portfolio", portfolio);
	mav.getModel().put("market", market);
	mav.getModel().put("positions", positions);
	return mav;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ModelAndView doDelete(@RequestParam("portfolio") Long portfolioId) {
	Portfolio portfolio = portfolioDao.findPortfolio(portfolioId);
	logger.info(msgOld("delete portfolio <{0}>", portfolio));
	portfolioDao.deletePortfolio(portfolio);
	ModelAndView mav = new ModelAndView("redirect:/portfolio");
	return mav;
    }

    @RequestMapping(value = "/optimize", method = RequestMethod.GET)
    public ModelAndView getStock(@RequestParam("portfolio") Long portfolioId) {
	Portfolio portfolio = portfolioDao.findPortfolio(portfolioId);
	List<Position> positions = optimizeService.optimize(portfolio);
	portfolioDao.setPositions(portfolio, positions);
	ModelAndView mav = new ModelAndView("redirect:/portfolio/view?portfolio=" + portfolio.getId());
	return mav;
    }

}
