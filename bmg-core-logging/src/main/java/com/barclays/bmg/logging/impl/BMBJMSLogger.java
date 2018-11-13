package com.barclays.bmg.logging.impl;

import com.barclays.bmg.context.BMBContextHolder;
import com.barclays.bmg.logging.BMBLog;
import com.barclays.bmg.logging.service.LoggingService;
import com.barclays.bmg.logging.service.request.LoggingServiceRequest;

public class BMBJMSLogger implements BMBLog {

    LoggingService loggingService;

    public BMBJMSLogger(Class clazz) {

    }

    public BMBJMSLogger(String logger) {

    }

    private void log(String level, String message, String errorCode, Object t) {
	LoggingServiceRequest request = new LoggingServiceRequest();

	request.setContext(BMBContextHolder.getContext());
	request.setLevel(level);
	request.setMessage(message);
	loggingService.log(request);

    }

    @Override
    public void debug(String message) {

    }

    @Override
    public void debug(String message, Object t) {
	// TODO Auto-generated method stub

    }

    @Override
    public void error(String message) {
	// TODO Auto-generated method stub

    }

    @Override
    public void error(String message, Object t) {
	// TODO Auto-generated method stub

    }

    @Override
    public void error(String message, String errroCode, Object t) {
	// TODO Auto-generated method stub

    }

    @Override
    public void error(String message, String errroCode) {
	// TODO Auto-generated method stub

    }

    @Override
    public void fatal(String message) {
	// TODO Auto-generated method stub

    }

    @Override
    public void fatal(String message, Object t) {
	// TODO Auto-generated method stub

    }

    @Override
    public void info(String message) {
	// TODO Auto-generated method stub

    }

    @Override
    public void info(String message, Object t) {
	// TODO Auto-generated method stub

    }

    @Override
    public void trace(String message) {
	// TODO Auto-generated method stub

    }

    @Override
    public void trace(String message, Object t) {
	// TODO Auto-generated method stub

    }

    @Override
    public void warn(String message) {
	// TODO Auto-generated method stub

    }

    @Override
    public void warn(String message, Object t) {
	// TODO Auto-generated method stub

    }

    @Override
    public void warn(String message, String errroCode) {
	// TODO Auto-generated method stub

    }

    @Override
    public void warn(String message, String errroCode, Object t) {
	// TODO Auto-generated method stub

    }

    public LoggingService getLoggingService() {
	return loggingService;
    }

    public void setLoggingService(LoggingService loggingService) {
	this.loggingService = loggingService;
    }

}
