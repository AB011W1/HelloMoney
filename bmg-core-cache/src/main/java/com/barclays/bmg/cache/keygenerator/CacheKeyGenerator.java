package com.barclays.bmg.cache.keygenerator;

import java.io.Serializable;

import org.aopalliance.intercept.MethodInvocation;

/**
 * <p>
 * Generates a unique key based on the description of an invocation to an intercepted method.
 * </p>
 * 
 * @author Elicer Zheng
 */
public interface CacheKeyGenerator {

    /**
     * Generates the key for a cache entry.
     * 
     * @param methodInvocation
     *            the description of an invocation to the intercepted method.
     * @return the created key.
     */
    Serializable generateKey(MethodInvocation methodInvocation);
}
