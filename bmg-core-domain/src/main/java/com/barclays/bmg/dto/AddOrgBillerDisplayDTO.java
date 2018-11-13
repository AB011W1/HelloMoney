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

package com.barclays.bmg.dto;

import java.io.Serializable;

public class AddOrgBillerDisplayDTO implements Serializable {

    private static final long serialVersionUID = 2291797624848476281L;
    public final static String BILLER_LANDLINE = "Landline";
    private String billerId;

    private String billerName;

    private String billerCategoryId;

    private String billerAccountNumber;

    public String getBillerId() {
	return billerId;
    }

    public void setBillerId(String billerId) {
	this.billerId = billerId;
    }

    public String getBillerName() {
	return billerName;
    }

    public void setBillerName(String billerName) {
	this.billerName = billerName;
    }

    public String getBillerCategoryId() {
	return billerCategoryId;
    }

    public void setBillerCategoryId(String billerCategoryId) {
	this.billerCategoryId = billerCategoryId;
    }

    public String getBillerAccountNumber() {
	return billerAccountNumber;
    }

    public void setBillerAccountNumber(String billerAccountNumber) {
	this.billerAccountNumber = billerAccountNumber;
    }

    public static long getSerialVersionUID() {
	return serialVersionUID;
    }

    public static String getBILLERLANDLINE() {
	return BILLER_LANDLINE;
    }

}
