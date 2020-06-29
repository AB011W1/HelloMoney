package com.barclays.bmg.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

public class CreditCardStmtBalanceInfoDTO implements Serializable {

    private static final long serialVersionUID = 1297755041183641146L;

    // ORCHARD CHANGES START
    private Calendar statementDate;

    // ORCHARD CHANGES END
    private BigDecimal prevBalance;

    private BigDecimal paymentReceived;

    private BigDecimal totalPurchase;

    private BigDecimal totalCashWithdrawn;

    private BigDecimal otherDrOrCr;

    private BigDecimal feeAndCharge;

    private BigDecimal accountBalance;
    //CR75
    private BigDecimal minDue;

    private Calendar dueDate;

    private BigDecimal totalOutsAmt;

    // Add Start By Tim for Defect 460 Account Balance CR Issue
    private boolean accountBalancePositiveFlag = false; // default not
    // positive,not display
    // Cr
    // Add End By Tim for Defect 460 Account Balance CR Issue

    // Add Start By Tim for CR PreviousBalanceCRChange Issue
    private boolean previousBalanceFlag = false; // default not positive,not

    // display Cr

    // Add End y Tim for CR PreviousBalanceCRChange Issue
    //Cards Migration
    private String sequenceNumber;
    
    public String getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(String sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public BigDecimal getPrevBalance() {
	return prevBalance;
    }

    public void setPrevBalance(BigDecimal prevBalance) {
	this.prevBalance = prevBalance;
    }

    public BigDecimal getPaymentReceived() {
	return paymentReceived;
    }

    public void setPaymentReceived(BigDecimal paymentReceived) {
	this.paymentReceived = paymentReceived;
    }

    public BigDecimal getTotalPurchase() {
	return totalPurchase;
    }

    public void setTotalPurchase(BigDecimal totalPurchase) {
	this.totalPurchase = totalPurchase;
    }

    public BigDecimal getTotalCashWithdrawn() {
	return totalCashWithdrawn;
    }

    public void setTotalCashWithdrawn(BigDecimal totalCashWithdrawn) {
	this.totalCashWithdrawn = totalCashWithdrawn;
    }

    public BigDecimal getOtherDrOrCr() {
	return otherDrOrCr;
    }

    public void setOtherDrOrCr(BigDecimal otherDrOrCr) {
	this.otherDrOrCr = otherDrOrCr;
    }

    public BigDecimal getFeeAndCharge() {
	return feeAndCharge;
    }

    public void setFeeAndCharge(BigDecimal feeAndCharge) {
	this.feeAndCharge = feeAndCharge;
    }

    public BigDecimal getAccountBalance() {
	return accountBalance;
    }

    public void setAccountBalance(BigDecimal accountBalance) {
	this.accountBalance = accountBalance;
    }

    // Add Start By Tim for Defect 460 Account Balance CR Issue
    public boolean getAccountBalancePositiveFlag() {
	return accountBalancePositiveFlag;
    }

    public void setAccountBalancePositiveFlag(boolean accountBalancePositiveFlag) {
	this.accountBalancePositiveFlag = accountBalancePositiveFlag;
    }

    // Add End By Tim for Defect 460 Account Balance CR Issue

    // Add Start By Tim for CR PreviousBalanceCRChange Issue
    public boolean getPreviousBalanceFlag() {
	return previousBalanceFlag;
    }

    public void setPreviousBalanceFlag(boolean previousBalanceFlag) {
	this.previousBalanceFlag = previousBalanceFlag;
    }

    // Add End By Tim for CR PreviousBalanceCRChange Issue

    public Calendar getStatementDate() {
	return statementDate;
    }

    public void setStatementDate(Calendar statementDate) {
	this.statementDate = statementDate;
    }

	public BigDecimal getMinDue() {
		return minDue;
	}

	public void setMinDue(BigDecimal minDue) {
		this.minDue = minDue;
	}

	public Calendar getDueDate() {
		return dueDate;
	}

	public void setDueDate(Calendar dueDate) {
		this.dueDate = dueDate;
	}

	public BigDecimal getTotalOutsAmt() {
		return totalOutsAmt;
	}

	public void setTotalOutsAmt(BigDecimal totalOutsAmt) {
		this.totalOutsAmt = totalOutsAmt;
	}


}
