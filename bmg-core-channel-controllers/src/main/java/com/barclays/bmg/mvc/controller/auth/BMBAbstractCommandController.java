package com.barclays.bmg.mvc.controller.auth;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractCommandController;

import com.barclays.bmg.constants.AccountConstants;
import com.barclays.bmg.constants.BMGConstants;
import com.barclays.bmg.constants.ErrorCodeConstant;
import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.constants.ResponseModelConstant;
import com.barclays.bmg.constants.SessionConstant;
import com.barclays.bmg.constants.ViewConstant;
import com.barclays.bmg.context.BMBContextHolder;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.dto.SystemParameterDTO;
import com.barclays.bmg.dto.TdAccountDTO;
import com.barclays.bmg.exception.BMBDataAccessException;
import com.barclays.bmg.exception.FeatureBlackoutException;
import com.barclays.bmg.exception.JailBrokenException;
import com.barclays.bmg.json.model.JSONResponseError;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadData;
import com.barclays.bmg.json.response.BMBPayloadHeader;
import com.barclays.bmg.json.response.BaseJSONResponseModel;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.service.MessageResourceService;
import com.barclays.bmg.service.SystemParameterService;
import com.barclays.bmg.service.featureblackout.FeatureBlackoutService;
import com.barclays.bmg.service.featureblackout.request.FeatureBlackoutServiceRequest;
import com.barclays.bmg.service.request.MessageResourceServiceRequest;
import com.barclays.bmg.service.request.SystemParameterServiceRequest;
import com.barclays.bmg.service.response.MessageResourceServiceResponse;
import com.barclays.bmg.service.response.SystemParameterServiceResponse;
import com.barclays.bmg.utils.BMBCommonUtility;

/**
 * BMB abstract command controller
 *
 * @author e20026338
 *
 */
public abstract class BMBAbstractCommandController extends AbstractCommandController implements MessageSourceAware {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(BMBAbstractCommandController.class);

    private static SecureRandom random;
    private final static String ALGORITHM = "SHA1PRNG";
    private static final String BMB = "BMB";
    private static final String BEM = "BEM";

    private MessageSource messageSource;
    private String securityCheckRedirectURL = "/login2/j_spring_security_check";
    MessageResourceService messageResourceService;
    SystemParameterService systemParameterService;
    FeatureBlackoutService featureBlackoutService;
    private String requestUri;
    private String serVerUri;

    public String getSecurityCheckRedirectURL() {
	return securityCheckRedirectURL;
    }

    /**
     * set session attribute
     *
     * @param request
     * @param key
     * @return
     */
    protected Object getSessionAttribute(HttpServletRequest request, String key) {
	Object value = null;
	HttpSession session = request.getSession();

	if (session != null) {
	    // System.out.println("httpSession.getId() = "+session.getId());
	    value = session.getAttribute(key);
	}
	return value;
    }

    /**
     * Entry point into controller. Handle http request.
     */
    @Override
    protected ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {

	ModelAndView modelAndView = null;

	/**
	 * Check check command validation errors
	 */
	if (errors.hasErrors()) {
	    LOGGER.error("BindException in BMBAbstractCommandController " + errors.getMessage());
	    return handleModelErrors(errors);
	}

	// checkIfSessionActive(request,response);
	/**
	 * Call specialized controller handler method
	 */
	// checkFeatureBlackout(command);
	BMBBaseResponseModel model = null;
	try {
	    model = handle1(request, response, command, errors);

	} catch (Exception e) {
	    LOGGER.error("Error in handle " + e.getMessage(), e);
	    return getModelAndView(ViewConstant.FAILURE_VIEW, e, request);
	}

	/*
	 * BMBPayload bmbPayload = (BMBPayload) model; if (ResponseIdConstants.SECOND_AUTH_SUCCESS .equals(bmbPayload.getPayHdr() .getResId()) &&
	 * ResponseCodeConstants.SUCCESS_RES_CODE.equals(bmbPayload .getPayHdr().getResCde())) { modelAndView = new ModelAndView(new RedirectView(
	 * getSecurityCheckRedirectURL(), true)); return modelAndView; }
	 */

	// FIXME: create an specifc exp type and only catch that
	// if (model.isSuccess()) {
	modelAndView = new ModelAndView(ViewConstant.SUCCESS_VIEW, ResponseModelConstant.RESPONSE_MODEL_SUCCESS, model);

	// } else {
	//
	// modelAndView = new ModelAndView(ViewConstant.FAILURE_VIEW,
	// ResponseModelConstant.RESPONSE_MODEL_FAILURE,
	// model);
	//
	// }

	return modelAndView;

    }

    protected void checkIfSessionActive(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	/*
	 * String opCde = request.getParameter("opCde");
	 *
	 * if(!"authenticationFailure".equals(opCde) && !"OP0800".equals(opCde)){
	 */
	HttpSession session = request.getSession(false);
	if (!StringUtils.isEmpty(serVerUri)) {
	    requestUri = requestUri + "&" + serVerUri;
	}
	if (session == null) {
	    request.getRequestDispatcher(requestUri).forward(request, response);
	} else if (session != null) {
	    String opCde = request.getParameter("opCde");
	    Map<String, Object> userMap = getUserMapFromSession(request);
	    if (userMap == null) {
		request.getRequestDispatcher(requestUri).forward(request, response);
	    } else if (userMap != null && !"OP0100".equals(opCde)) {
		String userId = (String) userMap.get(SessionConstant.SESSION_USER_ID);
		if (userId == null) {
		    request.getRequestDispatcher(requestUri).forward(request, response);
		}
	    }
	}
	/* } */
    }

    /**
     * Forward to session expired page
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void forwardToSessionExpired(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	if (!StringUtils.isEmpty(serVerUri)) {
	    requestUri = requestUri + "&" + serVerUri;
	}
	request.getRequestDispatcher(requestUri).forward(request, response);

    }

    private void checkFeatureBlackout(Object command) {
	FeatureBlackoutServiceRequest request = new FeatureBlackoutServiceRequest();
	Context context = new Context();
	context.setBusinessId(BMBContextHolder.getContext().getBusinessId());
	context.setActivityId(getActivityId(command));
	context.setSystemId(BMBContextHolder.getContext().getSystemId());
	request.setContext(context);
	boolean blackout = false;
	if (!StringUtils.isEmpty(getActivityId(command))) {
	    blackout = featureBlackoutService.checkFeatureBlackout(request);
	}

	if (blackout) {
	    throw new FeatureBlackoutException();
	}

    }

    abstract protected String getActivityId(Object command);

    abstract protected BMBBaseResponseModel handle1(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors)
	    throws Exception;

    /**
     * remove session attribute
     *
     * @param request
     * @param key
     */
    protected void removeSessionAttribute(HttpServletRequest request, String key) {
	HttpSession session = request.getSession(false);
	if (session != null) {
	    session.removeAttribute(key);
	}
    }

    /**
     * add session attribute
     *
     * @param request
     * @param key
     * @param value
     */
    protected void setSessionAttribute(HttpServletRequest request, String key, Object value) {
	HttpSession session = request.getSession(false);
	if (session != null) {
	    session.setAttribute(key, value);
	}

    }

    /**
     * get context from session
     *
     * @param request
     * @return
     */
    protected Context getContextFromSession(HttpServletRequest request) {

	return (Context) getSessionAttribute(request, "context");

    }

    /**
     * generate command error response model and view
     *
     * @param errors
     * @return
     */
    protected ModelAndView handleModelErrors(BindException errors) {

	List<ObjectError> errorsList = errors.getAllErrors();
	// StringBuilder builder = new StringBuilder();
	/*
	 * BaseJSONResponseModel baseResponse = null; for (ObjectError error : errorsList) {
	 *
	 * if (error != null) { String errorKey = error.getCode(); String message = messageSource.getMessage(errorKey, null, null); //TODO //
	 * baseResponse.getError().addMessage(message); }
	 *
	 * } //TODO //baseResponse.getError().setErrType(ErrorCodeConstant.ERROR_CODE_1002 ); //baseResponse.setSuccess(false);
	 *
	 * return new ModelAndView(ViewConstant.FAILURE_VIEW, ResponseModelConstant.RESPONSE_MODEL_FAILURE, baseResponse);
	 */

	Context context = BMBContextHolder.getContext();
	ResponseContext respContext = new ResponseContext();
	respContext.setContext(context);

	// MessageResourceServiceRequest msgResServReq = new
	// MessageResourceServiceRequest();
	// msgResServReq.setContext(context);
	// msgResServReq.setMessageKey(messageKey)
	//
	// messageResourceService.getMessageDescByKey(request );

	BMBPayload errorBMBResponse = new BMBPayload();
	BMBPayloadHeader bmbPayloadHeader = new BMBPayloadHeader();

	bmbPayloadHeader.setSerVer(ResponseIdConstants.RESPONSE_SERVICE_VERSION);

	BMBPayloadData baseResponse = null;
	String errorMsg = null;

	for (ObjectError error : errorsList) {

	    if (error != null) {
		String errorKey = error.getCode();

		if (errorKey != null && errorKey.contains("-")) {
		    String[] resIdAnCode = errorKey.split("-");
		    bmbPayloadHeader.setResId(resIdAnCode[0]);
		    bmbPayloadHeader.setResCde(resIdAnCode[1]);
		    // errorMsg = getErrorMessage(resIdAnCode[1]);

		    // Retrieving the message description from DB
		    respContext.setResCde(resIdAnCode[1]);
		    getMessage(respContext);
		    errorMsg = respContext.getResMsg();

		    break;// TODO
		}

	    }

	}
	if (errorMsg == null) {
	    // error code BEM9999
	    // Earlier to me errorMsg = "error";

	    bmbPayloadHeader.setResId(ResponseIdConstants.EXCEPTION_RESPONSE_ID);
	    bmbPayloadHeader.setResCde(ErrorCodeConstant.BEM_MESSAGE_PREFIX);
	    // errorMsg = getErrorMessage(resIdAnCode[1]);
	    // Retrieving the message description from DB
	    respContext.setResCde(ErrorCodeConstant.BEM_MESSAGE_PREFIX);
	    getMessage(respContext);
	    errorMsg = respContext.getResMsg();
	}
	bmbPayloadHeader.setResMsg(errorMsg);

	errorBMBResponse.setPayHdr(bmbPayloadHeader);
	errorBMBResponse.setPayData(baseResponse);

	BMBBaseResponseModel responseModel = errorBMBResponse;
	String serVersion = context.getServiceVersion();
	if (!StringUtils.isEmpty(serVersion) && SessionConstant.VERSION_RELEASE_ONE.equals(serVersion)) {
	    responseModel = createRel1ErrorResponseModel(errorMsg, ErrorCodeConstant.RECOVERABLE_ERROR);
	}

	return new ModelAndView(ViewConstant.FAILURE_VIEW, ResponseModelConstant.RESPONSE_MODEL_FAILURE, responseModel);

    }

    /**
     * generate Error Model from List of errors.
     *
     * @param errors
     * @return
     */
    protected BMBPayloadData createErrorModel(List<String> errors) {
	BMBPayloadData responseModel = null;
	JSONResponseError error = new JSONResponseError();
	for (String errorMsg : errors) {
	    error.addMessage(errorMsg);
	}
	// responseModel.setError(error);
	// responseModel.setSuccess(false);

	return responseModel;
    }

    /**
     * set user from session
     *
     * @param request
     * @return
     */
    protected void setUserMapIntoSession(HttpServletRequest request, String key, Object value) {

	Map<String, Object> userMap = (Map<String, Object>) getSessionAttribute(request, SessionConstant.SESSION_USER);
	if (userMap == null) {
	    userMap = new HashMap<String, Object>();
	}
	userMap.put(key, value);
	setSessionAttribute(request, SessionConstant.SESSION_USER, userMap);

    }

    /**
     * set activity from session
     *
     * @param request
     * @return
     */
    protected void setActivityMapIntoSession(HttpServletRequest request, String key, String value) {

	Map<String, String> userMap = (Map<String, String>) getSessionAttribute(request, SessionConstant.SESSION_ACTIVITY);
	if (userMap == null) {
	    userMap = new HashMap<String, String>();
	}
	userMap.put(key, value);
	setSessionAttribute(request, SessionConstant.SESSION_ACTIVITY, userMap);

    }

    @Deprecated
    /*
     * set process from session
     *
     * @param request
     *
     * @return
     */
    protected void setProcessMapIntoSession(HttpServletRequest request, String key, Object value) {

	Map<String, Object> userMap = (Map<String, Object>) getSessionAttribute(request, SessionConstant.SESSION_PROCESS);
	if (userMap == null) {
	    userMap = new HashMap<String, Object>();
	}
	userMap.put(key, value);
	setSessionAttribute(request, SessionConstant.SESSION_PROCESS, userMap);

    }

    /**
     * This method allows the user to persist an object throughout the processing of the flow. It is backed by httpsession.
     *
     * @param request
     * @param processKey
     * @param key
     * @param value
     */
    protected void setIntoProcessMap(HttpServletRequest request, String processKey, String key, Object value) {
	if (StringUtils.isNotEmpty(processKey)) {
	    Map<String, Object> processMap = (Map<String, Object>) getSessionAttribute(request, SessionConstant.SESSION_PROCESS);
	    if (processMap == null) {
		processMap = new HashMap<String, Object>();
		setSessionAttribute(request, SessionConstant.SESSION_PROCESS, processMap);
	    }
	    Map<String, Object> process = (Map<String, Object>) processMap.get(processKey);

	    if (process == null) {
		process = new HashMap<String, Object>();
		processMap.put(processKey, process);
	    }

	    process.put(key, value);
	} else {
	    throw new RuntimeException("Process Key is empty");
	}
    }

    /**
     * Retrieves the data from the process.
     *
     * @param request
     * @param processKey
     * @param key
     * @return
     */
    /*
     * protected Object getFromProcessMap(HttpServletRequest request, String processKey, String key) { if (StringUtils.isNotEmpty(processKey)) {
     * Map<String, Object> processMap = (Map<String, Object>) getSessionAttribute( request, SessionConstant.SESSION_PROCESS); if (processMap == null)
     * { throw new RuntimeException("Process Map not set for key " + processKey); } Map<String, Object> process = (Map<String, Object>) processMap
     * .get(processKey);
     *
     * if (process == null) { throw new RuntimeException("Process Map not set for key " + processKey); }
     *
     * return process.get(key); } else throw new RuntimeException("Process Key is empty"); }
     */

    protected Object getFromProcessMap(HttpServletRequest request, String processKey, String key) {

	if (StringUtils.isNotEmpty(processKey)) {

	    Map<String, Object> processMap = (Map<String, Object>) getSessionAttribute(request, SessionConstant.SESSION_PROCESS);

	    if (processMap != null) {

		Map<String, Object> process = (Map<String, Object>) processMap.get(processKey);

		if (process != null) {
		    return process.get(key);
		}
	    }
	    return null;
	} else {
	    throw new RuntimeException("processKey is empty");
	}
    }

    /**
     * Removes an entry from the current process map
     *
     * @param request
     * @param processKey
     * @param objKey
     */
    protected void removeFromProcessMap(HttpServletRequest request, String processKey, String objKey) {
	if (StringUtils.isNotEmpty(processKey)) {
	    Map<String, Object> processMap = (Map<String, Object>) getSessionAttribute(request, SessionConstant.SESSION_PROCESS);
	    if (processMap != null) {
		/*
		 * processMap = new HashMap<String, Object>(); setSessionAttribute(request, SessionConstant.SESSION_PROCESS, processMap);
		 */

		Map<String, Object> process = (Map<String, Object>) processMap.get(processKey);

		if (process != null) {
		    process.remove(objKey);
		}
	    }
	} else {
	    throw new RuntimeException("Process Key is empty");
	}
    }

    /**
     * Evicts all objects store for the process
     *
     * @param request
     * @param processKey
     */
    protected void cleanProcess(HttpServletRequest request, String processKey) {
	if (StringUtils.isNotEmpty(processKey)) {
	    Map<String, Object> processMap = (Map<String, Object>) getSessionAttribute(request, SessionConstant.SESSION_PROCESS);
	    if (processMap != null) {
		processMap.remove(processKey);
	    }

	} else {
	    throw new RuntimeException("Process Key is empty");
	}
    }

    /**
     * get user from session
     *
     * @param request
     * @return
     */
    protected Map<String, Object> getUserMapFromSession(HttpServletRequest request) {

	Map<String, Object> userMap = (Map<String, Object>) getSessionAttribute(request, SessionConstant.SESSION_USER);
	if (userMap == null) {
	    userMap = new HashMap<String, Object>();
	}
	return userMap;

    }

    /**
     * get activity from session
     *
     * @param request
     * @return
     */
    protected Map<String, String> getActivityMapFromSession(HttpServletRequest request) {

	Map<String, String> userMap = (Map<String, String>) getSessionAttribute(request, SessionConstant.SESSION_ACTIVITY);
	if (userMap == null) {
	    userMap = new HashMap<String, String>();
	}
	return userMap;

    }

    @Deprecated
    /*
     * get process from session
     *
     * @param request
     *
     * @return
     */
    protected Map<String, Object> getProcessMapFromSession(HttpServletRequest request) {

	Map<String, Object> userMap = (Map<String, Object>) getSessionAttribute(request, SessionConstant.SESSION_PROCESS);
	if (userMap == null) {
	    userMap = new HashMap<String, Object>();
	}
	return userMap;

    }

    /**
     *
     * Clear process Map
     *
     * @param request
     */
    protected void clearProcessMap(HttpServletRequest request) {
	Map<String, String> userMap = null;
	setSessionAttribute(request, SessionConstant.SESSION_PROCESS, userMap);
    }

    /**
     * @param response
     */
    protected void getMessage(ResponseContext response) {
	MessageResourceServiceRequest messageRequest = new MessageResourceServiceRequest();
	messageRequest.setContext(response.getContext());
	messageRequest.setMessageKey(response.getResCde());

	MessageResourceServiceResponse messageResponse = messageResourceService.getMessageDescByKey(messageRequest);
	response.setResMsg(messageResponse.getMessageDesc());
	response.setErrTyp(messageResponse.getErrTyp());
    }

    /**
     * @param response
     */
    protected String getSystemParam(Context context, String paramId) {

	SystemParameterDTO systemParameterDTO = new SystemParameterDTO();

	systemParameterDTO.setBusinessId(context.getBusinessId());
	systemParameterDTO.setSystemId(context.getSystemId());
	systemParameterDTO.setParameterId(paramId);
	systemParameterDTO.setActivityId("COMMON");

	SystemParameterServiceRequest systemParameterServiceRequest = new SystemParameterServiceRequest();
	systemParameterServiceRequest.setSystemParameterDTO(systemParameterDTO);
	SystemParameterServiceResponse systemParameterServiceResponse = systemParameterService.getSystemParameter(systemParameterServiceRequest);
	return systemParameterServiceResponse.getSystemParameterDTO().getParameterValue();

    }

    protected String getErrorMessage(String errorKey) {

	return (messageSource.getMessage(errorKey, null, null));

    }

    /**
     * Generate correlation ids for list of account nos and carry it in session.
     *
     * @param acctList
     * @param request
     * @param httpRequest
     */
    protected void mapCorrelationIds(List<? extends CustomerAccountDTO> acctList, RequestContext request, HttpServletRequest httpRequest,
	    ResponseContext response) {

	Map<String, Object> contextMap = request.getContext().getContextMap();
	Map<String, String> accountMap = (Map<String, String>) getSessionAttribute(httpRequest, AccountConstants.ACCOUNT_MAP);
	Map<String, String> correlationIdMap = (Map<String, String>) contextMap.get(AccountConstants.CORRELATION_ID_MAP);
	if (accountMap == null) {
	    accountMap = new HashMap<String, String>();
	    correlationIdMap = new HashMap<String, String>();
	}

	if (acctList != null && !acctList.isEmpty()) {
	    for (CustomerAccountDTO acct : acctList) {
		if (acct != null && acct.getAccountNumber() != null) {

		    String coId = correlationIdMap.get(acct.getAccountNumber());
		    if (StringUtils.isEmpty(coId)) {
			if (acct instanceof TdAccountDTO) {
			    TdAccountDTO tdAcct = (TdAccountDTO) acct;
			    if (tdAcct.getDepositDTO() != null) {
				coId = generateCorrelationId(tdAcct.getAccountNumber() + tdAcct.getDepositDTO().getDepositNumber());
				correlationIdMap.put(tdAcct.getAccountNumber() + "-" + tdAcct.getDepositDTO().getDepositNumber(), coId);
			    } else {
				coId = generateCorrelationId(acct.getAccountNumber());
				correlationIdMap.put(acct.getAccountNumber(), coId);
			    }
			} else {
			    coId = generateCorrelationId(acct.getAccountNumber());
			    correlationIdMap.put(acct.getAccountNumber(), coId);
			}

		    }
		}
	    }
	    Collection<String> acctNos = correlationIdMap.keySet();
	    if (!acctNos.isEmpty()) {
		for (String acctNo : acctNos) {
		    accountMap.put(correlationIdMap.get(acctNo), acctNo);
		}
	    }
	    setSessionAttribute(httpRequest, AccountConstants.ACCOUNT_MAP, accountMap);
	    contextMap.put(AccountConstants.CORRELATION_ID_MAP, correlationIdMap);
	}
	response.setContext(request.getContext());
    }

    /**
     *
     * Map the correlation Id for each account. Correlation Id for each account can be get from request context Map.
     *
     * @param acctList
     * @param request
     * @param httpRequest
     */
    protected void mapCorrelationIds(List<? extends CustomerAccountDTO> acctList, RequestContext request, HttpServletRequest httpRequest,
	    ResponseContext response, String processKey) {

	Map<String, Object> contextMap = request.getContext().getContextMap();
	Map<String, String> accountMap = (Map<String, String>) getFromProcessMap(httpRequest, processKey, AccountConstants.ACCOUNT_MAP);
	Map<String, String> correlationIdMap = (Map<String, String>) contextMap.get(AccountConstants.CORRELATION_ID_MAP);
	if (correlationIdMap == null) {
	    correlationIdMap = new HashMap<String, String>();
	}
	if (accountMap == null) {
	    accountMap = new HashMap<String, String>();
	}
	if (acctList != null && !acctList.isEmpty()) {
	    for (CustomerAccountDTO acct : acctList) {
		if (acct != null && acct.getAccountNumber() != null) {

		    String coId = correlationIdMap.get(acct.getAccountNumber());
		    if (StringUtils.isEmpty(coId)) {
			if (acct instanceof TdAccountDTO) {
			    TdAccountDTO tdAcct = (TdAccountDTO) acct;
			    if (tdAcct.getDepositDTO() != null) {
				coId = generateCorrelationId(tdAcct.getAccountNumber() + tdAcct.getDepositDTO().getDepositNumber());
				correlationIdMap.put(tdAcct.getAccountNumber() + "-" + tdAcct.getDepositDTO().getDepositNumber(), coId);
			    } else {
				coId = generateCorrelationId(acct.getAccountNumber());
				correlationIdMap.put(acct.getAccountNumber(), coId);
			    }
			} else {
			    coId = generateCorrelationId(acct.getAccountNumber());
			    correlationIdMap.put(acct.getAccountNumber(), coId);
			}

		    }
		}
	    }
	    Collection<String> acctNos = correlationIdMap.keySet();
	    if (!acctNos.isEmpty()) {
		for (String acctNo : acctNos) {
		    accountMap.put(correlationIdMap.get(acctNo), acctNo);
		}
	    }
	    setIntoProcessMap(httpRequest, processKey, AccountConstants.ACCOUNT_MAP, accountMap);
	    contextMap.put(AccountConstants.CORRELATION_ID_MAP, correlationIdMap);
	}
	response.setContext(request.getContext());
    }

    /**
     * Generate correlation id.
     *
     * @param acctNo
     * @return
     */
    private static String generateCorrelationId(String acctNo) {
	String ranActNo = acctNo;
	if (acctNo != null && acctNo.length() > 3) {
	    ranActNo = ranActNo.substring(3);
	}
	Long coId = System.currentTimeMillis();
	ranActNo = randomDigit() + ranActNo + coId.toString().substring(5) + randomDigit();

	return ranActNo;

    }

    private static String randomDigit() {
	SecureRandom random = getSecureRandom();
	int index = Math.round((float) (random.nextDouble() * 10));
	if (index > 9) {
	    index = 9;
	}
	return String.valueOf(index);
    }

    public static SecureRandom getSecureRandom() {

	try {
	    if (random == null) {
		random = SecureRandom.getInstance(ALGORITHM);
	    }
	} catch (Exception e) {
	    // log.error("Algorithm 'SHA1PRNG' doesn't exist when create a new SecureRandom instance",
	    // ErrorCode.SYS_SYSTEM_ERROR, e);
	}
	return random;
    }

    /**
     * Return account number against particular correlation id.
     *
     * @param correlationId
     * @param httpRequest
     * @return
     */
    protected String getAccountNumber(String correlationId, HttpServletRequest httpRequest) {
	Map<String, String> accountMap = (Map<String, String>) getSessionAttribute(httpRequest, AccountConstants.ACCOUNT_MAP);

	String acct = null;
	if (accountMap != null) {
	    acct = accountMap.get(correlationId);
	}
	return acct;
    }

    /**
     * Return account number against particular correlation id.
     *
     * @param correlationId
     * @param httpRequest
     * @return
     */
    protected String getAccountNumber(String correlationId, HttpServletRequest httpRequest, String processKey) {
	Map<String, String> accountMap = (Map<String, String>) getFromProcessMap(httpRequest, processKey, AccountConstants.ACCOUNT_MAP);

	String acct = null;
	if (accountMap != null) {
	    acct = accountMap.get(correlationId);
	}
	return acct;
    }

    /**
     * Return account number against particular correlation id.
     *
     * @param correlationId
     * @param httpRequest
     * @return
     */
    protected Map<String, String> setEcrimeAccountMapToSession(HttpServletRequest httpRequest, String processKey) {
	Map<String, String> accountMap = (Map<String, String>) getFromProcessMap(httpRequest, processKey, AccountConstants.ACCOUNT_MAP);

	httpRequest.setAttribute("ecrimeAccountMap", accountMap);

	return accountMap;
    }

    /**
     *
     * Clear the session start of new process.
     *
     * @param httpRequest
     */
    protected void clearCorrelationIds(HttpServletRequest httpRequest, String processKey) {
	removeFromProcessMap(httpRequest, processKey, AccountConstants.ACCOUNT_MAP);
    }

    /**
     * Add parameters to context
     *
     * @param request
     */
    public Context createContext(HttpServletRequest httpRequest) {
	Context context = new Context();

	// context.setActivityId(ActivityConstant.LOGIN_ACTIVITY_ID);
	Map<String, Object> userMap = getUserMapFromSession(httpRequest);

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
	context.setTimezoneOffset((String) userMap.get(SessionConstant.SESSION_TIMEZONE_OFFSET));

	HttpSession httpSession = httpRequest.getSession();

	// System.out.println("httpSession.getId() $$$$$$$$$$$$$$$$$$$$$ "+httpSession.getId());

	if (httpSession != null) {
	    context.setServiceVersion((String) httpSession.getAttribute(SessionConstant.SESSION_BMG_SERVICE_VERSION));
	}
	/*
	 * Map<String, String> ppMap = (Map<String, String>) getSessionAttribute( httpRequest, SessionConstant.SESSION_PP_MAP);
	 */
	context.setPpMap((Map<String, String>) userMap.get(SessionConstant.SESSION_PP_MAP));
	context.setActivityRefNo(getActivityReferenceNumber(httpRequest));
	context.setOrgRefNo(BMBCommonUtility.generateRefNo());
	// setSessionAttribute(httpRequest, "context", context);

	//Changes for caching of account list & reduce one call to enhance performance
	context.setAccountList((List<? extends CustomerAccountDTO>)userMap.get(SessionConstant.SESSION_ACCOUNT_LIST+context.getSessionId()));
	return context;
    }

    /**
     * Remove session attribute form user map
     *
     * @param request
     * @param key
     */

    protected void removeSessionAttributeFromUserMap(HttpServletRequest request, String key) {
	Map<String, Object> userMap = (Map<String, Object>) getSessionAttribute(request, SessionConstant.SESSION_USER);
	if (userMap != null) {
	    userMap.remove(key);

	}
    }

    protected String getActivityReferenceNumber(HttpServletRequest httpRequest) {
	// String activityRefNo = (String)getSessionAttribute(httpRequest,
	// SessionConstant.PROCESS_REF_NO);
	String activityRefNo = (String) getUserMapFromSession(httpRequest).get(SessionConstant.PROCESS_REF_NO);
	if (StringUtils.isEmpty(activityRefNo)) {
	    activityRefNo = BMBCommonUtility.generateRefNo();
	}
	Boolean firstStep = (Boolean) getUserMapFromSession(httpRequest).get(SessionConstant.FIRST_STEP);
	Boolean lastStep = (Boolean) getUserMapFromSession(httpRequest).get(SessionConstant.LAST_STEP);
	if (firstStep != null && firstStep) {
	    activityRefNo = BMBCommonUtility.generateRefNo();
	    setUserMapIntoSession(httpRequest, SessionConstant.STEP_ID, Integer.valueOf(1));
	    setUserMapIntoSession(httpRequest, SessionConstant.FIRST_STEP, false);
	    setUserMapIntoSession(httpRequest, SessionConstant.PROCESS_REF_NO, activityRefNo);
	}
	if (lastStep == null || lastStep != null && !lastStep) {
	    Integer stepId = (Integer) getUserMapFromSession(httpRequest).get(SessionConstant.STEP_ID);
	    if (stepId != null) {
		activityRefNo = activityRefNo + "-" + stepId;
		stepId = stepId + 1;
		setUserMapIntoSession(httpRequest, SessionConstant.STEP_ID, stepId);
	    }
	}
	if (lastStep != null && lastStep) {
	    activityRefNo = activityRefNo.split("-")[0];
	    setUserMapIntoSession(httpRequest, SessionConstant.LAST_STEP, false);
	    removeSessionAttributeFromUserMap(httpRequest, SessionConstant.PROCESS_REF_NO);
	}
	return activityRefNo;
    }

    /**
     * @param responses
     * @return
     */
    protected boolean checkAllOperationResponses(ResponseContext... responses) {
	boolean result = true;
	if (responses != null) {
	    for (ResponseContext response : responses) {
		if (response != null && !response.isSuccess()) {
		    result = false;
		    break;
		}
	    }
	}
	return result;
    }

    protected void finishOff(HttpServletRequest httpRequest) {
	HttpSession httpSession = httpRequest.getSession(false);
	if (httpSession != null) {
	    httpSession.invalidate();
	}
	/*
	 * SecurityContext securityContext = SecurityContextHolder.getContext(); if (securityContext != null) { SecurityContextHolder.clearContext();
	 * }
	 */
    }

    /**
     *
     * Clear the session start of new process.
     *
     * @param httpRequest
     */
    protected void clearCorrelationIds(HttpServletRequest httpRequest) {
	removeSessionAttribute(httpRequest, AccountConstants.ACCOUNT_MAP);
    }

    public MessageSource getMessageSource() {
	return messageSource;
    }

    public void setMessageSource(MessageSource messageSource) {
	this.messageSource = messageSource;
    }

    public MessageResourceService getMessageResourceService() {
	return messageResourceService;
    }

    public void setMessageResourceService(MessageResourceService messageResourceService) {
	this.messageResourceService = messageResourceService;
    }

    public SystemParameterService getSystemParameterService() {
	return systemParameterService;
    }

    public void setSystemParameterService(SystemParameterService systemParameterService) {
	this.systemParameterService = systemParameterService;
    }

    public void setSecurityCheckRedirectURL(String securityCheckRedirectURL) {
	this.securityCheckRedirectURL = securityCheckRedirectURL;
    }

    public FeatureBlackoutService getFeatureBlackoutService() {
	return featureBlackoutService;
    }

    public void setFeatureBlackoutService(FeatureBlackoutService featureBlackoutService) {
	this.featureBlackoutService = featureBlackoutService;
    }

    protected void setFirstStep(HttpServletRequest httpRequest) {
	setUserMapIntoSession(httpRequest, SessionConstant.FIRST_STEP, true);
    }

    protected void setLastStep(HttpServletRequest httpRequest) {
	setUserMapIntoSession(httpRequest, SessionConstant.LAST_STEP, true);

    }

    public String getRequestUri() {
	return requestUri;
    }

    public void setRequestUri(String requestUri) {
	this.requestUri = requestUri;
    }

    public String getSerVerUri() {
	return serVerUri;
    }

    public void setSerVerUri(String serVerUri) {
	this.serVerUri = serVerUri;
    }

    /**
     * Exception handling
     */

    protected ModelAndView getModelAndView(String viewName, Exception exception, HttpServletRequest request) {

	BMBPayload payload = new BMBPayload();

	BMBPayloadHeader payHdr = new BMBPayloadHeader();

	payHdr.setResId(ResponseIdConstants.EXCEPTION_RESPONSE_ID);
	payHdr.setSerVer(ResponseIdConstants.RESPONSE_SERVICE_VERSION);
	payHdr.setResCde(ResponseCodeConstants.EXCEPTION_RES_CODE);

	String resMsg = getMessage(ResponseCodeConstants.EXCEPTION_RES_CODE);
	String errType = ErrorCodeConstant.UNRECOVERABLE_ERROR;
	if (exception instanceof BMBDataAccessException) {
	    if (LOGGER.isDebugEnabled()) {
		LOGGER.error("Got the exception..... " + exception.getMessage(), exception);
	    }

	    BMBDataAccessException dae = (BMBDataAccessException) exception;
	    String bmbErrCode = dae.getErrorCode();
	    String bmbErrMsg = dae.getMessage();
	    String txnRefNo = dae.getTxnRefNo();

	    if (bmbErrCode != null) {
		// String errCdeMsg = BMGConstants.EMPTYSTR;
		if (!bmbErrCode.isEmpty() && bmbErrCode.contains(BMB)) {
		    payHdr.setResCde(bmbErrCode);
		    if (bmbErrMsg != null) {
			payHdr.setResMsg(getMessage(bmbErrCode, bmbErrMsg));
		    } else {
			payHdr.setResMsg(getMessage(bmbErrCode));
		    }
		} else {
		    payHdr.setResCde(BEM + bmbErrCode);
		    payHdr.setResMsg(getMessage(ErrorCodeConstant.BEM_MESSAGE_PREFIX + bmbErrCode));
		    payHdr.setTxnRefNo(txnRefNo);
		}

	    } else {
		payHdr.setResCde(ResponseCodeConstants.EXCEPTION_RES_CODE);
		payHdr.setResMsg(resMsg);

	    }
	    errType = ErrorCodeConstant.UNRECOVERABLE_ERROR;

	} else if (exception instanceof FeatureBlackoutException) {
	    payHdr.setResCde(ResponseCodeConstants.FEATURE_BLACKOUT_RES_CODE);
	    errType = ErrorCodeConstant.UNRECOVERABLE_ERROR;
	    payHdr.setResMsg(getMessage(ResponseCodeConstants.FEATURE_BLACKOUT_RES_CODE));
	} else if (exception instanceof JailBrokenException) {
	    String errCode = ((JailBrokenException) exception).getErrorCode();
	    payHdr.setResCde(errCode);
	    errType = ErrorCodeConstant.UNRECOVERABLE_ERROR;
	    payHdr.setResMsg(getMessage(errCode));
	} else {
	     payHdr.setResMsg(resMsg);
	    payHdr.setExpTrace(null);
	    errType = ErrorCodeConstant.UNRECOVERABLE_ERROR;
	}

	payload.setPayHdr(payHdr);
	/**
	 * Service Versioning for release one.
	 */
	BMBBaseResponseModel responseModel = payload;
	HttpSession httpSession = request.getSession(false);
	if (httpSession != null) {
	    String serVersion = (String) httpSession.getAttribute(SessionConstant.SESSION_BMG_SERVICE_VERSION);
	    if (!StringUtils.isEmpty(serVersion) && SessionConstant.VERSION_RELEASE_ONE.equals(serVersion)) {
		responseModel = createRel1ErrorResponseModel(payHdr.getResMsg(), errType);
	    }
	}
	return new ModelAndView(viewName, ResponseModelConstant.RESPONSE_MODEL_FAILURE, responseModel);
    }

    protected BaseJSONResponseModel createRel1ErrorResponseModel(String errorMsg, String errType) {
	BaseJSONResponseModel responseModel = new BaseJSONResponseModel();
	responseModel.setSuccess(false);
	JSONResponseError error = new JSONResponseError();
	error.addMessage(errorMsg);
	error.setJSONErrorType(errType);
	responseModel.setError(error);
	return responseModel;
    }

    private String getMessage(String resCde) {
	MessageResourceServiceRequest messageRequest = new MessageResourceServiceRequest();
	messageRequest.setContext(BMBContextHolder.getContext());
	messageRequest.setMessageKey(resCde);

	MessageResourceServiceResponse messageResponse = messageResourceService.getMessageDescByKey(messageRequest);
	return messageResponse.getMessageDesc();
    }

    private String getMessage(String resCde, String defMsg) {
	MessageResourceServiceRequest messageRequest = new MessageResourceServiceRequest();
	messageRequest.setContext(BMBContextHolder.getContext());
	messageRequest.setMessageKey(resCde);
	MessageResourceServiceResponse messageResponse = messageResourceService.getMessageDescByKey(messageRequest);
	if (messageResponse != null) {
	    if (!defMsg.equals(BMGConstants.EMPTYSTR)) {
		return messageResponse.getMessageDesc() + "(" + defMsg + ")";
	    } else {
		return messageResponse.getMessageDesc();
	    }
	} else {
	    return defMsg;
	}
    }
}
