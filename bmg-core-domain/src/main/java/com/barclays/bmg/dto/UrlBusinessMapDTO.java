package com.barclays.bmg.dto;

import com.barclays.bmg.dto.BaseDomainDTO;

@SuppressWarnings("serial")
public class UrlBusinessMapDTO extends BaseDomainDTO {

    private String countryCode;

    private String localCurrencyCode;

    private int tzOffSet;

    public String getCountryCode() {
	return countryCode;
    }

    public void setCountryCode(String countryCode) {
	this.countryCode = countryCode;
    }

    public String getLocalCurrencyCode() {
	return localCurrencyCode;
    }

    public void setLocalCurrencyCode(String localCurrencyCode) {
	this.localCurrencyCode = localCurrencyCode;
    }

    public int getTzOffSet() {
	return tzOffSet;
    }

    public void setTzOffSet(int tzOffSet) {
	this.tzOffSet = tzOffSet;
    }

}
