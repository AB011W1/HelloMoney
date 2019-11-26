package com.barclays.bmg.mvc.controller.USSDAuthentication;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.validation.BindException;

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
 * The Controller is for USSD Authentication of Change requests initiated
 * through Sybrin portal
 *
 */
public class UssdUpdateRecordsRequestController extends BMBAbstractCommandController {

	private static final Logger LOGGER = Logger.getLogger(UssdUpdateRecordsRequestController.class);

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
	 * @param bmbJSONBuilder
	 *            the BMBJSONBuilder object to set
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
	 * @param authChangeRequestOperation
	 *            the AuthChangeRequestOperation object to set
	 * 
	 **/
	public void setAuthChangeRequestOperation(AuthChangeRequestOperation authChangeRequestOperation) {
		this.authChangeRequestOperation = authChangeRequestOperation;
	}

	@Override
	protected BMBBaseResponseModel handle1(HttpServletRequest request, HttpServletResponse response, Object command,
			BindException errors) throws Exception {

		LOGGER.debug("Entry in UssdUpdateRecordsRequestController ");
		AuthenticateChangeOperationRequest authChangeOperationRequest = makeRequest(request);

		AuthenticateChangeOperationResponse authChangeOperationResponse = authChangeRequestOperation
				.updateRecords(authChangeOperationRequest);

		LOGGER.debug("Exit from UssdUpdateRecordsRequestController ");

		return (BMBBaseResponseModel) bmbJSONBuilder.createJSONResponse(authChangeOperationResponse);
	}

	/**
	 * This method makeRequest has the purpose to construct the operation request.
	 *
	 * @param HttpRequest
	 * @return AuthenticateChangeOperationRequest
	 */
	@SuppressWarnings("unchecked")
	private AuthenticateChangeOperationRequest makeRequest(HttpServletRequest request) {

		Map<String, Object> userMap = getUserMapFromSession(request);
		List<GetRecordResDocumentInfo> documents = (List<GetRecordResDocumentInfo>) userMap
				.get(SessionConstant.SESSION_DOCUMENT_LIST);

		String activityId = userMap.get(SessionConstant.SESSION_AUTH_ACTIVITY_ID) != null
				? userMap.get(SessionConstant.SESSION_AUTH_ACTIVITY_ID).toString()
				: "";
		String userInput = (String) request.getParameter(USSDInputParamsEnum.AUTHORIZE_CHANGE_REQUEST.getParamName());

		Context context = createContext(request);
		context.setActivityId(request.getParameter(USSDInputParamsEnum.ACTIVITY_ID.getParamName()));
		context.setActivityRefNo(BMBCommonUtility.generateRefNo());
		context.setOpCde(request.getParameter(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME));
		context.getContextMap().put(USSDConstants.DATA_TYPE_MOBILE_NUMBER,
				request.getParameter(USSDConstants.MOBILE_NO));
		AuthenticateChangeOperationRequest authChangeOperationRequest = new AuthenticateChangeOperationRequest();

		authChangeOperationRequest.setContext(context);
		authChangeOperationRequest.setDocumentDetails(documents);
		authChangeOperationRequest.setActivityId(activityId);

		if (USSDConstants.APPROVE_REQUEST_INPUT.equals(userInput)) {
			authChangeOperationRequest.setUssdStatus(RequestConstants.AUTH_REQUEST_APPROVE);
		} else if (USSDConstants.REJECT_REQUEST_INPUT.equals(userInput)) {
			authChangeOperationRequest.setUssdStatus(RequestConstants.AUTH_REQUEST_REJECT);
		}

		return authChangeOperationRequest;
	}
}
