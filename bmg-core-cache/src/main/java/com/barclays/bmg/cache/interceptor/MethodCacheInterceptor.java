/*
 * Copyright (c) 2009 Barclays Bank Plc, All Rights Reserved.
 *
 * This code is confidential to Barclays Bank Plc and shall not be disclosed
 * outside the Bank without the prior written permission of the Director of
 * CIO
 *
 * In the event that such disclosure is permitted the code shall not be copied
 * or distributed other than on a need-to-know basis and any recipients may be
 * required to sign a confidentiality undertaking in favour of Barclays Bank
 * Plc.
 */

package com.barclays.bmg.cache.interceptor;

import java.io.Serializable;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import com.barclays.bmg.cache.CacheHandler;
import com.barclays.bmg.cache.annotation.EvictServiceCache;
import com.barclays.bmg.cache.keygenerator.CacheKeyGenerator;

/**
 */
@Aspect
public class MethodCacheInterceptor implements MethodInterceptor {
    private CacheHandler cacheHandler;
    private CacheKeyGenerator keyGenerator;
    private String cacheName;

    /**
     * @return the cacheName
     */
    public String getCacheName() {
	return cacheName;
    }

    /**
     * @param cacheName
     *            the cacheName to set
     */
    public void setCacheName(String cacheName) {
	this.cacheName = cacheName;
    }

    /**
     * @return the keyGenerator
     */
    public CacheKeyGenerator getKeyGenerator() {
	return keyGenerator;
    }

    /**
     * @param keyGenerator
     *            the keyGenerator to set
     */
    public void setKeyGenerator(CacheKeyGenerator keyGenerator) {
	this.keyGenerator = keyGenerator;
    }

    /**
     * @return the cacheHandler
     */
    public CacheHandler getCacheHandler() {
	return cacheHandler;
    }

    /**
     * @param cacheHandler
     *            the cacheHandler to set
     */
    public void setCacheHandler(CacheHandler cacheHandler) {
	this.cacheHandler = cacheHandler;
    }

    /*
     * This the enter point of this interceptor Class.
     * 
     * @see org.aopalliance.intercept.MethodInterceptor#invoke(org.aopalliance.intercept .MethodInvocation)
     */
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
	// String targetName =
	// invocation.getThis().getClass().getInterfaces()[0].getName();
	// String methodName = invocation.getMethod().getName();
	Object[] arguments = invocation.getArguments();

	/*
	 * if (invocation.getThis().getClass().getInterfaces()[0].isAnnotationPresent (EntityCache.class)) { return getResult(arguments, invocation);
	 * } else {
	 */
	/*
	 * if (invocation.getMethod().isAnnotationPresent(CacheServiceResponse.class )) {
	 */
	return getResult(arguments, invocation);
	/*
	 * } else { return invocation.proceed(); }
	 */
	/* } */

    }

    private Object getResult(Object[] arguments, MethodInvocation invocation) throws Throwable {
	Object result = null;

	if (arguments != null) {
	    Serializable cacheKey = keyGenerator.generateKey(invocation);
	    if (cacheHandler != null) {
		result = cacheHandler.getObject(cacheName, cacheKey);

		if (result == null) {
		    // Make sure that the same key can not be set twice
		    synchronized (this) {
			// Double Check the same key can not be set twice
			result = cacheHandler.getObject(cacheName, cacheKey);
			if (result == null) {
			    result = invocation.proceed();

			    cacheHandler.putObject(cacheName, cacheKey, result);
			}
		    }
		}
	    } else {
		result = invocation.proceed();
	    }
	}

	return result;
    }

    @Before("@annotation(evictServiceCache)")
    public void flush(EvictServiceCache evictServiceCache) {

	if (!StringUtils.isEmpty(cacheName)) {
	    cacheHandler.flush(cacheName);
	}
    }
}
