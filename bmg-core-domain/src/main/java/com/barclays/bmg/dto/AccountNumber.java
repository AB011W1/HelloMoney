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

public class AccountNumber implements Serializable {

    private static final long serialVersionUID = -1352341093268627871L;

    private String accountNumber;

    public AccountNumber(String accountNumber) {
	this.accountNumber = accountNumber;
    }

    /**
     * @return the accountNumber
     */
    public String getAccountNumber() {
	return accountNumber;
    }

    /**
     * @param accountNumber
     *            the accountNumber to set
     */
    public void setAccountNumber(String accountNumber) {
	this.accountNumber = accountNumber;
    }

}
