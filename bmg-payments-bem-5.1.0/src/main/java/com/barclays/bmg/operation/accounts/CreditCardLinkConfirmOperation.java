package com.barclays.bmg.operation.accounts;

import com.barclays.bmg.audit.annotation.AuditSupport;
import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.constants.AuditConstant;
import com.barclays.bmg.operation.BMBCommonOperation;
import com.barclays.bmg.operation.accounts.request.CreditCardLinkConfirmOperationRequest;
import com.barclays.bmg.operation.accounts.request.CreditCardLinkConfirmOperationResponse;
import com.barclays.bmg.service.ReportProblemService;
import com.barclays.bmg.service.request.ReportProblemServiceRequest;
import com.barclays.bmg.service.response.ReportProblemServiceResponse;

public class CreditCardLinkConfirmOperation extends BMBCommonOperation {


	private ReportProblemService reportProblemService;

	/**
	 *
	 * @param request
	 * @return
	 */
	@AuditSupport(auditType = AuditConstant.AUDIT_TYPE_TRANSACTION, activityState = AuditConstant.SRC_COM_SSC, serviceDescription = "SD_CCD_LINK", stepId = "1", activityType = "auditCCDLink")
	public CreditCardLinkConfirmOperationResponse creditCardLink(CreditCardLinkConfirmOperationRequest creditCardLinkConfirmOperationRequest) {

		CreditCardLinkConfirmOperationResponse cCLinkResp = new CreditCardLinkConfirmOperationResponse();
		ReportProblemServiceRequest cCLinkRequest = new ReportProblemServiceRequest();
		cCLinkRequest.setAccountNumber(creditCardLinkConfirmOperationRequest.getPrimaryAccountNumber());
		loadParameters(creditCardLinkConfirmOperationRequest.getContext(), ActivityConstant.COMMON_ID, ActivityConstant.SEC_COMMON_ID);
		cCLinkRequest.setContext(creditCardLinkConfirmOperationRequest.getContext());
		ReportProblemServiceResponse reportProblemServiceResponse = reportProblemService.addProblem(cCLinkRequest);

		if(reportProblemServiceResponse != null){
			cCLinkResp.setCaseNumber(reportProblemServiceResponse.getCaseNumber());
			cCLinkResp.setSuccess(reportProblemServiceResponse.isSuccess());
			cCLinkResp.setResCde(reportProblemServiceResponse.getResCde());
			cCLinkResp.setResMsg(reportProblemServiceResponse.getResMsg());
		}
		return cCLinkResp;


	}

	public void setReportProblemService(ReportProblemService reportProblemService) {
		this.reportProblemService = reportProblemService;
	}

	public ReportProblemService getReportProblemService() {
		return reportProblemService;
	}

}
