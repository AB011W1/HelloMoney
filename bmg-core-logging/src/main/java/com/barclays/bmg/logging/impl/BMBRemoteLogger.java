package com.barclays.bmg.logging.impl;

import com.barclays.bmg.context.BMBContextHolder;
import com.barclays.bmg.logging.BMBLog;
import com.barclays.bmg.logging.service.LoggingService;
import com.barclays.bmg.logging.service.request.LoggingServiceRequest;

public class BMBRemoteLogger implements BMBLog {

    LoggingService loggingService;
    String className;

    public BMBRemoteLogger(Class clazz) {
	this.setClassName(clazz.getCanonicalName());

    }

    public BMBRemoteLogger(String logger) {
	this.setClassName(logger);
    }

    public BMBRemoteLogger(Class clazz, LoggingService loggingService) {
	this.loggingService = loggingService;
	this.setClassName(clazz.getCanonicalName());

    }

    public BMBRemoteLogger(String logger, LoggingService loggingService) {
	this.loggingService = loggingService;
	this.setClassName(logger);
    }

    private void log(String level, String message, String errorCode, Object t) {
	LoggingServiceRequest request = new LoggingServiceRequest();

	request.setClassName(className);
	request.setContext(BMBContextHolder.getContext());
	request.setLevel(level);
	request.setMessage(message + t.toString());
	loggingService.log(request);

    }

    @Override
    public void debug(String message) {
	log(BMBLog.DEBUG, message, null, "");
    }

    @Override
    public void debug(String message, Object t) {
	log(BMBLog.DEBUG, message, null, t);

    }

    @Override
    public void error(String message) {
	log(BMBLog.ERROR, message, null, "");

    }

    @Override
    public void error(String message, Object t) {
	log(BMBLog.ERROR, message, null, "");

    }

    @Override
    public void error(String message, String errorCode, Object t) {
	log(BMBLog.ERROR, message, errorCode, t);

    }

    @Override
    public void error(String message, String errorCode) {
	log(BMBLog.ERROR, message, errorCode, "");

    }

    @Override
    public void fatal(String message) {
	log(BMBLog.FATAL, message, null, "");

    }

    @Override
    public void fatal(String message, Object t) {
	log(BMBLog.FATAL, message, null, t);

    }

    @Override
    public void info(String message) {
	log(BMBLog.INFO, message, null, "");

    }

    @Override
    public void info(String message, Object t) {
	log(BMBLog.INFO, message, null, t);

    }

    @Override
    public void trace(String message) {
	log(BMBLog.TRACE, message, null, "");

    }

    @Override
    public void trace(String message, Object t) {
	log(BMBLog.TRACE, message, null, t);

    }

    @Override
    public void warn(String message) {
	log(BMBLog.WARN, message, null, "");

    }

    @Override
    public void warn(String message, Object t) {
	log(BMBLog.WARN, message, null, t);

    }

    @Override
    public void warn(String message, String errorCode) {
	log(BMBLog.WARN, message, errorCode, "");

    }

    @Override
    public void warn(String message, String errorCode, Object t) {
	log(BMBLog.WARN, message, errorCode, t);

    }

    public LoggingService getLoggingService() {
	return loggingService;
    }

    public void setLoggingService(LoggingService loggingService) {
	this.loggingService = loggingService;
    }

    public String getClassName() {
	return className;
    }

    public void setClassName(String className) {
	this.className = className;
    }

}
