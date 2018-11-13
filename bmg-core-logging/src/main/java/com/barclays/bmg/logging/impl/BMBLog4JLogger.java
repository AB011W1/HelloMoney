package com.barclays.bmg.logging.impl;

import com.barclays.bmg.logging.BMBLog;

public class BMBLog4JLogger implements BMBLog {

    private final org.apache.log4j.Logger logger;

    public BMBLog4JLogger(Class clazz) {
	this.logger = org.apache.log4j.Logger.getLogger(clazz);
    }

    public BMBLog4JLogger(String logger) {
	this.logger = org.apache.log4j.Logger.getLogger(logger);
    }

    @Override
    public void debug(String message) {
	this.logger.debug(message);

    }

    @Override
    public void debug(String message, Object t) {
	if (t instanceof Throwable) {
	    this.logger.debug(message, (Throwable) t);
	} else {
	    this.debug(message + t.toString());
	}

    }

    @Override
    public void error(String message) {
	this.logger.error(message);

    }

    @Override
    public void error(String message, Object t) {
	if (t instanceof Throwable) {
	    this.logger.debug(message, (Throwable) t);
	} else {
	    this.debug(message + t.toString());
	}

    }

    @Override
    public void error(String message, String errroCode, Object t) {
	if (t instanceof Throwable) {
	    this.logger.error(errroCode + "-" + message, (Throwable) t);
	} else {
	    this.error(errroCode + "-" + message + t.toString());
	}

    }

    @Override
    public void error(String message, String errroCode) {
	this.error(errroCode + "-" + message);

    }

    @Override
    public void fatal(String message) {
	this.logger.fatal(message);

    }

    @Override
    public void fatal(String message, Object t) {
	if (t instanceof Throwable) {
	    this.logger.debug(message, (Throwable) t);
	} else {
	    this.debug(message + t.toString());
	}

    }

    @Override
    public void info(String message) {
	this.logger.info(message);

    }

    @Override
    public void info(String message, Object t) {
	if (t instanceof Throwable) {
	    this.logger.debug(message, (Throwable) t);
	} else {
	    this.debug(message + t.toString());
	}

    }

    @Override
    public void trace(String message) {
	this.logger.trace(message);

    }

    @Override
    public void trace(String message, Object t) {
	if (t instanceof Throwable) {
	    this.logger.debug(message, (Throwable) t);
	} else {
	    this.debug(message + t.toString());
	}

    }

    @Override
    public void warn(String message) {
	this.logger.warn(message);

    }

    @Override
    public void warn(String message, Object t) {
	if (t instanceof Throwable) {
	    this.logger.debug(message, (Throwable) t);
	} else {
	    this.debug(message + t.toString());
	}

    }

    @Override
    public void warn(String message, String errroCode) {
	this.warn(errroCode + "-" + message);

    }

    @Override
    public void warn(String message, String errroCode, Object t) {
	if (t instanceof Throwable) {
	    this.logger.warn(errroCode + "-" + message, (Throwable) t);
	} else {
	    this.warn(errroCode + "-" + message + t.toString());
	}

    }

}
