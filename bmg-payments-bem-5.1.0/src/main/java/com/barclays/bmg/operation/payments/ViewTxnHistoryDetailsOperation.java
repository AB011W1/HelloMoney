package com.barclays.bmg.operation.payments;

import java.util.List;

import com.barclays.bmg.audit.annotation.AuditSupport;
import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.constants.AuditConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.dto.BillerDTO;
import com.barclays.bmg.operation.request.billpayment.ViewTxnHistoryDetailsOperationRequest;
import com.barclays.bmg.operation.response.billpayment.ViewTxnHistoryDetailsOperationResponse;
import com.barclays.bmg.service.ViewTxnHistoryDetailsService;
import com.barclays.bmg.service.request.BillerServiceRequest;
import com.barclays.bmg.service.request.ViewTxnHistoryDetailsServiceRequest;
import com.barclays.bmg.service.response.BillerServiceResponse;
import com.barclays.bmg.service.response.ViewTxnHistoryDetailsServiceResponse;

public abstract class ViewTxnHistoryDetailsOperation extends BMBPaymentsOperation {

    private ViewTxnHistoryDetailsService viewTxnHistoryDetailsService;

    public ViewTxnHistoryDetailsService getViewTxnHistoryDetailsService() {
	return viewTxnHistoryDetailsService;
    }

    public void setViewTxnHistoryDetailsService(ViewTxnHistoryDetailsService viewTxnHistoryDetailsService) {
	this.viewTxnHistoryDetailsService = viewTxnHistoryDetailsService;
    }

    @AuditSupport(auditType = AuditConstant.AUDIT_TYPE_TRANSACTION, activityState = AuditConstant.SRC_COM_SSC, serviceDescription = "SD_PAY_BILL_ENQUIRY", stepId = "2", activityType = "auditBillPaymentEnquiry")
    public ViewTxnHistoryDetailsOperationResponse viewTxnHistoryDetails(ViewTxnHistoryDetailsOperationRequest request) {

	Context context = request.getContext();
	loadParameters(context, ActivityConstant.COMMON_ID, ActivityConstant.SEC_COMMON_ID);

	ViewTxnHistoryDetailsServiceResponse viewTxnHistoryDetailsServiceResponse = null;

	viewTxnHistoryDetailsServiceResponse = executeService(request);

	ViewTxnHistoryDetailsOperationResponse viewTxnHistoryDetailsOperationResponse = buildOperationResponse(request,
		viewTxnHistoryDetailsServiceResponse);

	viewTxnHistoryDetailsOperationResponse.setSuccess(viewTxnHistoryDetailsServiceResponse.isSuccess());
	if (!viewTxnHistoryDetailsOperationResponse.isSuccess()) {
	    getMessage(viewTxnHistoryDetailsOperationResponse);

	}
	viewTxnHistoryDetailsOperationResponse.setContext(context);

	return viewTxnHistoryDetailsOperationResponse;

    }

    protected String getBillerNameById(String billerId, RequestContext requestContext) {
	String billerName = null;
	List<BillerDTO> billerList = getAllBillerList(requestContext);
	/*
	 * for (int i = 0; i < billerList.size(); i++) { BillerDTO biller = billerList.get(i); if (null != billerId &&
	 * biller.getBillerId().equals(billerId)) { billerName = biller.getBillerName(); break; }
	 * 
	 * }
	 */
	BillerServiceRequest request = new BillerServiceRequest();
	request.setBillerId(billerId);
	request.setBusinessId(requestContext.getContext().getBusinessId());
	BillerServiceResponse response = getBillerService().getBillerForBillerId(request);
	//Modified for Defect #2137
	if (response != null && response.getBillerDTO() != null) {
	    billerName = response.getBillerDTO().getBillerName();
	}
	return billerName;

    }

    protected ViewTxnHistoryDetailsServiceRequest buildServiceRequest(ViewTxnHistoryDetailsOperationRequest request) {
	ViewTxnHistoryDetailsServiceRequest viewTxnHistoryDetailsServiceRequest = new ViewTxnHistoryDetailsServiceRequest();
	viewTxnHistoryDetailsServiceRequest.setContext(request.getContext());
	viewTxnHistoryDetailsServiceRequest.setTransactionRefNo(request.getTransactionRefNo());
	return viewTxnHistoryDetailsServiceRequest;

    }

    public abstract ViewTxnHistoryDetailsServiceResponse executeService(ViewTxnHistoryDetailsOperationRequest request);

    public abstract ViewTxnHistoryDetailsOperationResponse buildOperationResponse(ViewTxnHistoryDetailsOperationRequest request,
	    ViewTxnHistoryDetailsServiceResponse response);

}
