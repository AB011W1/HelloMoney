package com.barclays.bmg.operation.request.fundtransfer.external;

import com.barclays.bmg.context.RequestContext;

/**
 * @author BTCI
 * 
 */
public class DeleteBeneficiaryOperationRequest extends RequestContext {

    private String payeeId;

    /**
     * @return String
     */
    public String getPayeeId() {
	return payeeId;
    }

    /**
     * @param payeeId
     */
    public void setPayeeId(String payeeId) {
	this.payeeId = payeeId;
    }
}
