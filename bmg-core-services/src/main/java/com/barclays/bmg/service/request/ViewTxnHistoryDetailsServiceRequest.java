package com.barclays.bmg.service.request;

import com.barclays.bmg.context.RequestContext;

public class ViewTxnHistoryDetailsServiceRequest extends RequestContext {

    private String transactionRefNo;

    public String getTransactionRefNo() {
	return transactionRefNo;
    }

    public void setTransactionRefNo(String transactionRefNo) {
	this.transactionRefNo = transactionRefNo;
    }

}
