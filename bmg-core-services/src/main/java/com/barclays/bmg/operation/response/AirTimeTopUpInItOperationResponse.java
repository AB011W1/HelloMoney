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
import com.barclays.bmg.operation.response.fundtransfer.external.RetrieveAcctListOperationResponse;

/**
 * ****************** Revision History ********************** </br> Project Name is <b>bmg-core-services</b> </br> The file name is
 * <b>AirTimeTopUpInItOperationResponse.java</b> </br> Description is <b>Initial Version</b> </br> Updated Date is <b>May 17, 2013</b> </br>
 * ******************************************************
 * 
 * @author e20037686 </br> * The Class AirTimeTopUpInItOperationResponse created using JRE 1.6.0_10
 */
public class AirTimeTopUpInItOperationResponse extends ResponseContext {

    /**
     * The Constant named "serialVersionUID" is created.
     */
    private static final long serialVersionUID = -2949911197694137278L;

    /**
     * The instance variable named "acctListOperationResponse" is created.
     */
    private RetrieveAcctListOperationResponse acctListOperationResponse;

    /**
     * The instance variable named "airTimeTopUpOperationResponse" is created.
     */
    private AirTimeTopUpOperationResponse airTimeTopUpOperationResponse;

    /**
     * Gets the acct list operation response.
     * 
     * @return the AcctListOperationResponse
     */
    public RetrieveAcctListOperationResponse getAcctListOperationResponse() {
	return acctListOperationResponse;
    }

    /**
     * Sets values for AcctListOperationResponse.
     * 
     * @param acctListOperationResponse
     *            the acct list operation response
     */
    public void setAcctListOperationResponse(RetrieveAcctListOperationResponse acctListOperationResponse) {
	this.acctListOperationResponse = acctListOperationResponse;
    }

    /**
     * Gets the air time top up operation response.
     * 
     * @return the AirTimeTopUpOperationResponse
     */
    public AirTimeTopUpOperationResponse getAirTimeTopUpOperationResponse() {
	return airTimeTopUpOperationResponse;
    }

    /**
     * Sets values for AirTimeTopUpOperationResponse.
     * 
     * @param airTimeTopUpOperationResponse
     *            the air time top up operation response
     */
    public void setAirTimeTopUpOperationResponse(AirTimeTopUpOperationResponse airTimeTopUpOperationResponse) {
	this.airTimeTopUpOperationResponse = airTimeTopUpOperationResponse;
    }
}