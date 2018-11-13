package com.barclays.ussd.sessionMgmt;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

// TODO: Auto-generated Javadoc
/**
 * The Class USSDSessionHandler.
 */
public class USSDSessionHandler {

    /** The Constant logger. */
    private static final Logger LOGGER = Logger.getLogger(USSDSessionHandler.class);

    /**
     * Remove session from the Map.
     * 
     * @param msisdn
     *            the msisdn
     * @param request
     *            the request
     */
    public void removeSession(HttpServletRequest request) {
	LOGGER.debug("Removing the Session ");
	HttpSession session = request.getSession();
	if (session != null)
	    session.invalidate();
    }

    /**
     * Checks if is session valid.
     * 
     * @param sessiontmp
     *            the session
     * @return true, if is session valid
     */
    public boolean isSessionValid(HttpServletRequest request) {
	LOGGER.debug("Validating the Session ");
	HttpSession session = request.getSession();
	if (session != null) {
	    try {
		return session.isNew();
	    } catch (IllegalStateException e) {
		if (LOGGER.isDebugEnabled()) {
		    LOGGER.debug("Session is expired.");
		}
		return false;
	    }
	} else {
	    if (LOGGER.isDebugEnabled()) {
		LOGGER.debug("Session is expired.");
	    }
	    return false;
	}
    }

    /**
     * Gets the session from the request.
     * 
     * @param msisdn
     *            the msisdn
     * @return the session request
     */
    public HttpSession getSessionRequest(HttpServletRequest request) {
	return request.getSession();
    }

    /**
     * Gets the session request.
     * 
     * @param msisdn
     *            the msisdn
     * @return the session request
     */
    public static HttpSession getSession(HttpServletRequest request) {
	return request.getSession();
    }

    public boolean isNewRequest(HttpServletRequest request) {
	LOGGER.debug("Checking the new session or old one.");
	return request.getSession().isNew();
    }
}
