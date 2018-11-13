package com.barclays.ussd.session.handler;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.cache.dynacache.UserSessionDynaCacheHandler;
import com.barclays.ussd.utils.USSDConstants;

public class USSDUserSessionManager {
    private static final String SESSION_PREFIX = "SESSION";
    private static final String RESPONSE_PREFIX = "RESPONSE";
    private static final String UNDERSCORE = "_";
    private static final String CACHE_NAME = "";

    @Autowired
    private UserSessionDynaCacheHandler userSessionDynaCacheHandler;

    // private static final int MAP_INIT_SIZE = ConfigurationManager.getInt("sessionManagerMapInitialSize");
    // private static final int MAP_MAX_SIZE = ConfigurationManager.getInt("sessionManagerMapMaxSize");

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(USSDUserSessionManager.class);

    // private Map<String, HttpSession> sessionMap = new HashMap<String, HttpSession>(MAP_MAX_SIZE);
    // private Map<String, MenuItemDTO> responseMap = new UserSessionLRUMap<String, MenuItemDTO>(MAP_INIT_SIZE, MAP_MAX_SIZE);

    public void addUserSession(String msisdn, HttpSession session, String businessId, String countryCode) {
	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("Session Manager:- Persisting the user :" + msisdn);
	}
	// sessionMap.put(msisdn, session);
	userSessionDynaCacheHandler.putObject(CACHE_NAME, getSessionKey(msisdn, businessId, countryCode), getSessionMapObject(session));
    }

    private Map<String, Object> retrieveUserSession(String msisdn, String businessId, String countryCode) {
	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("Retrieving the user from the distributed map");
	}
	// return sessionMap.get(msisdn);
	Map<String, Object> sessionMap = (Map<String, Object>) userSessionDynaCacheHandler.getObject(CACHE_NAME, getSessionKey(msisdn, businessId,
		countryCode));
	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("sessionDTO=" + sessionMap);
	}
	return sessionMap == null ? null : sessionMap;
    }

    public boolean oldSessionExists(String msisdn, String businessId, String countryCode) {
	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("Session Manager:- checking if old session exists for the user :" + msisdn);
	    LOGGER.debug("Session Manager:- CACHE HANDLER :" + userSessionDynaCacheHandler);
	}
	// return sessionMap.containsKey(msisdn);
	return userSessionDynaCacheHandler.containsObject(CACHE_NAME, getSessionKey(msisdn, businessId, countryCode));
    }

    public void removeUserSession(String msisdn, String businessId, String countryCode) {
	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("Session Manager:- Removing the user :" + msisdn);
	}
	// sessionMap.remove(msisdn);
	userSessionDynaCacheHandler.removeObject(CACHE_NAME, getSessionKey(msisdn, businessId, countryCode));
    }

    private void removeOldResponse(String msisdn, String businessId, String countryCode) {
	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("Session Manager:- Removing the response for the user :" + msisdn);
	}
	// responseMap.remove(msisdn);
	userSessionDynaCacheHandler.removeObject(CACHE_NAME, getResponseKey(msisdn, businessId, countryCode));
    }

    public void sessionCleanUp(HttpSession session, String businessId, String countryCode) {
	String msisdnWithCountry = (String) session.getAttribute("msisdnWithCountry");

	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("Session Manager:- Invoking cleanup for the user :" + msisdnWithCountry);
	}
	removeUserSession(msisdnWithCountry, businessId, countryCode);
	removeOldResponse(msisdnWithCountry, businessId, countryCode);
    }

    public void updateUserSession(String msisdn, HttpSession newSession, String businessId, String countryCode) {
	Map<String, Object> oldSessionAttributeMap = retrieveUserSession(msisdn, businessId, countryCode);

	if (oldSessionAttributeMap != null) {
	    if (LOGGER.isDebugEnabled()) {
		LOGGER.debug("Session Manager:- Its a re-visit for the user :" + msisdn);
	    }
	    for (Map.Entry<String, Object> entry : oldSessionAttributeMap.entrySet()) {
		newSession.setAttribute(entry.getKey(), entry.getValue());
	    }
	    newSession.setAttribute(USSDConstants.OLD_SESSION_FLAG, true);
	} else {
	    if (LOGGER.isDebugEnabled()) {
		LOGGER.debug("Session Manager:- Its a fresh visit for the user :" + msisdn);
	    }
	    newSession.setAttribute(USSDConstants.OLD_SESSION_FLAG, false);
	}

	addUserSession(msisdn, newSession, businessId, countryCode);
    }

    public void setIntoResponseMap(String msisdn, MenuItemDTO menuItemDTO, String businessId, String countryCode) {
	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("Session Manager:- Setting the response into the distributed map for the user :" + msisdn);
	}
	// responseMap.put(msisdn, menuItemDTO);
	userSessionDynaCacheHandler.putObject(CACHE_NAME, getResponseKey(msisdn, businessId, countryCode), menuItemDTO);
    }

    public MenuItemDTO getOldResponse(String msisdn, String businessId, String countryCode) {
	MenuItemDTO responseDTO = (MenuItemDTO) userSessionDynaCacheHandler.getObject(CACHE_NAME, getResponseKey(msisdn, businessId, countryCode));
	return responseDTO;
    }

    public void setUserSessionDynaCacheHandler(UserSessionDynaCacheHandler userSessionDynaCacheHandler) {
	this.userSessionDynaCacheHandler = userSessionDynaCacheHandler;
    }

    private String getSessionKey(String msisdn, String businessId, String countryCode) {
	return (new StringBuffer(SESSION_PREFIX).append(UNDERSCORE).append(msisdn).append(UNDERSCORE).append(businessId).append(UNDERSCORE)
		.append(countryCode)).toString();
    }

    private String getResponseKey(String msisdn, String businessId, String countryCode) {
	return (new StringBuffer(RESPONSE_PREFIX).append(UNDERSCORE).append(msisdn).append(UNDERSCORE).append(businessId).append(UNDERSCORE)
		.append(countryCode)).toString();
    }

    private Map<String, Object> getSessionMapObject(HttpSession httpSession) {
	Map<String, Object> attributeMap = null;
	if (httpSession != null) {
	    attributeMap = new HashMap<String, Object>();
	    Enumeration attributeNames = httpSession.getAttributeNames();
	    while (attributeNames.hasMoreElements()) {
		String nextElement = (String) attributeNames.nextElement();
		attributeMap.put(nextElement, httpSession.getAttribute(nextElement));
	    }
	}
	return attributeMap;
    }
}
