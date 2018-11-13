package com.barclays.bmg.mvc.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.DelegatingFilterProxy;

public class BMBDelegatingFilterProxy extends DelegatingFilterProxy {

    private List<String> opCodes;
    private String skipOpCodes = "skipOpCodes";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws ServletException, IOException {
	// TODO Auto-generated method stub

	HttpServletRequest httpRequest = (HttpServletRequest) request;
	// HttpServletResponse httpResponse = (HttpServletResponse)response;
	HttpSession httpSession = httpRequest.getSession();

	WebApplicationContext webContext = WebApplicationContextUtils.getRequiredWebApplicationContext(httpSession.getServletContext());

	opCodes = (List<String>) webContext.getBean(skipOpCodes);

	String opCde = request.getParameter("opCde");
	if (opCde == null) {
	    opCde = (String) request.getAttribute("opCde");
	}
	boolean skip = false;
	if (!opCodes.isEmpty()) {
	    for (String opCode : opCodes) {
		if (opCode.equals(opCde)) {
		    skip = true;
		}
	    }
	}
	if (!skip) {
	    super.doFilter(request, response, filterChain);
	} else {
	    filterChain.doFilter(request, response);
	}
	/*
	 * if(!"OP0100".equals(opCde) && !"OP0101".equals(opCde) && !"OP0102".equals(opCde) && !"OP0103".equals(opCde) && !"OP0105".equals(opCde) &&
	 * !"authenticationFailure".equals(opCde) && !"OP0111".equals(opCde) && !"OP0800".equals(opCde) && !"OP9901".equals(opCde)){
	 * 
	 * super.doFilter(request, response, filterChain);
	 * 
	 * }else{ filterChain.doFilter(request, response); }
	 */
    }

    public List<String> getOpCodes() {
	return opCodes;
    }

    public void setOpCodes(List<String> opCodes) {
	this.opCodes = opCodes;
    }

}
