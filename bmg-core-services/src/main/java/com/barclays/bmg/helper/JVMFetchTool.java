package com.barclays.bmg.helper;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/*
 * build for fetch JVM parameter
 */
public class JVMFetchTool {

    private static final Logger LOGGER = Logger.getLogger(JVMFetchTool.class);

    /*
     * fetch JVM parameter by key
     */
    public static String fetchJVMParm(String key) {
	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("key=" + key);
	}

	if (StringUtils.isEmpty(key)) {
	    return key;
	}

	String property = System.getProperty(key);

	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("property=" + property);
	}

	return property;
    }
}