package com.barclays.bmg.dao.operation.USSDAuthentication;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.datacontract.schemas._2004._07.DocumentManager_Models.Document;
import org.datacontract.schemas._2004._07.DocumentManager_Models.DocumentManagementAllocation;
import org.datacontract.schemas._2004._07.DocumentManager_Models.Field;
import org.datacontract.schemas._2004._07.DocumentManager_Models.File;
import org.datacontract.schemas._2004._07.DocumentManager_Models.GetRecord;
import org.datacontract.schemas._2004._07.DocumentManager_Models.Login;
import org.datacontract.schemas._2004._07.DocumentManager_Models.LoginResult;
import org.datacontract.schemas._2004._07.DocumentManager_Models.SearchAttribute;

import com.barclays.bmg.constants.RequestConstants;
import com.barclays.bmg.constants.SystemParameterConstant;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.operation.BMBCommonOperation;
import com.barclays.bmg.service.request.USSDAuthentication.GetChangeRecordsRequest;
import com.barclays.bmg.service.request.USSDAuthentication.UpdateRecordsRequest;
import com.barclays.bmg.service.response.USSDAuthentication.GetRecordResDocumentInfo;
import com.barclays.ussd.exception.USSDBlockingException;

public class AuthenticateChangeReqAdptOperation extends BMBCommonOperation {

	private static final Logger LOGGER = Logger.getLogger(AuthenticateChangeReqAdptOperation.class);
	private static String logonToken;

	/**
	 * @param workContext
	 * @return Login Object
	 *
	 * This method creates the login object with the required parameters to
	 * call Sybrin Login service. There are 2 parameters: UserName & Password in Login object
	 */
	public Login loginRequest(WorkContext workContext) {

		LOGGER.debug("Call to loginRequest method to set UserName & Password");
		
		DAOContext daoContext = (DAOContext) workContext;
		Object[] args = daoContext.getArguments();
		GetChangeRecordsRequest request = (GetChangeRecordsRequest) args[0];
		Map<String, Object> systemParameterMap = request.getContext().getContextMap();

		String userName = systemParameterMap != null ? systemParameterMap.get(SystemParameterConstant.AUTH_REQUEST_LOGIN_USERNAME).toString()
				: null;
		String password = systemParameterMap != null ? systemParameterMap.get(SystemParameterConstant.AUTH_REQUEST_LOGIN_PASSWORD).toString()
				: null;

		//Login Request
		Login loginObject = new Login();
		loginObject.setSybrinUser(userName);
		loginObject.setSybrinPassword(password);

		LOGGER.debug("UserName & Password to call Sybrin login service: " + loginObject.getSybrinUser() + " & "
				+ loginObject.getSybrinPassword());

		return loginObject;
	}

	/**
	 * @param WorkContext
	 *            workContext,LoginResult result
	 * @return GetRecord
	 *
	 * This method creates the GetRecord object with the required
	 * parameters, to call Sybrin getRecords service for pending change request.
	 * 
	 */
	public GetRecord getChangeRecords(WorkContext workContext, LoginResult result) throws USSDBlockingException {

		LOGGER.debug("Call to getChangeRecords method to set accountNumber for change request");
		
		logonToken = result.getLoginToken();
		DAOContext daoContext = (DAOContext) workContext;
		Object[] args = daoContext.getArguments();
		GetChangeRecordsRequest request = (GetChangeRecordsRequest) args[0];
		Map<String, Object> systemParameterMap = request.getContext().getContextMap();

		String accountNo = request.getAccountNumber();
		StringBuilder searchCriteria = new StringBuilder("$filter=AccountIdentifier eq ");
		searchCriteria.append("\'").append(accountNo).append("\' and USSDStatus eq \'PENDING\'");
		SearchAttribute attribute = new SearchAttribute();
		attribute.setAttribute(searchCriteria.toString());

		SearchAttribute[] searchAttributes = new SearchAttribute[] { attribute };
		String entityID = systemParameterMap.get(SystemParameterConstant.AUTH_REQUEST_ENTITY_ID.toString()).toString();
		String lockRecords = systemParameterMap.get(SystemParameterConstant.AUTH_REQUEST_LOCK_RECORDS).toString();
		boolean islockRecords = true;
		if ("N".equals(lockRecords)) {
			islockRecords = false;
		}

		//Get Records Request
		GetRecord getRecordsObj = new GetRecord();
		getRecordsObj.setEntityID(entityID);
		getRecordsObj.setLockRecords(islockRecords);
		getRecordsObj.setLogonToken(logonToken);
		getRecordsObj.setSearchAttributes(searchAttributes);

		return getRecordsObj;

	}

	/**
	 * @param WorkContext
	 *            workContext
	 * @return DocumentManagementAllocation
	 * 
	 * This method creates the DocumentManagementAllocation object with the
	 * required parameters, to call Sybrin updateRecords service to approve/reject the change request.
	 * 
	 */
	public DocumentManagementAllocation updateRecords(WorkContext workContext) throws USSDBlockingException {

		LOGGER.debug("Call to updateRecords method to send APPROVE/REJECT status");
		
		DAOContext daoContext = (DAOContext) workContext;
		Object[] args = daoContext.getArguments();
		UpdateRecordsRequest request = (UpdateRecordsRequest) args[0];
		Map<String, Object> systemParameterMap = request.getContext().getContextMap();

		String sysParamEntityId = systemParameterMap.get(SystemParameterConstant.AUTH_REQUEST_ENTITY_ID.toString()).toString();
		String sysParamProcessId = systemParameterMap.get(SystemParameterConstant.AUTH_REQUEST_PROCESS_ID.toString()).toString();
		String activityId = request.getActivityId();
		String ussdStatus = request.getUssdStatus();
		List<GetRecordResDocumentInfo> documents = request.getDocuments();
		Document[] documentsArray = new Document[documents.size()];

		for (int i = 0; i < documents.size(); i++) {
			GetRecordResDocumentInfo documentInfo = documents.get(i);
			Field[] updateFields = new Field[2];
			Document updateDocument = new Document();

			// For sending optimisticId
			Field optimisticIdField = new Field();
			String fieldName = RequestConstants.AUTH_REQUEST_OPTIMISTIC_LOCK_ID;
			String fieldValue = documentInfo.getOptimisticLockID();
			optimisticIdField.setFieldName(fieldName);
			optimisticIdField.setFieldValue(fieldValue);

			// For sending USSD Status
			Field ussdStatusField = new Field();
			String fieldName1 = RequestConstants.AUTH_REQUEST_USSD_STATUS;
			String fieldValue1 = ussdStatus;
			ussdStatusField.setFieldName(fieldName1);
			ussdStatusField.setFieldValue(fieldValue1);

			updateFields[0] = optimisticIdField;
			updateFields[1] = ussdStatusField;

			updateDocument.setDocumentState(RequestConstants.AUTH_REQUEST_DOCUMENT_STATE);
			updateDocument.setEntityID(sysParamEntityId);
			updateDocument.setFields(updateFields);
			updateDocument.setID(documentInfo.getDocId());
			File documentFile[] = {};
			Document linkedDocuments[] = {};
			updateDocument.setFiles(documentFile);
			updateDocument.setLinkedDocuments(linkedDocuments);
			documentsArray[i] = updateDocument;

		}

		//UpdateRecords Request
		DocumentManagementAllocation documentManagementAllocation = new DocumentManagementAllocation();
		documentManagementAllocation.setAcitivityID(activityId);
		documentManagementAllocation.setDocuments(documentsArray);
		documentManagementAllocation.setProcessID(sysParamProcessId);
		documentManagementAllocation.setLogonToken(logonToken);

		return documentManagementAllocation;

	}
}
