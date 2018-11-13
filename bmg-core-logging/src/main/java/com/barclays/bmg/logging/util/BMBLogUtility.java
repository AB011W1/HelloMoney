package com.barclays.bmg.logging.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.barclays.bmg.logging.BMBLog;
import com.barclays.bmg.logging.impl.BMBLog4JLogger;
import com.barclays.bmg.logging.impl.BMBRemoteLogger;
import com.barclays.bmg.logging.service.LoggingService;

public class BMBLogUtility {

    static String logType;
    static LoggingService loggingService;
    private static ApplicationContext appctx = null;

    public static BMBLog getLogger(Class clazz) {

	BMBLog bmbLog = null;

	try {
	    appctx = new ClassPathXmlApplicationContext("bmb-logging-config.xml");
	    loggingService = (LoggingService) appctx.getBean("remoteLoggingService");
	    bmbLog = new BMBRemoteLogger(clazz, loggingService);
	} catch (Exception ex) {
	    bmbLog = new BMBLog4JLogger(clazz);
	}

	return bmbLog;
    }

    public static BMBLog getLogger(String logger) {

	BMBLog bmbLog = null;

	try {
	    appctx = new ClassPathXmlApplicationContext("bmb-logging-config.xml");
	    loggingService = (LoggingService) appctx.getBean("remoteLoggingService");
	    bmbLog = new BMBRemoteLogger(logger, loggingService);
	} catch (Exception ex) {
	    bmbLog = new BMBLog4JLogger(logger);
	}

	return bmbLog;
    }

}
