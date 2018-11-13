package com.barclays.bmg.cache.ehcache;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import com.barclays.bmg.cache.CacheHandler;

public class EhCacheHandler implements CacheHandler {

    private static CacheManager cacheManager;
    private Cache cache;

    public EhCacheHandler(String xmlUrl) {
	if (cacheManager == null) {
	    URL url = getClass().getResource(xmlUrl);
	    cacheManager = CacheManager.create(url);
	}

    }

    @Override
    public void flush(String cacheName) {
	cache = cacheManager.getCache(cacheName);
	try {
	    cache.removeAll();
	} catch (IllegalStateException e) {
	    // TODO Auto-generated catch block
	    
	} catch (CacheException e) {
	    // TODO Auto-generated catch block
	    
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    
	}

    }

    @Override
    public Object getObject(String cacheName, Object key) {
	Object returnObject = null;

	cache = cacheManager.getCache(cacheName);

	if (cache != null) {
	    List keys = cache.getKeys();
	    if (keys.contains(key)) {
		Element obj = cache.get(key);
		if (obj != null) {
		    return obj.getValue();
		}
	    }
	}
	return returnObject;
    }

    @Override
    public void putObject(String cacheName, Object key, Object object) {
	cache = cacheManager.getCache(cacheName);
	if (cache != null && key != null && object != null) {
	    cache.put(new Element(key, object));
	}

    }

    @Override
    public Object removeObject(String cacheName, Object key) {
	cache = cacheManager.getCache(cacheName);
	if (cache != null) {
	    return cache.remove(key);
	}
	return null;
    }

}
