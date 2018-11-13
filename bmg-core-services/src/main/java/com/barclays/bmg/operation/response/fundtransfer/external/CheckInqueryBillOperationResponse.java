package com.barclays.bmg.operation.response.fundtransfer.external;

import java.math.BigDecimal;

import com.barclays.bmg.context.ResponseContext;

public class CheckInqueryBillOperationResponse extends ResponseContext {

    private static final long serialVersionUID = -5221073743581809632L;

    private BigDecimal minBillAmt;
    private BigDecimal maxBilAmt;
    private BigDecimal outBalAmt;

    public BigDecimal getMinBillAmt() {
	return minBillAmt;
    }

    public void setMinBillAmt(BigDecimal minBillAmt) {
	this.minBillAmt = minBillAmt;
    }

    public BigDecimal getMaxBilAmt() {
	return maxBilAmt;
    }

    public void setMaxBilAmt(BigDecimal maxBilAmt) {
	this.maxBilAmt = maxBilAmt;
    }

    public BigDecimal getOutBalAmt() {
	return outBalAmt;
    }

    public void setOutBalAmt(BigDecimal outBalAmt) {
	this.outBalAmt = outBalAmt;
    }

}
