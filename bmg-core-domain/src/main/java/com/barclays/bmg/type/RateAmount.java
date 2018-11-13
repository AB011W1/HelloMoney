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

import java.io.Serializable;
import java.math.BigDecimal;

/* *************************** Revision History *********************************
 * Version        Author          Date                     Description
 * 0.1            ELicer Zheng        9 Oct 2009                  Initial version
 *
 *
 ********************************************************************************/

/**
 * TODO Class/Interface description. Elicer Zheng
 */

public class RateAmount implements Serializable {
    /**
    *
    */
    private static final long serialVersionUID = 1L;

    private BigDecimal amount;

    /**
    *
    */
    public RateAmount() {
	super();
    }

    /**
     * @param amountParam
     *            BigDecimal
     */
    public RateAmount(BigDecimal amountParam) {
	super();
	this.amount = amountParam;
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

    /**
     * @return String
     */
    public String getInterestAmount() {
	// DecimalFormat decimalFormat = new DecimalFormat();
	// if (amount != null) {
	//
	// return decimalFormat.format(amount) + "%";
	// } else {
	// return null;
	// }
	return null;
    }
}
