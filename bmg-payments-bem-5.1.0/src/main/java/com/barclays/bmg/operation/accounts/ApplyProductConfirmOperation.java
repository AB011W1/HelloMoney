package com.barclays.bmg.operation.accounts;

import com.barclays.bmg.audit.annotation.AuditSupport;
import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.constants.AuditConstant;
import com.barclays.bmg.constants.SMSConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.operation.BMBCommonOperation;
import com.barclays.bmg.operation.accounts.request.ApplyProductConfirmOperationRequest;
import com.barclays.bmg.operation.accounts.request.ApplyProductConfirmOperationResponse;
import com.barclays.bmg.service.ReportProblemService;
import com.barclays.bmg.service.product.request.ListValueResServiceRequest;
import com.barclays.bmg.service.product.response.ListValueResByGroupServiceResponse;
import com.barclays.bmg.service.request.ReportProblemServiceRequest;
import com.barclays.bmg.service.response.ReportProblemServiceResponse;
import com.barclays.bmg.ussd.auth.service.request.SMSDetailsServiceRequest;
import com.barclays.bmg.ussd.service.SMSDetailsService;

public class ApplyProductConfirmOperation extends BMBCommonOperation {


	private ReportProblemService reportProblemService;
	private SMSDetailsService smsDetailsService;

	/**
	 *
	 * @param request
	 * @return
	 */
	@AuditSupport(auditType = AuditConstant.AUDIT_TYPE_TRANSACTION, activityState = AuditConstant.SRC_COM_SSC, serviceDescription = "SD_CCD_LINK", stepId = "1", activityType = "auditApplyProd")
	public ApplyProductConfirmOperationResponse applyProduct(ApplyProductConfirmOperationRequest applyProductConfirmOperationRequest) {

		ApplyProductConfirmOperationResponse applyProductResp = new ApplyProductConfirmOperationResponse();
		ReportProblemServiceRequest applyProductRequest = new ReportProblemServiceRequest();
		applyProductRequest.setAccountNumber(applyProductConfirmOperationRequest.getPrimaryAccountNumber());
		loadParameters(applyProductConfirmOperationRequest.getContext(), ActivityConstant.COMMON_ID, ActivityConstant.SEC_COMMON_ID);
		applyProductRequest.setContext(applyProductConfirmOperationRequest.getContext());
		applyProductRequest.setProductName(applyProductConfirmOperationRequest.getProductName());
		applyProductRequest.setSubProductName(applyProductConfirmOperationRequest.getSubProductName());
		ReportProblemServiceResponse reportProblemServiceResponse = reportProblemService.addProblem(applyProductRequest);

		if(reportProblemServiceResponse != null){
			applyProductResp.setCaseNumber(reportProblemServiceResponse.getCaseNumber());
			applyProductResp.setSuccess(reportProblemServiceResponse.isSuccess());
			applyProductResp.setResCde(reportProblemServiceResponse.getResCde());
			applyProductResp.setResMsg(reportProblemServiceResponse.getResMsg());
		}
		return applyProductResp;


	}

	 /**
     * This method sendSMSSuccessAcknowledgment has the purpose to send SMS acknowledgment for successful self registration.
     *
     * @param addOrgBeneficiaryOperationResponse
     *
     * @param SelfRegistrationExecutionOperationRequest
     *            void
     */
    public void sendSMSSuccessAcknowledgment(ApplyProductConfirmOperationRequest applyProductConfirmOperationRequest,
    		ApplyProductConfirmOperationResponse applyProductConfirmOperationResponse) {

	SMSDetailsServiceRequest smsRequest = new SMSDetailsServiceRequest();
	Context context = applyProductConfirmOperationRequest.getContext();

	loadParameters(context, ActivityConstant.COMMON_ID, ActivityConstant.SEC_COMMON_ID);
	smsRequest.setContext(context);

	smsRequest.setParentResponse(applyProductConfirmOperationResponse);
	smsRequest.setParentRequest(applyProductConfirmOperationRequest);

	smsRequest.setEvent(getSMSEvent(applyProductConfirmOperationRequest, SMSConstants.SMS_LDGEN_SUCCESS));
	smsRequest.setPriority(getSMSPriority(applyProductConfirmOperationRequest, SMSConstants.SMS_LDGEN_PRIORITY));
	smsRequest.setDynamicFields(getSMSPriority(applyProductConfirmOperationRequest, SMSConstants.SMS_LDGEN_FIELD_SUCCESS));

	smsDetailsService.getSMSDetails(smsRequest);

    }

    /**
     * This method getSMSEvent has the purpose to get event, for which SMS has to be sent.
     *
     * @param SelfRegistrationExecutionOperationRequest
     * @return String
     */
    private String getSMSEvent(ApplyProductConfirmOperationRequest applyProductConfirmOperationRequest, String smsKey) {

	ListValueResServiceRequest listValueResServiceRequest = new ListValueResServiceRequest();
	listValueResServiceRequest.setContext(applyProductConfirmOperationRequest.getContext());
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
    private String getSMSPriority(ApplyProductConfirmOperationRequest applyProductConfirmOperationRequest, String smsKey) {

	ListValueResServiceRequest listValueResServiceRequest = new ListValueResServiceRequest();
	listValueResServiceRequest.setContext(applyProductConfirmOperationRequest.getContext());
	listValueResServiceRequest.setListValueKey(smsKey);
	ListValueResByGroupServiceResponse listValueResByGroupServiceResponse = listValueResService.getListValueByKey(listValueResServiceRequest);

	String priority = listValueResByGroupServiceResponse.getListValueCahceDTO().get(0).getLabel();
	return priority;

    }
	public void setReportProblemService(ReportProblemService reportProblemService) {
		this.reportProblemService = reportProblemService;
	}

	public ReportProblemService getReportProblemService() {
		return reportProblemService;
	}

	public SMSDetailsService getSmsDetailsService() {
		return smsDetailsService;
	}

	public void setSmsDetailsService(SMSDetailsService smsDetailsService) {
		this.smsDetailsService = smsDetailsService;
	}

}
