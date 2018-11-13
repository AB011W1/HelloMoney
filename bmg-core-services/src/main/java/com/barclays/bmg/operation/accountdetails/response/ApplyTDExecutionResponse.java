package com.barclays.bmg.operation.accountdetails.response;

import com.barclays.bmg.context.ResponseContext;

public class ApplyTDExecutionResponse extends ResponseContext {

    private static final long serialVersionUID = 1695401155047913117L;
    private String transactionRefNum;
    private String transactionDate;

    public static long getSerialVersionUID() {
	return serialVersionUID;
    }

    public void setTransactionRefNum(String transactionRefNum) {
	this.transactionRefNum = transactionRefNum;
    }

    public String getTransactionRefNum() {
	return transactionRefNum;
    }

    public void setTransactionDate(String transactionDate) {
	this.transactionDate = transactionDate;
    }

    public String getTransactionDate() {
	return transactionDate;
    }

}
