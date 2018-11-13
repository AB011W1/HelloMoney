package com.barclays.bmg.operation.accountdetails.request;

import com.barclays.bmg.context.RequestContext;

public class ApplyTDValidationOperationRequest extends RequestContext {
    private String acctNo;
    private String depositAmount;
    private String tenureMonths;
    private String tenureDays;
    private String productId;

    public String getAcctNo() {
	return acctNo;
    }

    public void setAcctNo(String acctNo2) {
	this.acctNo = acctNo2;
    }

    public String getDepositAmount() {
	return depositAmount;
    }

    public void setDepositAmount(String depositAmount) {
	this.depositAmount = depositAmount;
    }

    public void setProductId(String productId) {
	this.productId = productId;
    }

    public String getProductId() {
	return productId;
    }

    public void setTenureMonths(String tenureMonth) {
	this.tenureMonths = tenureMonth;
    }

    public String getTenureMonths() {
	return tenureMonths;
    }

    public void setTenureDays(String tenureDays) {
	this.tenureDays = tenureDays;
    }

    public String getTenureDays() {
	return tenureDays;
    }

}
