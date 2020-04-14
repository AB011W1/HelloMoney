package com.barclays.bmg.operation.payments;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.barclays.bmg.audit.annotation.AuditSupport;
import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.constants.ActivityIdConstantBean;
import com.barclays.bmg.constants.AuditConstant;
import com.barclays.bmg.constants.BillPaymentConstants;
import com.barclays.bmg.constants.FundTransferResponseCodeConstants;
import com.barclays.bmg.constants.SMSConstants;
import com.barclays.bmg.constants.SystemIdConstants;
import com.barclays.bmg.constants.SystemParameterConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dto.CustomerDTO;
import com.barclays.bmg.dto.TransactionDTO;
import com.barclays.bmg.operation.request.fundtransfer.DomesticFundTransferExecuteOperationRequest;
import com.barclays.bmg.operation.response.fundtransfer.DomesticFundTransferExecuteOperationResponse;
import com.barclays.bmg.service.RetrieveIndCustBySCVIDService;
import com.barclays.bmg.service.fundtransfer.DomesticFundTransferService;
import com.barclays.bmg.service.product.request.ListValueResServiceRequest;
import com.barclays.bmg.service.product.response.ListValueResByGroupServiceResponse;
import com.barclays.bmg.service.request.DomesticFundTransferServiceRequest;
import com.barclays.bmg.service.request.RetrieveIndCustBySCVIDServiceRequest;
import com.barclays.bmg.service.response.DomesticFundTransferServiceResponse;
import com.barclays.bmg.service.response.RetrieveIndCustBySCVIDServiceResponse;
import com.barclays.bmg.ussd.auth.service.request.SMSDetailsServiceRequest;
import com.barclays.bmg.ussd.service.SMSDetailsService;
import com.barclays.ussd.dto.UssdBranchLookUpDTO;
import com.barclays.ussd.utils.USSDInputParamsEnum;

public class DomesticFundTransferExecuteOperation extends BMBPaymentsOperation {
    private DomesticFundTransferService domesticFundTransferService;
    private RetrieveIndCustBySCVIDService retrieveIndCustBySCVIDService;

    /**
     * The instance variable for smsDetailsService of type SMSDetailsService
     */
    private SMSDetailsService smsDetailsService;

    public SMSDetailsService getSmsDetailsService() {
	return smsDetailsService;
    }

    public void setSmsDetailsService(SMSDetailsService smsDetailsService) {
	this.smsDetailsService = smsDetailsService;
    }

    @AuditSupport(auditType = AuditConstant.AUDIT_TYPE_ACTIVITY, activityState = AuditConstant.SRC_COM_EXECUTOR, serviceDescription = "SD_MAKE_DOMESTIC_FT", stepId = "3", activityType = "auditDomesticFundTransfer")
    public DomesticFundTransferExecuteOperationResponse makeDomesticFundTransfer(DomesticFundTransferExecuteOperationRequest request) {
	Context context = request.getContext();
	loadParameters(context, ActivityConstant.COMMON_ID, ActivityConstant.SEC_COMMON_ID);
	DomesticFundTransferExecuteOperationResponse response = new DomesticFundTransferExecuteOperationResponse();
	response.setContext(context);
	TransactionDTO transactionDTO = request.getTransactionDTO();

	String systemId = request.getContext().getSystemId();
	if (systemId != null && SystemIdConstants.UB.equals(systemId)) {
	    transactionDTO.setScndLvlAuthTyp("NON"); // !!!!!!!!!!!!!!!!!!! mocked
	}

	if (checkTransactionAbility(request)) {

	    if (transactionDTO.isScndLvlauthReq() && !BillPaymentConstants.AUTH_TYPE_NON.equals(transactionDTO.getScndLvlAuthTyp())) {
		response.setScndLvlAuthReq(true);
		return response;
	    }
	    DomesticFundTransferServiceRequest domesticFundTransferServiceRequest = createServiceRequest(request);
	    // invoke customer info service and set cust name in req context
	    retrieveCustomerFullNameAndSetInContext(domesticFundTransferServiceRequest);
	    makeFundTransfer(domesticFundTransferServiceRequest, transactionDTO, response);
	} else {
	    response.setSuccess(false);
	    response.setResCde(FundTransferResponseCodeConstants.FT_TRANSACTIONABILITY_ERROR);
	}
	return response;
    }

    @Transactional
    private void makeFundTransfer(DomesticFundTransferServiceRequest domesticFundTransferServiceRequest, TransactionDTO transactionDTO,
	    DomesticFundTransferExecuteOperationResponse response) {

    //CPB MakeBillPayment fields DTO - 30/05
    domesticFundTransferServiceRequest.setChargeDTO(transactionDTO.getChargeDTO());
	DomesticFundTransferServiceResponse domesticFundTransferServiceResponse = domesticFundTransferService
		.makeDomesticFundTransfer(domesticFundTransferServiceRequest);

	if (domesticFundTransferServiceResponse != null && domesticFundTransferServiceResponse.isSuccess()) {
	    response.setTrnDate(domesticFundTransferServiceResponse.getTrnDate());
	    response.setTrnRef(domesticFundTransferServiceResponse.getTrnRef());
	    response.setTxnMsg(domesticFundTransferServiceResponse.getTxnMsg());

	    upgradeTransactionLimit(domesticFundTransferServiceRequest, transactionDTO.getTxnAmt().getAmount());
	}
    }

    private void retrieveCustomerFullNameAndSetInContext(DomesticFundTransferServiceRequest domesticFundTransferServiceRequest) {
	// code for getting customer info by scvid
	RetrieveIndCustBySCVIDServiceRequest retrieveIndCustBySCVIDServiceRequest = new RetrieveIndCustBySCVIDServiceRequest();
	CustomerDTO customer = new CustomerDTO();
	customer.setCustomerID(domesticFundTransferServiceRequest.getContext().getCustomerId());
	retrieveIndCustBySCVIDServiceRequest.setCustomer(customer);
	retrieveIndCustBySCVIDServiceRequest.setContext(domesticFundTransferServiceRequest.getContext());
	RetrieveIndCustBySCVIDServiceResponse retrieveIndCustBySCVIDServiceResponse = retrieveIndCustBySCVIDService
		.retrieveIndCustBySCVID(retrieveIndCustBySCVIDServiceRequest);
	if (retrieveIndCustBySCVIDServiceResponse != null) {
	    domesticFundTransferServiceRequest.getContext().setFullName(retrieveIndCustBySCVIDServiceResponse.getContext().getFullName());
	}
	//
    }

    private DomesticFundTransferServiceRequest createServiceRequest(DomesticFundTransferExecuteOperationRequest request) {
	DomesticFundTransferServiceRequest domesticFundTransferServiceRequest = new DomesticFundTransferServiceRequest();
	domesticFundTransferServiceRequest.setContext(request.getContext());
	TransactionDTO transactionDTO = request.getTransactionDTO();
	if (transactionDTO != null && transactionDTO.getBeneficiaryDTO() != null
		&& transactionDTO.getBeneficiaryDTO().getDestinationBankCode() == null) {
	    Context context = request.getContext();
	    Map<String, Object> sysMap = context.getContextMap();
	    String destBankCode = (String) sysMap.get(SystemParameterConstant.BUSINESS_BANK_CODE);
	    
	    //ZM,BW,TZ once off change
	    if(null != transactionDTO.getBankLetter() && null != transactionDTO && null != transactionDTO.getBankCode()) {
	    	
	    	destBankCode = (transactionDTO.getBankCode());
	    }
	    
	    transactionDTO.getBeneficiaryDTO().setDestinationBankCode(destBankCode);
	}
	if (transactionDTO != null) {
	    domesticFundTransferServiceRequest.setTxnAmt(transactionDTO.getTxnAmt());
	    domesticFundTransferServiceRequest.setBeneficiaryDTO(transactionDTO.getBeneficiaryDTO());
	    domesticFundTransferServiceRequest.setFxRateDTO(transactionDTO.getFxRateDTO());
	    domesticFundTransferServiceRequest.setSourceAcct(transactionDTO.getSourceAcct());
	    domesticFundTransferServiceRequest.setTxnNot(transactionDTO.getTxnNot());
	    domesticFundTransferServiceRequest.setTxnTyp(transactionDTO.getTxnType());
	    if(null != transactionDTO.getNib())
	    	domesticFundTransferServiceRequest.getBeneficiaryDTO().setNib(transactionDTO.getNib());
	    
	    //ZMBRB,BWBRB,TZBRB one-ff
	    if(null != transactionDTO.getBankLetter())
	    	domesticFundTransferServiceRequest.setBankLetter(transactionDTO.getBankLetter());
	    
	    
	}
	return domesticFundTransferServiceRequest;
    }

    /**
     * This method sendSMSSuccessAcknowledgment has the purpose to send SMS acknowledgment for successful self registration.
     *
     * @param fudnTransferExecuteOperationResponse
     *
     * @param SelfRegistrationExecutionOperationRequest
     *            void
     */
    public void sendSMSSuccessAcknowledgment(DomesticFundTransferExecuteOperationRequest fundTransferExecuteOperationRequest,
	    DomesticFundTransferExecuteOperationResponse fudnTransferExecuteOperationResponse) {

	SMSDetailsServiceRequest smsRequest = new SMSDetailsServiceRequest();
	if (fundTransferExecuteOperationRequest.getContext().getActivityId() != null
		&& fundTransferExecuteOperationRequest.getContext().getActivityId().equalsIgnoreCase(
			ActivityIdConstantBean.FUND_TRANSFER_EXTERNAL_PAYEE_ACTIVITY_ID)) {
	    smsRequest.setEvent(getSMSEvent(fundTransferExecuteOperationRequest, SMSConstants.SMS_FTEXT_PAYEE_SUCCESS));
	    smsRequest.setPriority(getSMSPriority(fundTransferExecuteOperationRequest, SMSConstants.SMS_FTEXPAYEE_SUCCESS_PRIORITY));
	    smsRequest.setDynamicFields(getSMSPriority(fundTransferExecuteOperationRequest, SMSConstants.SMS_FTEXT_PAYEE_FIELD_SUCCESS));

	}else if (fundTransferExecuteOperationRequest.getContext().getActivityId() != null
			&& fundTransferExecuteOperationRequest.getContext().getActivityId().equalsIgnoreCase(
					ActivityIdConstantBean.GHIPPS_FUND_TRANSFER_OTHER_BANK_ACCOUNTS)) {
			    smsRequest.setEvent(getSMSEvent(fundTransferExecuteOperationRequest, SMSConstants.SMS_FTEXT_PAYEE_SUCCESS));
			    smsRequest.setPriority(getSMSPriority(fundTransferExecuteOperationRequest, SMSConstants.SMS_FTEXPAYEE_SUCCESS_PRIORITY));
			    smsRequest.setDynamicFields(getSMSPriority(fundTransferExecuteOperationRequest, SMSConstants.SMS_FTEXT_PAYEE_FIELD_SUCCESS));

	}else if (fundTransferExecuteOperationRequest.getContext().getActivityId() != null
		&& fundTransferExecuteOperationRequest.getContext().getActivityId().equalsIgnoreCase(
			ActivityIdConstantBean.FUND_TRANSFER_INTERNATIONAL_PAYEE_ACTIVITY_ID)) {
	    smsRequest.setEvent(getSMSEvent(fundTransferExecuteOperationRequest, SMSConstants.SMS_FTINT_PAYEE_SUCCESS));
	    smsRequest.setPriority(getSMSPriority(fundTransferExecuteOperationRequest, SMSConstants.SMS_FTINPAYEE_SUCCESS_PRIORITY));
	    smsRequest.setDynamicFields(getSMSPriority(fundTransferExecuteOperationRequest, SMSConstants.SMS_FTINT_PAYEE_FIELD_SUCCESS));

	} else if (fundTransferExecuteOperationRequest.getContext().getActivityId() != null
		&& fundTransferExecuteOperationRequest.getContext().getActivityId().equalsIgnoreCase(ActivityConstant.PMT_FT_OWN)) {
	    smsRequest.setEvent(getSMSEvent(fundTransferExecuteOperationRequest, SMSConstants.SMS_FTOWN_SUCCESS));
	    smsRequest.setPriority(getSMSPriority(fundTransferExecuteOperationRequest, SMSConstants.SMS_FTOWN_SUCCESS_PRIORITY));
	    smsRequest.setDynamicFields(getSMSPriority(fundTransferExecuteOperationRequest, SMSConstants.SMS_FTOWN_FIELD_SUCCESS));

	} else if (fundTransferExecuteOperationRequest.getContext().getActivityId() != null
		&& fundTransferExecuteOperationRequest.getContext().getActivityId().equalsIgnoreCase(
			ActivityConstant.FUND_TRANSFER_INTERNAL_PAYEE_ACTIVITY_ID)
		|| fundTransferExecuteOperationRequest.getContext().getActivityId().equalsIgnoreCase(
			ActivityIdConstantBean.FUND_TRANSFER_INTERNAL_ONETIME_ACTIVITY_ID)) {
	    smsRequest.setEvent(getSMSEvent(fundTransferExecuteOperationRequest, SMSConstants.SMS_FTINT_PAYEE_SUCCESS));
	    smsRequest.setPriority(getSMSPriority(fundTransferExecuteOperationRequest, SMSConstants.SMS_FTINPAYEE_SUCCESS_PRIORITY));
	    smsRequest.setDynamicFields(getSMSPriority(fundTransferExecuteOperationRequest, SMSConstants.SMS_FTINT_PAYEE_FIELD_SUCCESS));

	}

	Context context = fundTransferExecuteOperationRequest.getContext();

	smsRequest.setParentResponse(fudnTransferExecuteOperationResponse);
	smsRequest.setParentRequest(fundTransferExecuteOperationRequest);

	loadParameters(context, ActivityConstant.COMMON_ID, ActivityConstant.SEC_COMMON_ID);
	smsRequest.setContext(context);
	smsDetailsService.getSMSDetails(smsRequest);

    }

    /**
     * This method sendSMSFailAcknowledgment has the purpose to send SMS acknowledgment for self registration failure.
     *
     * @param fudnTransferExecuteOperationResponse
     *
     * @param SelfRegistrationExecutionOperationRequest
     *            void
     */
    public void sendSMSFailAcknowledgment(DomesticFundTransferExecuteOperationRequest request,
	    DomesticFundTransferExecuteOperationResponse fudnTransferExecuteOperationResponse) {

	SMSDetailsServiceRequest smsRequest = new SMSDetailsServiceRequest();
	if (request.getContext().getActivityId() != null
		&& request.getContext().getActivityId().equalsIgnoreCase(ActivityIdConstantBean.FUND_TRANSFER_EXTERNAL_PAYEE_ACTIVITY_ID)) {
	    smsRequest.setEvent(getSMSEvent(request, SMSConstants.SMS_FTEXT_PAYEE_FAIL));
	    smsRequest.setPriority(getSMSPriority(request, SMSConstants.SMS_FTEXPAYEE_FAIL_PRIORITY));
	    smsRequest.setDynamicFields(getSMSPriority(request, SMSConstants.SMS_FTEXT_PAYEE_FIELD_FAIL));

	} else if (request.getContext().getActivityId() != null
		&& request.getContext().getActivityId().equalsIgnoreCase(ActivityIdConstantBean.FUND_TRANSFER_INTERNATIONAL_PAYEE_ACTIVITY_ID)) {
	    smsRequest.setEvent(getSMSEvent(request, SMSConstants.SMS_FTINT_PAYEE_FAIL));
	    smsRequest.setPriority(getSMSPriority(request, SMSConstants.SMS_FTINPAYEE_FAIL_PRIORITY));
	    smsRequest.setDynamicFields(getSMSPriority(request, SMSConstants.SMS_FTINT_PAYEE_FIELD_FAIL));

	} else if (request.getContext().getActivityId() != null && request.getContext().getActivityId().equalsIgnoreCase(ActivityConstant.PMT_FT_OWN)) {
	    smsRequest.setEvent(getSMSEvent(request, SMSConstants.SMS_FTOWN_FAIL));
	    smsRequest.setPriority(getSMSPriority(request, SMSConstants.SMS_FTOWN_FAIL_PRIORITY));
	    smsRequest.setDynamicFields(getSMSPriority(request, SMSConstants.SMS_FTOWN_FIELD_SUCCESS));

	} else if (request.getContext().getActivityId() != null
		&& request.getContext().getActivityId().equalsIgnoreCase(ActivityConstant.FUND_TRANSFER_INTERNAL_PAYEE_ACTIVITY_ID)
		|| request.getContext().getActivityId().equalsIgnoreCase(ActivityIdConstantBean.FUND_TRANSFER_INTERNAL_ONETIME_ACTIVITY_ID)) {
	    smsRequest.setEvent(getSMSEvent(request, SMSConstants.SMS_FTINT_PAYEE_FAIL));
	    smsRequest.setPriority(getSMSPriority(request, SMSConstants.SMS_FTINPAYEE_FAIL_PRIORITY));
	    smsRequest.setDynamicFields(getSMSPriority(request, SMSConstants.SMS_FTINT_PAYEE_FIELD_SUCCESS));
	}

	Context context = request.getContext();

	smsRequest.setParentResponse(fudnTransferExecuteOperationResponse);
	smsRequest.setParentRequest(request);

	loadParameters(context, ActivityConstant.COMMON_ID, ActivityConstant.SEC_COMMON_ID);
	smsRequest.setContext(context);
	smsDetailsService.getSMSDetails(smsRequest);

    }

    /**
     * This method getSMSEvent has the purpose to get event, for which SMS has to be sent.
     *
     * @param SelfRegistrationExecutionOperationRequest
     * @return String
     */
    private String getSMSEvent(DomesticFundTransferExecuteOperationRequest fundTransferExecuteOperationRequest, String smsKey) {

	ListValueResServiceRequest listValueResServiceRequest = new ListValueResServiceRequest();
	listValueResServiceRequest.setContext(fundTransferExecuteOperationRequest.getContext());
	listValueResServiceRequest.setListValueKey(smsKey);
	ListValueResByGroupServiceResponse listValueResByGroupServiceResponse = listValueResService.getListValueByKey(listValueResServiceRequest);

	String event = listValueResByGroupServiceResponse.getListValueCahceDTO().get(0).getLabel();
	return event;

    }

    /**
     * This method getSMSPriority has the purpose to get the priority of the SMS to be sent.
     *
     * @param SelfRegistrationExecutionOperationRequest
     * @return String
     */
    private String getSMSPriority(DomesticFundTransferExecuteOperationRequest request, String smsKey) {

	ListValueResServiceRequest listValueResServiceRequest = new ListValueResServiceRequest();
	listValueResServiceRequest.setContext(request.getContext());
	listValueResServiceRequest.setListValueKey(smsKey);
	ListValueResByGroupServiceResponse listValueResByGroupServiceResponse = listValueResService.getListValueByKey(listValueResServiceRequest);

	String priority = listValueResByGroupServiceResponse.getListValueCahceDTO().get(0).getLabel();
	return priority;

    }

    public DomesticFundTransferService getDomesticFundTransferService() {
	return domesticFundTransferService;
    }

    public void setDomesticFundTransferService(DomesticFundTransferService domesticFundTransferService) {
	this.domesticFundTransferService = domesticFundTransferService;
    }

    public void setRetrieveIndCustBySCVIDService(RetrieveIndCustBySCVIDService retrieveIndCustBySCVIDService) {
	this.retrieveIndCustBySCVIDService = retrieveIndCustBySCVIDService;
    }
}
