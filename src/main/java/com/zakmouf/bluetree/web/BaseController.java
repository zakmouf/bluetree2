package com.zakmouf.bluetree.web;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zakmouf.bluetree.domain.Price;
import com.zakmouf.bluetree.util.DateUtil;

public class BaseController {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Deprecated
    protected String msgOld(String pattern, Object... arguments) {
	return MessageFormat.format(pattern, arguments);
    }

    protected String msg(String pattern, Object... arguments) {
	return String.format(pattern, arguments);
    }

    protected DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    protected String formatDate(Date date) {
	return dateFormat.format(date);
    }

    protected Date parseDate(String source) {
	try {
	    return dateFormat.parse(source);
	} catch (ParseException ex) {
	    throw new RuntimeException("failed to parse date : " + source, ex);
	}
    }

    protected void generateChart(HttpServletRequest request, String sessionName, List<Price> prices) {

	request.getSession().removeAttribute(sessionName);

	if (prices.isEmpty()) {
	    Price price = new Price();
	    price.setDate(DateUtil.today());
	    price.setValue(0.0D);
	    prices.add(price);
	}

	TimeSeriesCollection dataset = new TimeSeriesCollection();
	TimeSeries series = new TimeSeries("DUMMY", Day.class);
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

	request.getSession().setAttribute(sessionName, chart);

    }

}
