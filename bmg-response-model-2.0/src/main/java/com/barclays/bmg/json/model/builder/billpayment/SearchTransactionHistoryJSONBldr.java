package com.barclays.bmg.json.model.builder.billpayment;

import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.constants.TransactionHistoryConstants;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.BillPaymentHistory;
import com.barclays.bmg.dto.TransactionHistoryDTO;
import com.barclays.bmg.json.model.AmountJSONModel;
import com.barclays.bmg.json.model.CasaAccountJSONModel;
import com.barclays.bmg.json.model.billpayment.BillerJSONModel;
import com.barclays.bmg.json.model.billpayment.TransactionHistoryJSONBean;
import com.barclays.bmg.json.model.billpayment.TransactionHistoryJSONModel;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadHeader;
import com.barclays.bmg.operation.response.billpayment.SearchTransactionHistoryOperationResponse;
import com.barclays.bmg.utils.BMGFormatUtility;

/**
 * @author BTCI
 * 
 *         JSON Builder class for Search Transaction History response
 */
public class SearchTransactionHistoryJSONBldr extends BMBMultipleResponseJSONBuilder {

    /**
     * @param transactionType
     * @return Get the response id as per Transaction Type
     */
    private String getResponseId(String transactionType) {

	String resId = null;
	if (TransactionHistoryConstants.BILL_PAYMENT.equals(transactionType)) {
	    resId = ResponseIdConstants.SEARCH_TRANSACTION_HISTORY_BP;
	}
	return resId;
    }

    /**
     * @param bmbPayload
     * @param responseContexts
     */
    protected void populatePayLoad(BMBPayload bmbPayload, ResponseContext... responseContexts) {
	TransactionHistoryJSONBean transactionHistoryJSONBean = new TransactionHistoryJSONBean();
	SearchTransactionHistoryOperationResponse response = (SearchTransactionHistoryOperationResponse) responseContexts[0];
	if (response != null && response.isSuccess()) {

	    for (TransactionHistoryDTO transactionHistoryDTO : response.getTransactionHistoryDTOList()) {

		if (transactionHistoryDTO instanceof BillPaymentHistory) {

		    populateBillPaymentHistoryPayload(transactionHistoryDTO, transactionHistoryJSONBean);

		}
	    }

	}
	bmbPayload.setPayData(transactionHistoryJSONBean);
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder# createMultipleJSONResponse(com.barclays.bmg.context.ResponseContext[])
     */
    @Override
    public Object createMultipleJSONResponse(ResponseContext... responseContexts) {
	BMBPayloadHeader bmbPayloadHeader = null;
	BMBPayload bmbPayload = null;
	for (ResponseContext response : responseContexts) {
	    if (response != null && !response.isSuccess()) {
		bmbPayloadHeader = createHeader(response);
		break;
	    }
	}

	if (bmbPayloadHeader != null) {
	    bmbPayload = new BMBPayload(bmbPayloadHeader);
	} else {
	    ResponseContext response = responseContexts[0];
	    bmbPayload = new BMBPayload(createHeader(response));
	    populatePayLoad(bmbPayload, responseContexts);
	}

	return bmbPayload;
    }

    /**
     * @param response
     * @return BMBPayloadHeader
     */
    protected BMBPayloadHeader createHeader(ResponseContext response) {

	BMBPayloadHeader header = new BMBPayloadHeader();
	SearchTransactionHistoryOperationResponse resp = (SearchTransactionHistoryOperationResponse) response;
	if (response != null) {
	    if (response.isSuccess()) {
		header.setResCde(ResponseCodeConstants.SUCCESS_RES_CODE);
		header.setResMsg("");
	    } else {
		header.setResCde(response.getResCde());
		header.setResMsg(response.getResMsg());
	    }
	    header.setResId(getResponseId(resp.getFundTransferType()));

	}
	header.setSerVer(ResponseIdConstants.RESPONSE_SERVICE_VERSION);

	return header;
    }

    /**
     * @param transactionHistoryDTO
     * @param transactionHistoryJSONBean
     * 
     *            Populate json response for BillPayment History
     */
    private void populateBillPaymentHistoryPayload(TransactionHistoryDTO transactionHistoryDTO, TransactionHistoryJSONBean transactionHistoryJSONBean) {

	BillPaymentHistory billPaymentHistory = (BillPaymentHistory) transactionHistoryDTO;

	TransactionHistoryJSONModel transactionHistoryJSONModel = new TransactionHistoryJSONModel();

	CasaAccountJSONModel fromAccountInfo = new CasaAccountJSONModel();
	fromAccountInfo.setMkdActNo(getMaskedCardNumber(billPaymentHistory.getFromAccount().getAccountNumber()));
	fromAccountInfo.setTyp(billPaymentHistory.getFromAccount().getAccountType());

	transactionHistoryJSONModel.setFromAccountInfo(fromAccountInfo);

	BillerJSONModel billerJSONModel = new BillerJSONModel();
	billerJSONModel.setBillerName(billPaymentHistory.getBillerName());
	billerJSONModel.setBillerRefNo(billPaymentHistory.getBillReferenceNumber());
	billerJSONModel.setBillerId(billPaymentHistory.getBillerId());
	transactionHistoryJSONModel.setBillerInfo(billerJSONModel);

	transactionHistoryJSONModel.setTransactionDate(BMGFormatUtility.getLongDate(billPaymentHistory.getTransactionDate()));
	transactionHistoryJSONModel.setTransactionRefNumber(billPaymentHistory.getTransactionReferenceNumber());
	transactionHistoryJSONModel.setStatus(billPaymentHistory.getStatus());
	transactionHistoryJSONModel.setTransactionType(billPaymentHistory.getTransactionType());

	AmountJSONModel amountJSONModel = new AmountJSONModel();
	amountJSONModel.setAmt(billPaymentHistory.getAmount().getAmount().toString());
	amountJSONModel.setCurr(billPaymentHistory.getAmount().getCurrency());
	transactionHistoryJSONModel.setAmountInfo(amountJSONModel);
	transactionHistoryJSONBean.addToTransactionHistoryJSONModelList(transactionHistoryJSONModel);
    }

}
