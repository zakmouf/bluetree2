package com.zakmouf.bluetree.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;

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
