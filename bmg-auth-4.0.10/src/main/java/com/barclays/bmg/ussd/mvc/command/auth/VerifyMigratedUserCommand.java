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
package com.barclays.bmg.ussd.mvc.command.auth;

public class VerifyMigratedUserCommand {

    private String mobileNo;
    private String accountNo;
    private String branchCode;
    private String usrStatInMCE;
    private String usrNam;
    private String pass;

    public String getAccountNo() {
	return accountNo;
    }

    public void setAccountNo(String accountNo) {
	this.accountNo = accountNo;
    }

    public String getMobileNo() {
	return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
	this.mobileNo = mobileNo;
    }

    public String getBranchCode() {
	return branchCode;
    }

    public void setBranchCode(String branchCode) {
	this.branchCode = branchCode;
    }

    public String getUsrStatInMCE() {
	return usrStatInMCE;
    }

    public void setUsrStatInMCE(String usrStatInMCE) {
	this.usrStatInMCE = usrStatInMCE;
    }

    public String getUsrNam() {
	return usrNam;
    }

    public void setUsrNam(String usrNam) {
	this.usrNam = usrNam;
    }

    public String getPass() {
	return pass;
    }

    public void setPass(String pass) {
	this.pass = pass;
    }

}
