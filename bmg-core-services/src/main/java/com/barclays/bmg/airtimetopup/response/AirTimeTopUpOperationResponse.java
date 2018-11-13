package com.barclays.bmg.airtimetopup.response;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.service.response.BillerServiceResponse;

public class AirTimeTopUpOperationResponse extends ResponseContext {

    /**
     *Default Version Id
     */
    private static final long serialVersionUID = -865374500576533132L;

    private BillerServiceResponse billerServiceResponse;

    /**
     * @return the billerServiceResponse
     */
    public BillerServiceResponse getBillerServiceResponse() {
	return billerServiceResponse;
    }

    /**
     * @param billerServiceResponse
     *            the billerServiceResponse to set
     */
    public void setBillerServiceResponse(BillerServiceResponse billerServiceResponse) {
	this.billerServiceResponse = billerServiceResponse;
    }

}
