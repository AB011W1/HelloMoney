package com.barclays.bmg.cache;

import java.util.Properties;

import com.barclays.bmg.cache.dynacache.DynaCacheHandler;
import com.barclays.bmg.cache.ehcache.EhCacheHandler;
import com.ibatis.sqlmap.engine.cache.CacheController;
import com.ibatis.sqlmap.engine.cache.CacheModel;

public class IbatisCacheController implements CacheController {

    private static final String CACHE_TYPE_KEY = "ssc.cachetype";
    private static final String DYNAMIC_CACHE_JNDI_NAME = "ssc.dynamiccache.jndiname";
    private static final String EHCACHE_TYPE = "ehcache";
    private static final String DYNAMIC_CACHE_TYPE = "dynamicCache";
    private static final String RUNTIME_PROPERTY_URL = "/runtime.properties";
    private static final String EHCACHE_CONFIGFILE_URL = "/ehcache.xml";

    /** The EhCache CacheManager. */
    private static CacheHandler cacheHandler;

    /**
     * Flush a cache model.
     * 
     * @param cacheModel
     *            - the model to flush.
     */
    public void flush(CacheModel cacheModel) {

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
    public Object getObject(CacheModel cacheModel, Object key) {

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
    public void putObject(CacheModel cacheModel, Object key, Object object) {
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
    public Object removeObject(CacheModel cacheModel, Object key) {
	return cacheHandler.removeObject(cacheModel.getId(), key);
    }

    /**
     * Need to add <property name="configFile" value="/ehcache.xml"/> Configure a cache controller. Initialize the EH Cache Manager as a singleton.
     * 
     * @param props
     *            - the properties object continaing configuration information.
     */
    public void setProperties(Properties props) {

	try {

	    if (cacheHandler == null) {
		Properties property = new Properties();
		property.load(getClass().getResource(RUNTIME_PROPERTY_URL).openStream());
		synchronized (this) {

		    if (cacheHandler == null) {
			if (DYNAMIC_CACHE_TYPE.equalsIgnoreCase(property.getProperty(CACHE_TYPE_KEY))) {

			    cacheHandler = new DynaCacheHandler(property.getProperty(DYNAMIC_CACHE_JNDI_NAME));

			} else if (EHCACHE_TYPE.equalsIgnoreCase(property.getProperty(CACHE_TYPE_KEY))) {
			    cacheHandler = new EhCacheHandler(EHCACHE_CONFIGFILE_URL);
			}

		    }

		}
	    }
	} catch (Exception e) {
	    

	}

    }

    // /**
    // * Shut down the EH Cache CacheManager.
    // */
    // public void finalize() {
    // if (cacheManager != null) {
    // cacheManager.shutdown();
    // }
    // }
}
