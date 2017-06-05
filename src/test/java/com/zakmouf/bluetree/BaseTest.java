package com.zakmouf.bluetree;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class BaseTest {

    protected final Logger logger = LoggerFactory.getLogger(getClass());
    protected DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    protected String msg(String pattern, Object... arguments) {
        return String.format(pattern, arguments);
    }

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
