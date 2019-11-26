package com.barclays.bmg.dao.service.impl.USSDAuthentication;

import java.rmi.RemoteException;

import org.apache.log4j.Logger;
import org.datacontract.schemas._2004._07.DocumentManager_Models.DocumentActionResult;
import org.datacontract.schemas._2004._07.DocumentManager_Models.DocumentManagementAllocation;
import org.datacontract.schemas._2004._07.DocumentManager_Models.GetRecord;
import org.datacontract.schemas._2004._07.DocumentManager_Models.GetResult;
import org.datacontract.schemas._2004._07.DocumentManager_Models.Login;
import org.datacontract.schemas._2004._07.DocumentManager_Models.LoginResult;
import org.tempuri.IDocumentHandlerProxy;

public class AuthenticateChangetransmissionService {
	private static final Logger LOGGER = Logger.getLogger(AuthenticateChangetransmissionService.class);

	/**
	 * @param Login login, String serviceEndPoint
	 * @return LoginResult
	 *
	 * This is the service method to call Sybrin Login API
	 * 
	 */
	public LoginResult loginRequest(Login login, String serviceEndPoint) throws RemoteException {

		LOGGER.debug("Call to Sybrin Login service");

		IDocumentHandlerProxy handler = new IDocumentHandlerProxy(serviceEndPoint);
		LoginResult result = handler.login(login);

		if (result.getIsLoggedIn()) {
			LOGGER.debug("Login Result: IsLoggedIn" + result.getIsLoggedIn());
		} else {
			LOGGER.debug("Login Failure message: " + result.getFailureMessage());
		}

		return result;

	}

	/**
	 * @param GetRecord
	 *            records, String serviceEndPoint
	 * @return GetResult
	 *
	 * This is the service method to call Sybrin getRecords API
	 * 
	 */
	public GetResult getChangeRecords(GetRecord records, String serviceEndPoint) throws RemoteException {

		LOGGER.debug("Call to Sybrin getRecords service");

		IDocumentHandlerProxy handler = new IDocumentHandlerProxy(serviceEndPoint);
		GetResult getResult = handler.getRecords(records);

		if (getResult != null && getResult.getFailureMessage() != null) {
			LOGGER.debug("getRecords Failure message: " + getResult.getFailureMessage());
		}

		return getResult;

	}

	/**
	 * @param DocumentManagementAllocation
	 *            documentManagementAllocation, String serviceEndPoint
	 * @return DocumentActionResult
	 *
	 * This is the service method to call Sybrin updateRecords API
	 * 
	 */
	public DocumentActionResult updateRecords(DocumentManagementAllocation documentManagementAllocation,
			String serviceEndPoint) throws RemoteException {

		LOGGER.debug("Call to Sybrin updateRecords service");

		IDocumentHandlerProxy handler = new IDocumentHandlerProxy(serviceEndPoint);
		DocumentActionResult updateRecordsResult = handler.updateRecords(documentManagementAllocation);

		if (updateRecordsResult != null && updateRecordsResult.getFailureMessage() != null) {
			LOGGER.debug("updateRecords Failure message: " + updateRecordsResult.getFailureMessage());
		}

		return updateRecordsResult;

	}

}
