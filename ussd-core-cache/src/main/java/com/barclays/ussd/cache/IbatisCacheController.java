/**
 * IbatisCacheController.java
 */
package com.barclays.ussd.cache;

import java.util.Properties;

import org.apache.log4j.Logger;

import com.barclays.ussd.cache.dynacache.DynaCacheHandler;
import com.barclays.ussd.cache.ehcache.EhCacheHandler;
import com.barclays.ussd.common.configuration.ConfigurationManager;
import com.ibatis.sqlmap.engine.cache.CacheController;
import com.ibatis.sqlmap.engine.cache.CacheModel;

/**
 * The Class IbatisCacheController.
 * 
 * @author BTCI
 */
public class IbatisCacheController implements CacheController {

    /** The Constant logger. */
    private static final Logger LOGGER = Logger.getLogger(IbatisCacheController.class);

    /** The Constant CACHE_TYPE_KEY. */
    private static final String CACHE_TYPE_KEY = "ssc.cachetype";

    /** The Constant DYNAMIC_CACHE_JNDI_NAME. */
    private static final String DYNAMIC_CACHE_JNDI_NAME = "ssc.dynamiccache.jndiname";

    /** The Constant EHCACHE_TYPE. */
    private static final String EHCACHE_TYPE = "ehcache";

    /** The Constant DYNAMIC_CACHE_TYPE. */
    private static final String DYNAMIC_CACHE_TYPE = "dynamicCache";

    /** The Constant EHCACHE_CONFIGFILE_URL. */
    private static final String EHCACHE_CONFIGFILE_URL = "/ehcache.xml";

    /** The EhCache CacheManager. */
    private static CacheHandler cacheHandler;

    /**
     * Flush a cache model.
     * 
     * @param cacheModel
     *            - the model to flush.
     */
    public void flush(final CacheModel cacheModel) {
	cacheHandler.flush(cacheModel.getId());
    }

    /**
     * Get an object from a cache model.
     * 
     * @param cacheModel
     *            - the model.
     * @param key
     *            - the key to the object.
     * @return the object if in the cache, or null(?).
     */
    public Object getObject(final CacheModel cacheModel, final Object key) {
	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("Reading from cache");
	}
	return cacheHandler.getObject(cacheModel.getId(), key);
    }

    /**
     * Put an object into a cache model.
     * 
     * @param cacheModel
     *            - the model to add the object to.
     * @param key
     *            - the key to the object.
     * @param object
     *            - the object to add.
     */
    public void putObject(final CacheModel cacheModel, final Object key, final Object object) {
	cacheHandler.putObject(cacheModel.getId(), key, object);
    }

    /**
     * Remove an object from a cache model.
     * 
     * @param cacheModel
     *            - the model to remove the object from.
     * @param key
     *            - the key to the object.
     * @return the removed object(?).
     */
    public Object removeObject(final CacheModel cacheModel, final Object key) {
	return cacheHandler.removeObject(cacheModel.getId(), key);
    }

    /**
     * Need to add <property name="configFile" value="/ehcache.xml"/> Configure a cache controller. Initialize the EH Cache Manager as a singleton.
     * 
     * @param props
     *            - the properties object continaing configuration information.
     */
    public void setProperties(final Properties props) {
	try {
	    if (cacheHandler == null) {
		synchronized (this) {
		    if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Setting the cache handler");
		    }
		    if (DYNAMIC_CACHE_TYPE.equalsIgnoreCase(ConfigurationManager.getString(CACHE_TYPE_KEY))) {
			cacheHandler = new DynaCacheHandler(ConfigurationManager.getString(DYNAMIC_CACHE_JNDI_NAME));
		    } else if (EHCACHE_TYPE.equalsIgnoreCase(ConfigurationManager.getString(CACHE_TYPE_KEY))) {
			cacheHandler = EhCacheHandler.getInstance(EHCACHE_CONFIGFILE_URL);
		    }
		}

	    }
	} catch (final Exception e) {
	    LOGGER.fatal("Unable to set the cache handler. Exception caught:", e);
	}

    }

}
