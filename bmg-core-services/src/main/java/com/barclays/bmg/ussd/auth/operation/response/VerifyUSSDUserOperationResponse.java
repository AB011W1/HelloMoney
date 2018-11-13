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
package com.barclays.bmg.ussd.auth.operation.response;

import com.barclays.bmg.context.ResponseContext;

/**
 * * ****************** Revision History ********************** </br> Project Name is <b>bmg-auth-4.0.10</b> </br> The file name is
 * <b>VerifyUSSDUserOperationResponse.java</b> </br> Description is <b>Initial Version</b> </br> Updated Date is <b>May 21, 2013</b> </br>
 * ************************************************************
 *
 * @author E20043104
 *
 */
public class VerifyUSSDUserOperationResponse extends ResponseContext {

    private String cryptoStatusCode;
    private String userStatusInMCE;
    private String langPref;
    private String cryptoPinStatus;
    private String scvId;
    private String customerAccessStatusCode;
    //CR-77:Customer Name Shown on page
    private String customerFirstName;


    public String getCryptoStatusCode() {
	return cryptoStatusCode;
    }

    public void setCryptoStatusCode(String cryptoStatusCode) {
	this.cryptoStatusCode = cryptoStatusCode;
    }

    /**
     * @return the userStatusInMCE
     */
    public String getUserStatusInMCE() {
	return userStatusInMCE;
    }

    /**
     * @param userStatusInMCE
     *            the userStatusInMCE to set
     */
    public void setUserStatusInMCE(String userStatusInMCE) {
	this.userStatusInMCE = userStatusInMCE;
    }

    public String getLangPref() {
	return langPref;
    }

    public void setLangPref(String langPref) {
	this.langPref = langPref;
    }

    public String getCryptoPinStatus() {
	return cryptoPinStatus;
    }

    public void setCryptoPinStatus(String cryptoPinStatus) {
	this.cryptoPinStatus = cryptoPinStatus;
    }

    public String getScvId() {
	return scvId;
    }

    public void setScvId(String scvId) {
	this.scvId = scvId;
    }

	public String getCustomerAccessStatusCode() {
		return customerAccessStatusCode;
	}

	public void setCustomerAccessStatusCode(String customerAccessStatusCode) {
		this.customerAccessStatusCode = customerAccessStatusCode;
	}

	public void setCustomerFirstName(String customerFirstName) {
		this.customerFirstName = customerFirstName;
	}

	public String getCustomerFirstName() {
		return customerFirstName;
	}
}