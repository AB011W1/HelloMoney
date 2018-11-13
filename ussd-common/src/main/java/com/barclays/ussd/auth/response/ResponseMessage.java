package com.barclays.ussd.auth.response;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.barclays.ussd.auth.bean.USSDResponse;
import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.auth.bean.UserProfile;
import com.barclays.ussd.bean.NavigationOptionsDTO;
import com.barclays.ussd.bean.MenuItem;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.common.configuration.ConfigurationManager;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDSessionConstants;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.UssdResourceBundle;
import com.barclays.ussd.xmlrequest.USSDXMLRequest;
import com.barclays.ussd.xmlrequest.USSDXMLRequestHeader;
import com.barclays.ussd.xmlrequest.XMLRequestResponseParser;
import com.barclays.ussd.xmlresponse.USSDXMLResponse;
import com.barclays.ussd.xmlresponse.USSDXMLResponseBody;
import com.barclays.ussd.xmlresponse.USSDXMLResponseHeader;

// TODO: Auto-generated Javadoc
/**
 * The Class ResponseMessage.
 */
public class ResponseMessage {
    private static final String DEMO_MODE_LABEL = "demoMode";

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(ResponseMessage.class);

    /**
     * Response message from user profile.
     *
     * @param profile
     *            the profile
     * @return the string
     */
    public USSDXMLResponseBody responseMessageFromUserProfile(UserProfile profile) {
	LOGGER.debug("Entring into responseMessageFromUserProfile method of class ResponseMessage. Got the profile: " + profile);
	USSDResponse response = loadResponseFromUserProfile(profile, USSDConstants.STATUS_CONTINUE);
	USSDXMLResponseBody responseBody = new USSDXMLResponseBody();
	BeanUtils.copyProperties(response, responseBody);
	LOGGER.debug("Exit from responseMessageFromUserProfile method of class ResponseMessage.");
	return responseBody;
    }

    /**
     * Load response from user profile.
     *
     * @param profile
     *            the profile
     * @return the uSSD response
     */
    public USSDResponse loadResponseFromUserProfile(UserProfile profile, String status) {
	LOGGER.debug("Entering into loadResponseFromUserProfile method of the class ResponseMessage.");
	USSDResponse response = new USSDResponse();
	response.setExtra(null);
	response.setNextLevel(null);
	response.setServiceId(null);
	response.setStatus(status);
	response.setText(profile.getMsg());
	if (profile.getErrorMsg() != null) {
		//CR-77
		response.setText(profile.getErrorMsg());
		if(!profile.getCustomerFirstName().equalsIgnoreCase(" ")){
			response.setText(profile.getCustomerFirstName()+ ","+ profile.getErrorMsg());
		}

	}
	response.setValueOfSelection(null);
	LOGGER.debug("Exit from loadResponseFromUserProfile method of the class ResponseMessage.");
	return response;
    }

    /**
     * Create the Response Header for the XML response
     *
     * @param request
     * @param profile
     * @return
     */
    public USSDXMLResponseHeader createXmlResponseHeader(final HttpServletRequest request, String msisdnWithCountry) {
	LOGGER.debug("Entering into createXmlResponseHeader method");
	USSDXMLResponseHeader headerResponse = new USSDXMLResponseHeader();
	USSDXMLRequestHeader requestHeader = ((USSDXMLRequest) request.getAttribute(USSDSessionConstants.REQ_OBJECT)).getRequestHeader();
	headerResponse.setMsisdn(msisdnWithCountry);
	headerResponse.setSession(requestHeader.getSessionId());
	headerResponse.setNonceValue(requestHeader.getNonce());
	LOGGER.debug("Getting the ResponseHeader from the response " + headerResponse);
	LOGGER.debug("Exit from createXmlResponseHeader method ");
	return headerResponse;
    }

    /**
     * If session is <b>invalid</b> or <b>logout</b> or <b>blocking</b> exception then we need to make status <b>end</b>.
     *
     * @param profile
     * @return
     */
    public USSDXMLResponseBody responseMsgUserProfileStatusEnd(UserProfile profile) {
	LOGGER.debug("Entering into ResponseMessage.responseMsgUserProfileStatusEnd() for the Status End.");
	USSDResponse response = loadResponseFromUserProfile(profile, USSDConstants.STATUS_END);
	USSDXMLResponseBody responseBody = new USSDXMLResponseBody();
	BeanUtils.copyProperties(response, responseBody);
	LOGGER.debug("Exit from ResponseMessage.responseMsgUserProfileStatusEnd() for the Status End.");
	return responseBody;
    }

    /**
     * Generate String format
     *
     * @param request
     * @param profile
     * @return
     * @throws IOException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws InvalidAlgorithmParameterException
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    public String generateXmlResponseString(final HttpServletRequest request, UserProfile profile, String msisdnWithCountry) {
	LOGGER.debug("Entering into ResponseMessage.generateXmlResponseString()");
	USSDXMLResponseBody responseBody = responseMessageFromUserProfile(profile);
	USSDXMLResponseHeader headerResponse = createXmlResponseHeader(request, msisdnWithCountry);
	LOGGER.debug("Exit from the ResponseMessage.generateXmlResponseString().");
	return getXmlResponseString(request, responseBody, headerResponse);
    }

    /**
     * Generate String format
     *
     * @param request
     * @param profile
     * @return
     * @throws IOException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws InvalidAlgorithmParameterException
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    public String generateXmlResponseStringEndStatus(final HttpServletRequest request, UserProfile profile, String msisdnWithCountry) {
	LOGGER.debug("Entering into ResponseMessage.generateXmlResponseStringEndStatus() with statuse continue.");
	USSDXMLResponseBody responseBody = responseMsgUserProfileStatusEnd(profile);
	USSDXMLResponseHeader headerResponse = createXmlResponseHeader(request, msisdnWithCountry);
	LOGGER.debug("Exit from ResponseMessage.generateXmlResponseStringEndStatus() with statuse continue.");
	return getXmlResponseString(request, responseBody, headerResponse);
    }

    /**
     * Call from the Controller
     *
     * @param request
     * @param message
     * @param msisdnWithCountry
     * @return
     */
    public String generateXmlRespBlockingException(final HttpServletRequest request, String message, String msisdnWithCountry) {
	LOGGER.debug("Entering into ResponseMessage.generateXmlRespBlockingException() for the block Exception.");
	LOGGER.debug("Get the message for the blocking Exception " + message + ", mobile number wih country code " + msisdnWithCountry);
	USSDXMLResponseBody responseBody = responseMessageForException(message, USSDConstants.STATUS_END);
	USSDXMLResponseHeader headerResponse = createXmlResponseHeader(request, msisdnWithCountry);
	LOGGER.debug("Exit from ResponseMessage.generateXmlRespBlockingException() for the block Exception.");
	return getXmlResponseString(request, responseBody, headerResponse);
    }

    public USSDXMLResponseBody responseMessageForException(String message, String status) {
	LOGGER.debug("Entering into ResponseMessage.responseMessageForException() for the Exception.");
	LOGGER.debug("Get the message for the Exception " + message + " with Status " + status);
	USSDResponse response = loadResponseForException(message, status);
	USSDXMLResponseBody responseBody = new USSDXMLResponseBody();
	BeanUtils.copyProperties(response, responseBody);
	LOGGER.debug("Exit from ResponseMessage.responseMessageForException() for the Exception.");
	return responseBody;
    }

    public USSDResponse loadResponseForException(String message, String status) {
	LOGGER.debug("intering into ResponseMessage.loadResponseForException() for the create response exception.");
	LOGGER.debug("Get the message " + message + "with Status " + status);
	USSDResponse response = new USSDResponse();
	response.setExtra(null);
	response.setNextLevel(null);
	response.setServiceId(null);
	response.setStatus(status);
	response.setText(message);
	response.setValueOfSelection(null);
	return response;
    }

    public String generateXmlRespNonBlockingException(final HttpServletRequest request, String message, String msisdnWithCountry) {
	LOGGER.debug("Entering into ResponseMessage.generateXmlRespNonBlockingException() for the non blocking Exception.");
	LOGGER.debug("Get the message for the non blocking Exception " + message + ", mobile number wih country code " + msisdnWithCountry);
	USSDXMLResponseBody responseBody = responseMessageForException(message, USSDConstants.STATUS_CONTINUE);
	USSDXMLResponseHeader headerResponse = createXmlResponseHeader(request, msisdnWithCountry);
	LOGGER.debug("exit from ResponseMessage.generateXmlRespNonBlockingException() for the non blocking Exception.");
	return getXmlResponseString(request, responseBody, headerResponse);
    }

    /**
     *
     * @param request
     * @param responseBody
     * @param headerResponse
     * @return
     */
    public String getXmlResponseString(final HttpServletRequest request, USSDXMLResponseBody responseBody, USSDXMLResponseHeader headerResponse) {
	LOGGER.debug("Entering into ResponseMessage.getXmlResponseString() to generate XML response String.");
	String result = null;
	XMLRequestResponseParser parser = new XMLRequestResponseParser();
	USSDXMLResponse xmlResponse = new USSDXMLResponse();
	xmlResponse.setBodyResponse(responseBody);
	xmlResponse.setHeaderResponse(headerResponse);
	request.setAttribute("XML_RESPONSE", xmlResponse);
	boolean enableXSDValidation = true;
	if ("FALSE".equalsIgnoreCase(ConfigurationManager.getString("enableXSDValidation"))) {
	    enableXSDValidation = false;
	}
	result = parser.getXMLResponseString(xmlResponse, enableXSDValidation);
	LOGGER.debug("Generatred XML response " + result);
	LOGGER.debug("Exit from ResponseMessage.getXmlResponseString() to generate XML response String.");
	return result;
    }

    /**
     * Response message from menu list.
     *
     * @param menuItemDTO
     *            the menu item dto
     * @param cntry
     * @param lang
     * @param ussdResourceBundle
     * @param ussdSessionMgmt
     * @param b
     * @return the string
     */
    public USSDXMLResponseBody responseMessageFromMenuList(MenuItemDTO menuItemDTO, UssdResourceBundle ussdResourceBundle, String lang, String cntry,
	    boolean demoModeFlag, NavigationOptionsDTO backHomeOptionDTO) {

	LOGGER.debug("Entering into ResponseMessage.responseMessageFromMenuList()");
	USSDResponse response = loadResponseFromMenuList(menuItemDTO, ussdResourceBundle, lang, cntry, demoModeFlag, backHomeOptionDTO);
	USSDXMLResponseBody responseBody = new USSDXMLResponseBody();
	BeanUtils.copyProperties(response, responseBody);
	LOGGER.debug("Exit from ResponseMessage.responseMessageFromMenuList()");
	return responseBody;
    }

    /**
     * Load response from menu list.
     *
     * @param menuItemDTO
     *            the menu item dto
     * @param cntry
     * @param lang
     * @param ussdResourceBundle
     * @param demoModeFlag
     * @param backHomeOptionDTO
     * @return the uSSD response
     */
    public USSDResponse loadResponseFromMenuList(MenuItemDTO menuItemDTO, UssdResourceBundle ussdResourceBundle, String lang, String cntry,
	    boolean demoModeFlag, NavigationOptionsDTO backHomeOptionDTO) {
	LOGGER.debug("Entering into ResponseMessage.loadResponseFromMenuList() generate from the Menu DTO object");
	USSDResponse response = new USSDResponse();
	StringBuffer buffer = new StringBuffer();
	boolean backOptionPresent = false;
	boolean homeOptionPresent = false;
	if (menuItemDTO != null) {
	    if (demoModeFlag) {
		buffer.append(ussdResourceBundle.getLabel(DEMO_MODE_LABEL, new Locale(lang, cntry)));
		buffer.append(USSDConstants.NEW_LINE);
	    }
	    if (StringUtils.isNotEmpty(menuItemDTO.getPageError())) {
		buffer.append(menuItemDTO.getPageError());
	    }
	    if (StringUtils.isNotEmpty(menuItemDTO.getPageHeader())) {
		buffer.append(menuItemDTO.getPageHeader());
	    }

	    if (menuItemDTO.getPageBody() != null && menuItemDTO.getPageBody().trim().length() != 0) {
		buffer.append(menuItemDTO.getPageBody());
	    }
	    if (null != menuItemDTO.getMenuItemList()) {
		for (MenuItem menuItem : menuItemDTO.getMenuItemList()) {
		    if (backHomeOptionDTO.getBackOption().equalsIgnoreCase(menuItem.getOptionId())) {
			backOptionPresent = true;
		    } else if (backHomeOptionDTO.getHomeOption().equalsIgnoreCase(menuItem.getOptionId())) {
			homeOptionPresent = true;
		    } else {
			buffer.append(USSDConstants.NEW_LINE);
			buffer.append(menuItem.getOptionId() + USSDConstants.DOT_SEPERATOR + menuItem.getLabelId());
		    }
		}
		USSDUtils.appendHomeAndBackOptionInMenu(buffer, backOptionPresent, homeOptionPresent, ussdResourceBundle, lang, cntry,
			backHomeOptionDTO);
	    }
	    if (menuItemDTO.getPageFooter() != null && menuItemDTO.getPageFooter().trim().length() != 0) {
		// buffer.append(USSDConstants.NEW_LINE);
		buffer.append(menuItemDTO.getPageFooter());
	    }

	    if (menuItemDTO.getScrollers() != null && menuItemDTO.getScrollers().trim().length() != 0) {
		// buffer.append(USSDConstants.NEW_LINE);
		buffer.append(menuItemDTO.getScrollers());
	    }

	    response.setExtra(null);
	    response.setText(buffer.toString());
	    response.setNextLevel(1);
	    response.setServiceId(null);
	    response.setStatus(USSDConstants.STATUS_CONTINUE);
	    response.setValueOfSelection(null);
	}
	LOGGER.debug("getting the Menu Response " + response);
	LOGGER.debug("Exit from ResponseMessage.loadResponseFromMenuList() generate from the Menu DTO object");
	return response;
    }

    /**
     * Response message from menu list.
     *
     * @param response
     *            the response
     * @return String This function returns response to Aggregator/MNO on receipt of unrecoverable error
     */
    public String responseMessageFromMenuList(String response, String status) {
	StringBuilder sb = new StringBuilder();
	// 1. NextLevel \n 2."response" \n 3. Text \n 4. ValueOfSelection \n 5. ServiceId \n 6.Status
	sb.append("null").append(USSDConstants.PIPE).append(response).append(USSDConstants.PIPE).append("null").append(USSDConstants.PIPE)
		.append("null").append(USSDConstants.PIPE).append(status).append(USSDConstants.PIPE).append("null");
	LOGGER.debug("Generating the Response from the menuDTO " + sb.toString());
	return sb.toString();
    }

}
