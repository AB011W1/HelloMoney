package com.barclays.bmg.operation.beneficiary;

import java.util.List;
import java.util.Map;

import com.barclays.bmg.audit.annotation.AuditSupport;
import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.constants.AddOrgBeneficiaryConstants;
import com.barclays.bmg.constants.AuditConstant;
import com.barclays.bmg.constants.SMSConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.dto.BillerDTO;
import com.barclays.bmg.operation.payments.BMBPaymentsOperation;
import com.barclays.bmg.operation.request.fundtransfer.external.AddOrgBenefeciaryOperationRequest;
import com.barclays.bmg.operation.response.fundtransfer.external.AddOrgBeneficiaryOperationResponse;
import com.barclays.bmg.service.product.request.ListValueResServiceRequest;
import com.barclays.bmg.service.product.response.ListValueResByGroupServiceResponse;
import com.barclays.bmg.service.request.AddOrganizationBeneficiaryServiceRequest;
import com.barclays.bmg.service.request.BillerServiceRequest;
import com.barclays.bmg.service.response.AddOrgBeneficiaryServiceResponse;
import com.barclays.bmg.service.response.BillerServiceResponse;
import com.barclays.bmg.ussd.auth.service.request.SMSDetailsServiceRequest;
import com.barclays.bmg.ussd.service.SMSDetailsService;

/**
 * @author E20041929 This operation contains multiple operations track for Biller registration service
 *
 */
public class AddOrgBeneficiaryOperation extends BMBPaymentsOperation {

    public Map<String, String> payGroupTxnType;
    private static final String AIR_TIME = "AT";
    private static final String MOBILE_WALLET = "WT";
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

    @Override
    public List<BeneficiaryDTO> retrievePayeeList(RequestContext request, ResponseContext response) {

	// TODO Auto-generated method stub
	return super.retrievePayeeList(request, response);
    }

    @Override
    public List<BillerDTO> getAllBillerList(RequestContext request) {

	return super.getAllBillerList(request);
    }

    /**
     * Get all biller list.
     *
     * @param request
     * @return
     */
    public List<BillerDTO> getBillPaymentsBillerList(AddOrgBenefeciaryOperationRequest request) {
	BillerServiceRequest billerServiceRequest = new BillerServiceRequest();
	billerServiceRequest.setContext(request.getContext());
	billerServiceRequest.setBillerCategoryId(request.getBillerType());
	billerServiceRequest.setBillerName(request.getBillerId());

	BillerServiceResponse billerServiceResponse = super.getBillerService().getBillPaymentsBillerList(billerServiceRequest);
	return billerServiceResponse.getBillerList();
    }

    @AuditSupport(auditType = AuditConstant.AUDIT_TYPE_TRANSACTION, activityState = AuditConstant.SRC_COM_EXECUTOR, serviceDescription = "SD_ADD_ORG_PAYEE", stepId = "3", activityType = "addOrgBeneficiary")
    public AddOrgBeneficiaryOperationResponse registerBillers(AddOrgBenefeciaryOperationRequest billerRegistrationOpRequest) {

	AddOrgBeneficiaryOperationResponse operationResponse = new AddOrgBeneficiaryOperationResponse();

	AddOrgBeneficiaryServiceResponse response = new AddOrgBeneficiaryServiceResponse();
	Context context = billerRegistrationOpRequest.getContext();
	response.setContext(context);
	//CR82 Changes
	String payGrp=null;
	String isEditFlow=null;
	if(context.getContextMap().containsKey("payGrp")){
		payGrp=context.getContextMap().get("payGrp").toString();
	}
	if(context.getContextMap().containsKey("isEditFlow")){
		isEditFlow=context.getContextMap().get("isEditFlow").toString();
	}
	loadParameters(billerRegistrationOpRequest.getContext(), ActivityConstant.COMMON_ID, ActivityConstant.SEC_COMMON_ID);
	//CR82 Changes
	if(payGrp!=null){
		context.addEntry("payGrp", payGrp);
	}
	if(isEditFlow!=null){
		context.addEntry("isEditFlow", isEditFlow);

	}

	response = getAddOrgBeneficairyService().addOrganizationBeneficiary(convertToAddOrgBenefServiceReq(billerRegistrationOpRequest));

	BillerDTO billerDTO = new BillerDTO();
	if (null != response.getBeneficiaryDTO())
	    billerDTO.setBillerName(response.getBeneficiaryDTO().getBillerName());
	operationResponse.setBillerDTO(billerDTO);

	operationResponse.setBeneficiaryDTO(response.getBeneficiaryDTO());
	operationResponse.setConsumerUniqueRefNo(response.getConsumerUniqueRefNo());
	operationResponse.setTxnReferenceNumber(response.getTxnReferenceNumber());
	operationResponse.setTxnTyp(response.getTxnTyp());

	operationResponse.setSuccess(response.isSuccess());
	operationResponse.setResCde(response.getResCde());
	operationResponse.setResMsg(response.getResMsg());

	return operationResponse;
    }

    public AddOrgBeneficiaryOperationResponse validateForm(AddOrgBenefeciaryOperationRequest request) {
	Context context = request.getContext();
	loadParameters(context, ActivityConstant.COMMON_ID, ActivityConstant.SEC_COMMON_ID);
	AddOrgBeneficiaryOperationResponse response = new AddOrgBeneficiaryOperationResponse();
	response.setContext(context);

	List<BillerDTO> allBillerList = super.getAllBillerList(request);

	if (allBillerList != null && !allBillerList.isEmpty()) {
	    for (BillerDTO billerDTO : allBillerList) {
		response.setSuccess(true);
		response.setResCde(AddOrgBeneficiaryConstants.ENLIST_AVLBL_BILLERS);
		response.setBillerDTO(billerDTO);
		return response;
	    }

	}

	response.setSuccess(true);
	return response;

    }

    private AddOrganizationBeneficiaryServiceRequest convertToAddOrgBenefServiceReq(AddOrgBenefeciaryOperationRequest billerRegistrationOpRequest) {
	AddOrganizationBeneficiaryServiceRequest request1 = new AddOrganizationBeneficiaryServiceRequest();
	request1.setBeneficiaryDTO(billerRegistrationOpRequest.getBeneficiaryDTO());
	request1.setAcctNumber(billerRegistrationOpRequest.getAcctNumber());
	request1.setBeneficiaryNickName(billerRegistrationOpRequest.getBillerNickName());
	request1.setBillerId(billerRegistrationOpRequest.getBillerId());
	request1.setBillerType(billerRegistrationOpRequest.getBillerType());
	request1.setAreaCode(billerRegistrationOpRequest.getAreaCode());
	request1.setContext(billerRegistrationOpRequest.getContext());
	return request1;
    }

    /**
     * This method sendSMSSuccessAcknowledgment has the purpose to send SMS acknowledgment for successful self registration.
     *
     * @param addOrgBeneficiaryOperationResponse
     *
     * @param SelfRegistrationExecutionOperationRequest
     *            void
     */
    public void sendSMSSuccessAcknowledgment(AddOrgBenefeciaryOperationRequest billerRegistrationOpRequest,
	    AddOrgBeneficiaryOperationResponse addOrgBeneficiaryOperationResponse) {

	SMSDetailsServiceRequest smsRequest = new SMSDetailsServiceRequest();
	Context context = billerRegistrationOpRequest.getContext();
	//CR82 Changes
	String payGrp="";
	String isEditFlow="";
	if(context.getContextMap().containsKey("payGrp")){
		payGrp=context.getContextMap().get("payGrp").toString();
	}
	if(context.getContextMap().containsKey("isEditFlow")){
		isEditFlow=context.getContextMap().get("isEditFlow").toString();
	}
	loadParameters(context, ActivityConstant.COMMON_ID, ActivityConstant.SEC_COMMON_ID);
	//CR82 Changes
	if(payGrp!=null){
		context.addEntry("payGrp", payGrp);
	}
	if(isEditFlow!=null){
		context.addEntry("isEditFlow", isEditFlow);
	}

	smsRequest.setContext(context);

	smsRequest.setParentResponse(addOrgBeneficiaryOperationResponse);
	smsRequest.setParentRequest(billerRegistrationOpRequest);
	//CR82
	if(payGrp.equalsIgnoreCase(AIR_TIME) && isEditFlow.equalsIgnoreCase("true") ){
		smsRequest.setEvent(getSMSEvent(billerRegistrationOpRequest, SMSConstants.SMS_AT_PAYEE_EDT_SUCCESS));
		smsRequest.setPriority(getSMSPriority(billerRegistrationOpRequest, SMSConstants.SMS_AT_EDT_PRIORITY));
		smsRequest.setDynamicFields(getSMSPriority(billerRegistrationOpRequest, SMSConstants.SMS_AT_PAYEE_EDT_FIELD_SUCCESS));
	} else if(payGrp.equalsIgnoreCase(MOBILE_WALLET) && isEditFlow.equalsIgnoreCase("true")){
		smsRequest.setEvent(getSMSEvent(billerRegistrationOpRequest, SMSConstants.SMS_WT_PAYEE_EDT_SUCCESS));
		smsRequest.setPriority(getSMSPriority(billerRegistrationOpRequest, SMSConstants.SMS_WT_EDT_PRIORITY));
		smsRequest.setDynamicFields(getSMSPriority(billerRegistrationOpRequest, SMSConstants.SMS_WT_PAYEE_EDT_FIELD_SUCCESS));
	} else if(payGrp.equalsIgnoreCase(AIR_TIME)){
		smsRequest.setEvent(getSMSEvent(billerRegistrationOpRequest, SMSConstants.SMS_AT_PAYEE_ADD_SUCCESS));
		smsRequest.setPriority(getSMSPriority(billerRegistrationOpRequest, SMSConstants.SMS_AT_PAYEE_ADD_SUCCESS_PRIORITY));
		smsRequest.setDynamicFields(getSMSPriority(billerRegistrationOpRequest, SMSConstants.SMS_AT_PAYEE_ADD_FIELD_SUCCESS));
	} else if(payGrp.equalsIgnoreCase(MOBILE_WALLET)) {
		smsRequest.setEvent(getSMSEvent(billerRegistrationOpRequest, SMSConstants.SMS_WT_PAYEE_ADD_SUCCESS));
		smsRequest.setPriority(getSMSPriority(billerRegistrationOpRequest, SMSConstants.SMS_WT_PAYEE_ADD_SUCCESS_PRIORITY));
		smsRequest.setDynamicFields(getSMSPriority(billerRegistrationOpRequest, SMSConstants.SMS_WT_PAYEE_ADD_FIELD_SUCCESS));
	} else {
		smsRequest.setEvent(getSMSEvent(billerRegistrationOpRequest, SMSConstants.SMS_BP_PAYEE_ADD_SUCCESS));
		smsRequest.setPriority(getSMSPriority(billerRegistrationOpRequest, SMSConstants.SMS_BPPAYEEADSUCCESS_PRIORITY));
		smsRequest.setDynamicFields(getSMSPriority(billerRegistrationOpRequest, SMSConstants.SMS_BP_PAYEE_FIELD_SUCCESS));
	}



	smsDetailsService.getSMSDetails(smsRequest);

    }

    /**
     * This method sendSMSFailAcknowledgment has the purpose to send SMS acknowledgment for self registration failure.
     *
     * @param addOrgBeneficiaryOperationResponse
     *
     * @param SelfRegistrationExecutionOperationRequest
     *            void
     */
    public void sendSMSFailAcknowledgment(AddOrgBenefeciaryOperationRequest request,
	    AddOrgBeneficiaryOperationResponse addOrgBeneficiaryOperationResponse) {

	SMSDetailsServiceRequest smsRequest = new SMSDetailsServiceRequest();
	Context context = request.getContext();
	//CR82 Changes
	String payGrp="";
	if(context.getContextMap().containsKey("payGrp")){
		payGrp=context.getContextMap().get("payGrp").toString();
	}
	loadParameters(context, ActivityConstant.COMMON_ID, ActivityConstant.SEC_COMMON_ID);
	//CR82 Changes
	if(payGrp!=null){
		context.addEntry("payGrp", payGrp);
	}
	smsRequest.setContext(context);

	smsRequest.setParentResponse(addOrgBeneficiaryOperationResponse);
	smsRequest.setParentRequest(request);
	//CR82
	if(payGrp.equalsIgnoreCase(AIR_TIME)){
		smsRequest.setEvent(getSMSEvent(request, SMSConstants.SMS_AT_PAYEE_ADD_FAIL));
		smsRequest.setPriority(getSMSPriority(request, SMSConstants.SMS_AT_PAYEE_ADD_FAIL_PRIORITY));
		smsRequest.setDynamicFields(getSMSPriority(request, SMSConstants.SMS_AT_PAYEE_ADD_FIELD_FAIL));
	} else if(payGrp.equalsIgnoreCase(MOBILE_WALLET)) {
		smsRequest.setEvent(getSMSEvent(request, SMSConstants.SMS_WT_PAYEE_ADD_FAIL));
		smsRequest.setPriority(getSMSPriority(request, SMSConstants.SMS_WT_PAYEE_ADD_FAIL_PRIORITY));
		smsRequest.setDynamicFields(getSMSPriority(request, SMSConstants.SMS_WT_PAYEE_ADD_FIELD_FAIL));
	} else {
		smsRequest.setEvent(getSMSEvent(request, SMSConstants.SMS_BP_PAYEE_ADD_FAIL));
		smsRequest.setPriority(getSMSPriority(request, SMSConstants.SMS_BPPAYEEADD_FAIL_PRIORITY));
		smsRequest.setDynamicFields(getSMSPriority(request, SMSConstants.SMS_BP_PAYEE_ADD_FIELD_FAIL));
	}




	smsDetailsService.getSMSDetails(smsRequest);

    }

    /**
     * This method getSMSEvent has the purpose to get event, for which SMS has to be sent.
     *
     * @param SelfRegistrationExecutionOperationRequest
     * @return String
     */
    private String getSMSEvent(AddOrgBenefeciaryOperationRequest billerRegistrationOpRequest, String smsKey) {

	ListValueResServiceRequest listValueResServiceRequest = new ListValueResServiceRequest();
	listValueResServiceRequest.setContext(billerRegistrationOpRequest.getContext());
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
    private String getSMSPriority(AddOrgBenefeciaryOperationRequest billerRegistrationOpRequest, String smsKey) {

	ListValueResServiceRequest listValueResServiceRequest = new ListValueResServiceRequest();
	listValueResServiceRequest.setContext(billerRegistrationOpRequest.getContext());
	listValueResServiceRequest.setListValueKey(smsKey);
	ListValueResByGroupServiceResponse listValueResByGroupServiceResponse = listValueResService.getListValueByKey(listValueResServiceRequest);

	String priority = listValueResByGroupServiceResponse.getListValueCahceDTO().get(0).getLabel();
	return priority;

    }

}
