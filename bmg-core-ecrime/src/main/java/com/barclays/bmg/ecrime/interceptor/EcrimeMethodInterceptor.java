package com.barclays.bmg.ecrime.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.barclays.bmg.ecrime.EcrimeFlowManager;
import com.barclays.bmg.utils.SpringBeansUtils;

public class EcrimeMethodInterceptor implements MethodInterceptor, ApplicationContextAware {

    ApplicationContext applicationContext;

    private static final Logger logger = Logger.getLogger(EcrimeMethodInterceptor.class);

    /**
     * Method interceptor to log ecrime response
     * 
     * @see org.aopalliance.intercept.MethodInterceptor#invoke(org.aopalliance.intercept.MethodInvocation)
     */
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {

	Object result = invocation.proceed();

	logger.debug("Enter into EcrimeMethodInterceptor");

	EcrimeFlowManager flowManager = SpringBeansUtils.getBeanForType(applicationContext, EcrimeFlowManager.class);
	if (flowManager != null) {
	    flowManager.executeResponse(result);
	}

	logger.debug("Exit from EcrimeMethodInterceptor");
	return result;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
	this.applicationContext = applicationContext;

    }

}
