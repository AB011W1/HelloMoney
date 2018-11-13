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

public class BillerInfoDTO implements Serializable {

    private static final long serialVersionUID = 2291797624848476281L;

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

    public String getBillerCategoryName() {
	return billerCategoryName;
    }

    public void setBillerCategoryName(String billerCategoryName) {
	this.billerCategoryName = billerCategoryName;
    }

    public String getBillerRefName() {
	return billerRefName;
    }

    public void setBillerRefName(String billerRefName) {
	this.billerRefName = billerRefName;
    }

    public static long getSerialVersionUID() {
	return serialVersionUID;
    }

    private String billerId;
    private String billerName;
    private String billerCategoryId;
    private String billerCategoryName;
    private String billerRefName;
    // biller id, biller name, biller category ID, biller category name and biller refrence name.

}
