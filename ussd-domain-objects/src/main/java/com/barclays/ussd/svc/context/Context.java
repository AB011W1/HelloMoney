package com.barclays.ussd.svc.context;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang.StringUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class Context.
 */
public class Context implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The business id. */
    private String businessId;

    /** The system id. */
    private String systemId;

    /** The language id. */
    private String languageId;

    /** The country code. */
    private String countryCode;

    /** The user id. */
    private String userId;

    /** The customer id. */
    private String customerId;

    /** The local currency. */
    private String localCurrency;

    /** The mobile phone. */
    private String mobilePhone;
    /** The context map. */
    private Map<String, Object> contextMap = new HashMap<String, Object>();

    /**
     * Gets the local currency.
     * 
     * @return the local currency
     */
    public String getLocalCurrency() {
	return localCurrency;
    }

    /**
     * Sets the local currency.
     * 
     * @param localCurrency
     *            the new local currency
     */
    public void setLocalCurrency(String localCurrency) {
	this.localCurrency = localCurrency;
    }

    /**
     * Gets the business id.
     * 
     * @return the business id
     */
    public String getBusinessId() {
	return businessId;
    }

    /**
     * Sets the business id.
     * 
     * @param businessId
     *            the new business id
     */
    public void setBusinessId(String businessId) {
	this.businessId = businessId;
    }

    /**
     * Gets the system id.
     * 
     * @return the system id
     */
    public String getSystemId() {
	return systemId;
    }

    /**
     * Sets the system id.
     * 
     * @param systemId
     *            the new system id
     */
    public void setSystemId(String systemId) {
	this.systemId = systemId;
    }

    /**
     * Gets the language id.
     * 
     * @return the language id
     */
    public String getLanguageId() {
	return languageId;
    }

    /**
     * Sets the language id.
     * 
     * @param languageId
     *            the new language id
     */
    public void setLanguageId(String languageId) {
	this.languageId = languageId;
    }

    /**
     * Gets the country code.
     * 
     * @return the country code
     */
    public String getCountryCode() {
	return countryCode;
    }

    /**
     * Sets the country code.
     * 
     * @param countryCode
     *            the new country code
     */
    public void setCountryCode(String countryCode) {
	this.countryCode = countryCode;
    }

    /**
     * Gets the user id.
     * 
     * @return the user id
     */
    public String getUserId() {
	return userId;
    }

    /**
     * Sets the user id.
     * 
     * @param userId
     *            the new user id
     */
    public void setUserId(String userId) {
	this.userId = userId;
    }

    /**
     * Gets the customer id.
     * 
     * @return the customer id
     */
    public String getCustomerId() {
	return customerId;
    }

    /**
     * Sets the customer id.
     * 
     * @param customerId
     *            the new customer id
     */
    public void setCustomerId(String customerId) {
	this.customerId = customerId;
    }

    /**
     * Gets the context map.
     * 
     * @return the context map
     */
    public Map<String, Object> getContextMap() {
	return contextMap;
    }

    /**
     * Sets the context map.
     * 
     * @param contextMap
     *            the context map
     */
    public void setContextMap(Map<String, Object> contextMap) {
	this.contextMap = contextMap;
    }

    /**
     * Gets the value.
     * 
     * @param key
     *            the key
     * @return the value
     */
    public Object getValue(String key) {
	return contextMap.get(key);
    }

    /**
     * Sets the value.
     * 
     * @param key
     *            the key
     * @param value
     *            the value
     */
    public void setValue(String key, Object value) {
	contextMap.put(key, value);
    }

    /**
     * Adds the entry.
     * 
     * @param key
     *            the key
     * @param value
     *            the value
     */
    public void addEntry(String key, Object value) {
	contextMap.put(key, value);
    }

    /**
     * Removes the entry.
     * 
     * @param key
     *            the key
     */
    public void removeEntry(String key) {
	contextMap.remove(key);
    }

    /**
     * Gets the mobile phone.
     * 
     * @return the mobile phone
     */
    public String getMobilePhone() {
	return mobilePhone;
    }

    /**
     * Sets the mobile phone.
     * 
     * @param mobilePhone
     *            the new mobile phone
     */
    public void setMobilePhone(String mobilePhone) {
	this.mobilePhone = mobilePhone;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
	int hashCode = 0;
	if (!StringUtils.isEmpty(businessId)) {
	    hashCode = hashCode + businessId.hashCode();
	}
	if (!StringUtils.isEmpty(systemId)) {
	    hashCode = hashCode + systemId.hashCode();
	}
	if (!StringUtils.isEmpty(languageId)) {
	    hashCode = hashCode + languageId.hashCode();
	}
	if (!StringUtils.isEmpty(countryCode)) {
	    hashCode = hashCode + countryCode.hashCode();
	}
	if (!StringUtils.isEmpty(userId)) {
	    hashCode = hashCode + userId.hashCode();
	}
	if (!StringUtils.isEmpty(customerId)) {
	    hashCode = hashCode + customerId.hashCode();
	}
	return hashCode;
    }
}
