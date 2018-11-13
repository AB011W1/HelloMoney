package com.barclays.bmg.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CreditCardStmtAccountInfoDTO implements Serializable {

    private static final long serialVersionUID = -5624743458580196526L;

    private BigDecimal totalCreditLimit;

    private BigDecimal totalCashLimit;

    private BigDecimal paymentDue;

    private BigDecimal minPaymentDue;

    private Date paymentDueDate;

    public BigDecimal getTotalCreditLimit() {
	return totalCreditLimit;
    }

    public void setTotalCreditLimit(BigDecimal totalCreditLimit) {
	this.totalCreditLimit = totalCreditLimit;
    }

    public BigDecimal getTotalCashLimit() {
	return totalCashLimit;
    }

    public void setTotalCashLimit(BigDecimal totalCashLimit) {
	this.totalCashLimit = totalCashLimit;
    }

    public BigDecimal getPaymentDue() {
	return paymentDue;
    }

    public void setPaymentDue(BigDecimal paymentDue) {
	this.paymentDue = paymentDue;
    }

    public BigDecimal getMinPaymentDue() {
	return minPaymentDue;
    }

    public void setMinPaymentDue(BigDecimal minPaymentDue) {
	this.minPaymentDue = minPaymentDue;
    }

    public Date getPaymentDueDate() {
	if (paymentDueDate != null) {
	    return new Date(paymentDueDate.getTime());
	}
	return null;
    }

    public void setPaymentDueDate(Date paymentDueDate) {
	if (paymentDueDate != null) {
	    this.paymentDueDate = new Date(paymentDueDate.getTime());
	} else {
	    this.paymentDueDate = null;
	}
    }

}
