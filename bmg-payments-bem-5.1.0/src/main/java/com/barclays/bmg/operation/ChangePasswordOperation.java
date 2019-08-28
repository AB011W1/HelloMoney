package com.barclays.bmg.operation;

import com.barclays.bmg.audit.annotation.AuditSupport;
import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.constants.AuditConstant;
import com.barclays.bmg.constants.SMSConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.operation.request.ChangePasswordOperationRequest;
import com.barclays.bmg.operation.response.ChangePasswordOperationResponse;
import com.barclays.bmg.service.PasswordChangeService;
import com.barclays.bmg.service.product.request.ListValueResServiceRequest;
import com.barclays.bmg.service.product.response.ListValueResByGroupServiceResponse;
import com.barclays.bmg.service.request.PasswordChangeServiceRequest;
import com.barclays.bmg.service.response.PasswordChangeServiceResponse;
import com.barclays.bmg.ussd.auth.service.request.SMSDetailsServiceRequest;
import com.barclays.bmg.ussd.service.SMSDetailsService;

public class ChangePasswordOperation extends BMBCommonOperation {

    private PasswordChangeService passwordChangeService;
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

    @AuditSupport(auditType = AuditConstant.AUDIT_TYPE_TRANSACTION, activityState = AuditConstant.SRC_COM_SSC, serviceDescription = "CHG_PWD_AUDIT_LOG", stepId = "1", activityType = "changePassword")
    public ChangePasswordOperationResponse changePassword(ChangePasswordOperationRequest request) {
	PasswordChangeServiceRequest serviceRequest = new PasswordChangeServiceRequest();
	PasswordChangeServiceResponse serviceResponse;

	ChangePasswordOperationResponse operationResponse = new ChangePasswordOperationResponse();
	Context context = request.getContext();
	loadParameters(context, ActivityConstant.COMMON_ID, ActivityConstant.SEC_COMMON_ID);

	serviceRequest.setAuthType(request.getAuthType());

	serviceRequest.setOldPassword(request.getOldPassword());
	serviceRequest.setNewPassword(request.getNewPassword());
	serviceRequest.setContext(context);

	serviceResponse = passwordChangeService.changePassword(serviceRequest);

	operationResponse.setSuccess(serviceResponse.isSuccess());
	if (!operationResponse.isSuccess()) {
	    getMessage(operationResponse);
	}
	operationResponse.setContext(context);
	return operationResponse;
    }

    /**
     * This method sendSMSSuccessAcknowledgment has the purpose to send SMS acknowledgment for successful Password change.
     * 
     * @param opResponse
     * 
     * @param ChangePasswordOperationRequest
     *            void
     */
    public void sendSMSSuccessAcknowledgment(ChangePasswordOperationRequest opRequest, ChangePasswordOperationResponse opResponse) {

	SMSDetailsServiceRequest smsRequest = new SMSDetailsServiceRequest();
	Context context = opRequest.getContext();

	loadParameters(context, ActivityConstant.COMMON_ID, ActivityConstant.SEC_COMMON_ID);
	smsRequest.setContext(context);

	smsRequest.setParentResponse(opResponse);
	smsRequest.setParentRequest(opRequest);

	smsRequest.setEvent(getSMSEvent(opRequest, SMSConstants.SMS_CHGPWD_SUCCESS));
	smsRequest.setPriority(getSMSPriority(opRequest, SMSConstants.SMS_CHGPWD_SUCCESS_PRIORITY));
	smsRequest.setDynamicFields(getSMSPriority(opRequest, SMSConstants.SMS_CHGPWD_FIELD_SUCCESS));

	smsDetailsService.getSMSDetails(smsRequest);

    }

    /**
     * This method getSMSEvent has the purpose to get event, for which SMS has to be sent.
     * 
     * @param SelfRegistrationExecutionOperationRequest
     * @return String
     */
    private String getSMSEvent(ChangePasswordOperationRequest opRequest, String smsKey) {

	ListValueResServiceRequest listValueResServiceRequest = new ListValueResServiceRequest();
	listValueResServiceRequest.setContext(opRequest.getContext());
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
    private String getSMSPriority(ChangePasswordOperationRequest opRequest, String smsKey) {

	ListValueResServiceRequest listValueResServiceRequest = new ListValueResServiceRequest();
	listValueResServiceRequest.setContext(opRequest.getContext());
	listValueResServiceRequest.setListValueKey(smsKey);
	ListValueResByGroupServiceResponse listValueResByGroupServiceResponse = listValueResService.getListValueByKey(listValueResServiceRequest);

	String priority = listValueResByGroupServiceResponse.getListValueCahceDTO().get(0).getLabel();
	return priority;

    }

    public PasswordChangeService getPasswordChangeService() {
	return passwordChangeService;
    }

    public void setPasswordChangeService(PasswordChangeService passwordChangeService) {
	this.passwordChangeService = passwordChangeService;
    }

}
