/*
 * Copyright (c) 2009 Barclays Bank Plc, All Rights Reserved.
 *
 * This code is confidential to Barclays Bank Plc and shall not be disclosed
 * outside the Bank without the prior written permission of the Director of
 * CIO
 *
 * In the event that such disclosure is permitted the code shall not be copied
 * or distributed other than on a need-to-know basis and any recipients may be
 * required to sign a confidentiality undertaking in favour of Barclays Bank
 * Plc.
 */

package com.barclays.bmg.type;

import java.util.Date;

/**
 * Detailed address information for loan application.
 *
 * @version $Revision: 1.1 $
 *
 * @author Martin Sit
 */

public class AddressDetail extends Address {

    /**
	 *
	 */
	private static final long serialVersionUID = 1561090818019544445L;
	private String street;
    private String buildingName;
    private String flatNo;
    private Date dateMovedIn;
    private Phone phoneNo;

    public String getStreet() {
	return street;
    }

    public void setStreet(String street) {
	this.street = street;
    }

    public String getBuildingName() {
	return buildingName;
    }

    public void setBuildingName(String buildingName) {
	this.buildingName = buildingName;
    }

    public String getFlatNo() {
	return flatNo;
    }

    public void setFlatNo(String flatNo) {
	this.flatNo = flatNo;
    }

    public Date getDateMovedIn() {
	if (dateMovedIn != null) {
	    return new Date(dateMovedIn.getTime());
	}
	return null;
    }

    public void setDateMovedIn(Date dateMovedIn) {
	if (dateMovedIn != null) {
	    this.dateMovedIn = new Date(dateMovedIn.getTime());
	} else {
	    this.dateMovedIn = null;
	}
    }

    public Phone getPhoneNo() {
	return phoneNo;
    }

    public void setPhoneNo(Phone phoneNo) {
	this.phoneNo = phoneNo;
    }
}
