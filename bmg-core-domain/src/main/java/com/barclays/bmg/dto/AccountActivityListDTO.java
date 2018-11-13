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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO Class/Interface description.
 * 
 * @author BMB Team
 */
public class AccountActivityListDTO implements Serializable {
    /**
	 *
	 */
    private static final long serialVersionUID = 675667011772647586L;
    private String accountNumber;
    private BigDecimal openingBalanceAmount;
    private BigDecimal closingBalanceAmount;
    private List<AccountActivityDTO> activityList = new ArrayList<AccountActivityDTO>();

    public String getAccountNumber() {
	return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
	this.accountNumber = accountNumber;
    }

    public BigDecimal getOpeningBalanceAmount() {
	return openingBalanceAmount;
    }

    public void setOpeningBalanceAmount(BigDecimal openingBalanceAmount) {
	this.openingBalanceAmount = openingBalanceAmount;
    }

    public BigDecimal getClosingBalanceAmount() {
	return closingBalanceAmount;
    }

    public void setClosingBalanceAmount(BigDecimal closingBalanceAmount) {
	this.closingBalanceAmount = closingBalanceAmount;
    }

    public List<AccountActivityDTO> getActivityList() {
	return activityList;
    }

    public void setActivityList(List<AccountActivityDTO> activityList) {
	this.activityList = activityList;
    }

    public void addAccountActivity(AccountActivityDTO activityDTO) {
	if (activityList == null) {
	    this.activityList = new ArrayList<AccountActivityDTO>();

	}
	this.activityList.add(activityDTO);
    }
}
