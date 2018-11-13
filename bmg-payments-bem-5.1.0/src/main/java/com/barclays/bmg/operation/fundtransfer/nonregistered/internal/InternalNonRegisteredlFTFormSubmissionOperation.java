package com.barclays.bmg.operation.fundtransfer.nonregistered.internal;

import org.apache.commons.lang.StringUtils;

import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.operation.payments.BMBPaymentsOperation;
import com.barclays.bmg.operation.request.fundtransfer.nonregistered.internal.InternalNonRegisteredFTFormSubmissionOperationRequest;
import com.barclays.bmg.operation.response.fundtransfer.nonregistered.internal.InternalNonRegisteredFTFormSubmissionOperationResponse;

public class InternalNonRegisteredlFTFormSubmissionOperation extends BMBPaymentsOperation {

	private static final String AED = "AED";
	private static final String KES = "KES";

	public InternalNonRegisteredFTFormSubmissionOperationResponse validateForm(
			InternalNonRegisteredFTFormSubmissionOperationRequest request) {
		InternalNonRegisteredFTFormSubmissionOperationResponse response = new InternalNonRegisteredFTFormSubmissionOperationResponse();
		Context context = request.getContext();
		response.setContext(context);
		CustomerAccountDTO fromAccount = request.getSourceAcct();
		loadParameters(context, ActivityConstant.COMMON_ID,
				ActivityConstant.SEC_COMMON_ID);
		if (response.isSuccess()) {
			response.setBeneficiaryDTO(request.getBeneficiaryDTO());
			response.setSourceAcct(fromAccount);
			response.setTxnAmt(request.getTxnAmt());
			response.setChargeKey(request.getChargeKey());
			response.setCharDtls(request.getCharDtls());
			response.setPayDtls(request.getPayDtls());
			response.setPayRson(request.getPayRson());
			response.setRem1(request.getRem1());
			response.setRem2(request.getRem2());
			response.setRem3(request.getRem3());
			response.setTxnNot(request.getTxnNot());
			response.setAuthReq(request.isAuthReq());
			response.setAuthType(request.getAuthType());
			response.setFxRateDTO(request.getFxRateDTO());
		}

		if (AED.equals(request.getCurr())
				|| KES.equals(request.getCurr())) {

			// Get the list value for Payment rson key and Payment dtls key.
			String payRsonValue = getListValueLabel(context,
					"FT_INTL_PMT_REASON", request.getPayRson());
			String payDtlsValue = getListValueLabel(context,
					"FT_INTL_PMT_DETAILS", request.getPayDtls());
//			if (payRsonValue != null && payDtlsValue != null) {
//			response.setPayRsonValue(payRsonValue);
//			response.setPayDtlsValue(payDtlsValue);
//		}
		if (payRsonValue != null) {
			response.setPayRsonValue(payRsonValue);
		}
		if (payDtlsValue != null) {
			response.setPayDtlsValue(payDtlsValue);
		}
		}

		if (!response.isSuccess() && StringUtils.isEmpty(response.getResMsg())) {
			getMessage(response);
		}
		return response;

	}

}
