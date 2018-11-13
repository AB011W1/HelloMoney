package com.barclays.bmg.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

/**
 *Mini Statement
 */

public class AccountTrnxDTO implements Serializable {

    /**
	 *
	 */
    private static final long serialVersionUID = 3320764884884738647L;

    private Calendar transactionDateTime;
    private String creditDebitTypeCode;
    private String transactionDescriptionCode;
    private BigDecimal transactionCurrencyAmount;
    private String transactionCurrencyCode;
    private String trnxReferenceNumber;

    /**
     * @return the transactionDateTime
     */
    public Calendar getTransactionDateTime() {
	return transactionDateTime;
    }

    /**
     * @param transactionDateTime
     *            the transactionDateTime to set
     */
    public void setTransactionDateTime(Calendar transactionDateTime) {
	this.transactionDateTime = transactionDateTime;
    }

    /**
     * @return the creditDebitTypeCode
     */
    public String getCreditDebitTypeCode() {
	return creditDebitTypeCode;
    }

    /**
     * @param creditDebitTypeCode
     *            the creditDebitTypeCode to set
     */
    public void setCreditDebitTypeCode(String creditDebitTypeCode) {
	this.creditDebitTypeCode = creditDebitTypeCode;
    }

    /**
     * @return the transactionDescriptionCode
     */
    public String getTransactionDescriptionCode() {
	return transactionDescriptionCode;
    }

    /**
     * @param transactionDescriptionCode
     *            the transactionDescriptionCode to set
     */
    public void setTransactionDescriptionCode(String transactionDescriptionCode) {
	this.transactionDescriptionCode = transactionDescriptionCode;
    }

    /**
     * @return the transactionCurrencyAmount
     */
    public BigDecimal getTransactionCurrencyAmount() {
	return transactionCurrencyAmount;
    }

    /**
     * @param transactionCurrencyAmount
     *            the transactionCurrencyAmount to set
     */
    public void setTransactionCurrencyAmount(BigDecimal transactionCurrencyAmount) {
	this.transactionCurrencyAmount = transactionCurrencyAmount;
    }

    /**
     * @return the transactionCurrencyCode
     */
    public String getTransactionCurrencyCode() {
	return transactionCurrencyCode;
    }

    /**
     * @param transactionCurrencyCode
     *            the transactionCurrencyCode to set
     */
    public void setTransactionCurrencyCode(String transactionCurrencyCode) {
	this.transactionCurrencyCode = transactionCurrencyCode;
    }

    /**
     * @return the trnxReferenceNumber
     */
    public String getTrnxReferenceNumber() {
	return trnxReferenceNumber;
    }

    /**
     * @param trnxReferenceNumber
     *            the trnxReferenceNumber to set
     */
    public void setTrnxReferenceNumber(String trnxReferenceNumber) {
	this.trnxReferenceNumber = trnxReferenceNumber;
    }

}
