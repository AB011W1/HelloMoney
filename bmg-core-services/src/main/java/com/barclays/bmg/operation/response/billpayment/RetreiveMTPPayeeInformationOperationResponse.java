package com.barclays.bmg.operation.response.billpayment;

public class RetreiveMTPPayeeInformationOperationResponse extends RetreivePayeeInformationOperationResponse {

    private static final long serialVersionUID = 5804974537854457317L;
    private String paySer;

    public String getPaySer() {
	return paySer;
    }

    public void setPaySer(String paySer) {
	this.paySer = paySer;
    }

}
