package com.barclays.bmg.operation.response.secondauth;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.operation.response.SQAGenerateAuthenticationOperationResponse;

public class TxnSecondAuthSQAOperationResponse extends ResponseContext {

    private static final long serialVersionUID = 3756909382273603744L;

    private SQAGenerateAuthenticationOperationResponse sqaResponse;
    private String txnRefNo;

    public SQAGenerateAuthenticationOperationResponse getSqaResponse() {
	return sqaResponse;
    }

    public void setSqaResponse(SQAGenerateAuthenticationOperationResponse sqaResponse) {
	this.sqaResponse = sqaResponse;
    }

    public String getTxnRefNo() {
	return txnRefNo;
    }

    public void setTxnRefNo(String txnRefNo) {
	this.txnRefNo = txnRefNo;
    }

}
