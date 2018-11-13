package com.barclays.bmg.type;

import java.io.Serializable;

public class Currency implements Serializable {

	private static final long serialVersionUID = 8573088954248208331L;
	private String currency;

    public Currency(String currency) {
	this.currency = currency;
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

}
