package com.barclays.bmg.operation.request.billpayment;

import com.barclays.bmg.context.RequestContext;

public class RetreiveCCPPayeeInformationOperationRequest extends RequestContext {

    private String payId;
    private String toAcctNo;
    private String payType;

    public String getPayId() {
	return payId;
    }

    public void setPayId(String payId) {
	this.payId = payId;
    }

    public String getToAcctNo() {
	return toAcctNo;
    }

    public void setToAcctNo(String toAcctNo) {
	this.toAcctNo = toAcctNo;
    }

    public String getPayType() {
	return payType;
    }

    public void setPayType(String payType) {
	this.payType = payType;
    }
}
