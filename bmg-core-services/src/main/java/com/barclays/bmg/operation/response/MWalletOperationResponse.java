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
 * Package name is com.barclays.bmg.operation.response
 * /
 */
package com.barclays.bmg.operation.response;

import java.util.List;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.BillerDTO;

/**
 * ****************** Revision History ********************** </br> Project Name is <b>bmg-core-services</b> </br> The file name is
 * <b>MWalletOperationResponse.java</b> </br> Description is <b>Initial Version</b> </br> Updated Date is <b>May 17, 2013</b> </br>
 * ******************************************************
 * 
 * @author e20037686 </br> * The Class MWalletOperationResponse created using JRE 1.6.0_10
 */
public class MWalletOperationResponse extends ResponseContext {

    /**
     * The Constant named "serialVersionUID" is created.
     */
    private static final long serialVersionUID = 8758180742106273186L;

    /**
     * The instance variable named "billerList" is created.
     */
    private List<BillerDTO> billerList;

    /**
     * Gets the biller list.
     * 
     * @return the BillerList
     */
    public List<BillerDTO> getBillerList() {
	return billerList;
    }

    /**
     * Sets values for BillerList.
     * 
     * @param billerList
     *            the biller list
     */
    public void setBillerList(List<BillerDTO> billerList) {
	this.billerList = billerList;
    }
}