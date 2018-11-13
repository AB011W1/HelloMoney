/**
 *
 */
package com.barclays.bmg.cache.keygenerator.hashcode;

import java.io.Serializable;
import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInvocation;

import com.barclays.bmg.cache.keygenerator.CacheKeyGenerator;

/* *************************** Revision History *********************************
 * Version        Author          Date                     Description
 * 0.1            ELicer Zheng        3 Nov 2009                  Initial version
 *
 *
 ********************************************************************************/

/**
 * <p>
 * Generates the key for a cache entry using the hashCode of the intercepted method and its arguments.
 * </p>
 * 
 * @author Elicer Zheng
 */
public class HashCodeCacheKeyGenerator implements CacheKeyGenerator {

    /**
     * Flag that indicates if this generator should generate the hash code of the arguments passed to the method to apply caching to. If fasel this
     * generator uses the default hash code of the arguments.
     */
    private boolean generateArgumentHashCode;

    /**
     * @return the generateArgumentHashCode
     */
    public boolean isGenerateArgumentHashCode() {
	return generateArgumentHashCode;
    }

    /**
     * Construct a <code>HashCodeCacheKeyGenerator</code>.
     */
    public HashCodeCacheKeyGenerator() {
	super();
    }

    /**
     * 
     * 
     * @param generateArgumentHashCode
     *            the new value for the flag that indicates if this generator should generate the hash code of the arguments passed to the method to
     *            apply caching to. If false, this generator uses the default hash code of the arguments.
     */
    public HashCodeCacheKeyGenerator(boolean generateArgumentHashCode) {
	this();
	setGenerateArgumentHashCode(generateArgumentHashCode);
    }

    /**
     * @see CacheKeyGenerator#generateKey(MethodInvocation)
     */
    public final Serializable generateKey(MethodInvocation methodInvocation) {
	HashCodeCalculator hashCodeCalculator = new HashCodeCalculator();

	Method method = methodInvocation.getMethod();
	hashCodeCalculator.append(System.identityHashCode(method));

	Object[] methodArguments = methodInvocation.getArguments();
	if (methodArguments != null) {
	    int methodArgumentCount = methodArguments.length;

	    for (int i = 0; i < methodArgumentCount; i++) {
		Object methodArgument = methodArguments[i];
		if ("Context".equals(methodArgument.getClass().getSimpleName()))
		    continue;
		int hash = 0;

		if (generateArgumentHashCode) {
		    hash = ReflectionsUtility.reflectionHashCode(methodArgument);
		} else {
		    hash = ObjectsUtility.nullSafeHashCode(methodArgument);
		}

		hashCodeCalculator.append(hash);
	    }
	}

	long checkSum = hashCodeCalculator.getCheckSum();
	int hashCode = hashCodeCalculator.getHashCode();

	Serializable cacheKey = new HashCodeCacheKey(checkSum, hashCode);
	return cacheKey;
    }

    /**
     * Sets the flag that indicates if this generator should generate the hash code of the arguments passed to the method to apply caching to. If
     * fasle, this generator uses the default hash code of the arguments.
     * 
     * @param newGenerateArgumentHashCode
     *            the new value of the flag
     */
    public final void setGenerateArgumentHashCode(boolean newGenerateArgumentHashCode) {
	generateArgumentHashCode = newGenerateArgumentHashCode;
    }

}