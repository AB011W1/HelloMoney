/**
 *
 */
package com.barclays.bmg.cache.factorybean;

import java.util.Properties;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import com.barclays.bmg.cache.CacheHandler;
import com.barclays.bmg.cache.dynacache.DynaCacheHandler;

/* *************************** Revision History *********************************
 * Version        Author          Date                     Description
 * 0.1            ELicer Zheng        3 Nov 2009                  Initial version
 *
 *
 ********************************************************************************/

/**
 * This is a cacheFactory Bean class .
 * 
 * Elicer Zheng
 */

public class CacheFactoryBean implements FactoryBean, InitializingBean {

    private static final String BMG_CACHE_CONFIG_FILE = "/runtime.properties";

    private static final String CACHE_TYPE_KEY = "ssc.cachetype";
    private static final String DYNAMIC_CACHE_JNDI_NAME = "ssc.dynamiccache.jndiname";
    private static final String EHCACHE_TYPE = "ehcache";
    private static final String DYNAMIC_CACHE_TYPE = "dynamicCache";

    // private static final String EHCACHE_CONFIGFILE_URL = "/ehcache.xml";
    // protected final transient MCFELog log = MCFELogUtility.getLogger(getClass());

    public CacheFactoryBean() throws Exception {
	property = new Properties();
	property.load(getClass().getResource(BMG_CACHE_CONFIG_FILE).openStream());
    }

    private CacheHandler cacheHandler;

    private CacheHandler ehCacheHandler;

    private CacheHandler dynamicCacheHandler;

    private Properties property;

    /**
     * @return the ehCacheHandler
     */
    public CacheHandler getEhCacheHandler() {
	return ehCacheHandler;
    }

    /**
     * @param ehCacheHandler
     *            the ehCacheHandler to set
     */
    public void setEhCacheHandler(CacheHandler ehCacheHandler) {
	this.ehCacheHandler = ehCacheHandler;
    }

    /**
     * @return the dynamicCacheHandler
     */
    public CacheHandler getDynamicCacheHandler() {
	return dynamicCacheHandler;
    }

    /**
     * @param dynamicCacheHandler
     *            the dynamicCacheHandler to set
     */
    public void setDynamicCacheHandler(CacheHandler dynamicCacheHandler) {
	this.dynamicCacheHandler = dynamicCacheHandler;
    }

    /**
     * @return the cacheHandler
     */
    public CacheHandler getCacheHandler() {
	return cacheHandler;
    }

    /**
     * @param cacheHandler
     *            the cacheHandler to set
     */
    public void setCacheHandler(CacheHandler cacheHandler) {
	this.cacheHandler = cacheHandler;
    }

    public void afterPropertiesSet() {
	// If no CacheManager given, fetch the default.
	try {

	    if (cacheHandler == null) {
		if (property != null) {
		    if (DYNAMIC_CACHE_TYPE.equalsIgnoreCase(property.getProperty(CACHE_TYPE_KEY))) {

			cacheHandler = new DynaCacheHandler(property.getProperty(DYNAMIC_CACHE_JNDI_NAME));

		    } else if (EHCACHE_TYPE.equalsIgnoreCase(property.getProperty(CACHE_TYPE_KEY))) {

			cacheHandler = this.getEhCacheHandler();

		    }

		}

	    }

	} catch (Exception e) {
	    // log.error("Initialize Cache Error, Failed to get Dynamic Cache Handler", CCSErrorCodeConstants.SYS_SYSTEM_ERROR, e);
	    // log.error("INITIALIZE CACHE ERROR, FAILED TO GET DYNAMIC CACHE HANDLER", e);

	}
    }

    public CacheHandler getObject() {
	return this.cacheHandler;
    }

    public Class<? extends CacheHandler> getObjectType() {
	return (this.cacheHandler != null ? this.cacheHandler.getClass() : CacheHandler.class);
    }

    public boolean isSingleton() {
	return true;
    }

}
