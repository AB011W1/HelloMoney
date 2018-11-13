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
import java.math.RoundingMode;

/* *************************** Revision History *********************************
 * Version        Author          Date                            Description
 * 0.1            Jason Wang        Aug 4, 2009                  Initial version
 *
 *
 ********************************************************************************/

/**
 * This class encapsulate accurate amount and its currency.
 * 
 * @author Jason Wang
 */
public class Amount implements Serializable {

    private static final long serialVersionUID = 1L;

    private String currency;

    private BigDecimal amount;

    public Amount() {
	super();
    }

    /**
     * @param currency
     * @param amount
     */
    public Amount(String currency, BigDecimal amount) {
	super();
	this.currency = currency;
	this.amount = amount;
    }

    /**
     * 
     * @param currency
     * @param amount
     * @return
     */
    public static Amount valueOf(String currency, BigDecimal amount) {
	return new Amount(currency, amount);
    }

    /**
     * @return the currency
     */
    public String getCurrency() {
	return currency;
    }

    /**
     * @param currency
     *            the currency to set
     */
    public void setCurrency(String currency) {
	this.currency = currency;
    }

    /**
     * @return the amount
     */
    public BigDecimal getAmount() {
	return amount;
    }

    /**
     * @param amount
     *            the amount to set
     */
    public void setAmount(BigDecimal amount) {
	this.amount = amount;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((amount == null) ? 0 : amount.hashCode());
	result = prime * result + ((currency == null) ? 0 : currency.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Amount other = (Amount) obj;
	if (amount == null) {
	    if (other.amount != null)
		return false;
	} else if (!amount.equals(other.amount))
	    return false;
	if (currency == null) {
	    if (other.currency != null)
		return false;
	} else if (!currency.equals(other.currency))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return currency + " " + (amount != null ? amount.setScale(2, RoundingMode.HALF_UP) : "");
    }
}
