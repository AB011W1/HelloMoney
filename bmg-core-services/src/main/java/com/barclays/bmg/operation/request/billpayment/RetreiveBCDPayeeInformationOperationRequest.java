package com.barclays.bmg.operation.request.billpayment;

import com.barclays.bmg.context.RequestContext;

public class RetreiveBCDPayeeInformationOperationRequest extends RequestContext {

    private String payId;
    private String payType;
    private String toAcctNo;

    public String getPayId() {
	return payId;
    }

    public void setPayId(String payId) {
	this.payId = payId;
    }

    public String getPayType() {
	return payType;
    }

    public void setPayType(String payType) {
	this.payType = payType;
    }

    public String getToAcctNo() {
	return toAcctNo;
    }

    public void setToAcctNo(String toAcctNo) {
	this.toAcctNo = toAcctNo;
    }

}
