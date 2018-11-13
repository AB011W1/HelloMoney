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
 * Package name is com.barclays.bmg.operation.request
 * /
 */
package com.barclays.bmg.operation.request;

import com.barclays.bmg.context.RequestContext;

/**
 * ****************** Revision History ********************** </br> Project Name is <b>bmg-core-services</b> </br> The file name is
 * <b>MWalletOperationRequest.java</b> </br> Description is <b>Initial Version</b> </br> Updated Date is <b>May 17, 2013</b> </br>
 * ******************************************************
 * 
 * @author e20037686 </br> * The Class MWalletOperationRequest created using JRE 1.6.0_10
 */
public class MWalletOperationRequest extends RequestContext {

    /**
     * The instance variable named "billerCatId" is created.
     */
    private String billerCatId;

    /**
     * Gets the biller cat id.
     * 
     * @return the BillerCatId
     */
    public String getBillerCatId() {
	return billerCatId;
    }

    /**
     * Sets values for BillerCatId.
     * 
     * @param billerCatId
     *            the biller cat id
     */
    public void setBillerCatId(String billerCatId) {
	this.billerCatId = billerCatId;
    }
}
