/**
 * DynaCacheHandler.java
 */
package com.barclays.ussd.cache.dynacache;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.barclays.ussd.cache.CacheHandler;
import com.barclays.ussd.common.configuration.ConfigurationManager;
import com.ibm.websphere.cache.DistributedMap;
import com.ibm.websphere.cache.EntryInfo;

/**
 * The Class DynaCacheHandler.
 *
 * @author BTCI
 */
public class DynaCacheHandler implements CacheHandler {

    /** The Constant logger. */
    private static final Logger LOGGER = Logger.getLogger(DynaCacheHandler.class);

    /** The cache jndi name. */
    private String cacheJndiName;

    /** The dynamic cache. */
    private DistributedMap dynamicCache;

    /** The Constant DEFAULT_TIME_OUT_SECONDS. */
    private static final int DEFAULT_TIME_OUT_SECONDS = 60;

    /** The Constant DEFAULT_INACTIVITY_SECONDS. */
    private static final int DEFAULT_INACTIVITY_SECONDS = 50;

    /** The Constant DEFAULT_ITEM_PRIORITY. */
    private static final int DEFAULT_ITEM_PRIORITY = 1;

    /** The Constant TIME_OUT_KEY. */
    private static final String TIME_OUT_KEY = "ssc.dynamiccache.timeouttime";

    /** The Constant INACTIVITY_KEY. */
    private static final String INACTIVITY_KEY = "ssc.dynamiccache.inactivitytime";

    /** The Constant PRIORITY_KEY. */
    private static final String PRIORITY_KEY = "ssc.dynamiccache.priority";

    /**
     * Instantiates a new dyna cache handler.
     *
     * @param cacheJndiName
     *            the cache jndi name
     * @throws Exception
     *             the exception
     */
    public DynaCacheHandler(final String cacheJndiName) throws Exception {
	InitialContext context;
	try {
	    context = new InitialContext();
	    if (LOGGER.isInfoEnabled()) {
		LOGGER.info("Setting the DynaCache handler");
	    }
	    dynamicCache = (DistributedMap) context.lookup(cacheJndiName);
	} catch (final NamingException e) {
	    LOGGER.fatal("Unable to set the dynacache handler. NamingException caught:", e);
	    throw e;
	} catch (final Exception e) {
	    LOGGER.fatal("Unable to set the dynacache handler. Exception caught:", e);
	    throw e;
	}

    }

    /**
     * Instantiates a new dyna cache handler.
     */
    public DynaCacheHandler() {
    }

    /**
     * Gets the cache jndi name.
     *
     * @return the cacheJndiName
     */
    public String getCacheJndiName() {
	return cacheJndiName;
    }

    /**
     * Sets the cache jndi name.
     *
     * @param cacheJndiName
     *            the cacheJndiName to set
     */
    public void setCacheJndiName(final String cacheJndiName) {
	this.cacheJndiName = cacheJndiName;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.barclays.mcfe.ssc.tools.cache.CacheHandler#flush(java.lang.String)
     */

    public void flush(final String cacheName) {
	dynamicCache.clear();
    }

    /*
     * (non-Javadoc)
     *
     * @see com.barclays.mcfe.ssc.tools.cache.CacheHandler#getObject(java.lang.String , java.lang.Object)
     */

    public Object getObject(final String cacheName, final Object key) {
	Object returnObject = null;

	if (dynamicCache.containsKey(key)) {
	    if (LOGGER.isDebugEnabled()) {
		LOGGER.debug("Reading from DynaCache");
	    }
	    returnObject = dynamicCache.get(key);
	} else {
	    if (LOGGER.isDebugEnabled()) {
		LOGGER.debug("object not present in DynaCache" + key);
	    }
	}

	return returnObject;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.barclays.mcfe.ssc.tools.cache.CacheHandler#putObject(java.lang.String , java.lang.Object, java.lang.Object)
     */

    public void putObject(final String cacheName, final Object key, final Object object) {
	final int priority = ConfigurationManager.getInt(PRIORITY_KEY, DEFAULT_ITEM_PRIORITY);
	final int timeOutTime = ConfigurationManager.getInt(TIME_OUT_KEY, DEFAULT_TIME_OUT_SECONDS);
	final int inActivityTime = ConfigurationManager.getInt(INACTIVITY_KEY, DEFAULT_INACTIVITY_SECONDS);
	dynamicCache.put(key, object, priority, timeOutTime, inActivityTime, EntryInfo.SHARED_PUSH_PULL, null);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.barclays.mcfe.ssc.tools.cache.CacheHandler#removeObject(java.lang .String, java.lang.Object)
     */

    public Object removeObject(final String cacheName, final Object key) {
	return dynamicCache.remove(key);
    }
}
