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
import java.util.ArrayList;
import java.util.List;

/** Mini Staement */
public class AccountTrnxHistoryDTO implements Serializable {
    /**
	 *
	 */
    private static final long serialVersionUID = 6991178378322269176L;

    private String accountNumber;
    private Double openingBalanceAmount;
    private Double closingBalanceAmount;
    private List<AccountTrnxDTO> trnsactionActivityList = new ArrayList<AccountTrnxDTO>();

    public String getAccountNumber() {
	return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
	this.accountNumber = accountNumber;
    }

    public Double getOpeningBalanceAmount() {
	return openingBalanceAmount;
    }

    public void setOpeningBalanceAmount(Double openingBalanceAmount) {
	this.openingBalanceAmount = openingBalanceAmount;
    }

    public Double getClosingBalanceAmount() {
	return closingBalanceAmount;
    }

    public void setClosingBalanceAmount(Double closingBalanceAmount) {
	this.closingBalanceAmount = closingBalanceAmount;
    }

    public List<AccountTrnxDTO> getTrnsactionActivityList() {
	return trnsactionActivityList;
    }

    public void setTrnsactionActivityList(List<AccountTrnxDTO> trnsactionActivityList) {
	this.trnsactionActivityList = trnsactionActivityList;
    }

}
