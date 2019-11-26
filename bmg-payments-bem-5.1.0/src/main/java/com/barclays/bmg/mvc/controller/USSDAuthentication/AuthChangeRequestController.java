package com.barclays.bmg.mvc.controller.USSDAuthentication;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.validation.BindException;

import com.barclays.bmg.constants.AccountConstants;
import com.barclays.bmg.constants.BMGProcessConstants;
import com.barclays.bmg.constants.RequestConstants;
import com.barclays.bmg.constants.SessionConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController;
import com.barclays.bmg.operation.USSDAuthentication.AuthChangeRequestOperation;
import com.barclays.bmg.operation.request.USSDAuthentication.AuthenticateChangeOperationRequest;
import com.barclays.bmg.operation.response.USSDAuthentication.AuthenticateChangeOperationResponse;
import com.barclays.bmg.service.response.USSDAuthentication.GetRecordResDocumentInfo;
import com.barclays.bmg.utils.BMBCommonUtility;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDInputParamsEnum;


/**
 * The Controller is for USSD Authentication of Change requests initiated through Sybrin portal
 *
 */
public class AuthChangeRequestController extends BMBAbstractCommandController {

	private static final Logger LOGGER = Logger.getLogger(AuthChangeRequestController.class);
	
	private String activityId;

	private BMBJSONBuilder bmbJSONBuilder;
	private AuthChangeRequestOperation authChangeRequestOperation;

	@Override
	protected String getActivityId(Object command) {
		return null;
	}
	
	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	/**
	 * @return bmbJSONBuilder
	 * 
	 **/
	public BMBJSONBuilder getBmbJSONBuilder() {
		return bmbJSONBuilder;
	}

	/**
	 * @param bmbJSONBuilder, 
	 * 			the BMBJSONBuilder object to set
	 * 
	 **/
	public void setBmbJSONBuilder(BMBJSONBuilder bmbJSONBuilder) {
		this.bmbJSONBuilder = bmbJSONBuilder;
	}

	/**
	 * @return authChangeRequestOperation
	 * 
	 **/
	public AuthChangeRequestOperation getAuthChangeRequestOperation() {
		return authChangeRequestOperation;
	}

	/**
	 * @param authChangeRequestOperation,
	 * 			the AuthChangeRequestOperation object to set
	 * 
	 **/
	public void setAuthChangeRequestOperation(
			AuthChangeRequestOperation authChangeRequestOperation) {
		this.authChangeRequestOperation = authChangeRequestOperation;
	}

	
	@SuppressWarnings({ "unchecked"})
	@Override
	protected BMBBaseResponseModel handle1(HttpServletRequest request,HttpServletResponse response, 
			Object command, BindException errors)
			throws Exception {

		LOGGER.debug("Entry in AuthChangeRequestController");
		AuthenticateChangeOperationRequest authChangeOperationRequest = makeRequest(request);

		AuthenticateChangeOperationResponse authChangeOperationResponse = authChangeRequestOperation
				.getChangeRecords(authChangeOperationRequest);

		List<GetRecordResDocumentInfo> documents = (List<GetRecordResDocumentInfo>) authChangeOperationResponse.getDocuments();
		Map<String, Object> userMap = getUserMapFromSession(request);
		userMap.put(SessionConstant.SESSION_DOCUMENT_LIST, documents);
		userMap.put(SessionConstant.SESSION_AUTH_ACTIVITY_ID, authChangeOperationResponse.getActivityId());
		
		LOGGER.debug("Exit from AuthChangeRequestController");

		return (BMBBaseResponseModel) bmbJSONBuilder
				.createJSONResponse(authChangeOperationResponse);
	}

	/**
	 * This method has the purpose to construct the operation
	 * request.
	 *
	 * @param HttpRequest
	 * @return AuthenticateChangeOperationRequest
	 */
	@SuppressWarnings("unchecked")
	private AuthenticateChangeOperationRequest makeRequest(
			HttpServletRequest request) {

		String activityId = request.getParameter(USSDInputParamsEnum.ACTIVITY_ID.getParamName());
		String actNoCorr = request.getParameter(USSDConstants.DATA_TYPE_ACCTNO);
		String accountNumber=null;
		
		if(RequestConstants.AUTH_CARD_CHANGE_REQUEST_ACTIVITY_ID.equals(activityId)){
			
			Map<String, String> accountMap = (Map<String, String>) getFromProcessMap(request,BMGProcessConstants.CREDIT_PAYMENT,AccountConstants.ACCOUNT_MAP);
			accountNumber = accountMap.get(actNoCorr);

		} else if(RequestConstants.AUTH_REQUEST_ACTIVITY_ID.equals(activityId)){
			
			accountNumber = getAccountNumber(actNoCorr, request,BMGProcessConstants.ACCOUNTS_PROCESS);
			if (accountNumber == null)
			accountNumber = actNoCorr;
		}

		Context context = createContext(request);
		context.setActivityId(activityId);
		context.setActivityRefNo(BMBCommonUtility.generateRefNo());
		context.setOpCde(request.getParameter(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME));
		context.getContextMap().put(USSDConstants.DATA_TYPE_MOBILE_NUMBER, request.getParameter(USSDConstants.MOBILE_NO));
		context.getContextMap().put(USSDConstants.USSD_ACCOUNT_NUMBER, accountNumber);
		
		AuthenticateChangeOperationRequest authChangeOperationRequest = new AuthenticateChangeOperationRequest();
		authChangeOperationRequest.setContext(context);
		authChangeOperationRequest.setAccountNumber(accountNumber);

		return authChangeOperationRequest;
	}

	
}
