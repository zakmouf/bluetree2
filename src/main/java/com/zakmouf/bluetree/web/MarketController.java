package com.zakmouf.bluetree.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zakmouf.bluetree.dao.MarketDao;
import com.zakmouf.bluetree.domain.Market;

@Controller
public class MarketController extends BaseController {

    private MarketDao marketDao;

    @Autowired
    public void setMarketDao(MarketDao marketDao) {
	this.marketDao = marketDao;
    }

    @RequestMapping(value = "/admin/market", method = RequestMethod.GET)
    public String getList(ModelMap model) {
	List<Market> markets = marketDao.getMarkets();
	model.put("markets", markets);
	return "admin/market/list";
    }

}
