package com.barclays.bmg.operation.formvalidation;

import com.barclays.bmg.context.Context;
import com.barclays.bmg.operation.payments.BMBPaymentsOperation;
import com.barclays.bmg.operation.request.fundtransfer.external.TransactionLimitOperationRequest;
import com.barclays.bmg.operation.response.fundtransfer.external.TransactionLimitOperationResponse;
import com.barclays.bmg.service.request.TransactionLimitServiceRequest;
import com.barclays.bmg.service.response.TransactionLimitServiceResponse;

public class TransactionLimitOperation extends BMBPaymentsOperation{


	/**
	 * @param request
	 * @return
	 * Returns aValidDailyLimit depending on the activity Id.
	 */
	public TransactionLimitOperationResponse getAValidDailyLimit(TransactionLimitOperationRequest request){
		TransactionLimitOperationResponse response = new TransactionLimitOperationResponse();
		Context context = request.getContext();
		response.setContext(context);
		response.setAValidDailyLimit(getAValidDailyLimit(request,context.getActivityId()));

		return response;
	}

	/**
	 * @param request
	 * @return
	 *
	 * Check whether Transaction limit is crossed depending on txnAmt in request.
	 * Also returns whether second level auth is required or Not.
	 */
	public TransactionLimitOperationResponse checkTransactionLimit(TransactionLimitOperationRequest request){
		TransactionLimitOperationResponse response = new TransactionLimitOperationResponse();
		Context context = request.getContext();
		response.setContext(context);
		TransactionLimitServiceRequest txnLimitServiceReq = new TransactionLimitServiceRequest();
		txnLimitServiceReq.setAmountInLCY(request.getTxnAmt());
		txnLimitServiceReq.setContext(context);
		TransactionLimitServiceResponse txnLimitResponse = getTransactionLimitService()
				.checkTransactionLimit(txnLimitServiceReq);

		if (!txnLimitResponse.isSuccess()) {
			response.setResCde(txnLimitResponse
					.getResCde());
			response.setResMsg(txnLimitResponse
					.getResMsg());
			response.setErrTyp(txnLimitResponse.getErrTyp());
		} else {
			if (txnLimitResponse.isAuthRequired()) {
				String authType = getAuthType(request,context.getActivityId());
				response.setAuthRequired(true);
				response.setAuthType(authType);
			}
		}
		return response;

	}

}
