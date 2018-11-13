package com.barclays.bmg.audit.dto;

import java.io.Serializable;
import java.util.Date;

import javax.xml.datatype.XMLGregorianCalendar;

public class TransactionAuditDTO implements Serializable {

    private static final long serialVersionUID = -4624013412671538628L;

    private String backEndId;
    private String backEndRefNo;
    private Double baseCurrencyAmount;
    private String fromAccount;
    private String fromAccountCurrency;
    private Double fxRate;
    private Date postingDate;
    private String respCode;
    private String sourceComponent;
    private String toAccount;
    private String toAccountCurrency;
    private Double transactionAmount;
    private String transactionCurrency;
    private Date transactionDateTime;
    private String transactionRefNo;
    private String transactionState;
    private String transactionStatus;
    private String mwRefNum;
    private String mwBackEndRefNum;
    private Date mwDateTime;
    private String reqRes;
    private String ipAddress;
    private String sessionId;
    private String creditCardNo;
    private Integer stepId;
    private String baInd;

    private String resolveFlag;
    private String errorMessage;

    /**
     * Gets the value of the backEndId property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getBackEndId() {
	return backEndId;
    }

    /**
     * Sets the value of the backEndId property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setBackEndId(String value) {
	this.backEndId = value;
    }

    /**
     * Gets the value of the backEndRefNo property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getBackEndRefNo() {
	return backEndRefNo;
    }

    /**
     * Sets the value of the backEndRefNo property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setBackEndRefNo(String value) {
	this.backEndRefNo = value;
    }

    /**
     * Gets the value of the baseCurrencyAmount property.
     * 
     * @return possible object is {@link Double }
     * 
     */
    public Double getBaseCurrencyAmount() {
	return baseCurrencyAmount;
    }

    /**
     * Sets the value of the baseCurrencyAmount property.
     * 
     * @param value
     *            allowed object is {@link Double }
     * 
     */
    public void setBaseCurrencyAmount(Double value) {
	this.baseCurrencyAmount = value;
    }

    /**
     * Gets the value of the fromAccount property.
     * 
     * @return possible object is {@link Long }
     * 
     */
    public String getFromAccount() {
	return fromAccount;
    }

    /**
     * Sets the value of the fromAccount property.
     * 
     * @param value
     *            allowed object is {@link Long }
     * 
     */
    public void setFromAccount(String value) {
	this.fromAccount = value;
    }

    /**
     * Gets the value of the fromAccountCurrency property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getFromAccountCurrency() {
	return fromAccountCurrency;
    }

    /**
     * Sets the value of the fromAccountCurrency property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setFromAccountCurrency(String value) {
	this.fromAccountCurrency = value;
    }

    /**
     * Gets the value of the fxRate property.
     * 
     * @return possible object is {@link Double }
     * 
     */
    public Double getFxRate() {
	return fxRate;
    }

    /**
     * Sets the value of the fxRate property.
     * 
     * @param value
     *            allowed object is {@link Double }
     * 
     */
    public void setFxRate(Double value) {
	this.fxRate = value;
    }

    /**
     * Gets the value of the postingDate property.
     * 
     * @return possible object is {@link XMLGregorianCalendar }
     * 
     */
    public Date getPostingDate() {
	if (postingDate != null) {
	    return new Date(postingDate.getTime());
	}
	return null;
    }

    /**
     * Sets the value of the postingDate property.
     * 
     * @param value
     *            allowed object is {@link XMLGregorianCalendar }
     * 
     */
    public void setPostingDate(Date value) {
	if (value != null) {
	    this.postingDate = new Date(value.getTime());
	} else {
	    this.postingDate = null;
	}
    }

    /**
     * Gets the value of the respCode property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getRespCode() {
	return respCode;
    }

    /**
     * Sets the value of the respCode property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setRespCode(String value) {
	this.respCode = value;
    }

    /**
     * Gets the value of the sourceComponent property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getSourceComponent() {
	return sourceComponent;
    }

    /**
     * Sets the value of the sourceComponent property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setSourceComponent(String value) {
	this.sourceComponent = value;
    }

    /**
     * Gets the value of the toAccount property.
     * 
     * @return possible object is {@link Long }
     * 
     */
    public String getToAccount() {
	return toAccount;
    }

    /**
     * Sets the value of the toAccount property.
     * 
     * @param value
     *            allowed object is {@link Long }
     * 
     */
    public void setToAccount(String value) {
	this.toAccount = value;
    }

    /**
     * Gets the value of the toAccountCurrency property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getToAccountCurrency() {
	return toAccountCurrency;
    }

    /**
     * Sets the value of the toAccountCurrency property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setToAccountCurrency(String value) {
	this.toAccountCurrency = value;
    }

    /**
     * Gets the value of the transactionAmount property.
     * 
     * @return possible object is {@link Double }
     * 
     */
    public Double getTransactionAmount() {
	return transactionAmount;
    }

    /**
     * Sets the value of the transactionAmount property.
     * 
     * @param value
     *            allowed object is {@link Double }
     * 
     */
    public void setTransactionAmount(Double value) {
	this.transactionAmount = value;
    }

    /**
     * Gets the value of the transactionCurrency property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTransactionCurrency() {
	return transactionCurrency;
    }

    /**
     * Sets the value of the transactionCurrency property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setTransactionCurrency(String value) {
	this.transactionCurrency = value;
    }

    /**
     * Gets the value of the transactionDateTime property.
     * 
     * @return possible object is {@link XMLGregorianCalendar }
     * 
     */
    public Date getTransactionDateTime() {
	if (transactionDateTime != null) {
	    return new Date(transactionDateTime.getTime());
	}
	return null;
    }

    /**
     * Sets the value of the transactionDateTime property.
     * 
     * @param value
     *            allowed object is {@link XMLGregorianCalendar }
     * 
     */
    public void setTransactionDateTime(Date value) {
	if (value != null) {
	    this.transactionDateTime = new Date(value.getTime());
	} else {
	    this.transactionDateTime = null;
	}
    }

    /**
     * Gets the value of the transactionRefNo property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTransactionRefNo() {
	return transactionRefNo;
    }

    /**
     * Sets the value of the transactionRefNo property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setTransactionRefNo(String value) {
	this.transactionRefNo = value;
    }

    /**
     * Gets the value of the transactionState property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTransactionState() {
	return transactionState;
    }

    /**
     * Sets the value of the transactionState property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setTransactionState(String value) {
	this.transactionState = value;
    }

    /**
     * Gets the value of the transactionStatus property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTransactionStatus() {
	return transactionStatus;
    }

    /**
     * Sets the value of the transactionStatus property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setTransactionStatus(String value) {
	this.transactionStatus = value;
    }

    /**
     * Gets the value of the mwRefNum property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getMwRefNum() {
	return mwRefNum;
    }

    /**
     * Sets the value of the mwRefNum property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setMwRefNum(String value) {
	this.mwRefNum = value;
    }

    /**
     * Gets the value of the mwBackEndRefNum property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getMwBackEndRefNum() {
	return mwBackEndRefNum;
    }

    /**
     * Sets the value of the mwBackEndRefNum property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setMwBackEndRefNum(String value) {
	this.mwBackEndRefNum = value;
    }

    /**
     * Gets the value of the mwDateTime property.
     * 
     * @return possible object is {@link XMLGregorianCalendar }
     * 
     */
    public Date getMwDateTime() {
	if (mwDateTime != null) {
	    return new Date(mwDateTime.getTime());
	}
	return null;
    }

    /**
     * Sets the value of the mwDateTime property.
     * 
     * @param value
     *            allowed object is {@link XMLGregorianCalendar }
     * 
     */
    public void setMwDateTime(Date value) {
	if (value != null) {
	    this.mwDateTime = new Date(value.getTime());
	} else {
	    this.mwDateTime = null;
	}
    }

    public String getReqRes() {
	return reqRes;
    }

    public void setReqRes(String reqRes) {
	this.reqRes = reqRes;
    }

    public String getIpAddress() {
	return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
	this.ipAddress = ipAddress;
    }

    public String getSessionId() {
	return sessionId;
    }

    public void setSessionId(String sessionId) {
	this.sessionId = sessionId;
    }

    public String getCreditCardNo() {
	return creditCardNo;
    }

    public void setCreditCardNo(String creditCardNo) {
	this.creditCardNo = creditCardNo;
    }

    public Integer getStepId() {
	return stepId;
    }

    public void setStepId(Integer stepId) {
	this.stepId = stepId;
    }

    public String getBaInd() {
	return baInd;
    }

    public void setBaInd(String baInd) {
	this.baInd = baInd;
    }

    public String getResolveFlag() {
	return resolveFlag;
    }

    public void setResolveFlag(String resolveFlag) {
	this.resolveFlag = resolveFlag;
    }

    public String getErrorMessage() {
	return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
	this.errorMessage = errorMessage;
    }

}
