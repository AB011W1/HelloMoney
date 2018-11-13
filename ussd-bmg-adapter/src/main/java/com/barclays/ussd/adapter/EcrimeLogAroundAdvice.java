package com.barclays.ussd.adapter;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import com.barclays.bmg.ecrime.EcrimeConfiguration;
import com.barclays.bmg.ecrime.EcrimeContext;
import com.barclays.bmg.ecrime.EcrimeFlowManager;

/**
 * @author E20041929
 *
 */
@Aspect
public class EcrimeLogAroundAdvice {

    private final static Logger logger = Logger.getLogger("com.barclays.bmg");
    // private SpringBeansFactory beansFactory;

    private EcrimeFlowManager flowManager;

    private EcrimeConfiguration ecrimeConfig;

    private List<String> ignorePattern = new ArrayList<String>();

    private static final String ENABLE_ECRIME_LOG_KEY = "enableEcrimeLog";

    public void initEcrimeLogAround() {

	ignorePattern.add(".+skins/internet/default/.+");
	ignorePattern.add(".+/resources/ssc-spring/.+");
	ignorePattern.add(".+/resources/dojo/.+");

    }

    @Around("execution(* com.barclays.ussd.adapter.BMGAdapter.handleBMGRequest(..))")
    public Object ecrimeFilterJob(ProceedingJoinPoint joinPoint) throws Throwable {
	Object resultValue = null;
	logger.info("ecrimeFilterJob is running");
	Object[] args = joinPoint.getArgs();
	if (args != null && args.length > 0) {
	    HttpServletRequest httpRequest = (HttpServletRequest) args[0];
	    HttpServletResponse httpResponse = (HttpServletResponse) args[1];
	    // Map<String, String> queryParamMap = (Map<String, String>) args[2];
	    logger.info(" Method arguments typecasted ");

	    //System.out.println("ecrimeFilterJob before is running");
	    // continue on the intercepted method
	    if (isEcrimeLogEnabled()) {

		String requestURL = httpRequest.getRequestURL().toString();

		if (!ignoreRequest(requestURL)) {

		    // Inititial Context and Set to the Thread Local
		    if (logger.isDebugEnabled()) {
			logger.debug("Inititial Context and Set to the Thread Local");
		    }
		    EcrimeContext ecrimeContext = new EcrimeContext();
		    flowManager.setEcrimeContext(ecrimeContext);
		    ecrimeContext.setRequestURL(requestURL);

		    // doFilter
		    if (logger.isDebugEnabled()) {
			logger.debug("proceeding further to  bmg controller's handle method call");
		    }
		    resultValue = joinPoint.proceed();

		    // execute Request
		    if (logger.isDebugEnabled()) {
			logger.debug("execute Request");
		    }
		    flowManager.exeucteRequest(httpRequest, httpResponse);

		    // clear Thread Local Context
		    if (logger.isDebugEnabled()) {
			logger.debug("clear Thread Local Context");
		    }
		    try {
			flowManager.executeTask();
		    } catch (Exception e) {
			
		    }

		    flowManager.clean();

		} else {
		    if (logger.isDebugEnabled()) {
			/* Changed logging for Veracode CRLF Injection issues */
			requestURL = requestURL.replace("\r", " ").replace("\n", " ");
			logger.debug("ignore this requestURL:" + requestURL);
			/* Ends log change */

		    }
		    resultValue = joinPoint.proceed();
		}
	    } else {
		logger.debug("Disabled Ecrime for this business");
		resultValue = joinPoint.proceed();
	    }
	}

	//System.out.println("******" + resultValue);
	return resultValue;

    }

    @Around("execution(* com.barclays.bmg.mvc.controller.accountdetails.RetrieveAllCustAcctController.handleRequestInternal1(..))")
    public Object ecrimeFilterJob202(ProceedingJoinPoint joinPoint) throws Throwable {
	return ecrimeFilterJob(joinPoint);
    }

    /**
     * TODO Method description, including pre/post conditions and invariants.
     *
     * @return
     */
    private boolean isEcrimeLogEnabled() {
	return ecrimeConfig.getBooleanParam(ENABLE_ECRIME_LOG_KEY, false);
    }

    /**
     * TODO Method description, including pre/post conditions and invariants.
     *
     * @param requestUrl
     * @return
     */
    private boolean ignoreRequest(String requestUrl) {
	boolean ignore = false;
	for (String pattern : ignorePattern) {
	    if (requestUrl.matches(pattern)) {
		ignore = true;
		break;
	    }
	}
	return ignore;
    }

    public void init(FilterConfig arg0) throws ServletException {

    }

    /**
     * @param ecrimeConfig
     *            the ecrimeConfig to set
     */
    public void setEcrimeConfig(EcrimeConfiguration ecrimeConfig) {
	this.ecrimeConfig = ecrimeConfig;
    }

    /**
     * @param flowManager
     *            the flowManager to set
     */
    public void setFlowManager(EcrimeFlowManager flowManager) {
	this.flowManager = flowManager;
    }

    /**
     * @return the flowManager
     */
    public EcrimeFlowManager getFlowManager() {
	return flowManager;
    }

}