package com.barclays.bmg.operation.request.fundtransfer.external;

import java.math.BigDecimal;

import com.barclays.bmg.context.RequestContext;

public class TransactionLimitOperationRequest extends RequestContext {

    private BigDecimal txnAmt;

    public BigDecimal getTxnAmt() {
	return txnAmt;
    }

    public void setTxnAmt(BigDecimal txnAmt) {
	this.txnAmt = txnAmt;
    }

}
