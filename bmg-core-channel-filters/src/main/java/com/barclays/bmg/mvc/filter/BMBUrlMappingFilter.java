package com.barclays.bmg.mvc.filter;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.barclays.bmg.channel.resolver.config.ApplicationUrlResolverConfig;
import com.barclays.bmg.channel.resolver.config.FunctionUrlResolverConfig;

public class BMBUrlMappingFilter implements Filter {

    @Override
    public void destroy() {
	// TODO Auto-generated method stub

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

	boolean forwarded = false;
	HttpServletRequest httpRequest = (HttpServletRequest) request;
	HttpServletResponse httpResponse = (HttpServletResponse) response;
	HttpSession httpSession = httpRequest.getSession();
	WebApplicationContext webContext = WebApplicationContextUtils.getRequiredWebApplicationContext(httpSession.getServletContext());

	String serVersion = request.getParameter("serVer");
	Boolean releaseOne = (Boolean) httpSession.getAttribute("releaseOne");
	if (StringUtils.isEmpty(serVersion) && releaseOne) {
	    String requestUri = httpRequest.getRequestURI();

	    ApplicationUrlResolverConfig applicationUrlResolverConfig = (ApplicationUrlResolverConfig) webContext.getBean("1.0");
	    if (applicationUrlResolverConfig != null) {
		String fnId = getRequestId(requestUri);
		if (fnId != null) {
		    Map<String, FunctionUrlResolverConfig> functionMap = applicationUrlResolverConfig.getFunctionMap();
		    FunctionUrlResolverConfig functionConfig = functionMap.get(fnId);
		    if (functionConfig != null) {
			String fnURL = getFunctionURL(httpRequest, functionConfig);

			RequestDispatcher requestDispatcher = httpRequest.getRequestDispatcher(fnURL);
			forwarded = true;
			requestDispatcher.forward(httpRequest, httpResponse);

		    }

		}
	    }
	}

	if (!forwarded) {
	    chain.doFilter(httpRequest, httpResponse);
	}

    }

    private String getRequestId(String requestUri) {
	int lastSlash = requestUri.lastIndexOf("/");
	// int firstDot = requestUri.indexOf(".");
	if (lastSlash != -1) {
	    return requestUri.substring(lastSlash + 1);
	}
	return null;
    }

    @SuppressWarnings("unchecked")
    private String getFunctionURL(HttpServletRequest httpRequest, FunctionUrlResolverConfig functionConfig) {

	String requestURL = httpRequest.getRequestURI();
	requestURL = requestURL.replaceFirst(httpRequest.getContextPath(), "");
	requestURL = requestURL.replaceFirst("/" + functionConfig.getId(), "?opCde=" + functionConfig.getOpCde());
	Map<String, String> attrMap = functionConfig.getAttrMap();

	/* Below code commented and re-implemented according to find bugs defect */
	/*
	 * if (attrMap != null) { Map<String, String[]> parameterMap = httpRequest.getParameterMap(); Set<String> orgAttrSet = parameterMap.keySet();
	 * for (String orgAttr : orgAttrSet) { String[] paramVals = parameterMap.get(orgAttr); if (attrMap.get(orgAttr) != null) { requestURL =
	 * requestURL + "&" + attrMap.get(orgAttr) + "=" + paramVals[0]; ; }
	 * 
	 * } }
	 */

	if (attrMap != null) {
	    Map<String, String[]> parameterMap = httpRequest.getParameterMap();
	    Set<Entry<String, String[]>> orgAttrSet = parameterMap.entrySet();
	    for (Entry<String, String[]> orgAttr : orgAttrSet) {
		String[] paramVals = parameterMap.get(orgAttr.getKey());
		if (attrMap.get(orgAttr.getKey()) != null) {
		    requestURL = requestURL + "&" + attrMap.get(orgAttr.getKey()) + "=" + paramVals[0];
		    ;
		}

	    }
	}

	requestURL = requestURL + "&serVer=1.0";
	return requestURL;
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
	// TODO Auto-generated method stub

    }

}
