package com.zakmouf.bluetree.web;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
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
import com.zakmouf.bluetree.util.PriceUtil;

@Controller
@RequestMapping("/stock")
public class StockController extends BaseController {

    private StockDao stockDao;

    @Autowired
    public void setStockDao(StockDao stockDao) {
	this.stockDao = stockDao;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getList() {
	List<Stock> stocks = stockDao.getStocks();
	ModelAndView mav = new ModelAndView("stockList");
	mav.getModel().put("stocks", stocks);
	return mav;
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public ModelAndView getNew() {
	StockForm form = new StockForm();
	form.setSymbol("symbol");
	form.setName("name");
	ModelAndView mav = new ModelAndView("stockNew");
	mav.getModel().put("form", form);
	return mav;
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public ModelAndView submitNew(@ModelAttribute StockForm form) {
	String symbol = form.getSymbol();
	String name = form.getName();
	if (stockDao.findStock(symbol) != null) {
	    ModelAndView mav = new ModelAndView("stockNew");
	    mav.getModel().put("form", form);
	    return mav;
	}
	Stock stock = new Stock();
	stock.setSymbol(symbol);
	stock.setName(name);
	stockDao.insertStock(stock);
	ModelAndView mav = new ModelAndView("redirect:/stock");
	return mav;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView getEdit(@RequestParam("stock") Long stockId) {
	Stock stock = stockDao.findStock(stockId);
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
	String name = form.getName();
	Stock stock = stockDao.findStock(symbol);
	if (stock != null) {
	    if (!stock.getId().equals(stockId)) {
		ModelAndView mav = new ModelAndView("stockEdit");
		mav.getModel().put("form", form);
		return mav;
	    }
	}
	stock = stockDao.findStock(stockId);
	stock.setSymbol(symbol);
	stock.setName(name);
	logger.info(msg("update stock <{0}>", stock));
	stockDao.updateStock(stock);
	ModelAndView mav = new ModelAndView("redirect:/stock");
	return mav;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ModelAndView doDelete(@RequestParam("stock") Long stockId) {
	Stock stock = stockDao.findStock(stockId);
	logger.info(msg("delete stock <{0}>", stock));
	stockDao.deleteStock(stock);
	ModelAndView mav = new ModelAndView("redirect:/stock");
	return mav;
    }

    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public ModelAndView getView(@RequestParam("stock") Long stockId, HttpServletRequest request) {
	Stock stock = stockDao.findStock(stockId);
	List<Price> prices = stockDao.getPrices(stock);
	StockViewForm form = new StockViewForm();
	form.setStock(stock);
	form.setCount(prices.size());
	if (!prices.isEmpty()) {
	    form.setFirstDate(PriceUtil.firstDate(prices));
	    form.setLastDate(PriceUtil.lastDate(prices));
	    form.setFromDate(dateFormat.format(form.getFirstDate()));
	    form.setToDate(dateFormat.format(form.getLastDate()));
	}
	JFreeChart chart = generateChart(stock, prices);
	request.getSession().setAttribute("stock", chart);
	ModelAndView mav = new ModelAndView("stockView");
	mav.getModel().put("form", form);
	return mav;
    }

    protected JFreeChart generateChart(Stock stock, List<Price> prices) {

	TimeSeriesCollection dataset = new TimeSeriesCollection();
	TimeSeries series = new TimeSeries(stock.getSymbol(), Day.class);
	for (Price price : prices) {
	    series.add(new Day(price.getDate()), price.getValue());
	}
	dataset.addSeries(series);

	DateAxis domainAxis = new DateAxis();
	domainAxis.setTickMarksVisible(false);
	domainAxis.setAxisLineVisible(false);
	domainAxis.setTickLabelFont(new Font("Tahoma", Font.PLAIN, 11));

	NumberAxis rangeAxis = new NumberAxis();
	rangeAxis.setAutoRangeIncludesZero(false);
	rangeAxis.setTickMarksVisible(false);
	rangeAxis.setAxisLineVisible(false);
	rangeAxis.setTickLabelFont(new Font("Tahoma", Font.PLAIN, 11));

	XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer(true, false);
	renderer.setSeriesPaint(0, new Color(51, 0, 204));

	XYPlot plot = new XYPlot(dataset, domainAxis, rangeAxis, renderer);
	plot.setBackgroundPaint(Color.WHITE);
	plot.setOutlineStroke(null);
	plot.setDomainGridlinesVisible(false);
	plot.setRangeGridlinesVisible(true);
	plot.setRangeGridlineStroke(new BasicStroke(0.5f));
	plot.setRangeGridlinePaint(Color.DARK_GRAY);

	JFreeChart chart = new JFreeChart(null, null, plot, false);
	chart.setBackgroundPaint(Color.white);
	chart.setAntiAlias(true);
	chart.setBorderVisible(false);

	return chart;

    }

}
