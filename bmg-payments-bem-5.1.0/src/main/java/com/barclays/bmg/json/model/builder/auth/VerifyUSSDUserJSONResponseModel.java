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
package com.barclays.bmg.json.model.builder.auth;

import com.barclays.bmg.json.response.BMBPayloadData;

/**
 * * ****************** Revision History ********************** </br> Project Name is <b>bmg-auth-4.0.10</b> </br> The file name is
 * <b>VerifyUSSDUserJSONResponseModel.java</b> </br> Description is <b>Initial Version</b> </br> Updated Date is <b>May 21, 2013</b> </br>
 * ***********************************************************
 *
 * @author E20043104
 *
 */
public class VerifyUSSDUserJSONResponseModel extends BMBPayloadData {

    /**
	 *
	 */
    private static final long serialVersionUID = -4371096448019827135L;

    private String cryptoStatusCode;
    private String cryptoPinStatus;
    private String userStatusInMCE;
    private String langPref;
    private String scvId;
    private String customerAccessStatusCode;
    //CR-77
    private String customerFirstName;
    
    //Set welcome banner in customerDTO for INC INC0063990
    private String bocBannerFlag;
    private String bannerMessage;

    public String getBocBannerFlag() {
		return bocBannerFlag;
	}

	public void setBocBannerFlag(String bocBannerFlag) {
		this.bocBannerFlag = bocBannerFlag;
	}

	public String getBannerMessage() {
		return bannerMessage;
	}

	public void setBannerMessage(String bannerMessage) {
		this.bannerMessage = bannerMessage;
	}

	public String getCryptoStatusCode() {
	return cryptoStatusCode;
    }

    public void setCryptoStatusCode(String cryptoStatusCode) {
	this.cryptoStatusCode = cryptoStatusCode;
    }

    public String getCryptoPinStatus() {
	return cryptoPinStatus;
    }

    public void setCryptoPinStatus(String cryptoPinStatus) {
	this.cryptoPinStatus = cryptoPinStatus;
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