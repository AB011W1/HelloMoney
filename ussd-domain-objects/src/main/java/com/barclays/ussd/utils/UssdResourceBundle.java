package com.barclays.ussd.utils;

import java.util.Locale;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

// TODO: Auto-generated Javadoc
/**
 * The Class UssdResourceBundle.
 */
public class UssdResourceBundle {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(UssdResourceBundle.class);

    /** The resource bundle. */
    private ReloadableResourceBundleMessageSource resourceBundle;

    /**
     * Gets the label.
     * 
     * @param key
     *            the key
     * @param locale
     *            the locale
     * @return String
     */
    public String getLabel(final String key, final Locale locale) {

	return getLabel(key, new Object[] {}, locale);
    }

    public String getErrorLabel(final String key, final Locale locale) {

	return getErrorLabel(key, new Object[] {}, locale);
    }

    /**
     * Gets the label.
     * 
     * @param key
     *            the key
     * @param arr
     *            the arr
     * @param locale
     *            the locale
     * @return String
     */
    public String getLabel(String key, final Object[] arr, final Locale locale) {

	String value = null;
	try {
	    value = resourceBundle.getMessage(key, arr, locale);
	    if (value == null) {
		LOGGER.error("Exception occured while fetching value of key [" + key + "]");
	    }

	} catch (final BeansException e) {
	    value = e.getMessage();
	    LOGGER.error("Exception occured while fetching value of key [" + key + "]", e);
	} catch (final NoSuchMessageException e) {
	    value = USSDConstants.UNKNOWN_LABEL;
	    // value = resourceBundle.getMessage(USSDConstants.GENERIC_ERROR_CODE, arr, locale);
	    LOGGER.error("Exception occured while fetching value of key [" + key + "]", e);
	}
	return value;
    }

    public String getErrorLabel(String key, final Object[] arr, final Locale locale) {

	String value = null;
	try {
	    value = resourceBundle.getMessage(key, arr, locale);
	    if (value == null) {
		LOGGER.error("Exception occured while fetching value of key [" + key + "]");
	    }

	} catch (final BeansException e) {
	    value = e.getMessage();
	    LOGGER.error("Exception occured while fetching value of key [" + key + "]", e);
	} catch (final NoSuchMessageException e) {
	    value = resourceBundle.getMessage(USSDConstants.GENERIC_ERROR_CODE, arr, locale);
	    LOGGER.error("Exception occured while fetching value of key [" + key + "]", e);
	}
	return value;
    }

    /**
     * Gets the resource bundle.
     * 
     * @return the resourceBundle
     */
    public final ReloadableResourceBundleMessageSource getResourceBundle() {
	return resourceBundle;
    }

    /**
     * Sets the resource bundle.
     * 
     * @param resourceBundle
     *            the resourceBundle to set
     */
    public final void setResourceBundle(final ReloadableResourceBundleMessageSource resourceBundle) {
	this.resourceBundle = resourceBundle;
    }

}