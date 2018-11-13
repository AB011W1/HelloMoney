/**
 * CacheHandler.java
 */
package com.barclays.ussd.cache;

/**
 * The Interface CacheHandler.
 * 
 * @author BTCI
 */
public interface CacheHandler {

    /**
     * Flush a cache model.
     * 
     * @param cacheName
     *            the cache name
     */
    public void flush(String cacheName);

    /**
     * Get an object from a cache model.
     * 
     * @param cacheName
     *            the cache name
     * @param key
     *            - the key to the object.
     * @return the object if in the cache, or null(?).
     */
    public Object getObject(String cacheName, Object key);

    /**
     * Put an object into a cache model.
     * 
     * @param cacheName
     *            the cache name
     * @param key
     *            - the key to the object.
     * @param object
     *            - the object to add.
     */
    public void putObject(String cacheName, Object key, Object object);

    /**
     * Remove an object from a cache model.
     * 
     * @param cacheName
     *            the cache name
     * @param key
     *            - the key to the object.
     * @return the removed object(?).
     */
    public Object removeObject(String cacheName, Object key);

}
