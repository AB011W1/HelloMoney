package com.barclays.bmg.dao.processing.USSDAuthentication;

import java.util.Map;

import org.apache.log4j.Logger;
import org.datacontract.schemas._2004._07.DocumentManager_Models.GetRecord;
import org.datacontract.schemas._2004._07.DocumentManager_Models.GetResult;
import org.datacontract.schemas._2004._07.DocumentManager_Models.Login;
import org.datacontract.schemas._2004._07.DocumentManager_Models.LoginResult;

import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.dao.core.processing.Controller;
import com.barclays.bmg.dao.operation.USSDAuthentication.AuthenticateChangeReqAdptOperation;
import com.barclays.bmg.dao.operation.USSDAuthentication.AuthenticateChangeResAdptOperation;
import com.barclays.bmg.dao.service.impl.USSDAuthentication.AuthenticateChangetransmissionService;
import com.barclays.bmg.service.request.USSDAuthentication.GetChangeRecordsRequest;

/**
 * Controller class to set the required parameters in request and call Login & GetRecords web
 * service.
 * 
 * @param WorkContext
 *            workContext
 *
 */
public class AuthenticateChangeRequestDaoController implements Controller {

	private static final Logger LOGGER = Logger.getLogger(AuthenticateChangeRequestDaoController.class);

	private AuthenticateChangeReqAdptOperation authenticateChangeReqAdptOperation;

	private AuthenticateChangetransmissionService authenticateChangetransmissionService;

	private AuthenticateChangeResAdptOperation authenticateChangeResAdptOperation;
	
	private static final String AUTH_REQUEST_SERVICE_ENDPOINT = "USSDAUTHENTICATION_SERVICE_ENDPOINT";
	
	/**
	 * @return authenticateChangeReqAdptOperation
	 */
	public AuthenticateChangeReqAdptOperation getAuthenticateChangeReqAdptOperation() {
		return authenticateChangeReqAdptOperation;
	}

	
	/**
	 * @param authenticateChangeReqAdptOperation
	 * 			to set AuthenticateChangeReqAdptOperation object
	 */
	public void setAuthenticateChangeReqAdptOperation(
			AuthenticateChangeReqAdptOperation authenticateChangeReqAdptOperation) {
		this.authenticateChangeReqAdptOperation = authenticateChangeReqAdptOperation;
	}

	
	/**
	 * @return authenticateChangetransmissionService
	 */
	public AuthenticateChangetransmissionService getAuthenticateChangetransmissionService() {
		return authenticateChangetransmissionService;
	}

	
	/**
	 * @param authenticateChangetransmissionService
	 * 			to set AuthenticateChangetransmissionService object
	 */
	public void setAuthenticateChangetransmissionService(
			AuthenticateChangetransmissionService authenticateChangetransmissionService) {
		this.authenticateChangetransmissionService = authenticateChangetransmissionService;
	}

	
	/**
	 * @return authenticateChangeResAdptOperation
	 */
	public AuthenticateChangeResAdptOperation getAuthenticateChangeResAdptOperation() {
		return authenticateChangeResAdptOperation;
	}

	
	/**
	 * @param authenticateChangeResAdptOperation
	 * 			to set AuthenticateChangeResAdptOperation object
	 */
	public void setAuthenticateChangeResAdptOperation(
			AuthenticateChangeResAdptOperation authenticateChangeResAdptOperation) {
		this.authenticateChangeResAdptOperation = authenticateChangeResAdptOperation;
	}

	@Override
	public Object handleRequest(WorkContext workContext) throws Exception {

		LOGGER.debug("Dao Controller to call Login and GetRecords service");

		DAOContext daoContext = (DAOContext) workContext;
		Object[] args = daoContext.getArguments();
		GetChangeRecordsRequest request = (GetChangeRecordsRequest) args[0];
		Map<String, Object> systemParameterMap = request.getContext().getContextMap();
		String serviceEndPoint = systemParameterMap != null
				? systemParameterMap.get(AUTH_REQUEST_SERVICE_ENDPOINT).toString()
				: null;

		LOGGER.debug("Sybrin Services EndPoint: " + serviceEndPoint);

		Login loginRequest = authenticateChangeReqAdptOperation.loginRequest(workContext);
		LoginResult loginResponse = authenticateChangetransmissionService.loginRequest(loginRequest, serviceEndPoint);

		// When logged in successfully then only call getRecords API
		if (loginResponse.getIsLoggedIn()) {
			GetRecord records = authenticateChangeReqAdptOperation.getChangeRecords(workContext, loginResponse);
			GetResult getRecordsResponse = authenticateChangetransmissionService.getChangeRecords(records,
					serviceEndPoint);

			LOGGER.debug("GetRecords response: " + getRecordsResponse.getActionSuccessful());

			return authenticateChangeResAdptOperation.adaptResponse(workContext, getRecordsResponse);

		} else {

			LOGGER.debug("Login Failure response: " + loginResponse.getFailureMessage());
			return authenticateChangeResAdptOperation.adaptLoginFailureResponse(loginResponse.getFailureMessage());
		}

	}
	
}
