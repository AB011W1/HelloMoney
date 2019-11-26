package com.barclays.bmg.dao.processing.USSDAuthentication;

import java.util.Map;

import org.apache.log4j.Logger;
import org.datacontract.schemas._2004._07.DocumentManager_Models.DocumentActionResult;
import org.datacontract.schemas._2004._07.DocumentManager_Models.DocumentManagementAllocation;

import com.barclays.bmg.constants.SystemParameterConstant;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.dao.core.processing.Controller;
import com.barclays.bmg.dao.operation.USSDAuthentication.AuthenticateChangeReqAdptOperation;
import com.barclays.bmg.dao.operation.USSDAuthentication.AuthenticateChangeResAdptOperation;
import com.barclays.bmg.dao.service.impl.USSDAuthentication.AuthenticateChangetransmissionService;
import com.barclays.bmg.service.request.USSDAuthentication.UpdateRecordsRequest;

/**
 * Controller class to set the required parameters in request and call
 * UpdateRecords web service.
 * 
 * @param WorkContext
 *            workContext
 *
 */
public class UssdUpdateRecordsDaoController implements Controller {

	private static final Logger LOGGER = Logger.getLogger(UssdUpdateRecordsDaoController.class);

	private AuthenticateChangeReqAdptOperation authenticateChangeReqAdptOperation;

	private AuthenticateChangetransmissionService authenticateChangetransmissionService;

	private AuthenticateChangeResAdptOperation authenticateChangeResAdptOperation;
	
	/**
	 * @return authenticateChangeReqAdptOperation
	 */
	public AuthenticateChangeReqAdptOperation getAuthenticateChangeReqAdptOperation() {
		return authenticateChangeReqAdptOperation;
	}

	/**
	 * @param authenticateChangeReqAdptOperation
	 *            to set AuthenticateChangeReqAdptOperation object
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
	 *            to set AuthenticateChangetransmissionService object
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
	 *            to set AuthenticateChangeResAdptOperation object
	 */
	public void setAuthenticateChangeResAdptOperation(
			AuthenticateChangeResAdptOperation authenticateChangeResAdptOperation) {
		this.authenticateChangeResAdptOperation = authenticateChangeResAdptOperation;
	}

	
	@Override
	public Object handleRequest(WorkContext workContext) throws Exception {

		LOGGER.debug("Dao Controller to call UpdateRecords service");

		DAOContext daoContext = (DAOContext) workContext;
		Object[] args = daoContext.getArguments();
		UpdateRecordsRequest request = (UpdateRecordsRequest) args[0];
		Map<String, Object> systemParameterMap = request.getContext().getContextMap();
		String serviceEndPoint = systemParameterMap != null
				? systemParameterMap.get(SystemParameterConstant.AUTH_REQUEST_SERVICE_ENDPOINT).toString()
				: null;

		LOGGER.debug("Sybrin Services EndPoint: " + serviceEndPoint);

		DocumentManagementAllocation documentManagementAllocation = authenticateChangeReqAdptOperation
				.updateRecords(workContext);
		DocumentActionResult updateRecordsResponse = authenticateChangetransmissionService
				.updateRecords(documentManagementAllocation, serviceEndPoint);

		if (updateRecordsResponse != null && updateRecordsResponse.getFailureMessage() != null) {
			LOGGER.debug("UpdateRecords response: " + updateRecordsResponse.getFailureMessage());
		}

		return authenticateChangeResAdptOperation.adaptUpdateRecordsResponse(workContext, updateRecordsResponse);
	}
	
}
