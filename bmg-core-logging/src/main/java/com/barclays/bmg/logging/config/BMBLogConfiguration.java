package com.barclays.bmg.logging.config;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class BMBLogConfiguration extends PropertiesConfiguration {

    private static BMBLogConfiguration logConfig = null;

    private final static String LOG_CONFIG_NAME = "mcfe.properties";

    public BMBLogConfiguration(String fileName) throws ConfigurationException {
	super(fileName);
    }

    public static BMBLogConfiguration getInstance() {

	try {
	    if (logConfig == null) {
		logConfig = new BMBLogConfiguration(LOG_CONFIG_NAME);
	    }

	} catch (ConfigurationException ex) {


	}
	return logConfig;

    }

}
