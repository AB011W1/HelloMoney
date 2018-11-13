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
package com.barclays.bmg.ussd.auth.service.response;

import com.barclays.bmg.context.ResponseContext;

public class VerifyMigratedUserServiceResponse extends ResponseContext {

    private String customerDetails;

    private boolean registered;

    private String scvid;

    private String bankCIF;

    private String serviceResponse;

    public String getServiceResponse() {
	return serviceResponse;
    }

    public void setServiceResponse(String serviceResponse) {
	this.serviceResponse = serviceResponse;
    }

    public boolean isRegistered() {
	return registered;
    }

    public void setRegistered(boolean registered) {
	this.registered = registered;
    }

    private boolean addedToMCE;

    public boolean isAddedToMCE() {
	return addedToMCE;
    }

    public void setAddedToMCE(boolean addedToMCE) {
	this.addedToMCE = addedToMCE;
    }

    private boolean pinSent;

    public boolean isPinSent() {
	return pinSent;
    }

    public void setPinSent(boolean pinSent) {
	this.pinSent = pinSent;
    }

    public String getCustomerDetails() {
	return customerDetails;
    }

    public void setCustomerDetails(String customerDetails) {
	this.customerDetails = customerDetails;
    }

    public String getScvid() {
	return scvid;
    }

    public void setScvid(String scvid) {
	this.scvid = scvid;
    }

    public String getBankCIF() {
	return bankCIF;
    }

    public void setBankCIF(String bankCIF) {
	this.bankCIF = bankCIF;
    }

}
