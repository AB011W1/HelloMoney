package com.barclays.bmg.operation.payments;

import java.math.BigDecimal;

import com.barclays.bmg.cashsend.operation.request.CashSendOneTimeExecuteOperationRequest;
import com.barclays.bmg.cashsend.operation.response.CashSendOneTimeExecuteOperationResponse;
import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.constants.SMSConstants;
import com.barclays.bmg.constants.SystemIdConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dto.CustomerDTO;
import com.barclays.bmg.dto.TransactionDTO;
import com.barclays.bmg.exception.BMBDataAccessException;
import com.barclays.bmg.operation.BMBCommonOperation;
import com.barclays.bmg.operation.request.billpayment.ManageFundtransferStatusOperationRequest;
import com.barclays.bmg.operation.response.billpayment.ManageFundtransferStatusOperationResponse;
import com.barclays.bmg.service.ManageFundtransferStatusService;
import com.barclays.bmg.service.RetrieveIndCustBySCVIDService;
import com.barclays.bmg.service.accounts.SendMultipleNotificationsService;
import com.barclays.bmg.service.product.request.ListValueResServiceRequest;
import com.barclays.bmg.service.product.response.ListValueResByGroupServiceResponse;
import com.barclays.bmg.service.request.ManageFundtransferStatusServiceRequest;
import com.barclays.bmg.service.request.RetrieveIndCustBySCVIDServiceRequest;
import com.barclays.bmg.service.request.SendMultipleNotificationsServiceRequest;
import com.barclays.bmg.service.response.ManageFundtransferStatusServiceResponse;
import com.barclays.bmg.service.response.RetrieveIndCustBySCVIDServiceResponse;
import com.barclays.bmg.ussd.auth.service.request.SMSDetailsServiceRequest;

public class ManageFundtransferStatusOperation extends BMBPaymentsOperation {
	private ManageFundtransferStatusService manageFundtransferStatusService;
	private RetrieveIndCustBySCVIDService retrieveIndCustBySCVIDService;
	SendMultipleNotificationsService sendMultipleNotificationsService;

	public SendMultipleNotificationsService getSendMultipleNotificationsService() {
		return sendMultipleNotificationsService;
	}

	public void setSendMultipleNotificationsService(
			SendMultipleNotificationsService sendMultipleNotificationsService) {
		this.sendMultipleNotificationsService = sendMultipleNotificationsService;
	}

	public RetrieveIndCustBySCVIDService getRetrieveIndCustBySCVIDService() {
		return retrieveIndCustBySCVIDService;
	}

	public void setRetrieveIndCustBySCVIDService(
			RetrieveIndCustBySCVIDService retrieveIndCustBySCVIDService) {
		this.retrieveIndCustBySCVIDService = retrieveIndCustBySCVIDService;
	}

	public ManageFundtransferStatusService getManageFundtransferStatusService() {
		return manageFundtransferStatusService;
	}

	public void setManageFundtransferStatusService(
			ManageFundtransferStatusService manageFundtransferStatusService) {
		this.manageFundtransferStatusService = manageFundtransferStatusService;
	}

	public ManageFundtransferStatusOperationResponse manageFundtransferStatus(
			ManageFundtransferStatusOperationRequest request) {
		Context context = request.getContext();
		ManageFundtransferStatusOperationResponse response = new ManageFundtransferStatusOperationResponse();
		response.setContext(context);
		loadParameters(context, ActivityConstant.COMMON_ID,
				ActivityConstant.SEC_COMMON_ID);
		TransactionDTO transactionDTO = request.getTransactionDTO();

		ManageFundtransferStatusServiceRequest manageFundTransferServiceRequest = createServiceRequest(request);

		manageFundtransferStatus(manageFundTransferServiceRequest,
				transactionDTO, response);

		return response;
	}

	private void manageFundtransferStatus(
			ManageFundtransferStatusServiceRequest manageFundTransferServiceRequest,
			TransactionDTO transactionDTO,
			ManageFundtransferStatusOperationResponse response) {
		// TODO Auto-generated method stub
		retrieveCustomerFullNameAndSetInContext(manageFundTransferServiceRequest);
		ManageFundtransferStatusServiceResponse manageFundtransferStatusServiceResponse = null;

		try {
			manageFundtransferStatusServiceResponse = manageFundtransferStatusService
					.manageFundTransferStatus(manageFundTransferServiceRequest);
		} catch (BMBDataAccessException dae) {
			if (inprogressErrorCodeList.contains(dae.getErrorCode())) {
				upgradeTransactionLimit(manageFundTransferServiceRequest, transactionDTO
						.getTxnAmtInLCY());
			}
			throw dae;
		}

		if (manageFundtransferStatusServiceResponse != null
				&& manageFundtransferStatusServiceResponse.isSuccess()) {
			response.setServiceResponseCode(manageFundtransferStatusServiceResponse.getServiceResponseCode());
			response.setErrorList(manageFundtransferStatusServiceResponse.getErrorList());
			response.setErrorCode(manageFundtransferStatusServiceResponse.getErrorCode());
			response.setErrorDesc(manageFundtransferStatusServiceResponse.getErrorDesc());
			response.setTxnRefNo(manageFundtransferStatusServiceResponse.getTxnRefNo());
			response.setTxnDt(manageFundtransferStatusServiceResponse.getTxnDt());
			response.setMobileNos(manageFundtransferStatusServiceResponse.getMobileNos());
			upgradeTransactionLimit(manageFundTransferServiceRequest,new BigDecimal(manageFundTransferServiceRequest.getDebitAmount()));
		}
	}

	private ManageFundtransferStatusServiceRequest createServiceRequest(
			ManageFundtransferStatusOperationRequest operationRequest) {
		ManageFundtransferStatusServiceRequest serviceRequest = new ManageFundtransferStatusServiceRequest();
		serviceRequest.setBankCIF(operationRequest.getBankCIF());
		serviceRequest.setBusinessID(operationRequest.getBusinessID());
		serviceRequest.setContext(operationRequest.getContext());
		serviceRequest.setCreditAmount(operationRequest.getCreditAmount());
		serviceRequest.setDebitAmount(operationRequest.getDebitAmount());
		serviceRequest.setInitiatingCustomerFullName(operationRequest
				.getInitiatingCustomerFullName());
		serviceRequest.setRemarks(operationRequest.getRemarks());
		serviceRequest.setScvId(operationRequest.getScvId());
		serviceRequest.setSystemID(operationRequest.getSystemID());
		serviceRequest.setTransactionDTO(operationRequest.getTransactionDTO());
		serviceRequest.setTransactionNumber(operationRequest
				.getTransactionNumber());
		serviceRequest.setTransactionStatus(operationRequest
				.getTransactionStatus());
		serviceRequest.setUserId(operationRequest.getUserId());
		serviceRequest.setActionCode(operationRequest.getActionCode());
		serviceRequest.setAccno(operationRequest.getAccno());
		serviceRequest.setBillerId(operationRequest.getBillerId());
		serviceRequest.setBillerName(operationRequest.getBillerName());
		serviceRequest.setAcconoToCredit(operationRequest.getAcconoToCredit());
		serviceRequest.setFundTransferType(operationRequest.getFundTransferType());
		serviceRequest.setTransactionStatusMessage(operationRequest.getTransactionStatusMessage());
		return serviceRequest;
	}

	private void retrieveCustomerFullNameAndSetInContext(
			ManageFundtransferStatusServiceRequest payBillServiceRequest) {
		// code for getting customer info by scvid
		RetrieveIndCustBySCVIDServiceRequest retrieveIndCustBySCVIDServiceRequest = new RetrieveIndCustBySCVIDServiceRequest();
		CustomerDTO customer = new CustomerDTO();
		customer.setCustomerID(payBillServiceRequest.getContext()
				.getCustomerId());
		retrieveIndCustBySCVIDServiceRequest.setCustomer(customer);
		retrieveIndCustBySCVIDServiceRequest.setContext(payBillServiceRequest
				.getContext());
		RetrieveIndCustBySCVIDServiceResponse retrieveIndCustBySCVIDServiceResponse = retrieveIndCustBySCVIDService
				.retrieveIndCustBySCVID(retrieveIndCustBySCVIDServiceRequest);
		if (retrieveIndCustBySCVIDServiceResponse != null) {
			payBillServiceRequest.getContext().setFullName(
					retrieveIndCustBySCVIDServiceResponse.getContext()
							.getFullName());
		}
		//
	}

	 public void transactionInitiatedSMSAcknowledgment(
			 ManageFundtransferStatusOperationRequest request, String[] mobileNos) {

		SendMultipleNotificationsServiceRequest smsRequest = new SendMultipleNotificationsServiceRequest();
		Context context = request.getContext();

		loadParameters(context, ActivityConstant.COMMON_ID,
				ActivityConstant.SEC_COMMON_ID);
		smsRequest.setContext(context);

		smsRequest.setEventId(getSMSEvent(request, SMSConstants.SMS_TRAN_INITIATE_SUCCESS));
		smsRequest.setPriority(getSMSPriority(request,
				SMSConstants.SMS_TRAN_INITIATE_SUCCESS_PRIORITY));
		smsRequest.setDynamicFields(getDynamicFields(request,
				SMSConstants.SMS_TRAN_INITIATE_SUCCESS_FIELDS));
		smsRequest.setAccountNo(request.getAccno().trim());
		smsRequest.setAmount(request.getDebitAmount());
		smsRequest.setMobileNo(mobileNos);
		smsRequest.setTransId(request.getTransactionNumber());
		smsRequest.setUserRefno("0");
		sendMultipleNotificationsService.sendMultipleNotifications(smsRequest);
	}

	 public void transactionApprovedSMSAcknowledgment(
			 ManageFundtransferStatusOperationRequest request,String[] mobileNos) {

		SendMultipleNotificationsServiceRequest smsRequest = new SendMultipleNotificationsServiceRequest();
		Context context = request.getContext();

		loadParameters(context, ActivityConstant.COMMON_ID,
				ActivityConstant.SEC_COMMON_ID);
		smsRequest.setContext(context);

		smsRequest.setEventId(getSMSEvent(request, SMSConstants.SMS_TRAN_APPROVED));
		smsRequest.setPriority(getSMSPriority(request,
				SMSConstants.SMS_TRAN_APPROVED_PRIORITY));
		smsRequest.setDynamicFields(getDynamicFields(request,
				SMSConstants.SMS_TRAN_APPROVED_FIELDS));
		smsRequest.setAccountNo(request.getAccno().trim());
		smsRequest.setAmount(request.getDebitAmount());
		smsRequest.setMobileNo(mobileNos);
		smsRequest.setTransId(request.getTransactionNumber());
		smsRequest.setUserRefno("0");
		sendMultipleNotificationsService.sendMultipleNotifications(smsRequest);
	}

	 public void transactionRejectedSMSAcknowledgment(
			 ManageFundtransferStatusOperationRequest request,String[] mobileNos) {

		SendMultipleNotificationsServiceRequest smsRequest = new SendMultipleNotificationsServiceRequest();
		Context context = request.getContext();

		loadParameters(context, ActivityConstant.COMMON_ID,
				ActivityConstant.SEC_COMMON_ID);
		smsRequest.setContext(context);

		smsRequest.setEventId(getSMSEvent(request, SMSConstants.SMS_TRAN_REJECTED));
		smsRequest.setPriority(getSMSPriority(request,
				SMSConstants.SMS_TRAN_REJECTED_PRIORITY));
		smsRequest.setDynamicFields(getDynamicFields(request,
				SMSConstants.SMS_TRAN_REJECTED_FIELDS));
		smsRequest.setAccountNo(request.getAccno().trim());
		smsRequest.setAmount(request.getDebitAmount());
		smsRequest.setMobileNo(mobileNos);
		smsRequest.setTransId(request.getTransactionNumber());
		smsRequest.setUserRefno("0");
		sendMultipleNotificationsService.sendMultipleNotifications(smsRequest);
	}

	private String getSMSEvent(ManageFundtransferStatusOperationRequest request,
			String smsKey) {
		ListValueResServiceRequest listValueResServiceRequest = new ListValueResServiceRequest();
		listValueResServiceRequest.setContext(request.getContext());
		listValueResServiceRequest.setListValueKey(smsKey);
		ListValueResByGroupServiceResponse listValueResByGroupServiceResponse = listValueResService
				.getListValueByKey(listValueResServiceRequest);
		String event = listValueResByGroupServiceResponse
				.getListValueCahceDTO().get(0).getLabel();
		return event;
	}

	private String getSMSPriority(
			ManageFundtransferStatusOperationRequest request, String smsKey) {
		ListValueResServiceRequest listValueResServiceRequest = new ListValueResServiceRequest();
		listValueResServiceRequest.setContext(request.getContext());
		listValueResServiceRequest.setListValueKey(smsKey);
		ListValueResByGroupServiceResponse listValueResByGroupServiceResponse = listValueResService
				.getListValueByKey(listValueResServiceRequest);
		String priority = listValueResByGroupServiceResponse
				.getListValueCahceDTO().get(0).getLabel();
		return priority;

	}

	private String getDynamicFields(
			ManageFundtransferStatusOperationRequest request, String smsKey) {
		ListValueResServiceRequest listValueResServiceRequest = new ListValueResServiceRequest();
		listValueResServiceRequest.setContext(request.getContext());
		listValueResServiceRequest.setListValueKey(smsKey);
		ListValueResByGroupServiceResponse listValueResByGroupServiceResponse = listValueResService
				.getListValueByKey(listValueResServiceRequest);
		String event = listValueResByGroupServiceResponse
				.getListValueCahceDTO().get(0).getLabel();
		return event;
	}

}