package com.barclays.bmg.dto;

public class ContactInfoDTO extends BaseDTO {

    /**
	 *
	 */
    private static final long serialVersionUID = 4905545099212512729L;

    private String fullName;
    private String addressLineOne;
    private String addressLineTwo;
    private String addressLineThree;
    private String contactNo;

    public String getAddressLineOne() {
	return addressLineOne;
    }

    public void setAddressLineOne(String addressLineOne) {
	this.addressLineOne = addressLineOne;
    }

    public String getAddressLineTwo() {
	return addressLineTwo;
    }

    public void setAddressLineTwo(String addressLineTwo) {
	this.addressLineTwo = addressLineTwo;
    }

    public String getAddressLineThree() {
	return addressLineThree;
    }

    public void setAddressLineThree(String addressLineThree) {
	this.addressLineThree = addressLineThree;
    }

    public String getContactNo() {
	return contactNo;
    }

    public void setContactNo(String contactNo) {
	this.contactNo = contactNo;
    }

    public String getFullName() {
	return fullName;
    }

    public void setFullName(String fullName) {
	this.fullName = fullName;
    }

}
