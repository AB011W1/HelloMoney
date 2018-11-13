/*
 * Copyright (c) 2013 Barclays Bank Plc, All Rights Reserved.
 *
 * This code is confidential to Barclays Bank Plc and shall not be disclosed
 * outside the Bank without the prior written permission of the Director of
 * CIO
 *
 * In the event that such disclosure is permitted the code shall not be copied
 * or distributed other than on a need-to-know basis and any recipients may be
 * required to sign a confidentiality undertaking in favour of Barclays Bank
 * Plc.
 */
package com.barclays.bmg.ussd.auth.operation.request;

import com.barclays.bmg.context.RequestContext;

/**
 * * ****************** Revision History ********************** </br> Project Name is <b>bmg-core-services</b> </br> The file name is
 * <b>SelfRegistrationExecutionOperationRequest.java</b> </br> Description is <b>Initial Version</b> </br> Updated Date is <b>May 22, 2013</b> </br>
 * ***********************************************************
 * 
 * @author E20043104
 * 
 */
public class SelfRegistrationExecutionOperationRequest extends RequestContext {

    private String mobileNo;

    private String accountNo;

    private String branchCode;

    private String prefLang;

    public String getPrefLang() {
	return prefLang;
    }

    public void setPrefLang(String prefLang) {
	this.prefLang = prefLang;
    }

    public String getBranchCode() {
	return branchCode;
    }

    public void setBranchCode(String branchCode) {
	this.branchCode = branchCode;
    }

    public String getMobileNo() {
	return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
	this.mobileNo = mobileNo;
    }

    public String getAccountNo() {
	return accountNo;
    }

    public void setAccountNo(String accountNo) {
	this.accountNo = accountNo;
    }

}
