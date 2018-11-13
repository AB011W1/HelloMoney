package com.barclays.bmg.logging;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import com.barclays.bmg.logging.annotation.LogMessageAround;

@Aspect
public class BMBLogAroundAdvice {

    private final Logger logger = Logger.getLogger(getClass());

    @Around("@annotation(logMessageAround)")
    public Object log(ProceedingJoinPoint proceedingJoinPoint, LogMessageAround logMessageAround) throws Throwable {

	String targetClass = proceedingJoinPoint.getTarget().getClass().getName();
	String methodName = proceedingJoinPoint.getSignature().getName();

	logger.debug("Enter to " + targetClass + "." + methodName);
	Object result = null;

	try {

	    result = proceedingJoinPoint.proceed();

	} catch (Throwable e) {
	    logger.error("Error in " + targetClass + "." + methodName);
	    throw e;
	}

	logger.debug("Exit from " + targetClass + "." + methodName);
	return result;
    }

}
