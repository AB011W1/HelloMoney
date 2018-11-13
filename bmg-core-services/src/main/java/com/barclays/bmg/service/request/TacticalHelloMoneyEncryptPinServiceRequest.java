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

import java.util.Properties;

import com.barclays.bmg.context.RequestContext;

public class TacticalHelloMoneyEncryptPinServiceRequest extends RequestContext {

    private String pin;

    private Properties bcagProperties;

    public String getPin() {
	return pin;
    }

    public void setPin(String pin) {
	this.pin = pin;
    }

    public Properties getBcagProperties() {
	return bcagProperties;
    }

    public void setBcagProperties(Properties bcagProperties) {
	this.bcagProperties = bcagProperties;
    }

}