package com.barclays.bmg.service.request;

import com.barclays.bem.BEMServiceHeader.BEMResHeader;
import com.barclays.bmg.context.RequestContext;

public class ReportProblemServiceRequest extends RequestContext {

    private String accountNumber;
    private String accountType;
    private BEMResHeader responseHeader;
    private String productName;
    private String subProductName;

    public String getAccountNumber() {
	return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
	this.accountNumber = accountNumber;
    }

    public String getAccountType() {
	return accountType;
    }

    public void setAccountType(String accountType) {
	this.accountType = accountType;
    }

    public BEMResHeader getResponseHeader() {
	return responseHeader;
    }

    public void setResponseHeader(BEMResHeader responseHeader) {
	this.responseHeader = responseHeader;
    }

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductName() {
		return productName;
	}

	public void setSubProductName(String subProductName) {
		this.subProductName = subProductName;
	}

	public String getSubProductName() {
		return subProductName;
	}

}
