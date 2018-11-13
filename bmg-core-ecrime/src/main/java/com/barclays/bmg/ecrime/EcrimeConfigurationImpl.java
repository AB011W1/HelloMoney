package com.barclays.bmg.ecrime;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.barclays.bmg.context.Context;
import com.barclays.bmg.context.BMBContextHolder;

/**
 * Ecrime Configuration Implementation class, using an xml file to store the configuration items.
 * 
 * @author
 */
public class EcrimeConfigurationImpl implements EcrimeConfiguration, InitializingBean {
    private ConfigurationReader configuratonReader;

    private Map<String, ConfigItems> businessConfigMap = new HashMap<String, ConfigItems>();

    private Map<String, String> globalConfigMap = new HashMap<String, String>();

    /**
     * @throws Exception
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    public void afterPropertiesSet() throws Exception {
	Assert.notNull(configuratonReader, "The configurationReader attribute is Required");
	loadConfiguation();
    }

    /**
     * load Configuration.
     */
    private void loadConfiguation() {

	// load global configuration items.
	int globalConfListSize = configuratonReader.getConfiguration().getMaxIndex("global-configuration(0).property");
	for (int i = 0; i <= globalConfListSize; i++) {

	    String key = MessageFormat.format("global-configuration(0).property({0})", new Object[] { i });
	    String name = this.configuratonReader.getConfiguration().getString(key + "[@name]");
	    String value = this.configuratonReader.getConfiguration().getString(key + "[@value]");
	    globalConfigMap.put(name, value);
	}

	// load business specific configuration items.
	String keyNode = "ecrime-configuration";

	int nodeListSize = configuratonReader.getConfiguration().getMaxIndex(keyNode);
	Object[] args = new Object[1];
	for (int i = 0; i <= nodeListSize; i++) {
	    args[0] = String.valueOf(i);
	    String tmpPrefix = MessageFormat.format(keyNode + "({0})", args);
	    String businessId = configuratonReader.getConfiguration().getString(tmpPrefix + "[@business-id]");
	    this.parseConfiguration(businessId, tmpPrefix);
	}

    }

    /**
     * parse configuration for the business specific configuration.
     * 
     * @param systemId
     * @param businessId
     * @param tmpPrefix
     */
    private void parseConfiguration(String businessId, String tmpPrefix) {

	ConfigItems items = new ConfigItems();
	for (int i = 0; i <= this.configuratonReader.getConfiguration().getMaxIndex(tmpPrefix + ".property"); i++) {
	    String prefix = MessageFormat.format(tmpPrefix + ".property({0})", new Object[] { i });
	    String name = this.configuratonReader.getConfiguration().getString(prefix + "[@name]");
	    String value = this.configuratonReader.getConfiguration().getString(prefix + "[@value]");
	    items.addProperty(name, value);

	}
	for (int i = 0; i <= this.configuratonReader.getConfiguration().getMaxIndex(tmpPrefix + ".maskRule"); i++) {
	    String prefix = MessageFormat.format(tmpPrefix + ".maskRule({0})", new Object[] { i });
	    String value = this.configuratonReader.getConfiguration().getString(prefix + "[@value]");
	    items.addMaskRule(value);

	}
	businessConfigMap.put(businessId, items);
    }

    /**
     * @param configuratonReader
     *            the configuratonReader to set
     */
    public void setConfiguratonReader(ConfigurationReader configuratonReader) {
	this.configuratonReader = configuratonReader;
    }

    /**
     * @param key
     * @return
     * @see com.barclays.mcfe.ssc.web.ecrime.EcrimeConfiguration#getBooleanParam(java.lang.String)
     */
    public boolean getBooleanParam(String key, boolean defaultValue) {
	String param = getStringParam(key, "" + defaultValue);
	return Boolean.valueOf(param);
    }

    /**
     * @param key
     * @param defaultValue
     * @return
     * @see com.barclays.mcfe.ssc.web.ecrime.EcrimeConfiguration#getGlobalParam(java.lang.String, java.lang.String)
     */
    public String getGlobalParam(String key, String defaultValue) {

	if (globalConfigMap.containsKey(key)) {
	    return globalConfigMap.get(key);
	} else {
	    return defaultValue;
	}
    }

    /**
     * @return
     * @see com.barclays.mcfe.ssc.web.ecrime.EcrimeConfiguration#getMaskRules()
     */
    public List<String> getMaskRules() {
	Context context = BMBContextHolder.getContext();
	if (context == null) {
	    return new ArrayList<String>();
	}
	String businessId = context.getBusinessId();
	if (businessConfigMap.containsKey(businessId)) {
	    return businessConfigMap.get(businessId).getRuleList();
	} else {
	    return new ArrayList<String>();
	}
    }

    /**
     * @param key
     * @return
     * @see com.barclays.mcfe.ssc.web.ecrime.EcrimeConfiguration#getStringParam(java.lang.String)
     */
    public String getStringParam(String key, String defaultValue) {
	Context context = BMBContextHolder.getContext();
	if (context == null) {
	    return defaultValue;
	}
	String businessId = context.getBusinessId();
	ConfigItems items = businessConfigMap.get(businessId);
	if (items != null && items.getPropertyMap().containsKey(key)) {
	    return items.getPropertyMap().get(key);
	} else {
	    return defaultValue;
	}
    }

    private static class ConfigItems {
	private Map<String, String> propertyMap;

	private List<String> ruleList;

	ConfigItems() {
	    propertyMap = new HashMap<String, String>();
	    ruleList = new ArrayList<String>();
	}

	/**
	 * @return the propertyMap
	 */
	Map<String, String> getPropertyMap() {
	    return propertyMap;
	}

	void addProperty(String name, String value) {
	    propertyMap.put(name, value);
	}

	/**
	 * @return the masklist
	 */
	List<String> getRuleList() {
	    return ruleList;
	}

	void addMaskRule(String rule) {
	    ruleList.add(rule);
	}

    }

}
