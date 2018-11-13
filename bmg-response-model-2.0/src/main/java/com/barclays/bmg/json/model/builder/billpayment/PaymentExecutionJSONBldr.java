package com.barclays.bmg.json.model.builder.billpayment;

import com.barclays.bmg.constants.BillPaymentConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.json.model.billpayment.PaymentConfirmationJSONBean;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadHeader;
import com.barclays.bmg.operation.response.billpayment.MakeBillPaymentOperationResponse;
import com.barclays.bmg.utils.BMGFormatUtility;

public class PaymentExecutionJSONBldr extends BMBMultipleResponseJSONBuilder {

	/**
	 * @param payeeType
	 * @return Get the response id as per payee type
	 */
	private String getResponseId(String payeeType) {

		String resId = null;
		if (BillPaymentConstants.PAYEE_TYPE_BILL_PAYMENT.equals(payeeType)) {
			resId = ResponseIdConstants.CONFIRM_BILL_PAYMENT_RESPONSE_ID;
		}
		if (BillPaymentConstants.PAYEE_TYPE_MOBILE_TOPUP.equals(payeeType)) {
			resId = ResponseIdConstants.CONFIRM_MTP_PAYEMENT_RESPONSE_ID;
		}

		if (BillPaymentConstants.PAYEE_TYPE_BARCLAY_CARD.equals(payeeType)) {
			resId = ResponseIdConstants.CONFIRM_BCD_PAYEMENT_RESPONSE_ID;
		}
		if (BillPaymentConstants.PAYEE_TYPE_CREDIT_CARD_PAYMENT
				.equals(payeeType)) {
			resId = ResponseIdConstants.CONFIRM_CCP_PAYEMENT_RESPONSE_ID;
		}
		if (BillPaymentConstants.KITS_PAY_TO_ACCOUNT
				.equals(payeeType)) {
			resId = ResponseIdConstants.CONFIRM_BILL_PAYMENT_RESPONSE_ID;
		}
		if (BillPaymentConstants.KITS_PAY_TO_PHONE
				.equals(payeeType)) {
			resId = ResponseIdConstants.CONFIRM_BILL_PAYMENT_RESPONSE_ID;
		}
		return resId;
	}

	protected void populatePayLoad(BMBPayload bmbPayload,
			ResponseContext... responseContexts) {
		PaymentConfirmationJSONBean paymentConfirmBean = null;
		MakeBillPaymentOperationResponse response = (MakeBillPaymentOperationResponse) responseContexts[0];
		if (response != null && response.isSuccess()) {
			paymentConfirmBean = new PaymentConfirmationJSONBean();
			paymentConfirmBean.setBemRefNo(response.getTrnRef());
			paymentConfirmBean.setTxnRefNo(response.getTrnRef());
			paymentConfirmBean.setTxnResRefNo(response.getTrnRef());
			paymentConfirmBean.setResDtTm(BMGFormatUtility.getLongDate(response
					.getTrnDate()));
			paymentConfirmBean.setTxnDtTm(BMGFormatUtility.getLongDate(response
					.getTrnDate()));
			paymentConfirmBean.setPymntRefNo(response.getPymntRefNo());
			paymentConfirmBean.setRcptNo(response.getRcptNo());
			paymentConfirmBean.setTokenNo(response.getTokenNo());
			paymentConfirmBean.setVoucherNo(response.getVoucherNo());
			paymentConfirmBean.setTxnMsg(response.getTxnMsg());
		}
		bmbPayload.setPayData(paymentConfirmBean);
	}

	@Override
	public Object createMultipleJSONResponse(
			ResponseContext... responseContexts) {
		BMBPayloadHeader bmbPayloadHeader = null;
		BMBPayload bmbPayload = null;
		for (ResponseContext response : responseContexts) {
			if (response != null && !response.isSuccess()) {
				bmbPayloadHeader = createHeader(response,
						getResponseId(response.getTxnTyp()));
				break;
			}
		}

		if (bmbPayloadHeader != null) {
			bmbPayload = new BMBPayload(bmbPayloadHeader);
		} else {
			ResponseContext response = responseContexts[0];
			bmbPayload = new BMBPayload(createHeader(response,
					getResponseId(response.getTxnTyp())));
			populatePayLoad(bmbPayload, responseContexts);
		}

		return bmbPayload;
	}

}
