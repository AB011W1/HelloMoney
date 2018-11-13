package com.barclays.bmg.type;

import java.io.Serializable;
import java.util.Date;

/**************************** Revision History ******************************
 * Version        Author          Date                        Description
 * 0.1            Eric Zhang        2009/02/26                Initial version
 *
 *
 ****************************************************************************/

/**
 * @author Eric Zhang
 */
public abstract class Address implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String customerNo;

    private String addressType;

    private String addressReferenceNumber;

    private String addressLine1;

    private String addressLine2;

    private String addressLine3;

    private String addressLine4;

    private String addressLine5;

    private String addressLine6;

    private String city;

    private String cityCode;

    private String state;

    private String stateCode;

    private String country;

    private String countryCode;

    private String postcode;

    private Date effectiveDate;

    private Date endDate;

    private String okToMail;

    private String lastMaintainedBy;

    private Date lastMaintainedDate;

    private String lastMaintainedTime;

    /**
     * @return the customerNo
     */
    public final String getCustomerNo() {
	return customerNo;
    }

    /**
     * @param args
     *            the customerNo to set
     */
    public final void setCustomerNo(String args) {
	this.customerNo = args;
    }

    /**
     * @return the addressType
     */
    public final String getAddressType() {
	return addressType;
    }

    /**
     * @param args
     *            the addressType to set
     */
    public final void setAddressType(String args) {
	this.addressType = args;
    }

    /**
     * @return the addressReferenceNumber
     */
    public final String getAddressReferenceNumber() {
	return addressReferenceNumber;
    }

    /**
     * @param args
     *            the addressReferenceNumber to set
     */
    public final void setAddressReferenceNumber(String args) {
	this.addressReferenceNumber = args;
    }

    /**
     * @return the addressLine1
     */
    public final String getAddressLine1() {
	return addressLine1;
    }

    /**
     * @param args
     *            the addressLine1 to set
     */
    public final void setAddressLine1(String args) {
	this.addressLine1 = args;
    }

    /**
     * @return the addressLine2
     */
    public final String getAddressLine2() {
	return addressLine2;
    }

    /**
     * @param args
     *            the addressLine2 to set
     */
    public final void setAddressLine2(String args) {
	this.addressLine2 = args;
    }

    /**
     * @return the addressLine3
     */
    public final String getAddressLine3() {
	return addressLine3;
    }

    /**
     * @param args
     *            the addressLine3 to set
     */
    public final void setAddressLine3(String args) {
	this.addressLine3 = args;
    }

    /**
     * @return the addressLine4
     */
    public final String getAddressLine4() {
	return addressLine4;
    }

    /**
     * @param args
     *            the addressLine4 to set
     */
    public final void setAddressLine4(String args) {
	this.addressLine4 = args;
    }

    /**
     * @return the addressLine5
     */
    public final String getAddressLine5() {
	return addressLine5;
    }

    /**
     * @param args
     *            the addressLine5 to set
     */
    public final void setAddressLine5(String args) {
	this.addressLine5 = args;
    }

    /**
     * @return the addressLine6
     */
    public final String getAddressLine6() {
	return addressLine6;
    }

    /**
     * @param args
     *            the addressLine6 to set
     */
    public final void setAddressLine6(String args) {
	this.addressLine6 = args;
    }

    /**
     * @return the city
     */
    public final String getCity() {
	return city;
    }

    /**
     * @param args
     *            the city to set
     */
    public final void setCity(String args) {
	this.city = args;
    }

    /**
     * @return the state
     */
    public final String getState() {
	return state;
    }

    /**
     * @param args
     *            the state to set
     */
    public final void setState(String args) {
	this.state = args;
    }

    /**
     * @return the country
     */
    public final String getCountry() {
	return country;
    }

    /**
     * @param args
     *            the country to set
     */
    public final void setCountry(String args) {
	this.country = args;
    }

    /**
     * @return the postcode
     */
    public final String getPostcode() {
	return postcode;
    }

    /**
     * @param args
     *            the postcode to set
     */
    public final void setPostcode(String args) {
	this.postcode = args;
    }

    /**
     * @return the effectiveDate
     */
    public final Date getEffectiveDate() {
	if (effectiveDate != null) {
	    return new Date(effectiveDate.getTime());
	}
	return null;
    }

    /**
     * @param args
     *            the effectiveDate to set
     */
    public final void setEffectiveDate(Date args) {
	if (args != null) {
	    this.effectiveDate = new Date(args.getTime());
	} else {
	    this.effectiveDate = null;
	}
    }

    /**
     * @return the endDate
     */
    public final Date getEndDate() {
	if (endDate != null) {
	    return new Date(endDate.getTime());
	}
	return null;
    }

    /**
     * @param args
     *            the endDate to set
     */
    public final void setEndDate(Date args) {
	if (args != null) {
	    this.endDate = new Date(args.getTime());
	} else {
	    this.endDate = null;
	}
    }

    /**
     * @return the okToMail
     */
    public final String getOkToMail() {
	return okToMail;
    }

    /**
     * @param args
     *            the okToMail to set
     */
    public final void setOkToMail(String args) {
	this.okToMail = args;
    }

    /**
     * @return the lastMaintainedBy
     */
    public final String getLastMaintainedBy() {
	return lastMaintainedBy;
    }

    /**
     * @param args
     *            the lastMaintainedBy to set
     */
    public final void setLastMaintainedBy(String args) {
	this.lastMaintainedBy = args;
    }

    /**
     * @return the lastMaintainedDate
     */
    public final Date getLastMaintainedDate() {
	if (lastMaintainedDate != null) {
	    return new Date(lastMaintainedDate.getTime());
	}
	return null;
    }

    /**
     * @param args
     *            the lastMaintainedDate to set
     */
    public final void setLastMaintainedDate(Date args) {
	if (args != null) {
	    this.lastMaintainedDate = new Date(args.getTime());
	} else {
	    this.lastMaintainedDate = null;
	}
    }

    /**
     * @return the lastMaintainedTime
     */
    public final String getLastMaintainedTime() {
	return lastMaintainedTime;
    }

    /**
     * @param args
     *            the lastMaintainedTime to set
     */
    public final void setLastMaintainedTime(String args) {
	this.lastMaintainedTime = args;
    }

    public String getCityCode() {
	return cityCode;
    }

    public void setCityCode(String cityCode) {
	this.cityCode = cityCode;
    }

    public String getStateCode() {
	return stateCode;
    }

    public void setStateCode(String stateCode) {
	this.stateCode = stateCode;
    }

    public String getCountryCode() {
	return countryCode;
    }

    public void setCountryCode(String countryCode) {
	this.countryCode = countryCode;
    }

    /**
     * toString.
     * 
     * @return the string
     */

    @Override
    public final String toString() {
	final StringBuilder sb = new StringBuilder();
	sb.append("Address customerNo=").append(this.customerNo).append("Address addressType=").append(this.addressType).append(
		"Address addressReferenceNumber=").append(this.addressReferenceNumber);
	return sb.toString();
    }

}
