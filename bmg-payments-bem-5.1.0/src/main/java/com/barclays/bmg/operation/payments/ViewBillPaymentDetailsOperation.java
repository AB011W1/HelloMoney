package com.barclays.bmg.operation.payments;

import com.barclays.bmg.operation.request.billpayment.ViewTxnHistoryDetailsOperationRequest;
import com.barclays.bmg.operation.response.billpayment.ViewTxnHistoryDetailsOperationResponse;
import com.barclays.bmg.service.response.ViewTxnHistoryDetailsServiceResponse;


public class ViewBillPaymentDetailsOperation extends ViewTxnHistoryDetailsOperation {


	@Override
	public ViewTxnHistoryDetailsServiceResponse executeService(
			ViewTxnHistoryDetailsOperationRequest request) {
		// TODO Auto-generated method stub
		ViewTxnHistoryDetailsServiceResponse viewBillPaymentDetailsServiceResponse= getViewTxnHistoryDetailsService().viewBillPaymentDetails(buildServiceRequest(request));
		return viewBillPaymentDetailsServiceResponse;

	}


	@Override
	public ViewTxnHistoryDetailsOperationResponse buildOperationResponse(
			ViewTxnHistoryDetailsOperationRequest request,ViewTxnHistoryDetailsServiceResponse response) {
		// TODO Auto-generated method stub
		ViewTxnHistoryDetailsOperationResponse viewTxnHistoryDetailsOperationResponse=new ViewTxnHistoryDetailsOperationResponse();

		if(null!=response && null!=response.getBillPaymentHistory()){

			String billerName=getBillerNameById(response.getBillPaymentHistory().getBillerId(),request);
			response.getBillPaymentHistory().setBillerName(billerName);

			viewTxnHistoryDetailsOperationResponse.setTransactionHistoryDTO(response.getBillPaymentHistory());

			}
		return viewTxnHistoryDetailsOperationResponse;
	}


}
