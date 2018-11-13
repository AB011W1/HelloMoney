package com.barclays.ussd.auth.USSDresquest;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.barclays.ussd.auth.bean.USSDRequest;
import com.barclays.ussd.bean.MsisdnDTO;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.UssdMenuBuilder;
import com.barclays.ussd.xmlrequest.USSDXMLRequest;
import com.barclays.ussd.xmlrequest.USSDXMLRequestBody;
import com.barclays.ussd.xmlrequest.USSDXMLRequestHeader;

public class USSDBaseRequestMapper {

    /** Variable for the extra parameter */
    /*
     * private static final String REQ_EXTRA = "EXTRA";
     *
     * private static final String REQ_CURRENTLEVEL = "CURRENTLEVEL";
     *
     * private static final String REQ_TEMPLEVEL = "TEMPLEVEL";
     *
     * private static final String REQ_INPUT = "INPUT";
     *//** Mobile number */
    /*
     * private static final String REQ_MSISDN = "MSISDN";
     *//** Business Id */
    /*
     * private static final String REQ_BUSINESS = "BUSINESS";
     */

    @Autowired
    private UssdMenuBuilder ussdMenuBuilder;

    /**
     * It handle the ussd input request perameters if businessId and MSISDN number is empty or getting the null value then it throws USSDBlocking
     * Exception
     *
     * @param request
     * @return
     * @throws USSDBlockingException
     */
    public USSDRequest getRequestBean(HttpServletRequest request) throws USSDBlockingException {
	USSDRequest ussdRequest = new USSDRequest();
	USSDXMLRequest reqObject = (USSDXMLRequest) request.getAttribute("reqObject");
	USSDXMLRequestHeader requestHeader = reqObject.getRequestHeader();
	String businessID = requestHeader.getBusinessId();
	String msisdn = requestHeader.getMsisdn();
	USSDXMLRequestBody requestBody = reqObject.getRequestBody();
	String currentInput = requestBody.getUserInput();
	String extra = requestBody.getExtra();

	// String msisdn = request.getParameter(REQ_MSISDN);
	// String businessId = request.getParameter(REQ_BUSINESS);
	String countryCode = null;
	boolean isBusinessNotValid = null == businessID || StringUtils.isEmpty(businessID);
	boolean isMSISDNNotValid = null == msisdn || StringUtils.isEmpty(msisdn);
	if (isMSISDNNotValid || isBusinessNotValid) {
	    throw new USSDBlockingException(USSDExceptions.USSD_DOWN.getUssdErrorCode());
	} else {
	    ussdRequest.setBusinessId(businessID);
	    countryCode = USSDUtils.getCountryCode(ussdRequest);

	}
	MsisdnDTO msisdnDTO = ussdMenuBuilder.getPhoneNoLength(countryCode, businessID);
	ussdRequest.setMsisdnWithCountry(msisdn);
	int mobileLength = msisdnDTO.getSnlen();
	if (msisdn.length() >= mobileLength) {
	    ussdRequest.setMsisdn(msisdn.substring(msisdn.length() - msisdnDTO.getSnlen()));
	} else {
	    throw new USSDBlockingException(USSDExceptions.USSD_INVALID_MOBILE.getUssdErrorCode(), USSDExceptions.USSD_INVALID_MOBILE
		    .getUssdErrorCode());
	}
	ussdRequest.setInput(currentInput);
	// ussdRequest.setCurrentLevel(request.getParameter(REQ_TEMPLEVEL));
	// ussdRequest.setLevel(request.getParameter(REQ_CURRENTLEVEL));
	String amount=requestBody.getAmount();
	ussdRequest.setExtra(extra);
	ussdRequest.setAmount(amount);
	/*
	 * ussdRequest.setInput(request.getParameter(REQ_INPUT)); ussdRequest.setCurrentLevel(request.getParameter(REQ_TEMPLEVEL));
	 * ussdRequest.setLevel(request.getParameter(REQ_CURRENTLEVEL)); ussdRequest.setExtra(request.getParameter(REQ_EXTRA));
	 */ussdRequest.setAggregator((String) request.getAttribute(USSDConstants.AGGREGATOR));
	return ussdRequest;
    }

}
