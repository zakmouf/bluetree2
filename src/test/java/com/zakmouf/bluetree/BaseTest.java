package com.zakmouf.bluetree;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseTest {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected String msg(String pattern, Object... arguments) {
	return MessageFormat.format(pattern, arguments);
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

}
