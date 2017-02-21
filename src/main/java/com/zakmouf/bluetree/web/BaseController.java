package com.zakmouf.bluetree.web;

import java.text.MessageFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseController {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected String msg(String pattern, Object... arguments) {
	return MessageFormat.format(pattern, arguments);
    }

}
