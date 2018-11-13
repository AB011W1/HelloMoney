package com.barclays.bmg.ussd.auth.operation.response;

import com.barclays.bmg.context.ResponseContext;

/**
 * * ****************** Revision History ********************** </br> Project Name is <b>bmg-core-services</b> </br> The file name is
 * <b>SelfRegistrationExecutionOperationResponse.java</b> </br> Description is <b>Initial Version</b> </br> Updated Date is <b>May 22, 2013</b> </br>
 * ***********************************************************
 * 
 * @author E20043104
 * 
 */
public class SelfRegistrationExecutionOperationResponse extends ResponseContext {

    /**
	 *
	 */
    private static final long serialVersionUID = -1875329580372001653L;

    private String customerDetails;

    private String pin;

    private String scvid;

    private String bankCIF;
    
    private String recipientName;

    public String getBankCIF() {
	return bankCIF;
    }

    public void setBankCIF(String bankCIF) {
	this.bankCIF = bankCIF;
    }

    public String getScvid() {
	return scvid;
    }

    public void setScvid(String scvid) {
	this.scvid = scvid;
    }

    public String getPin() {
	return pin;
    }

    public void setPin(String pin) {
	this.pin = pin;
    }

    private boolean registered;

    private String createdByInMCE;

    private boolean pinSent;

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

    public String getCreatedByInMCE() {
	return createdByInMCE;
    }

    public void setCreatedByInMCE(String createdByInMCE) {
	this.createdByInMCE = createdByInMCE;
    }

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

	public String getRecipientName() {
		return recipientName;
	}

	public void setRecipientName(String recipientName) {
		this.recipientName = recipientName;
	}
    
}
