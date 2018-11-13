package com.barclays.bmg.mvc.interceptor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.support.BeanDefinitionValidationException;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.barclays.bmg.constants.ActivityIdConstantBean;
import com.barclays.bmg.constants.BMGConstants;
import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.context.BMBContextHolder;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dto.SystemParameterDTO;
import com.barclays.bmg.exception.JailBrokenException;
import com.barclays.bmg.service.SystemParameterService;
import com.barclays.bmg.service.request.SystemParameterServiceRequest;
import com.barclays.bmg.service.response.SystemParameterServiceResponse;

public class ClientApplicationValidationInterceptor extends HandlerInterceptorAdapter implements InitializingBean, MessageSourceAware {

    SystemParameterService systemParameterService;
    // private static final String DELIMITER = null;
    private List<AllowedAttributesSet> allowedList;

    private List<ApplicationAttributeMatcher> allowedPatternMatcherList;

    private MessageSource messageSource;

    // private SSCMessageResolver messageResolver;
    // private BMGSystemUtility bmgSystemUtility;

    // public BMGSystemUtility getBmgSystemUtility() {
    // return bmgSystemUtility;
    // }
    //
    // public void setBmgSystemUtility(BMGSystemUtility bmgSystemUtility) {
    // this.bmgSystemUtility = bmgSystemUtility;
    // }

    public List<ApplicationAttributeMatcher> getAllowedPatternMatcherList() {
	return allowedPatternMatcherList;
    }

    public void setAllowedPatternMatcherList(List<ApplicationAttributeMatcher> allowedPatternMatcherList) {
	this.allowedPatternMatcherList = allowedPatternMatcherList;
    }

    // public SSCMessageResolver getMessageResolver() {
    // return messageResolver;
    // }
    //
    // public void setMessageResolver(SSCMessageResolver messageResolver) {
    // this.messageResolver = messageResolver;
    // }

    public MessageSource getMessageSource() {
	return messageSource;
    }

    public List<AllowedAttributesSet> getAllowedList() {
	return allowedList;
    }

    @SuppressWarnings("unchecked")
    public void setAllowedList(List allowedList) {
	this.allowedList = allowedList;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

	String applicationVersion = request.getParameter(BMGConstants.APPLICATION_VERSION);
	String deviceId = request.getParameter(BMGConstants.DEVICE_ID);
	String deviceOsName = request.getParameter(BMGConstants.DEVICE_OS_NAME);
	String deviceOsVesrion = request.getParameter(BMGConstants.DEVICE_OS_VESRION);
	String deviceModelName = request.getParameter(BMGConstants.DEVICE_MODEL_NAME);

	String appIntegrityFlag = request.getParameter(BMGConstants.APPLICATION_INTEGRITY_FLAG);

	boolean appIntgtyFlagAllowed = false;

	if (StringUtils.isEmpty(appIntegrityFlag) || appIntegrityFlag.equals("N")) {
	    appIntgtyFlagAllowed = true;
	} else {

	    Context context = BMBContextHolder.getContext();

	    SystemParameterDTO systemParameterDTO = new SystemParameterDTO();
	    systemParameterDTO.setBusinessId(context.getBusinessId());
	    systemParameterDTO.setSystemId(context.getSystemId());
	    systemParameterDTO.setActivityId(ActivityIdConstantBean.COMMON);
	    systemParameterDTO.setParameterId(BMGConstants.JAIL_BREAK_ALLOWD_FLAG);

	    SystemParameterServiceRequest systemParameterServiceReq = new SystemParameterServiceRequest();
	    systemParameterServiceReq.setSystemParameterDTO(systemParameterDTO);

	    SystemParameterServiceResponse sysParamServResp = systemParameterService.getSystemParameter(systemParameterServiceReq);

	    /*
	     * SystemParameterBean systemParameterBean = new SystemParameterBean(); systemParameterBean.setActivityId(ActivityIdConstantBean.COMMON);
	     * systemParameterBean .setParameterId(BMGConstants.JAIL_BREAK_ALLOWD_FLAG); String appIntgFlag =
	     * getBmgSystemUtility().getSystemParameter( context, systemParameterBean);
	     */

	    String appIntgFlag = "";
	    if (sysParamServResp != null && sysParamServResp.isSuccess()) {
		appIntgFlag = sysParamServResp.getSystemParameterDTO().getParameterValue();
	    }

	    if (!StringUtils.isEmpty(appIntgFlag) && appIntgFlag.equals("Y")) {
		appIntgtyFlagAllowed = true;
	    } else {
		appIntgtyFlagAllowed = false;
	    }
	}

	boolean allowed = false;

	if (appIntgtyFlagAllowed) { // --- Allow if jail break flag is not set
	    // or 'N';

	    for (Iterator<ApplicationAttributeMatcher> iterator = this.allowedPatternMatcherList.iterator(); iterator.hasNext();) {
		ApplicationAttributeMatcher matcher = iterator.next();
		allowed = matcher.match(applicationVersion, deviceId, deviceOsName, deviceOsVesrion, deviceModelName);
		if (allowed) {
		    break;
		}
	    }

	    if (allowed) {

		// HttpSession httpSession = request.getSession(false);

		// Set Into Session
		// setIntoSession(httpSession, applicationVersion, deviceOsName,
		// deviceOsVesrion, deviceModelName, deviceId);

		// set the attibutes in thread local
		/*
		 * BMGContext.put(BMGConstants.APPLICATION_VERSION, (String) httpSession .getAttribute(BMGConstants.APPLICATION_VERSION));
		 * BMGContext.put(BMGConstants.DEVICE_OS_NAME, (String) httpSession .getAttribute(BMGConstants.DEVICE_OS_NAME));
		 * BMGContext.put(BMGConstants.DEVICE_OS_VESRION, (String) httpSession .getAttribute(BMGConstants.DEVICE_OS_VESRION));
		 * BMGContext.put(BMGConstants.DEVICE_MODEL_NAME, (String) httpSession .getAttribute(BMGConstants.DEVICE_MODEL_NAME));
		 * BMGContext.put(BMGConstants.DEVICE_ID, (String) httpSession .getAttribute(BMGConstants.DEVICE_ID));
		 */

		return allowed;
	    } else {
		// wee need to handle the response
		handleApplicationNotSupported(request, response, handler);
	    }
	} else {

	    /*
	     * BMGSSCContext bmgssContext = (BMGSSCContext) MCFELogContextHolder .getLogContext();
	     */
	    /*
	     * this.resolveMessage(bmgssContext, BMGConstants.APPLICATION_INTG_FLAG);
	     */

	    // throw new ApplicationIntegrityException(BMGConstants.JAIL_BREAK_MSG);
	    throw new JailBrokenException(ResponseCodeConstants.JAIL_BREAK_MSG_CODE);

	}

	return false;

    }

    // todo
    /*
     * private Context getContext(HttpServletRequest request) { // TODO Auto-generated method stub
     * 
     * HttpSession session = request.getSession(false);
     * 
     * Map<String, Object> userMap = (Map<String, Object>)session.getAttribute(SessionConstant.SESSION_USER);
     * 
     * return null; }
     */

    // public void resolveMessage(SSCContext sscContext, String messageKey) {
    // this.messageResolver.resolveMessage(sscContext, messageKey);
    // }
    /*
     * private void setIntoSession(HttpSession httpSession, String appVersn, String devOSName, String devOSVersn, String devOSModel, String devOSId) {
     * 
     * if (StringUtils.isNotBlank(appVersn)) { httpSession .setAttribute(BMGConstants.APPLICATION_VERSION, appVersn); } if
     * (StringUtils.isNotBlank(devOSName)) { httpSession.setAttribute(BMGConstants.DEVICE_OS_NAME, devOSName); } if
     * (StringUtils.isNotBlank(devOSVersn)) { httpSession .setAttribute(BMGConstants.DEVICE_OS_VESRION, devOSVersn); } if
     * (StringUtils.isNotBlank(devOSModel)) { httpSession .setAttribute(BMGConstants.DEVICE_MODEL_NAME, devOSModel); } if
     * (StringUtils.isNotBlank(appVersn)) { httpSession.setAttribute(BMGConstants.DEVICE_ID, devOSId); } }
     */

    private void handleApplicationNotSupported(HttpServletRequest request, HttpServletResponse response, Object handler) {

	// throw new ApplicationNotSupportedException(this.getMessageSource()
	// .getMessage(MessageCodes.APPLICATION_NOT_SUPPORTED_EXCEPTION,
	// null, RequestContextUtils.getLocale(request)));

	throw new JailBrokenException(ResponseCodeConstants.APPLICATION_NOT_SUPPORTED_CODE);

    }

    public void afterPropertiesSet() throws Exception {

	// Pattern p = Pattern.compile(DELIMITER, Pattern.LITERAL);

	if (this.getAllowedList() == null || this.getAllowedList().size() == 0) {
	    throw new BeanDefinitionValidationException("No allowed patterns specified, atleast one pattren is needed");
	}

	allowedPatternMatcherList = new ArrayList<ApplicationAttributeMatcher>(this.getAllowedList().size());
	for (Iterator<AllowedAttributesSet> iterator = this.getAllowedList().iterator(); iterator.hasNext();) {
	    AllowedAttributesSet allowedAttributesSet = iterator.next();

	    ApplicationAttributeMatcher matcher = new ApplicationAttributeMatcher(allowedAttributesSet);
	    allowedPatternMatcherList.add(matcher);

	}

    }

    private static class ApplicationAttributeMatcher {
	private final ApplicationAttributePatternMatcher applicationVersionMatcher;
	private final ApplicationAttributePatternMatcher deviceIdMatcher;
	private final ApplicationAttributePatternMatcher deviceOsNameMatcher;
	private final ApplicationAttributePatternMatcher deviceOSVersionMatcher;
	private final ApplicationAttributePatternMatcher deviceModelNameMatcher;

	public ApplicationAttributeMatcher(AllowedAttributesSet allowedAttributesSet) {

	    this.applicationVersionMatcher = new ApplicationAttributePatternMatcher(allowedAttributesSet.getApplicationVersionExpr());
	    this.deviceIdMatcher = new ApplicationAttributePatternMatcher(allowedAttributesSet.getDeviceIdExpr());
	    this.deviceOsNameMatcher = new ApplicationAttributePatternMatcher(allowedAttributesSet.getDeviceOsNameExpr());
	    this.deviceOSVersionMatcher = new ApplicationAttributePatternMatcher(allowedAttributesSet.getDeviceOSVersionExpr());
	    this.deviceModelNameMatcher = new ApplicationAttributePatternMatcher(allowedAttributesSet.getDeviceModelNameExpr());

	}

	public boolean match(String applicationVersionValue, String deviceIdValue, String deviceOsNameValue, String deviceOSVersionValue,
		String deviceModelNameValue) {
	    boolean matched = false;
	    matched = this.applicationVersionMatcher.match(applicationVersionValue) && this.deviceIdMatcher.match(deviceIdValue)
		    && this.deviceOsNameMatcher.match(deviceOsNameValue) && this.deviceOSVersionMatcher.match(deviceOSVersionValue)
		    && this.deviceModelNameMatcher.match(deviceModelNameValue);
	    return matched;
	}
    }

    public enum MATCH_TYPE {
    ALL, EXACT, REG_EX
    }

    private static class ApplicationAttributePatternMatcher {

	final private MATCH_TYPE matchType;
	final private String literal;
	final private static char CHARSTART = '\'';

	final private Pattern regExPattern;

	public ApplicationAttributePatternMatcher(String applicationAttributeExpr) {

	    if ("(*)".equals(applicationAttributeExpr)) {
		matchType = MATCH_TYPE.ALL;
		literal = null;
		regExPattern = null;
	    } else if (CHARSTART == applicationAttributeExpr.charAt(0)) {
		literal = applicationAttributeExpr.substring(1).trim();
		matchType = MATCH_TYPE.EXACT;
		regExPattern = null;
	    } else {
		literal = null;
		matchType = MATCH_TYPE.REG_EX;
		regExPattern = Pattern.compile(applicationAttributeExpr.trim());
	    }

	}

	public boolean match(String attributeValue) {
	    boolean matched = false;

	    switch (matchType) {
	    case ALL:
		matched = true;
		break;

	    case EXACT:
		matched = this.literal.equals(attributeValue);
		break;

	    case REG_EX:
		Matcher m = this.regExPattern.matcher(attributeValue);
		matched = m.matches();
		break;

	    }

	    return matched;
	}

    }

    public void setMessageSource(MessageSource messageSource) {
	this.messageSource = messageSource;

    }

    public void setSystemParameterService(SystemParameterService systemParameterService) {
	this.systemParameterService = systemParameterService;
    }

}
