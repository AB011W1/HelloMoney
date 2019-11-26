package com.barclays.bmg.dao.operation.USSDAuthentication;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.datacontract.schemas._2004._07.DocumentManager_Models.Document;
import org.datacontract.schemas._2004._07.DocumentManager_Models.DocumentActionResult;
import org.datacontract.schemas._2004._07.DocumentManager_Models.Field;
import org.datacontract.schemas._2004._07.DocumentManager_Models.GetResult;

import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.service.request.USSDAuthentication.UpdateRecordsRequest;
import com.barclays.bmg.service.response.USSDAuthentication.AuthenticateChangeServiceResponse;
import com.barclays.bmg.service.response.USSDAuthentication.GetRecordResDocumentInfo;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;

public class AuthenticateChangeResAdptOperation extends ResponseContext {

	private static final Logger LOGGER = Logger.getLogger(AuthenticateChangeResAdptOperation.class);

	/**
	 * @param WorkContext
	 *            workContext
	 * @return AuthenticateChangeServiceResponse
	 *
	 *         This method constructs the response to display the change request
	 *         summary
	 * 
	 */
	public AuthenticateChangeServiceResponse adaptResponse(WorkContext workContext, Object obj) {

		GetResult response = (GetResult) obj;
		AuthenticateChangeServiceResponse authenticateChangeServiceResponse = new AuthenticateChangeServiceResponse();
		List<GetRecordResDocumentInfo> documentsInfo = new ArrayList<GetRecordResDocumentInfo>();

		if (null != response) {
			if (response.getActionSuccessful() && response.getRecords() != null) {

				LOGGER.debug("Successful Response for adaptResponse service");
				authenticateChangeServiceResponse.setSuccess(Boolean.TRUE);
				authenticateChangeServiceResponse.setResMsg(ResponseCodeConstants.SUCCESS_TXN_RESPONSE_MESSAGE);
				authenticateChangeServiceResponse.setResCde(ResponseCodeConstants.SUCCESS_RES_CODE);
				authenticateChangeServiceResponse.setServiceResponse(response.getActivityID());
				Document[] records = response.getRecords();

				for (Document doc : records) {

					GetRecordResDocumentInfo getRecordResDocumentInfo = new GetRecordResDocumentInfo();
					Field[] fields = doc.getFields();

					for (Field field : fields) {
						if (USSDConstants.OPTIMISTIC_LOCK_ID.equals(field.getFieldName())) {
							getRecordResDocumentInfo.setOptimisticLockID(field.getFieldValue());

						} else if (USSDConstants.CASE_TYPE.equals(field.getFieldName())) {
							getRecordResDocumentInfo.setCaseType(field.getFieldValue());

						} else if (USSDConstants.USSD_STATUS.equals(field.getFieldName())) {
							getRecordResDocumentInfo.setUssdStatus(field.getFieldValue());

						} else if (USSDConstants.FIELD_NAME.equals(field.getFieldName())) {
							getRecordResDocumentInfo.setFieldName(field.getFieldValue());

						} else if (USSDConstants.OLD_VALUE.equals(field.getFieldName())) {
							getRecordResDocumentInfo.setOldValue(field.getFieldValue());

						} else if (USSDConstants.NEW_VALUE.equals(field.getFieldName())) {
							getRecordResDocumentInfo.setNewValue(field.getFieldValue());

						}
					}
					getRecordResDocumentInfo.setDocId(doc.getID());
					documentsInfo.add(getRecordResDocumentInfo);
				}

				authenticateChangeServiceResponse.setDocuments(documentsInfo);
				authenticateChangeServiceResponse.setActivityId(response.getActivityID());
			} else {

				LOGGER.debug("Failure Message in adaptResponse: " + response.getFailureMessage());
				authenticateChangeServiceResponse.setSuccess(Boolean.FALSE);
				authenticateChangeServiceResponse.setResCde(USSDExceptions.USSD_AUTH_LOGIN_FAIL.getBmgCode());
				authenticateChangeServiceResponse.setResMsg(response.getFailureMessage());
				authenticateChangeServiceResponse.setServiceResponse(response.getFailureMessage());
			}
			authenticateChangeServiceResponse.setTxnDt(new Date());

		} else {
			authenticateChangeServiceResponse.setSuccess(Boolean.FALSE);
		}

		return authenticateChangeServiceResponse;
	}

	/**
	 * @param String
	 *            failureMessage
	 * @return AuthenticateChangeServiceResponse
	 *
	 *         This method constructs the failure response for Login service
	 * 
	 */
	public AuthenticateChangeServiceResponse adaptLoginFailureResponse(String failureMessage) {

		LOGGER.debug("Failure Message in adaptLoginFailureResponse: " + failureMessage);

		AuthenticateChangeServiceResponse authenticateChangeServiceResponse = new AuthenticateChangeServiceResponse();
		authenticateChangeServiceResponse.setServiceResponse(failureMessage);
		authenticateChangeServiceResponse.setResCde(USSDExceptions.USSD_AUTH_LOGIN_FAIL.getBmgCode());
		authenticateChangeServiceResponse.setSuccess(false);
		authenticateChangeServiceResponse.setResMsg(failureMessage);

		return authenticateChangeServiceResponse;
	}

	/**
	 * @param WorkContext
	 *            workContext, DocumentActionResult response
	 * @return AuthenticateChangeServiceResponse
	 *
	 *         This method constructs the response for updateRecords service after
	 *         Approval/Rejection of change requests
	 * 
	 */
	public AuthenticateChangeServiceResponse adaptUpdateRecordsResponse(WorkContext workContext,
			DocumentActionResult response) {

		AuthenticateChangeServiceResponse authenticateChangeServiceResponse = new AuthenticateChangeServiceResponse();
		DAOContext daoContext = (DAOContext) workContext;
		Object[] args = daoContext.getArguments();
		UpdateRecordsRequest request = (UpdateRecordsRequest) args[0];

		if (response.getActionSuccessful() && response.getFailureMessage() == null) {

			LOGGER.debug("Successful Response for adaptUpdateRecordsResponse service");
			authenticateChangeServiceResponse.setSuccess(Boolean.TRUE);
			authenticateChangeServiceResponse.setResMsg(ResponseCodeConstants.SUCCESS_TXN_RESPONSE_MESSAGE);
			authenticateChangeServiceResponse.setResCde(ResponseCodeConstants.SUCCESS_RES_CODE);
			authenticateChangeServiceResponse.setServiceResponse(request.getUssdStatus());
			
		} else {

			LOGGER.debug("Failure Message in adaptUpdateRecordsResponse: " + response.getFailureMessage());
			authenticateChangeServiceResponse.setSuccess(Boolean.FALSE);
			authenticateChangeServiceResponse.setResCde(USSDExceptions.USSD_AUTH_LOGIN_FAIL.getBmgCode());
			authenticateChangeServiceResponse.setResMsg(response.getFailureMessage());
			authenticateChangeServiceResponse.setServiceResponse(response.getFailureMessage());
		}

		authenticateChangeServiceResponse.setTxnDt(new Date());

		return authenticateChangeServiceResponse;
	}

}
