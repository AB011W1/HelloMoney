package com.barclays.bmg.airtimetopup.response;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.operation.response.fundtransfer.external.RetrieveAcctListOperationResponse;

public class AirTimeTopUpInItOperationResponse extends ResponseContext {

    private static final long serialVersionUID = -2949911197694137278L;

    private RetrieveAcctListOperationResponse acctListOperationResponse;

    private AirTimeTopUpOperationResponse airTimeTopUpOperationResponse;

    /**
     * @return the acctListOperationResponse
     */
    public RetrieveAcctListOperationResponse getAcctListOperationResponse() {
	return acctListOperationResponse;
    }

    /**
     * @param acctListOperationResponse
     *            the acctListOperationResponse to set
     */
    public void setAcctListOperationResponse(RetrieveAcctListOperationResponse acctListOperationResponse) {
	this.acctListOperationResponse = acctListOperationResponse;
    }

    /**
     * @return the airTimeTopUpOperationResponse
     */
    public AirTimeTopUpOperationResponse getAirTimeTopUpOperationResponse() {
	return airTimeTopUpOperationResponse;
    }

    /**
     * @param airTimeTopUpOperationResponse
     *            the airTimeTopUpOperationResponse to set
     */
    public void setAirTimeTopUpOperationResponse(AirTimeTopUpOperationResponse airTimeTopUpOperationResponse) {
	this.airTimeTopUpOperationResponse = airTimeTopUpOperationResponse;
    }

}
