package com.barclays.bmg.json.model.builder.billpayment;

import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.BillPaymentHistory;
import com.barclays.bmg.dto.TransactionHistoryDTO;
import com.barclays.bmg.json.model.AmountJSONModel;
import com.barclays.bmg.json.model.CasaAccountJSONModel;
import com.barclays.bmg.json.model.billpayment.BillerJSONModel;
import com.barclays.bmg.json.model.billpayment.TransactionHistoryJSONBean;
import com.barclays.bmg.json.model.billpayment.TransactionHistoryJSONModel;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadHeader;
import com.barclays.bmg.operation.response.billpayment.ViewTxnHistoryDetailsOperationResponse;
import com.barclays.bmg.utils.BMGFormatUtility;

/**
 * @author BTCI Json Builder class for View Bill Payment Details response
 */
public class ViewBillPaymentDetailsJSONBldr extends
		ViewTxnHistoryDetailsJSONBldr {

	@Override
	public String getResponseId(String transactionType) {

		return ResponseIdConstants.VIEW_BILL_PAYMENT_DETAILS;

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.barclays.bmg.json.model.builder.billpayment.ViewTxnHistoryDetailsJSONBldr
	 * #populatePayLoad(com.barclays.bmg.json.response.BMBPayload,
	 * com.barclays.bmg.context.ResponseContext[])
	 */
	@Override
	public void populatePayLoad(BMBPayload bmbPayload,
			ResponseContext... responseContexts) {
		TransactionHistoryJSONBean transactionHistoryJSONBean = new TransactionHistoryJSONBean();
		TransactionHistoryJSONModel transactionHistoryJSONModel = null;
		ViewTxnHistoryDetailsOperationResponse response = (ViewTxnHistoryDetailsOperationResponse) responseContexts[0];
		if (response != null && response.isSuccess()) {

			TransactionHistoryDTO transactionHistoryDTO = response
					.getTransactionHistoryDTO();

			if (transactionHistoryDTO instanceof BillPaymentHistory) {

				BillPaymentHistory billPaymentHistory = (BillPaymentHistory) transactionHistoryDTO;

				transactionHistoryJSONModel = new TransactionHistoryJSONModel();
				CasaAccountJSONModel fromAccountInfo = new CasaAccountJSONModel();
				// fromAccountInfo.setActNo(billPaymentHistory.getFromAccount().getAccountNumber());
				fromAccountInfo
						.setMkdActNo(getMaskedCardNumber(billPaymentHistory
								.getFromAccount().getAccountNumber()));
				fromAccountInfo.setTyp(billPaymentHistory.getFromAccount()
						.getAccountType());
				transactionHistoryJSONModel.setFromAccountInfo(fromAccountInfo);

				BillerJSONModel billerJSONModel = new BillerJSONModel();
				billerJSONModel.setBillerName(billPaymentHistory
						.getBillerName());
				billerJSONModel.setBillerRefNo(billPaymentHistory
						.getBillReferenceNumber());
				billerJSONModel.setBillerCatId(billPaymentHistory
						.getBillerType());
				transactionHistoryJSONModel.setBillerInfo(billerJSONModel);

				transactionHistoryJSONModel.setTransactionDate(BMGFormatUtility
						.getLongDate(billPaymentHistory.getTransactionDate()));
				transactionHistoryJSONModel
						.setTransactionRefNumber(billPaymentHistory
								.getTransactionReferenceNumber());
				transactionHistoryJSONModel.setStatus(billPaymentHistory
						.getStatus());
				transactionHistoryJSONModel
						.setTransactionType(billPaymentHistory.getType());

				AmountJSONModel amountJSONModel = new AmountJSONModel();
				amountJSONModel.setAmt(billPaymentHistory.getAmount()
						.getAmount().toString());
				amountJSONModel.setCurr(billPaymentHistory.getAmount()
						.getCurrency());

				transactionHistoryJSONModel.setAmountInfo(amountJSONModel);
				transactionHistoryJSONBean
						.setTransactionHistory(transactionHistoryJSONModel);
			}

		}
		bmbPayload.setPayData(transactionHistoryJSONBean);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.barclays.bmg.json.model.builder.billpayment.ViewTxnHistoryDetailsJSONBldr
	 * #createHeader(com.barclays.bmg.context.ResponseContext)
	 */
	@Override
	protected BMBPayloadHeader createHeader(ResponseContext response) {
		return super.createHeader(response);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.barclays.bmg.json.model.builder.billpayment.ViewTxnHistoryDetailsJSONBldr
	 * #createMultipleJSONResponse(com.barclays.bmg.context.ResponseContext[])
	 */
	@Override
	public Object createMultipleJSONResponse(
			ResponseContext... responseContexts) {
		return super.createMultipleJSONResponse(responseContexts);
	}

}
