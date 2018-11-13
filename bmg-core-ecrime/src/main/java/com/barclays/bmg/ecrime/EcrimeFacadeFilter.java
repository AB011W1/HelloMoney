package com.barclays.bmg.ecrime;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 *This is the main process for Ecrime.
 * 
 * @author
 */
public class EcrimeFacadeFilter implements Filter {
    private final static Logger logger = Logger.getLogger(EcrimeFacadeFilter.class);

    // private SpringBeansFactory beansFactory;

    private EcrimeFlowManager flowManager;

    private EcrimeConfiguration ecrimeConfig;

    private List<String> ignorePattern = new ArrayList<String>();

    private static final String ENABLE_ECRIME_LOG_KEY = "enableEcrimeLog";

    public void destroy() {

    }

    public EcrimeFacadeFilter() {
	ignorePattern.add(".+skins/internet/default/.+");
	ignorePattern.add(".+/resources/ssc-spring/.+");
	ignorePattern.add(".+/resources/dojo/.+");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
	if (isEcrimeLogEnabled()) {

	    HttpServletRequest httpRequest = (HttpServletRequest) request;
	    HttpServletResponse httpResponse = (HttpServletResponse) response;

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
		    logger.debug("chain doFilter");
		}
		chain.doFilter(request, response);

		// execute Request
		if (logger.isDebugEnabled()) {
		    logger.debug("execute Request");
		}
		flowManager.exeucteRequest(httpRequest, httpResponse);

		// clear Thread Local Context
		if (logger.isDebugEnabled()) {
		    logger.debug("clear Thread Local Context");
		}
		flowManager.executeTask();
		flowManager.clean();

	    } else {
		chain.doFilter(request, response);
	    }
	} else {
	    logger.debug("Disabled Ecrime for this business");
	    chain.doFilter(request, response);
	}

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
