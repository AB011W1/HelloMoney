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

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.service.response.BillerServiceResponse;

/**
 * ****************** Revision History ********************** </br> Project Name is <b>bmg-core-services</b> </br> The file name is
 * <b>AirTimeTopUpOperationResponse.java</b> </br> Description is <b>Initial Version</b> </br> Updated Date is <b>May 17, 2013</b> </br>
 * ******************************************************
 * 
 * @author e20037686 </br> * The Class AirTimeTopUpOperationResponse created using JRE 1.6.0_10
 */
public class AirTimeTopUpOperationResponse extends ResponseContext {

    /**
     * The Constant named "serialVersionUID" is created.
     */
    private static final long serialVersionUID = -865374500576533132L;

    /**
     * The instance variable named "billerServiceResponse" is created.
     */
    private BillerServiceResponse billerServiceResponse;

    /**
     * Gets the biller service response.
     * 
     * @return the BillerServiceResponse
     */
    public BillerServiceResponse getBillerServiceResponse() {
	return billerServiceResponse;
    }

    /**
     * Sets values for BillerServiceResponse.
     * 
     * @param billerServiceResponse
     *            the biller service response
     */
    public void setBillerServiceResponse(BillerServiceResponse billerServiceResponse) {
	this.billerServiceResponse = billerServiceResponse;
    }

}
