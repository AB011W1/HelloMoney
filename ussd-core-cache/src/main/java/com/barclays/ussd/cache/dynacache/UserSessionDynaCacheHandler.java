/**
 * UserSessionDynaCacheHandler.java
 */
package com.barclays.ussd.cache.dynacache;

import java.io.IOException;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.barclays.ussd.cache.CacheHandler;
import com.barclays.ussd.common.configuration.ConfigurationManager;
import com.barclays.ussd.utils.USSDUtils;
import com.ibm.websphere.cache.DistributedMap;
import com.ibm.websphere.cache.EntryInfo;

/**
 * The Class DynaCacheHandler.
 * 
 * @author BTCI
 */
public class UserSessionDynaCacheHandler implements CacheHandler {

    /** The Constant logger. */
    private static final Logger LOGGER = Logger.getLogger(UserSessionDynaCacheHandler.class);

    /** The dynamic cache. */
    private DistributedMap dynamicCache;

    /** The Constant DEFAULT_TIME_OUT_SECONDS. */
    private static final int DEFAULT_TIME_OUT_SECONDS = 600;

    /** The Constant DEFAULT_INACTIVITY_SECONDS. */
    private static final int DEFAULT_INACTIVITY_SECONDS = 250;

    /** The Constant DEFAULT_ITEM_PRIORITY. */
    private static final int DEFAULT_ITEM_PRIORITY = 1;

    /** The Constant TIME_OUT_KEY. */
    private static final String TIME_OUT_KEY = "ssc.user.session.dynamiccache.timeouttime";

    /** The Constant INACTIVITY_KEY. */
    private static final String INACTIVITY_KEY = "ssc.user.session.dynamiccache.inactivitytime";

    /** The Constant PRIORITY_KEY. */
    private static final String PRIORITY_KEY = "ssc.user.session.dynamiccache.priority";

    private static final String DYNAMIC_CACHE_JNDI_NAME = "ssc.user.session.dynamiccache.jndiname";

    private static final String RUNTIME_PROPERTY_URL = "/runtime.properties";

    private static final String DYNAMIC_CACHE_TYPE = "dynamicCache";

    private static final String CACHE_TYPE_KEY = "ssc.cachetype";

    private static final String DYNAMIC_CACHE_CONFIG_FILE = "/dynamicCache.properties";

    /**
     * Instantiates a new dyna cache handler.
     * 
     * @param cacheJndiName
     *            the cache jndi name
     * @throws Exception
     *             the exception
     */
    public UserSessionDynaCacheHandler() throws Exception {
	Properties property = new Properties();
	property.load(getClass().getResource(RUNTIME_PROPERTY_URL).openStream());
	synchronized (this) {
	    boolean sessionCacheEnabled = USSDUtils.isSessionCacheEnabled();
	    if (LOGGER.isDebugEnabled()) {
		String log = "sessionCacheEnabled=[" + sessionCacheEnabled + "]";
		LOGGER.debug(log);
	    }
	    if (sessionCacheEnabled && StringUtils.equalsIgnoreCase(DYNAMIC_CACHE_TYPE, property.getProperty(CACHE_TYPE_KEY))) {
		if (LOGGER.isDebugEnabled()) {
		    LOGGER.debug("Initializing the session chache handler bean");
		}
		String cacheJndiName = property.getProperty(DYNAMIC_CACHE_JNDI_NAME);
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
	}
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

	Properties property = new Properties();
	int priority = DEFAULT_ITEM_PRIORITY;
	int timeOutTime = DEFAULT_TIME_OUT_SECONDS;
	int inActivityTime = DEFAULT_INACTIVITY_SECONDS;
	try {
	    property.load(getClass().getResource(DYNAMIC_CACHE_CONFIG_FILE).openStream());
	    if (property != null) {
		if (property.get(PRIORITY_KEY) != null) {
		    priority = Integer.parseInt(String.valueOf(property.get(PRIORITY_KEY)));
		}
		if (property.get(TIME_OUT_KEY) != null) {
		    timeOutTime = Integer.parseInt(String.valueOf(property.get(TIME_OUT_KEY)));
		}
		if (property.get(INACTIVITY_KEY) != null) {
		    inActivityTime = Integer.parseInt(String.valueOf(property.get(INACTIVITY_KEY)));
		}
	    }
	} catch (IOException e) {
	}
	dynamicCache.put(key, object, priority, timeOutTime, inActivityTime, EntryInfo.SHARED_PUSH_PULL, null);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.barclays.mcfe.ssc.tools.cache.CacheHandler#removeObject(java.lang .String, java.lang.Object)
     */

    /*
     * public Object removeObject(final String cacheName, final Object key) { return dynamicCache.remove(key); dynamicCache.re }
     */

    public boolean containsObject(final String cacheName, final Object key) {
	return dynamicCache.containsKey(key, true);
    }

    @Override
    public Object removeObject(String cacheName, Object key) {
	// TODO Auto-generated method stub
	return dynamicCache.remove(key);
    }
}
