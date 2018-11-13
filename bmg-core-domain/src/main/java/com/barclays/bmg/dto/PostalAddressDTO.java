package com.barclays.bmg.dto;

import com.barclays.bmg.dto.BaseDomainDTO;

public class PostalAddressDTO extends BaseDomainDTO {

    public static final String MAILING_ADDRESS = "001";
    public static final String PERMANENT_ADDRESS = "002";

    private static final long serialVersionUID = 4863731321158802975L;

    private String typeCode;
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String countryCode;
    private String country;
    private String stateCode;
    private String stateName;
    private String cityCode;
    private String cityName;
    private String zipCode;

    public String getTypeCode() {
	return typeCode;
    }

    public void setTypeCode(String typeCode) {
	this.typeCode = typeCode;
    }

    public String getAddressLine1() {
	return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
	this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
	return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
	this.addressLine2 = addressLine2;
    }

    public String getAddressLine3() {
	return addressLine3;
    }

    public void setAddressLine3(String addressLine3) {
	this.addressLine3 = addressLine3;
    }

    public String getCountry() {
	return country;
    }

    public void setCountry(String country) {
	this.country = country;
    }

    public String getStateName() {
	return stateName;
    }

    public void setStateName(String stateName) {
	this.stateName = stateName;
    }

    public String getCityName() {
	return cityName;
    }

    public void setCityName(String cityName) {
	this.cityName = cityName;
    }

    public String getCountryCode() {
	return countryCode;
    }

    public void setCountryCode(String countryCode) {
	this.countryCode = countryCode;
    }

    public String getStateCode() {
	return stateCode;
    }

    public void setStateCode(String stateCode) {
	this.stateCode = stateCode;
    }

    public String getCityCode() {
	return cityCode;
    }

    public void setCityCode(String cityCode) {
	this.cityCode = cityCode;
    }

    public String getZipCode() {
	return zipCode;
    }

    public void setZipCode(String zipCode) {
	this.zipCode = zipCode;
    }
}
