package com.barclays.bmg.operation.USSDAuthentication;

import java.util.List;

import org.apache.log4j.Logger;

import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.operation.BMBCommonOperation;
import com.barclays.bmg.operation.request.USSDAuthentication.AuthenticateChangeOperationRequest;
import com.barclays.bmg.operation.response.USSDAuthentication.AuthenticateChangeOperationResponse;
import com.barclays.bmg.service.USSDAuthentication.AuthenticateChangeRequestService;
import com.barclays.bmg.service.USSDAuthentication.UssdUpdateRecordsService;
import com.barclays.bmg.service.request.USSDAuthentication.GetChangeRecordsRequest;
import com.barclays.bmg.service.request.USSDAuthentication.UpdateRecordsRequest;
import com.barclays.bmg.service.response.USSDAuthentication.AuthenticateChangeServiceResponse;
import com.barclays.bmg.service.response.USSDAuthentication.GetRecordResDocumentInfo;

/**
 * @author Hiral Pannchal 
 * This is the operation class to call the service for USSD Authentication of change requests
 *
 */
public class AuthChangeRequestOperation extends BMBCommonOperation {

	private static final Logger LOGGER = Logger.getLogger(AuthChangeRequestOperation.class);

	private AuthenticateChangeRequestService authenticateChangeRequestService;

	private UssdUpdateRecordsService ussdUpdateRecordsService;
	
	/**
	 * @return authenticateChangeRequestService
	 */
	public AuthenticateChangeRequestService getAuthenticateChangeRequestService() {
		return authenticateChangeRequestService;
	}

	/**
	 * @param authenticateChangeRequestService
	 *            the AuthenticateChangeRequestService object to set
	 */
	public void setAuthenticateChangeRequestService(AuthenticateChangeRequestService authenticateChangeRequestService) {
		this.authenticateChangeRequestService = authenticateChangeRequestService;
	}

	/**
	 * @return ussdUpdateRecordsService
	 */
	public UssdUpdateRecordsService getUssdUpdateRecordsService() {
		return ussdUpdateRecordsService;
	}

	/**
	 * @param ussdUpdateRecordsService
	 *            the UssdUpdateRecordsService object to set
	 */
	public void setUssdUpdateRecordsService(UssdUpdateRecordsService ussdUpdateRecordsService) {
		this.ussdUpdateRecordsService = ussdUpdateRecordsService;
	}

	/**
	 * 
	 * @param request
	 * @return AuthenticateChangeOperationResponse 
	 * This method calls the service to fetch the change request for a particular account number
	 * 
	 **/
	public AuthenticateChangeOperationResponse getChangeRecords(AuthenticateChangeOperationRequest request) {

		GetChangeRecordsRequest getChangeRecordsRequest = new GetChangeRecordsRequest();
		AuthenticateChangeOperationResponse authenticateChangeOperationResponse = new AuthenticateChangeOperationResponse();
		getChangeRecordsRequest.setAccountNumber(request.getAccountNumber());
		getChangeRecordsRequest.setContext(request.getContext());
		loadParameters(request.getContext(), ActivityConstant.COMMON_ID, ActivityConstant.SEC_COMMON_ID);

		AuthenticateChangeServiceResponse authenticateChangeServiceResponse = authenticateChangeRequestService
				.retrieveChangeRecords(getChangeRecordsRequest);
		
		if(authenticateChangeServiceResponse!=null) {
			LOGGER.debug("Response received: " + authenticateChangeServiceResponse.getResCde());
		} else {
			LOGGER.debug("Null Response receieved");
		}


		if (authenticateChangeServiceResponse != null) {
			authenticateChangeOperationResponse.setSuccess(authenticateChangeServiceResponse.isSuccess());
			authenticateChangeOperationResponse.setResCde(authenticateChangeServiceResponse.getResCde());
			authenticateChangeOperationResponse.setResMsg(authenticateChangeServiceResponse.getResMsg());
			authenticateChangeOperationResponse
					.setServiceResponse(authenticateChangeServiceResponse.getServiceResponse());
			authenticateChangeOperationResponse.setActivityId(authenticateChangeServiceResponse.getActivityId());
			authenticateChangeOperationResponse.setTxnDt(authenticateChangeServiceResponse.getTxnDt());
			authenticateChangeOperationResponse.setDocuments(authenticateChangeServiceResponse.getDocuments());
		}

		return authenticateChangeOperationResponse;
	}

	/**
	 * 
	 * @param request
	 * @return AuthenticateChangeOperationResponse 
	 * This method calls the service to authorize the change requests through USSD
	 * 
	 **/
	@SuppressWarnings("unchecked")
	public AuthenticateChangeOperationResponse updateRecords(AuthenticateChangeOperationRequest request) {

		UpdateRecordsRequest updateRecordsRequest = new UpdateRecordsRequest();
		AuthenticateChangeOperationResponse authenticateChangeOperationResponse = new AuthenticateChangeOperationResponse();
		updateRecordsRequest.setDocuments((List<GetRecordResDocumentInfo>) request.getDocumentDetails());
		updateRecordsRequest.setActivityId(request.getActivityId());
		updateRecordsRequest.setUssdStatus(request.getUssdStatus());
		updateRecordsRequest.setContext(request.getContext());
		loadParameters(request.getContext(), ActivityConstant.COMMON_ID, ActivityConstant.SEC_COMMON_ID);

		AuthenticateChangeServiceResponse authenticateChangeServiceResponse = ussdUpdateRecordsService
				.updateRecords(updateRecordsRequest);
		
		if(authenticateChangeServiceResponse!=null) {
			LOGGER.debug("Response received: " + authenticateChangeServiceResponse.getResCde());
		} else {
			LOGGER.debug("Null Response receieved");
		}

		if (authenticateChangeServiceResponse != null) {
			authenticateChangeOperationResponse.setSuccess(authenticateChangeServiceResponse.isSuccess());
			authenticateChangeOperationResponse.setResCde(authenticateChangeServiceResponse.getResCde());
			authenticateChangeOperationResponse.setResMsg(authenticateChangeServiceResponse.getResMsg());
			authenticateChangeOperationResponse
					.setServiceResponse(authenticateChangeServiceResponse.getServiceResponse());
			authenticateChangeOperationResponse.setTxnDt(authenticateChangeServiceResponse.getTxnDt());
		}

		return authenticateChangeOperationResponse;
	}

}
