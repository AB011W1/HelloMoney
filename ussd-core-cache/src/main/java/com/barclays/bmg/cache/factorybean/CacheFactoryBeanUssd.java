/**
 * CacheFactoryBean.java
 */
package com.barclays.bmg.cache.factorybean;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import com.barclays.ussd.cache.CacheHandler;
import com.barclays.ussd.cache.dynacache.DynaCacheHandler;
import com.barclays.ussd.common.configuration.ConfigurationManager;

/**
 * The Class CacheFactoryBean.
 *
 * @author BTCI
 */
public class CacheFactoryBeanUssd implements FactoryBean<CacheHandler>, InitializingBean {

    /** The Constant logger. */
    private static final Logger LOGGER = Logger.getLogger(CacheFactoryBeanUssd.class);

    /** The Constant CACHE_TYPE_KEY. */
    private static final String CACHE_TYPE_KEY = "ssc.cachetype";

    /** The Constant DYNAMIC_CACHE_JNDI_NAME. */
    private static final String DYNAMIC_CACHE_JNDI_NAME = "ssc.dynamiccache.jndiname";

    /** The Constant EHCACHE_TYPE. */
    private static final String EHCACHE_TYPE = "ehcache";

    /** The Constant DYNAMIC_CACHE_TYPE. */
    private static final String DYNAMIC_CACHE_TYPE = "dynamicCache";

    /** The cache handler. */
    private CacheHandler cacheHandler;

    /** The eh cache handler. */
    private CacheHandler ehCacheHandler;

    /** The dynamic cache handler. */
    private CacheHandler dynamicCacheHandler;

    /**
     * Gets the cache handler.
     *
     * @return the cacheHandler
     */
    public CacheHandler getCacheHandler() {
	return cacheHandler;
    }

    /**
     * Sets the cache handler.
     *
     * @param cacheHandler
     *            the cacheHandler to set
     */
    public void setCacheHandler(final CacheHandler cacheHandler) {
	this.cacheHandler = cacheHandler;
    }

    /**
     * Gets the eh cache handler.
     *
     * @return the ehCacheHandler
     */
    public CacheHandler getEhCacheHandler() {
	return ehCacheHandler;
    }

    /**
     * Sets the eh cache handler.
     *
     * @param ehCacheHandler
     *            the ehCacheHandler to set
     */
    public void setEhCacheHandler(final CacheHandler ehCacheHandler) {
	this.ehCacheHandler = ehCacheHandler;
    }

    /**
     * Gets the dynamic cache handler.
     *
     * @return the dynamicCacheHandler
     */
    public CacheHandler getDynamicCacheHandler() {
	return dynamicCacheHandler;
    }

    /**
     * Sets the dynamic cache handler.
     *
     * @param dynamicCacheHandler
     *            the dynamicCacheHandler to set
     */
    public void setDynamicCacheHandler(final CacheHandler dynamicCacheHandler) {
	this.dynamicCacheHandler = dynamicCacheHandler;
    }

    /**
     * Instantiates a new cache factory bean.
     *
     * @throws Exception
     *             the exception
     */
    public CacheFactoryBeanUssd() throws Exception {
    }

    /*
     * (non-Javadoc)
     *
     * @see org.springframework.beans.factory.FactoryBean#getObject()
     */
    public CacheHandler getObject() {
	return cacheHandler;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.springframework.beans.factory.FactoryBean#getObjectType()
     */
    public Class<? extends CacheHandler> getObjectType() {
	return (cacheHandler != null ? cacheHandler.getClass() : CacheHandler.class);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.springframework.beans.factory.FactoryBean#isSingleton()
     */
    public boolean isSingleton() {
	return true;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    public void afterPropertiesSet() throws Exception {
	if (LOGGER.isInfoEnabled()) {
	    LOGGER.info("Setting the cache handler");
	}

	try {
	    if (cacheHandler == null) {
		if (DYNAMIC_CACHE_TYPE.equalsIgnoreCase(ConfigurationManager.getString(CACHE_TYPE_KEY))) {
		    cacheHandler = new DynaCacheHandler(ConfigurationManager.getString(DYNAMIC_CACHE_JNDI_NAME));
		} else if (EHCACHE_TYPE.equalsIgnoreCase(ConfigurationManager.getString(CACHE_TYPE_KEY))) {
		    cacheHandler = getEhCacheHandler();
		}
	    }

	} catch (final Exception e) {
	    LOGGER.fatal("Unable to set the cache handler. Exception caught:",e );

	}

    }

}
