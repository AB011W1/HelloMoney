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
/**
 * Package name is com.barclays.bmg.service.request
 * /
 */
package com.barclays.bmg.service.request;

import com.barclays.bmg.context.RequestContext;

public class TacticalHelloMoneyVerifyUserServiceRequest extends RequestContext {

    private String custMSISDN;

    private String custPIN;

    private String thmUrl;

    /**
     * @return the custMSISDN
     */
    public String getCustMSISDN() {
	return custMSISDN;
    }

    /**
     * @param custMSISDN
     *            the custMSISDN to set
     */
    public void setCustMSISDN(String custMSISDN) {
	this.custMSISDN = custMSISDN;
    }

    /**
     * @return the custPIN
     */
    public String getCustPIN() {
	return custPIN;
    }

    /**
     * @param custPIN
     *            the custPIN to set
     */
    public void setCustPIN(String custPIN) {
	this.custPIN = custPIN;
    }

    public String getThmUrl() {
	return thmUrl;
    }

    public void setThmUrl(String thmUrl) {
	this.thmUrl = thmUrl;
    }

}