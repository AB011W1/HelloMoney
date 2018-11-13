/**
 * EhCacheHandler.java
 */
package com.barclays.ussd.cache.ehcache;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.apache.log4j.Logger;

import com.barclays.ussd.cache.CacheHandler;

/**
 * The Class EhCacheHandler.
 * 
 * @author BTCI
 */
public final class EhCacheHandler implements CacheHandler {

    /** The Constant logger. */
    private static final Logger LOGGER = Logger.getLogger(EhCacheHandler.class);

    private static EhCacheHandler ehCacheHandler;

    /** The cache manager. */
    private CacheManager cacheManager;

    /** The cache. */
    private Cache cache;

    /**
     * Instantiates a new eh cache handler.
     * 
     * @param xmlUrl
     *            the xml url
     */
    private EhCacheHandler(final String xmlUrl) {
	if (LOGGER.isInfoEnabled()) {
	    LOGGER.info("Setting the EhCache handler");
	}

	final URL url = getClass().getResource(xmlUrl);
	this.cacheManager = CacheManager.create(url);
    }

    public static EhCacheHandler getInstance(String xmlUrl) {
	if (ehCacheHandler == null) {
	    ehCacheHandler = new EhCacheHandler(xmlUrl);
	}
	return ehCacheHandler;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.barclays.ussd.cache.CacheHandler#flush(java.lang.String)
     */
    public void flush(final String cacheName) {
	this.cache = this.cacheManager.getCache(cacheName);

	try {
	    this.cache.removeAll();
	} catch (final IllegalStateException e) {
	    LOGGER.fatal("Unable to set the ehcache handler. IllegalStateException caught:", e);
	} catch (final CacheException e) {
	    LOGGER.fatal("Unable to set the ehcache handler. CacheException caught:", e);
	} catch (final IOException e) {
	    LOGGER.fatal("Unable to set the ehcache handler. IOException caught:", e);
	}

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.barclays.ussd.cache.CacheHandler#getObject(java.lang.String, java.lang.Object)
     */
    @SuppressWarnings("unchecked")
    public Object getObject(final String cacheName, final Object key) {
	final Object returnObject = null;
	this.cache = this.cacheManager.getCache(cacheName);

	if (this.cache != null) {
	    if (LOGGER.isDebugEnabled()) {
		LOGGER.debug("Reading from EhCache");
	    }

	    final List keys = this.cache.getKeys();

	    if (keys.contains(key)) {
		final Element obj = this.cache.get(key);
		if (obj != null) {
		    if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Got object from EhCache" + key);
		    }
		    return obj.getValue();
		}
	    } else {
		if (LOGGER.isDebugEnabled()) {
		    LOGGER.debug("object not present in EhCache" + key);
		}
	    }
	}
	return returnObject;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.barclays.ussd.cache.CacheHandler#putObject(java.lang.String, java.lang.Object, java.lang.Object)
     */
    public void putObject(final String cacheName, final Object key, final Object object) {
	this.cache = this.cacheManager.getCache(cacheName);
	if ((this.cache != null) && (key != null) && (object != null)) {
	    this.cache.put(new Element(key, object));
	}

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.barclays.ussd.cache.CacheHandler#removeObject(java.lang.String, java.lang.Object)
     */
    public Object removeObject(final String cacheName, final Object key) {
	this.cache = this.cacheManager.getCache(cacheName);
	if (this.cache != null) {
	    return this.cache.remove(key);
	}
	return null;
    }

}
