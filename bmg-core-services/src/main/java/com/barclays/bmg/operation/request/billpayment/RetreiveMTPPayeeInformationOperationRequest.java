package com.barclays.bmg.operation.request.billpayment;

import com.barclays.bmg.context.RequestContext;

public class RetreiveMTPPayeeInformationOperationRequest extends RequestContext {

    private String payeeId;
    private String mtpService;
    private String payeeType;

    public String getPayeeId() {
	return payeeId;
    }

    public void setPayeeId(String payeeId) {
	this.payeeId = payeeId;
    }

    public String getMtpService() {
	return mtpService;
    }

    public void setMtpService(String mtpService) {
	this.mtpService = mtpService;
    }

    public String getPayeeType() {
	return payeeType;
    }

    public void setPayeeType(String payeeType) {
	this.payeeType = payeeType;
    }

}
