package com.barclays.ussd.svc.httpclient;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HttpReqResContextHolder {

    public static final ThreadLocal<HttpServletRequest> requestThreadLocal = new ThreadLocal<HttpServletRequest>();
    public static final ThreadLocal<HttpServletResponse> responseThreadLocal = new ThreadLocal<HttpServletResponse>();

    public static void resetHttpContents() {
	requestThreadLocal.remove();
	responseThreadLocal.remove();
    }

    public static void setRequest(HttpServletRequest request) {
	requestThreadLocal.set(request);
    }

    public static HttpServletRequest getRequest() {
	return requestThreadLocal.get();
    }

    public static void setResponse(HttpServletResponse response) {
	responseThreadLocal.set(response);
    }

    public static HttpServletResponse getResponse() {
	return responseThreadLocal.get();
    }
}