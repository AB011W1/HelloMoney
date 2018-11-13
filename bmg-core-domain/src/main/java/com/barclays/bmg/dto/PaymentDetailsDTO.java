package com.barclays.bmg.dto;

public class PaymentDetailsDTO extends BaseDomainDTO {

    /**
	 *
	 */
    private static final long serialVersionUID = -1580683746075335185L;
    private String payDtlsKey;
    private String payDtlsValue;
    private String filterKey1;

    public String getPayDtlsKey() {
	return payDtlsKey;
    }

    public void setPayDtlsKey(String payDtlsKey) {
	this.payDtlsKey = payDtlsKey;
    }

    public String getPayDtlsValue() {
	return payDtlsValue;
    }

    public void setPayDtlsValue(String payDtlsValue) {
	this.payDtlsValue = payDtlsValue;
    }

    public String getFilterKey1() {
	return filterKey1;
    }

    public void setFilterKey1(String filterKey1) {
	this.filterKey1 = filterKey1;
    }

}
