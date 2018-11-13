package com.barclays.ussd.common.configuration;

import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.DefaultConfigurationBuilder;
import org.apache.log4j.Logger;

import com.barclays.ussd.exception.SystemException;

// TODO: Auto-generated Javadoc
/**
 * Read value from configuration file This class is created to read configuration values from different types of properties files. Need to define the
 * all configuration/properties file in the configuation.xml
 * 
 * @author vpatne
 * @version 1.0
 */
public final class ConfigurationManager {

    /** The Constant CONFIG_FILE. */
    private static final String CONFIG_FILE = "configuration.xml";

    /** The Constant DOT. */
    private static final String DOT = ".";

    /** The Constant MSG_NO_SUCH_ELEMENT. */
    private static final String MSG_NO_SUCH_ELEMENT = "NoSuchElementException";

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(ConfigurationManager.class);

    /** The configuration. */
    private static Configuration configuration = getConfiguration();

    private ConfigurationManager() {
	// TODO Auto-generated constructor stub
    }

    /** Reload the configuration. */
    public static void reloadConfiguration() {
	final Configuration config = getConfiguration();
	if (config != null) {
	    configuration = config;
	    if (LOGGER.isDebugEnabled()) {
		LOGGER.debug("Reloaded the configuration framework successfully.");
	    }
	}
    }

    /**
     * Load the properties file defined in the configuration.xml file.
     * 
     * @return the configuration
     */
    private static Configuration getConfiguration() {
	Configuration config = null;

	try {
	    if (LOGGER.isDebugEnabled()) {
		LOGGER.debug("Start configuration framework intialization.");
	    }

	    final URL configFileURL = Thread.currentThread().getContextClassLoader().getResource(CONFIG_FILE);
	    if (LOGGER.isDebugEnabled()) {
		if (configFileURL == null) {
		    final String msg = "Error loading the configuration file - " + CONFIG_FILE;
		    final SystemException exception = new SystemException(msg);
		    LOGGER.fatal(msg, exception);
		    throw exception;
		} else {
		    LOGGER.debug("Load - '" + configFileURL.getPath() + "' config file.");
		}
	    }

	    final DefaultConfigurationBuilder builder = new DefaultConfigurationBuilder(configFileURL);
	    config = builder.getConfiguration(true);
	    if (LOGGER.isDebugEnabled()) {
		LOGGER.debug("Configuration framework successfully intialized.");
	    }

	} catch (final Throwable e) {
	    final String msg = "Error loading the configuration file - " + CONFIG_FILE;
	    LOGGER.fatal(msg, e);
	    throw new SystemException(msg);
	}
	return config;
    }

    /**
     * Check if configuration is initialized.
     */
    private static void nullCheck() {
	if (null == configuration) {
	    final String msg = "Configuration file is not loaded, please ensure that its available in the classpath.";
	    LOGGER.error(msg);
	    throw new SystemException(msg);
	}
    }

    /**
     * Returns true if the corresponding argument key can be found in the configuration.
     * 
     * @param key
     *            the key
     * @return true, if successful
     */
    public static boolean containsKey(final String key) {
	nullCheck();
	return configuration.containsKey(key);
    }

    /**
     * Returns a string value by looking up in the property file associated with the key if the key is missing it will return null.
     * 
     * @param labelId
     *            the label id
     * @param language
     *            the language
     * @param businessId
     *            the business id
     * @param countryCode
     *            the country code
     * @return the string
     */
    public static String getLabel(final String labelId, final String language, final String businessId, final String countryCode) {
	final StringBuilder builder = new StringBuilder(labelId);
	builder.append(DOT);
	builder.append(language);
	builder.append(DOT);
	builder.append(DOT);
	builder.append(DOT);
	return getString(builder.toString(), null);
    }

    /**
     * Returns a string value by looking up in the property file associated with the key if the key is missing it will return null.
     * 
     * @param key
     *            the key
     * @return the string
     */
    public static String getString(final String key) {
	return getString(key, null);
    }

    /**
     * Returns a string value by looking up in the property file associated with the key if the key is missing it will return defaultValue which is
     * the argument.
     * 
     * @param key
     *            the key
     * @param defaultValue
     *            the default value
     * @return the string
     */
    public static String getString(final String key, final String defaultValue) {
	String value = null;
	nullCheck();
	try {
	    // if (LOGGER.isDebugEnabled()) {
	    // if (configuration.containsKey(key)) {
	    // LOGGER.debug("key[" + key + "] value[" + configuration.getString(key) + "]");
	    // } else {
	    // LOGGER.debug("key[" + key + "] - KEY_NOT_FOUND: key is missing in the property files, return default value [" + defaultValue
	    // + "]");
	    // }
	    // }
	    if (defaultValue == null) {
		value = configuration.getString(key);
	    } else {
		value = configuration.getString(key, defaultValue);
	    }

	} catch (final NoSuchElementException e) {
	    // as we are returning a null value if a key is missing in the
	    // property files
	    LOGGER.warn(MSG_NO_SUCH_ELEMENT, e);
	}
	return value;
    }

    /**
     * Returns a boolean object by looking up in the property file associated with the key If the corresponding key is missing it will return null.
     * 
     * @param key
     *            the key
     * @return the boolean
     */
    public static boolean getBoolean(final String key) {
	return getBoolean(key, false);
    }

    /**
     * Returns an boolean object by looking up in the property file associated with the key if the corresponding key is missing it will return
     * defaultValue which is the argument.
     * 
     * @param key
     *            the key
     * @param defaultValue
     *            the default value
     * @return the boolean
     */
    public static boolean getBoolean(final String key, final boolean defaultValue) {
	boolean value = false;
	nullCheck();
	try {
	    if (LOGGER.isDebugEnabled()) {
		if (configuration.containsKey(key)) {
		    // LOGGER.debug("key[" + key + "] value[" + configuration.getBoolean(key) + "]");
		} else {
		    LOGGER.debug("key[" + key + "] - KEY_NOT_FOUND: key is missing in the property files, return default value [" + defaultValue
			    + "]");
		}
	    }
	    value = configuration.getBoolean(key, defaultValue);

	} catch (final NoSuchElementException e) {
	    // as we are returning a null value if a key is missing in the
	    // property files
	    LOGGER.warn(MSG_NO_SUCH_ELEMENT, e);
	}
	return value;
    }

    /**
     * Returns an double object by looking up in the property file associated with the key if the corresponding key is missing it will return null.
     * 
     * @param key
     *            the key
     * @return the double
     */
    public static double getDouble(final String key) {
	return getDouble(key, -1);
    }

    /**
     * Returns an double object by looking up in the property file associated with the key if the corresponding key is missing it will return
     * defaultValue which is the argument.
     * 
     * @param key
     *            the key
     * @param defaultValue
     *            the default value
     * @return the double
     */
    public static double getDouble(final String key, final double defaultValue) {
	double value = -1;
	nullCheck();
	try {
	    if (LOGGER.isDebugEnabled()) {
		if (configuration.containsKey(key)) {
		    // LOGGER.debug("key[" + key + "] value[" + configuration.getDouble(key) + "]");
		} else {
		    LOGGER.debug("key[" + key + "] - KEY_NOT_FOUND: key is missing in the property files, return default value [" + defaultValue
			    + "]");
		}
	    }

	    value = configuration.getDouble(key, defaultValue);

	} catch (final NoSuchElementException e) {
	    // as we are returning a null value if a key is missing in the
	    // property files
	    LOGGER.warn(MSG_NO_SUCH_ELEMENT, e);
	}
	return value;
    }

    /**
     * Returns an float object by looking up in the property file associated with the key if the corresponding key is missing it will return null.
     * 
     * @param key
     *            the key
     * @return the float
     */
    public static float getFloat(final String key) {
	return getFloat(key, -1);
    }

    /**
     * Returns an float object by looking up in the property file associated with the key if the corresponding key is missing it will return
     * defaultValue which is the argument.
     * 
     * @param key
     *            the key
     * @param defaultValue
     *            the default value
     * @return the float
     */
    public static float getFloat(final String key, final float defaultValue) {
	float value = -1;
	nullCheck();
	try {
	    if (LOGGER.isDebugEnabled()) {
		if (configuration.containsKey(key)) {
		    // LOGGER.debug("key[" + key + "] value[" + configuration.getFloat(key) + "]");
		} else {
		    LOGGER.debug("key[" + key + "] - KEY_NOT_FOUND: key is missing in the property files, return default value [" + defaultValue
			    + "]");
		}
	    }
	    if (defaultValue == -1) {
		value = configuration.getFloat(key);
	    } else {
		value = configuration.getFloat(key, defaultValue);
	    }

	} catch (final NoSuchElementException e) {
	    // as we are returning a null value if a key is missing in the
	    // property files
	    LOGGER.warn(MSG_NO_SUCH_ELEMENT, e);
	}
	return value;
    }

    /**
     * Returns an integer object by looking up in the property file associated with the key if the corresponding key is missing it will return null.
     * 
     * @param key
     *            the key
     * @return the integer
     */
    public static int getInt(final String key) {
	return getInt(key, -1);
    }

    /**
     * Returns an integer object by looking up in the property file associated with the key if the corresponding key is missing it will return
     * defaultValue which is the argument.
     * 
     * @param key
     *            the key
     * @param defaultValue
     *            the default value
     * @return the integer
     */
    public static int getInt(final String key, final int defaultValue) {
	int value = -1;
	nullCheck();
	try {
	    if (LOGGER.isDebugEnabled()) {
		if (configuration.containsKey(key)) {
		    // LOGGER.debug("key[" + key + "] value[" + configuration.getInt(key) + "]");
		} else {
		    LOGGER.debug("key[" + key + "] - KEY_NOT_FOUND: key is missing in the property files, return default value [" + defaultValue
			    + "]");
		}
	    }
	    if (defaultValue == -1) {
		value = configuration.getInt(key);
	    } else {
		value = configuration.getInt(key, defaultValue);
	    }

	} catch (final NoSuchElementException e) {
	    // as we are returning a null value if a key is missing in the
	    // property files
	    LOGGER.warn(MSG_NO_SUCH_ELEMENT, e);
	}
	return value;
    }

    /**
     * Returns all the keys identified in the configuration.
     * 
     * @return the keys
     */
    @SuppressWarnings("unchecked")
    public static Iterator getKeys() {
	nullCheck();
	return configuration.getKeys();
    }

    /**
     * Returns a list matching with the key as an argument when using with XML.
     * 
     * @param key
     *            the key
     * @return the list
     */
    @SuppressWarnings("unchecked")
    public static List getList(final String key) {

	List value = null;
	nullCheck();
	try {
	    value = configuration.getList(key);
	} catch (final NoSuchElementException e) {
	    LOGGER.error(MSG_NO_SUCH_ELEMENT, e);
	    // as we are returning a null value if a key is missing in the
	    // property files
	}
	return value;
    }

}
