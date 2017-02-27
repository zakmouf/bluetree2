package com.zakmouf.bluetree.web;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
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
import com.zakmouf.bluetree.domain.Price;
import com.zakmouf.bluetree.domain.Stock;
import com.zakmouf.bluetree.util.DateUtil;

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
	List<Market> markets = marketDao.findAll();
	ModelAndView mav = new ModelAndView("marketList");
	mav.getModel().put("markets", markets);
	return mav;
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public ModelAndView getNew() {

	List<Stock> stocks = stockDao.findAll();

	MarketForm form = new MarketForm();
	form.setName("name");
	form.setRiskless(0.0D);
	if (stocks.isEmpty()) {
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
	if (marketDao.findByName(name) != null) {
	    List<Stock> stocks = stockDao.findAll();
	    ModelAndView mav = new ModelAndView("marketNew");
	    mav.getModel().put("stocks", stocks);
	    mav.getModel().put("form", form);
	    return mav;
	}
	Market market = new Market();
	market.setName(name);
	market.setRiskless(form.getRiskless());
	Stock indice = stockDao.findById(form.getIndiceId());
	market.setIndice(indice);
	logger.info(msg("insert market <{0}>", market));
	marketDao.insert(market);
	ModelAndView mav = new ModelAndView("redirect:/market/view?market=" + market.getId());
	return mav;
    }

    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public ModelAndView getView(@RequestParam("market") Long marketId, HttpServletRequest request) {
	Market market = marketDao.findById(marketId);
	Stock indice = market.getIndice();
	List<Price> prices = stockDao.findPrices(indice);
	generateChart(request, "market", prices);
	List<Stock> stocks = marketDao.getStocks(market);
	ModelAndView mav = new ModelAndView("marketView");
	mav.getModel().put("market", market);
	mav.getModel().put("stocks", stocks);
	return mav;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView getEdit(@RequestParam("market") Long marketId) {

	Market market = marketDao.findById(marketId);

	List<Stock> stocks = stockDao.findAll();

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
	Market market = marketDao.findByName(name);
	if (market != null) {
	    if (!market.getId().equals(marketId)) {
		List<Stock> stocks = stockDao.findAll();
		ModelAndView mav = new ModelAndView("marketEdit");
		mav.getModel().put("stocks", stocks);
		mav.getModel().put("form", form);
		return mav;
	    }
	}
	market = marketDao.findById(marketId);
	market.setName(name);
	market.setRiskless(form.getRiskless());
	Stock indice = stockDao.findById(form.getIndiceId());
	market.setIndice(indice);
	logger.info(msg("update market <{0}>", market));
	marketDao.update(market);
	ModelAndView mav = new ModelAndView("redirect:/market/view?market=" + market.getId());
	return mav;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ModelAndView doDelete(@RequestParam("market") Long marketId) {
	Market market = marketDao.findById(marketId);
	logger.info(msg("delete market <{0}>", market));
	marketDao.delete(market);
	ModelAndView mav = new ModelAndView("redirect:/market");
	return mav;
    }

    @RequestMapping(value = "/stock", method = RequestMethod.GET)
    public ModelAndView getStock(@RequestParam("market") Long marketId) {
	Market market = marketDao.findById(marketId);
	MarketStockForm form = new MarketStockForm();
	form.setText("symbol");
	ModelAndView mav = new ModelAndView("marketStock");
	mav.getModel().put("market", market);
	mav.getModel().put("form", form);
	return mav;
    }

    @RequestMapping(value = "/stock", method = RequestMethod.POST)
    public ModelAndView submitStock(@RequestParam("market") Long marketId, @ModelAttribute MarketStockForm form) {
	Market market = marketDao.findById(marketId);
	String[] symbols = StringUtils.split(form.getText());
	List<Stock> stocks = new ArrayList<Stock>();
	for (String symbol : symbols) {
	    Stock stock = stockDao.findBySymbol(symbol);
	    if (stock == null) {
		stock = new Stock();
		stock.setSymbol(symbol);
		;
		stock.setName("undefined");
		logger.info(msg("insert stock <{0}>", stock));
		stockDao.insert(stock);
	    }
	    stocks.add(stock);
	}
	marketDao.setStocks(market, stocks);
	logger.info(msg("market stock <{0}>", market));
	ModelAndView mav = new ModelAndView("redirect:/market/view?market=" + market.getId());
	return mav;
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public ModelAndView getUpdate(@RequestParam("market") Long marketId) {
	Market market = marketDao.findById(marketId);
	MarketUpdateForm form = new MarketUpdateForm();
	form.setStartDate("2012-12-01");
	form.setIncrement(250);
	ModelAndView mav = new ModelAndView("marketUpdate");
	mav.getModel().put("market", market);
	mav.getModel().put("form", form);
	return mav;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView submitUpdate(@RequestParam("market") Long marketId, @ModelAttribute MarketUpdateForm form) {
	Market market = marketDao.findById(marketId);
	String startDateStr = form.getStartDate();
	Date startDate = parseDate(startDateStr);
	Integer increment = form.getIncrement();
	List<Stock> stocks = new ArrayList<Stock>();
	stocks.add(market.getIndice());
	stocks.addAll(marketDao.getStocks(market));
	for (Stock stock : stocks) {
	    updateStock(stock, startDate, increment);
	}
	logger.info(msg("update market <{0}>", market));
	ModelAndView mav = new ModelAndView("redirect:/market/view?market=" + market.getId());
	return mav;
    }

    private void updateStock(Stock stock, Date startDate, Integer increment) {
	Date lastDate = stockDao.findLastDate(stock);
	if (lastDate == null) {
	    lastDate = startDate;
	} else {
	    lastDate = DateUtil.addDays(lastDate, 1);
	}
	Date endDate = DateUtil.today();
	updateStock(stock, lastDate, endDate, increment);
    }

    private void updateStock(Stock stock, Date startDate, Date endDate, Integer increment) {
	Calendar calendar = Calendar.getInstance();
	Date fromDate = startDate;
	calendar.setTime(fromDate);
	while (fromDate.compareTo(endDate) <= 0) {
	    calendar.add(Calendar.DAY_OF_MONTH, increment);
	    Date toDate = calendar.getTime();
	    if (toDate.compareTo(endDate) > 0) {
		toDate = endDate;
	    }
	    updateStock(stock, fromDate, toDate);
	    calendar.add(Calendar.DAY_OF_MONTH, 1);
	    fromDate = calendar.getTime();
	}
    }

    private void updateStock(Stock stock, Date fromDate, Date toDate) {
	String link = getLink(stock, fromDate, toDate);
	String page = pumpLink(link);
	List<Price> prices = parsePage(page);
	if (logger.isDebugEnabled()) {
	    logger.debug(msg("{0} : {1} -> {2} : {3}", stock, formatDate(fromDate), formatDate(toDate), prices.size()));
	}
	stockDao.insertPrices(stock, prices);
    }

    private String getLink(Stock stock, Date fromDate, Date toDate) {

	Calendar calendar = Calendar.getInstance();

	calendar.setTime(fromDate);
	int fromYear = calendar.get(Calendar.YEAR);
	int fromMonth = calendar.get(Calendar.MONTH);
	int fromDay = calendar.get(Calendar.DAY_OF_MONTH);

	calendar.setTime(toDate);
	int toYear = calendar.get(Calendar.YEAR);
	int toMonth = calendar.get(Calendar.MONTH);
	int toDay = calendar.get(Calendar.DAY_OF_MONTH);

	Object[] arguments = new Object[7];
	arguments[0] = stock.getSymbol();
	arguments[1] = new Integer(fromMonth).toString();
	arguments[2] = new Integer(fromDay).toString();
	arguments[3] = new Integer(fromYear).toString();
	arguments[4] = new Integer(toMonth).toString();
	arguments[5] = new Integer(toDay).toString();
	arguments[6] = new Integer(toYear).toString();

	String url = "http://ichart.finance.yahoo.com/table.csv?s={0}&a={1}&b={2}&c={3}&d={4}&e={5}&f={6}&g=d&ignore=.csv";

	return msg(url, arguments);

    }

    private String pumpLink(String link) {
	try {
	    URL url = new URL(link);
	    URLConnection urlConnection = url.openConnection();
	    InputStream inputStream = urlConnection.getInputStream();
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    byte[] bytes = new byte[4096];
	    int read = inputStream.read(bytes);
	    while (read != -1) {
		baos.write(bytes, 0, read);
		read = inputStream.read(bytes);
	    }
	    inputStream.close();
	    bytes = baos.toByteArray();
	    String page = new String(bytes);
	    return page;
	} catch (FileNotFoundException ex) {
	    return "";
	} catch (IOException ex) {
	    throw new RuntimeException(ex);
	}
    }

    private List<Price> parsePage(String page) {
	List<Price> prices = new ArrayList<Price>();
	StringTokenizer lines = new StringTokenizer(page, "\r\n");
	if (lines.hasMoreTokens()) {
	    lines.nextToken();
	}
	while (lines.hasMoreTokens()) {
	    StringTokenizer tokens = new StringTokenizer(lines.nextToken(), ",");
	    String dateStr = tokens.nextToken(); // date
	    tokens.nextToken(); // open
	    tokens.nextToken(); // high
	    tokens.nextToken(); // low
	    tokens.nextToken(); // close
	    tokens.nextToken(); // volume
	    String valueStr = tokens.nextToken(); // adj.close
	    Date date = parseDate(dateStr);
	    Double value = Double.valueOf(valueStr);
	    Price price = new Price();
	    price.setDate(date);
	    price.setValue(value);
	    prices.add(price);
	}
	return prices;
    }

}
