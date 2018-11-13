package com.barclays.bmg.cache;

/**
 * TODO This is a interface for CacheHandler defind the method used for Handle Cache
 * 
 * 
 */
public interface CacheHandler {
    /**
     * Flush a cache model.
     * 
     * @param cacheModel
     *            - the model to flush.
     */
    public void flush(String cacheName);

    /**
     * Get an object from a cache model.
     * 
     * @param cacheModel
     *            - the model.
     * @param key
     *            - the key to the object.
     * @return the object if in the cache, or null(?).
     */
    public Object getObject(String cacheName, Object key);

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
    public void putObject(String cacheName, Object key, Object object);

    /**
     * Remove an object from a cache model.
     * 
     * @param cacheModel
     *            - the model to remove the object from.
     * @param key
     *            - the key to the object.
     * @return the removed object(?).
     */
    public Object removeObject(String cacheName, Object key);

}
