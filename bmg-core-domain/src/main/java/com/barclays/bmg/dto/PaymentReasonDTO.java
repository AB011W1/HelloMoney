package com.barclays.bmg.dto;

public class PaymentReasonDTO extends BaseDomainDTO {

    /**
	 *
	 */
    private static final long serialVersionUID = 8956356209670018501L;
    private String payRsonKey;
    private String payRsonValue;

    public String getPayRsonKey() {
	return payRsonKey;
    }

    public void setPayRsonKey(String payRsonKey) {
	this.payRsonKey = payRsonKey;
    }

    public String getPayRsonValue() {
	return payRsonValue;
    }

    public void setPayRsonValue(String payRsonValue) {
	this.payRsonValue = payRsonValue;
    }

}
