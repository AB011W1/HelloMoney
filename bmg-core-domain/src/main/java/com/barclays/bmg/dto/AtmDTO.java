package com.barclays.bmg.dto;

public class AtmDTO extends BaseDTO {

    /**
	 *
	 */
    private static final long serialVersionUID = 3256312993522118925L;

    private String businessId;
    private String name;
    private String address;
    private String city;
    private String state;

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

    public static long getSerialVersionUID() {
	return serialVersionUID;
    }

}
