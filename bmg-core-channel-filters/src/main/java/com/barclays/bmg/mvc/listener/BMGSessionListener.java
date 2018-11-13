package com.barclays.bmg.mvc.listener;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.web.context.WebApplicationContext;

import com.barclays.bmg.constants.SessionConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.service.sessionactivity.SessionActivityPersistenceService;
import com.barclays.bmg.service.sessionactivity.dto.SessionActivityDTO;
import com.barclays.bmg.utils.BMBCommonUtility;
import com.barclays.bmg.utils.DateTimeUtil;

public class BMGSessionListener implements HttpSessionListener {

    public void sessionCreated(HttpSessionEvent se) {
    }

    public void sessionDestroyed(HttpSessionEvent se) {
	HttpSession session = se.getSession();

	Context context = createContext(session);

	// write logout activity into activity table.
	if (context != null) {
	    WebApplicationContext webCtx = (WebApplicationContext) se.getSession().getServletContext().getAttribute(
		    WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);

	    SessionActivityPersistenceService sessionActivityPersistenceService = null;

	    if (webCtx != null) {
		sessionActivityPersistenceService = (SessionActivityPersistenceService) webCtx.getBean("sessionActivityPersistenceService");
	    }

	    SessionActivityDTO sessionActivity = new SessionActivityDTO();
	    sessionActivity.setBusinessId(context.getBusinessId());
	    sessionActivity.setSystemId(context.getSystemId());
	    sessionActivity.setUserId(context.getUserId());
	    sessionActivity.setSessionId(context.getSessionId());
	    sessionActivity.setStatus(SessionActivityDTO.SUCCESS);
	    sessionActivity.setTxnTyp(SessionActivityDTO.TXN_LOG_OUT);
	    sessionActivity.setTxnTime(DateTimeUtil.getCurrentBusinessCalendar(context).getTime());
	    if (sessionActivityPersistenceService != null && context.getUserId() != null) {
		sessionActivityPersistenceService.persistSessionActivity(sessionActivity);
	    }

	}

    }

    /**
     * Add parameters to context
     * 
     * @param request
     */
    public Context createContext(HttpSession session) {
	Context context = new Context();

	// context.setActivityId(ActivityConstant.LOGIN_ACTIVITY_ID);
	Map<String, Object> userMap = getUserMapFromSession(session);

	context.setBusinessId((String) userMap.get(SessionConstant.SESSION_BUSINESS_ID));
	context.setCountryCode((String) userMap.get(SessionConstant.SESSION_COUNTRY_CODE));
	context.setLanguageId((String) userMap.get(SessionConstant.SESSION_LANGUAGE_ID));
	context.setSystemId((String) userMap.get(SessionConstant.SESSION_SYSTEM_ID));
	context.setSessionId((String) userMap.get(SessionConstant.SESSION_ID));
	context.setUserId((String) userMap.get(SessionConstant.SESSION_USER_ID));
	context.setCustomerId((String) userMap.get(SessionConstant.SESSION_CUSTOMER_ID));
	context.setLoginId((String) userMap.get(SessionConstant.SESSION_LOGIN_ID));
	context.setFullName((String) userMap.get(SessionConstant.SESSION_FULL_NAME));
	context.setMobilePhone((String) userMap.get(SessionConstant.SESSION_MOBILE_PHONE));
	context.setLocalCurrency((String) userMap.get(SessionConstant.SESSION_LOCAL_CURRENCY));
	context.setLastLoginTime((String) userMap.get(SessionConstant.SESSION_LAST_LOGIN_TIME));
	context.setActivityRefNo(BMBCommonUtility.generateRefNo());
	context.setTimezoneOffset((String) userMap.get(SessionConstant.SESSION_TIMEZONE_OFFSET));
	return context;
    }

    /**
     * get user from session
     * 
     * @param request
     * @return
     */
    protected Map<String, Object> getUserMapFromSession(HttpSession session) {

	Map<String, Object> userMap = (Map<String, Object>) getSessionAttribute(session, SessionConstant.SESSION_USER);
	if (userMap == null) {
	    userMap = new HashMap<String, Object>();
	}
	return userMap;

    }

    /**
     * set session attribute
     * 
     * @param request
     * @param key
     * @return
     */
    protected Object getSessionAttribute(HttpSession session, String key) {
	Object value = null;

	if (session != null) {
	    value = session.getAttribute(key);
	}
	return value;
    }

}
