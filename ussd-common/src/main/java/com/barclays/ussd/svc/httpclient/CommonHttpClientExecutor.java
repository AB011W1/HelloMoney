package com.barclays.ussd.svc.httpclient;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.barclays.bmg.context.BMBContextHolder;
import com.barclays.bmg.context.Context;
import com.barclays.ussd.adapter.BMGAdapter;
import com.barclays.ussd.exception.USSDBlockingException;

/**
 * The Class CommonHttpClientExecuter.
 */
public class CommonHttpClientExecutor {

    @Autowired
    private BMGAdapter ussdBmgAdapter;

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(CommonHttpClientExecutor.class);

    /**
     * @param useLocalGateway
     * @param url
     * @param queryParamMap
     * @return
     * @throws USSDBlockingException
     */
    public String invokebmg(final boolean useLocalGateway, final String url, final Map<String, String> queryParamMap) throws USSDBlockingException {
	String response = "";

	try {
	    response = httpCall(url, queryParamMap);
	} catch (final Exception e) {
	    LOGGER.error(e.getMessage(), e);
	}
	return response;
    }

    /**
     * @param url
     * @param queryParamMap
     * @return
     * @throws InterruptedException
     * @throws USSDBlockingException
     */
    public String httpCall(final String url, final Map<String, String> queryParamMap) throws InterruptedException, USSDBlockingException {
	final StringBuilder temp = new StringBuilder("");
	temp.append(getHttpResponseFromLocalBMGServlet1(queryParamMap));
	return temp.toString();
    }

    private String getHttpResponseFromLocalBMGServlet1(Map<String, String> queryParamMap) {
	HttpServletRequest httpRequestThreadLocal = HttpReqResContextHolder.getRequest();
	HttpServletResponse httpResponseThreadLocal = HttpReqResContextHolder.getResponse();
	String outputResponse = null;

	try {
	    Context context = BMBContextHolder.getContext();
	    if (context != null) {
		context.setBusinessId(queryParamMap.get("bizId"));
		context.setOpCde(queryParamMap.get("opCde"));
		context.setServiceVersion(queryParamMap.get("serVer"));
		String mobilePhone = queryParamMap.get("mobileNumber");
		if (StringUtils.isEmpty(mobilePhone)) {
		    mobilePhone = queryParamMap.get("usrNam");
		}
		context.setMobilePhone(mobilePhone);
	    }
	    outputResponse = ussdBmgAdapter.handleBMGRequest(httpRequestThreadLocal, httpResponseThreadLocal, queryParamMap);
	} catch (Exception e) {
	    LOGGER.error("Error in CommonHTTPClient Executor - " + e.getMessage(), e);
	} finally {
	    // TODO check this
	    // HttpReqResContextHolder.resetHttpContents();
	}

	return outputResponse;
    }
}
