package com.barclays.bmg.ecrime;

import java.util.List;

/**
 * Ecrime Configuration interface. Get the Parameter from the configuration. the parameter divide into two parts. <br/>
 * 1) business specific configurations, call getStringParam,getBooleanParam,getMaskRules method <br/>
 * 2) global configurations,call getGlobalParam <br/>
 * 
 * @author
 */
public interface EcrimeConfiguration {

    /**
     * Get String Parameter By Key for the current business
     * 
     * @param key
     *            Key parameter
     * @param defaultValue
     *            default Value if key not found
     * @return String Parameters
     */
    String getStringParam(String key, String defaultValue);

    /**
     * Get boolean Parameter By Key for the current business
     * 
     * @param key
     *            Key parameter
     * @param defaultValue
     *            default Value if key not found
     * @return true|false
     */
    boolean getBooleanParam(String key, boolean defaultValue);

    /**
     * Get Mask Rules for the current business
     * 
     * @return Mask Rules List
     */
    List<String> getMaskRules();

    /**
     * Get global String Parameter by key
     * 
     * @param key
     *            Key parameter
     * @param defaultValue
     *            default value if key not found
     * @return
     */
    String getGlobalParam(String key, String defaultValue);

}
