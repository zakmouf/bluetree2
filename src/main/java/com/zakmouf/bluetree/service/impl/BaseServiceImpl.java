package com.zakmouf.bluetree.service.impl;

import java.text.MessageFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseServiceImpl {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Deprecated
    protected String msgOld(String pattern, Object... arguments) {
	return MessageFormat.format(pattern, arguments);
    }

    protected String msg(String pattern, Object... arguments) {
	return String.format(pattern, arguments);
    }

}
