package com.barclays.bmg.operation.request.billpayment;

import com.barclays.bmg.context.RequestContext;

public class ViewTxnHistoryDetailsOperationRequest extends RequestContext {

    private String transactionRefNo;

    public String getTransactionRefNo() {
	return transactionRefNo;
    }

    public void setTransactionRefNo(String transactionRefNo) {
	this.transactionRefNo = transactionRefNo;
    }

}
