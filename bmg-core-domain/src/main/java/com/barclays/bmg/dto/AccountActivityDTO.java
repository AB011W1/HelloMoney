package com.barclays.bmg.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author BMB Team
 */

public class AccountActivityDTO implements Serializable {

    public static final String CREDIT_FLAG = "CR";
    public static final String DEBIT_FLAG = "DR";
    public static final String CREDIT_SUFFIX = "Cr";
    private static final long serialVersionUID = -106339568000883528L;

    private String accountNumber;
    private String currency;
    private Date transactionDate;
    private String transactionType;
    private String creditDebitFlag;
    private String transactionParticular;
    private BigDecimal transactionAmount;
    private String transactoinCurrency;
    private String transactoinReferenceNumber;
    private BigDecimal balance;
    private BigDecimal creditAmount;
    private BigDecimal debitAmount;
    private Date valueDate;
    private Date transactionPostDate;
    private String creditSuffix;

    /**
     * @return the creditSuffix
     */
    public String getCreditSuffix() {
	return creditSuffix;
    }

    /**
     * @param creditSuffix
     *            the creditSuffix to set
     */
    public void setCreditSuffix(String creditSuffix) {
	this.creditSuffix = creditSuffix;
    }

    /**
     * @return the transactionPostDate
     */
    public Date getTransactionPostDate() {
	if (transactionPostDate != null) {
	    return new Date(transactionPostDate.getTime());
	}
	return null;
    }

    /**
     * @param transactionPostDate
     *            the transactionPostDate to set
     */
    public void setTransactionPostDate(Date transactionPostDate) {
	if (transactionPostDate != null) {
	    this.transactionPostDate = new Date(transactionPostDate.getTime());
	} else {
	    this.transactionPostDate = null;
	}
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

    /**
    *
    */
    public AccountActivityDTO() {
	super();
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
     * @return the transactionDate
     */
    public Date getTransactionDate() {
	if (transactionDate != null) {
	    return new Date(transactionDate.getTime());
	}
	return null;
    }

    /**
     * @param transactionDate
     *            the transactionDate to set
     */
    public void setTransactionDate(Date transactionDate) {
	if (transactionDate != null) {
	    this.transactionDate = new Date(transactionDate.getTime());
	} else {
	    this.transactionDate = null;
	}
    }

    /**
     * @return the transactionType
     */
    public String getTransactionType() {
	return transactionType;
    }

    /**
     * @param transactionType
     *            the transactionType to set
     */
    public void setTransactionType(String transactionType) {
	this.transactionType = transactionType;
    }

    /**
     * @return the creditDebitFlag
     */
    public String getCreditDebitFlag() {
	return creditDebitFlag;
    }

    /**
     * @param creditDebitFlag
     *            the creditDebitFlag to set
     */
    public void setCreditDebitFlag(String creditDebitFlag) {
	this.creditDebitFlag = creditDebitFlag;
    }

    /**
     * @return the transactionParticular
     */
    public String getTransactionParticular() {
	return transactionParticular;
    }

    /**
     * @param transactionParticular
     *            the transactionParticular to set
     */
    public void setTransactionParticular(String transactionParticular) {
	this.transactionParticular = transactionParticular;
    }

    /**
     * @return the transactionAmount
     */
    public BigDecimal getTransactionAmount() {
	return transactionAmount;
    }

    /**
     * @param transactionAmount
     *            the transactionAmount to set
     */
    public void setTransactionAmount(BigDecimal transactionAmount) {
	this.transactionAmount = transactionAmount;
    }

    /**
     * @return the transactoinCurrency
     */
    public String getTransactoinCurrency() {
	return transactoinCurrency;
    }

    /**
     * @param transactoinCurrency
     *            the transactoinCurrency to set
     */
    public void setTransactoinCurrency(String transactoinCurrency) {
	this.transactoinCurrency = transactoinCurrency;
    }

    /**
     * @return the transactoinReferenceNumber
     */
    public String getTransactoinReferenceNumber() {
	return transactoinReferenceNumber;
    }

    /**
     * @param transactoinReferenceNumber
     *            the transactoinReferenceNumber to set
     */
    public void setTransactoinReferenceNumber(String transactoinReferenceNumber) {
	this.transactoinReferenceNumber = transactoinReferenceNumber;
    }

    /**
     * @return the serialVersionUID
     */
    public static long getSerialVersionUID() {
	return serialVersionUID;
    }

    /**
     * @return the balance
     */
    public BigDecimal getBalance() {
	return balance;
    }

    /**
     * @param balance
     *            the balance to set
     */
    public void setBalance(BigDecimal balance) {
	this.balance = balance;
    }

    /**
     * @return the creditAmount
     */
    public BigDecimal getCreditAmount() {
	return creditAmount;
    }

    /**
     * @param creditAmount
     *            the creditAmount to set
     */
    public void setCreditAmount(BigDecimal creditAmount) {
	this.creditAmount = creditAmount;
    }

    /**
     * @return the debitAmount
     */
    public BigDecimal getDebitAmount() {
	return debitAmount;
    }

    /**
     * @param debitAmount
     *            the debitAmount to set
     */
    public void setDebitAmount(BigDecimal debitAmount) {
	this.debitAmount = debitAmount;
    }

    /**
     * @return the valueDate
     */
    public Date getValueDate() {
	if (valueDate != null) {
	    return new Date(valueDate.getTime());
	}
	return null;
    }

    /**
     * @param valueDate
     *            the valueDate to set
     */
    public void setValueDate(Date valueDate) {
	if (valueDate != null) {
	    this.valueDate = new Date(valueDate.getTime());
	} else {
	    this.valueDate = null;
	}
    }

}
