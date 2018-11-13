package com.barclays.bmg.cache.dynacache;

import java.io.IOException;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.barclays.bmg.cache.CacheHandler;
import com.ibm.websphere.cache.DistributedMap;
import com.ibm.websphere.cache.EntryInfo;

/**
 * TODO This is dynamic Cache Handler
 * 
 * Elicer Zheng
 */
public class DynaCacheHandler implements CacheHandler {

    private String cacheJndiName;
    private DistributedMap dynamicCache;
    private static final String DYNAMIC_CACHE_CONFIG_FILE = "/dynamicCache.properties";
    private static final int DEFAULT_TIME_OUT_SECONDS = 60;
    private static final int DEFAULT_INACTIVITY_SECONDS = 50;
    private static final int DEFAULT_ITEM_PRIORITY = 1;
    private static final String TIME_OUT_KEY = "ssc.dynamiccache.timeouttime";
    private static final String INACTIVITY_KEY = "ssc.dynamiccache.inactivitytime";
    private static final String PRIORITY_KEY = "ssc.dynamiccache.priority";

    private Properties property;

    public DynaCacheHandler(String cacheJndiName) throws Exception {
	InitialContext context;
	try {
	    context = new InitialContext();
	    dynamicCache = (DistributedMap) context.lookup(cacheJndiName);
	    property = new Properties();
	    property.load(getClass().getResource(DYNAMIC_CACHE_CONFIG_FILE).openStream());
	} catch (NamingException e) {
	    
	    throw e;

	} catch (IOException e) {
	    
	    throw e;
	} catch (Exception e) {
	    
	    throw e;
	}

    }

    public DynaCacheHandler() {

    }

    /**
     * @return the cacheJndiName
     */
    public String getCacheJndiName() {
	return cacheJndiName;
    }

    /**
     * @param cacheJndiName
     *            the cacheJndiName to set
     */
    public void setCacheJndiName(String cacheJndiName) {
	this.cacheJndiName = cacheJndiName;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.barclays.mcfe.ssc.tools.cache.CacheHandler#flush(java.lang.String)
     */

    public void flush(String cacheName) {
	// TODO Auto-generated method stub
	dynamicCache.clear();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.barclays.mcfe.ssc.tools.cache.CacheHandler#getObject(java.lang.String, java.lang.Object)
     */

    public Object getObject(String cacheName, Object key) {
	// TODO Auto-generated method stub
	Object returnObject = null;
	if (dynamicCache.containsKey(key)) {
	    return dynamicCache.get(key);
	} else {
	    return returnObject;
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.barclays.mcfe.ssc.tools.cache.CacheHandler#putObject(java.lang.String, java.lang.Object, java.lang.Object)
     */

    public void putObject(String cacheName, Object key, Object object) {
	// TODO Auto-generated method stub
	int priority = DEFAULT_ITEM_PRIORITY;
	int timeOutTime = DEFAULT_TIME_OUT_SECONDS;
	int inActivityTime = DEFAULT_INACTIVITY_SECONDS;
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

	// dynamicCache.put(key, object, priority, timeOutTime, inActivityTime, null);
	dynamicCache.put(key, object, priority, timeOutTime, inActivityTime, EntryInfo.SHARED_PUSH_PULL, null);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.barclays.mcfe.ssc.tools.cache.CacheHandler#removeObject(java.lang.String, java.lang.Object)
     */

    public Object removeObject(String cacheName, Object key) {
	// TODO Auto-generated method stub
	return dynamicCache.remove(key);
    }

    // public static void main(String[] args) {
    // Properties property1 = new Properties();
    // try {
    // property1.load(new DynamicCacheHandler().getClass().getResource(DYNAMIC_CACHE_CONFIG_FILE).openStream());
    // int priority = DEFAULT_ITEM_PRIORITY;
    // int timeOutTime = DEFAULT_TIME_OUT_SECONDS;
    // int inActivityTime = DEFAULT_INACTIVITY_SECONDS;
    // if(property1 != null) {
    // if(property1.get(PRIORITY_KEY) != null) {
    // priority = Integer.parseInt(String.valueOf(property1.get(PRIORITY_KEY)));
    // }
    // if(property1.get(TIME_OUT_KEY)!= null) {
    // timeOutTime = (Integer)property1.get(TIME_OUT_KEY);
    // }
    // if(property1.get(INACTIVITY_KEY) != null) {
    // inActivityTime = (Integer)property1.get(INACTIVITY_KEY);
    // }
    // }
    // } catch (FileNotFoundException e) {
    // // TODO Auto-generated catch block
    // 
    // } catch (IOException e) {
    // // TODO Auto-generated catch block
    // 
    // }

    // }

}
