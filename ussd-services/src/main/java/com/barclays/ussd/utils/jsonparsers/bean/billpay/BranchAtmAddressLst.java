/**
 * CatPayeeLst.java
 */
package com.barclays.ussd.utils.jsonparsers.bean.billpay;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author BTCI
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BranchAtmAddressLst {

    @JsonProperty
    private String businessId;

    @JsonProperty
    private String name;

    @JsonProperty
    private String address;

    @JsonProperty
    private String city;

    @JsonProperty
    private String state;

    @JsonProperty
    private String contNum;

    @JsonProperty
    private String businessHours;

    public String getBusinessId() {
	return businessId;
    }

    public void setBusinessId(String businessId) {
	this.businessId = businessId;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getAddress() {
	return address;
    }

    public void setAddress(String address) {
	this.address = address;
    }

    public String getBusinessHours() {
	return businessHours;
    }

    public void setBusinessHours(String businessHours) {
	this.businessHours = businessHours;
    }

    public String getCity() {
	return city;
    }

    public void setCity(String city) {
	this.city = city;
    }

    public String getState() {
	return state;
    }

    public void setState(String state) {
	this.state = state;
    }

    public String getContNum() {
	return contNum;
    }

    public void setContNum(String contNum) {
	this.contNum = contNum;
    }

}
