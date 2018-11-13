package com.barclays.bmg.dao.core.proxy.handler;

import javax.xml.namespace.QName;
import javax.xml.rpc.handler.GenericHandler;
import javax.xml.rpc.handler.MessageContext;
import javax.xml.rpc.handler.soap.SOAPMessageContext;
import javax.xml.soap.SOAPException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.StopWatch;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;

public class WebServiceLogTimeHandler extends GenericHandler {

    private static final String SOAP_PARSING_EXCEPTION = "SOAP parsing exception : ";
    private static final String SERVICE_ID = "ServiceID";
    private static final String SERVICE_RES_CODE = "ServiceResCode";
    private static final String PP_ERROR_CODE = "PPErrorCode";
    private static final String PP_ERROR_DESC = "PPErrorDesc";
    private static final String FAULT_CODE = "faultcode";
    private static final String FAULT_STRING = "faultstring";
    private static final String CONSUMER_UNIQUE_REF_NO = "ConsumerUniqueRefNo";
    private static final String ORGANIZATION_CATEGORY_CODE = "p782:OrganizationCategoryCode";
    private static final String MS = "ms.";
    private static final String SUCCESS_CODE = "00000";
    private static final String SUBMITTED_CODE = "00001";
    private static final String SUCCESS_RESPONSE = "Success";
    private static final String ERROR_RESPONSE = "Error";
    private static final String BEM_RESPONSE_TIME = "BEM_RESPONSE_TIME";
    private static final String BEM_RESPONSE_TIME_BREAKUP = "BEM_RESPONSE_TIME_BREAKUP";
    private final static Logger LOGGER = Logger.getLogger("BEM_TAT_LOGGER");
    private final static Logger WSLTLOGGER = Logger.getLogger(WebServiceLogTimeHandler.class);

    private StopWatch sw = null;
    private String consumerRefNo;
    private String categoryId;

    public boolean handleRequest(MessageContext context) {
	try {
	    sw = new StopWatch();
	    sw.start();
	} catch (IllegalStateException e) {
	    // isException = true;
	    sw.reset();
	    sw.start();

	} finally {

	    SOAPMessageContext smc = (SOAPMessageContext) context;
	    String soapMessage;
	    try {
		soapMessage = smc.getMessage().getSOAPPart().getEnvelope().toString();
		consumerRefNo = getElementText(soapMessage, CONSUMER_UNIQUE_REF_NO);
		categoryId = getElementText(soapMessage, ORGANIZATION_CATEGORY_CODE);
	    } catch (SOAPException e) {
		WSLTLOGGER.error("Error in handleRequest", e);
	    }
	}
	return true;
    }

    public boolean handleResponse(MessageContext context) {
	boolean isException = false;
	try {
	    sw.stop();
	} catch (IllegalStateException e) {
	    isException = true;
	    WSLTLOGGER.error(e);
	} finally {
	    SOAPMessageContext smc = (SOAPMessageContext) context;
	    String serviceId = "";
	    String resCde = "";
	    String response = "";
	    StringBuilder errorDesc = new StringBuilder();

	    try {
		String soapMessage = smc.getMessage().getSOAPPart().getEnvelope().toString();
		serviceId = getElementText(soapMessage, SERVICE_ID);
		resCde = getElementText(soapMessage, SERVICE_RES_CODE);

		if (SUCCESS_CODE.equals(resCde) || SUBMITTED_CODE.equals(resCde)) {
		    response = SUCCESS_RESPONSE;
		    errorDesc.append(";");
		    errorDesc.append(";");
		    errorDesc.append(";");

		} else if (StringUtils.isEmpty(resCde)) {
		    response = ERROR_RESPONSE;
		    errorDesc.append(";");
		    errorDesc.append(";");
		    errorDesc.append(";");
		    errorDesc.append(getElementText(soapMessage, FAULT_CODE));
		    errorDesc.append(" - ");
		    errorDesc.append(getElementText(soapMessage, FAULT_STRING));
		} else {
		    response = ERROR_RESPONSE;

		    String host = getElementText(soapMessage, "Source");
		    String bemError = getElementText(soapMessage, "ErrorCode");
		    errorDesc.append(bemError);
		    errorDesc.append(";");
		    errorDesc.append(host);
		    errorDesc.append(";");
		    errorDesc.append(getElementText(soapMessage, PP_ERROR_CODE));
		    errorDesc.append(";");
		    errorDesc.append(getElementText(soapMessage, PP_ERROR_DESC));
		}

	    } catch (SOAPException e) {
		LOGGER.warn(SOAP_PARSING_EXCEPTION + e);
	    }
	    if (!isException) {
		logServiceResponse(serviceId, response, errorDesc.toString());
	    }
	}
	return true;
    }

    private static String getElementText(String message, String element) {
	String text = "";
	try {
	    int startIndex = message.indexOf("<" + element + ">");
	    if (startIndex > -1) {
		int endIndex = message.indexOf("</" + element + ">", startIndex);
		text = message.substring(startIndex + element.length() + 2, endIndex);
	    }
	} catch (Exception e) {
	    // ignore
	}
	return text;
    }

    private void logServiceResponse(String serviceId, String response, String errorDesc) {
	long time = sw.getTime();

	String msg = new StringBuffer("||").append(serviceId).append(";").append(consumerRefNo).append(";").append(categoryId).append(";").append(
		response).append(";").append(time).append(";").append(errorDesc).toString();

	/*
	 * String msg1 = new StringBuffer(serviceId).append(",").append(response).append(",").append(time).append(MS).append(errorDesc).append(",")
	 * .append(BEM_URN).append(",").append(CATEGORY_ID).toString();
	 */
	String msg1 = new StringBuffer(serviceId).append(",").append(response).append(",").append(time).append(MS).toString();
	Long bemTotalTime = (Long) MDC.get(BEM_RESPONSE_TIME);
	MDC.put(BEM_RESPONSE_TIME, Long.valueOf(bemTotalTime.longValue() + time));

	String breakup = (String) MDC.get(BEM_RESPONSE_TIME_BREAKUP);
	MDC.put(BEM_RESPONSE_TIME_BREAKUP, breakup + msg);

	if (sw.getTime() < 5000) {
	    if (LOGGER.isDebugEnabled()) {
		LOGGER.debug(msg1);
	    }
	} else {
	    LOGGER.warn(msg1);
	}
    }

    public boolean handleFault(MessageContext context) {
	boolean isException = false;
	try {
	    sw.stop();
	} catch (IllegalStateException e) {
	    isException = true;
	    WSLTLOGGER.error(e);
	} finally {
	    SOAPMessageContext smc = (SOAPMessageContext) context;
	    String serviceId = "";
	    String resCde = "";
	    String response = "";
	    StringBuilder errorDesc = new StringBuilder();
	    String ppErrorCode = "";
	    String ppErrorDesc = "";

	    try {
		String soapMessage = smc.getMessage().getSOAPPart().getEnvelope().toString();
		serviceId = getElementText(soapMessage, SERVICE_ID);
		resCde = getElementText(soapMessage, SERVICE_RES_CODE);

		if (StringUtils.isEmpty(resCde)) {
		    response = ERROR_RESPONSE;
		    errorDesc.append(";");
		    errorDesc.append(";");
		    errorDesc.append(";");
		    errorDesc.append(getElementText(soapMessage, FAULT_CODE));
		    errorDesc.append(" - ");
		    errorDesc.append("BEM TIMEOUT");
		    // errorDesc.append(getElementText(soapMessage, FAULT_STRING));
		}

	    } catch (SOAPException e) {
		LOGGER.error(SOAP_PARSING_EXCEPTION + e);
	    }
	    if (!isException) {
		logServiceResponse(serviceId, response, errorDesc.toString());
	    }
	}
	return true;
    }

    public QName[] getHeaders() {
	QName[] arr = {};
	return arr;
    }
}