package com.barclays.bmg.dto;

/* *************************** Revision History *********************************
 * Version        Author          Date                     Description
 *
 ********************************************************************************/
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.barclays.bmg.type.Currency;

public class CreditCardAccountDTO extends CustomerAccountDTO {

    private static final long serialVersionUID = -3749663121394018822L;

    /**
     * Account Status A = Active B = Conversion fraud C = Conversion transfer D = Dormant F = Fraud transfer H = Closed/conversion I = Inactive J =
     * Transfer in, migrated from inactive K = Transfer out, migrated from inactive M = Migrated N = New P = To be purged in next reload Q = Transfer
     * in today R = Transfer out today T = Transfer V = Conversion X = Charge-off/conversion Z = Charge-off 8 = Closed 9 = To be purged after reload.
     */
    public static final String ACCOUNT_STATUS_ACTIVE = "ACT";// A

    public static final String ACCOUNT_STATUS_CONVERSION_FRAUD = "CFD";// B

    public static final String ACCOUNT_STATUS_CONVERSION_TRANSFER = "CTR";// C

    public static final String ACCOUNT_STATUS_DORMANT = "DMT";// D

    public static final String ACCOUNT_STATUS_FRAUD_TRANSFER = "FTR";// F

    public static final String ACCOUNT_STATUS_CLOSED_CONVERSION = "CLC";// H

    public static final String ACCOUNT_STATUS_INACTIVE = "INA";// I

    public static final String ACCOUNT_STATUS_TRANSFER_IN_MIGRATED_FROM_INACTIVE = "TIM";// J

    public static final String ACCOUNT_STATUS_TRANSFER_OUT_MIGRATED_FROM_INACTIVE = "TOM";// K

    public static final String ACCOUNT_STATUS_MIGRATED = "MGR";// M

    public static final String ACCOUNT_STATUS_NEW = "NEW";// N

    public static final String ACCOUNT_STATUS_TO_BE_PURGED_NEXT_RELOAD = "TPN";// P

    public static final String ACCOUNT_STATUS_TRANSFER_IN_TODAY = "TID";// Q

    public static final String ACCOUNT_STATUS_TRANSFER_OUT_TODAY = "TOD";// R

    public static final String ACCOUNT_STATUS_TRANSFER = "TFR";// T

    public static final String ACCOUNT_STATUS_CONVERSION = "CON";// V

    public static final String ACCOUNT_STATUS_CHARGE_OFF_CONVERSION = "CHC";// X

    public static final String ACCOUNT_STATUS_CHARGE_OFF = "CHN";// Z

    public static final String ACCOUNT_STATUS_CLOSED = "CLN";// 8

    public static final String ACCOUNT_STATUS_TO_BE_PURGED_AFTER_RELOAD = "TPR";// 9

    private Date lastBilledDate;

    private BigDecimal amountOverDue;

    private BigDecimal pointBalance;

    private BigDecimal creditLimit;

    private BigDecimal availableCreditLimit;

    private BigDecimal cashLimit;

    private BigDecimal availableCashLimit;

    private Date lastPaymentDate;

    private BigDecimal lastPaymentAmount;

    private Date paymentDueDate;

    private BigDecimal minPaymentAmount;

    private BigDecimal lastBilledTotal; // last statement balance?

    private BigDecimal unBilledAmount;

    private BigDecimal outstandingBalance;

    private BigDecimal kadiKopeOutstandingBalanceAmount;

    private String org;

    private String logo;

    private String accountBlockCode1;

    private String accountBlockCode2;

    private BigDecimal statementBalance; // required?

    private Date lastCreditChangeDate;

    private Date temporaryCreditExpireDate;

    private BigDecimal temporaryCreditLimit;

    private Currency currencyCode;

    private CreditCardDTO primary;

    private List<CreditCardDTO> supplementaries;

    // private List<CreditCardStatementDTO> statements;

    // ORCHARD CHANGES START
    private BigDecimal totalCashLimit;
    private BigDecimal custAvailableLimit;
    private BigDecimal custAvailableCashLimit;
    
	// Card MIgration: Starts
	private Date cardExpireDate;

	public Date getCardExpireDate() {
		return cardExpireDate;
	}

	public void setCardExpireDate(Date cardExpireDate) {
		this.cardExpireDate = cardExpireDate;
	}
	// Card MIgration: Ends

    public BigDecimal getTotalCashLimit() {
	return totalCashLimit;
    }

    public void setTotalCashLimit(BigDecimal totalCashLimit) {
	this.totalCashLimit = totalCashLimit;
    }

    public BigDecimal getCustAvailableLimit() {
	return custAvailableLimit;
    }

    public void setCustAvailableLimit(BigDecimal custAvailableLimit) {
	this.custAvailableLimit = custAvailableLimit;
    }

    public BigDecimal getCustAvailableCashLimit() {
	return custAvailableCashLimit;
    }

    public void setCustAvailableCashLimit(BigDecimal custAvailableCashLimit) {
	this.custAvailableCashLimit = custAvailableCashLimit;
    }

    // ORCHARD CHANGES END
    private boolean requestFlg = true;

    public void addSupplementary(CreditCardDTO creditCard) {
	if (supplementaries == null) {
	    supplementaries = new ArrayList<CreditCardDTO>();
	}
	supplementaries.add(creditCard);
    }

    public BigDecimal getTemporaryCreditLimit() {
	return temporaryCreditLimit;
    }

    public void setTemporaryCreditLimit(BigDecimal temporaryCreditLimit) {
	this.temporaryCreditLimit = temporaryCreditLimit;
    }

    /**
     * @return the currencyCode
     */
    public Currency getCurrencyCode() {
	return currencyCode;
    }

    /**
     * @param currencyCode
     *            the currencyCode to set
     */
    public void setCurrencyCode(Currency currencyCode) {
	this.currencyCode = currencyCode;
    }

    public BigDecimal getCreditLimit() {
	return creditLimit;
    }

    public void setCreditLimit(BigDecimal creditLimit) {
	this.creditLimit = creditLimit;
    }

    public BigDecimal getAvailableCreditLimit() {
	return availableCreditLimit;
    }

    public void setAvailableCreditLimit(BigDecimal availableCreditLimit) {
	this.availableCreditLimit = availableCreditLimit;
    }

    public BigDecimal getCashLimit() {
	return cashLimit;
    }

    public void setCashLimit(BigDecimal cashLimit) {
	this.cashLimit = cashLimit;
    }

    public BigDecimal getAvailableCashLimit() {
	return availableCashLimit;
    }

    public void setAvailableCashLimit(BigDecimal availableCashLimit) {
	this.availableCashLimit = availableCashLimit;
    }

    public Date getLastPaymentDate() {
	if (lastPaymentDate != null) {
	    return new Date(lastPaymentDate.getTime());
	}
	return null;
    }

    public void setLastPaymentDate(Date lastPaymentDate) {
	if (lastPaymentDate != null) {
	    this.lastPaymentDate = new Date(lastPaymentDate.getTime());
	} else {
	    this.lastPaymentDate = null;
	}
    }

    public BigDecimal getLastPaymentAmount() {
	return lastPaymentAmount;
    }

    public void setLastPaymentAmount(BigDecimal lastPaymentAmount) {
	this.lastPaymentAmount = lastPaymentAmount;
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

    public BigDecimal getMinPaymentAmount() {
	return minPaymentAmount;
    }

    public void setMinPaymentAmount(BigDecimal minPaymentAmount) {
	this.minPaymentAmount = minPaymentAmount;
    }

    public BigDecimal getLastBilledTotal() {
	return lastBilledTotal;
    }

    public void setLastBilledTotal(BigDecimal lastBilledTotal) {
	this.lastBilledTotal = lastBilledTotal;
    }

    public BigDecimal getUnBilledAmount() {
	return unBilledAmount;
    }

    public void setUnBilledAmount(BigDecimal unBilledAmount) {
	this.unBilledAmount = unBilledAmount;
    }

    public BigDecimal getOutstandingBalance() {
	return outstandingBalance;
    }

    public void setOutstandingBalance(BigDecimal outstandingBalance) {
	this.outstandingBalance = outstandingBalance;
    }

    @Override
    public String getOrg() {
	return org;
    }

    @Override
    public void setOrg(String org) {
	this.org = org;
    }

    public String getLogo() {
	return logo;
    }

    public void setLogo(String logo) {
	this.logo = logo;
    }

    public String getAccountBlockCode1() {
	return accountBlockCode1;
    }

    public void setAccountBlockCode1(String accountBlockCode1) {
	this.accountBlockCode1 = accountBlockCode1;
    }

    public String getAccountBlockCode2() {
	return accountBlockCode2;
    }

    public void setAccountBlockCode2(String accountBlockCode2) {
	this.accountBlockCode2 = accountBlockCode2;
    }

    public BigDecimal getStatementBalance() {
	return statementBalance;
    }

    public void setStatementBalance(BigDecimal statementBalance) {
	this.statementBalance = statementBalance;
    }

    public CreditCardDTO getPrimary() {
	return primary;
    }

    public void setPrimary(CreditCardDTO primary) {
	this.primary = primary;
    }

    public List<CreditCardDTO> getSupplementaries() {
	return supplementaries;
    }

    public void setSupplementaries(List<CreditCardDTO> supplementaries) {
	this.supplementaries = supplementaries;
    }

    public Date getLastCreditChangeDate() {
	if (lastCreditChangeDate != null) {
	    return new Date(lastCreditChangeDate.getTime());
	}
	return null;
    }

    public void setLastCreditChangeDate(Date lastCreditChangeDate) {
	if (lastCreditChangeDate != null) {
	    this.lastCreditChangeDate = new Date(lastCreditChangeDate.getTime());
	} else {
	    this.lastCreditChangeDate = null;
	}
    }

    public Date getTemporaryCreditExpireDate() {
	if (temporaryCreditExpireDate != null) {
	    return new Date(temporaryCreditExpireDate.getTime());
	}
	return null;
    }

    public void setTemporaryCreditExpireDate(Date temporaryCreditExpireDate) {
	if (temporaryCreditExpireDate != null) {
	    this.temporaryCreditExpireDate = new Date(temporaryCreditExpireDate.getTime());
	} else {
	    this.temporaryCreditExpireDate = null;
	}
    }

    public Date getLastBilledDate() {
	if (lastBilledDate != null) {
	    return new Date(lastBilledDate.getTime());
	}
	return null;
    }

    public void setLastBilledDate(Date lastBilledDate) {
	if (lastBilledDate != null) {
	    this.lastBilledDate = new Date(lastBilledDate.getTime());
	} else {
	    this.lastBilledDate = null;
	}
    }

    public BigDecimal getAmountOverDue() {
	return amountOverDue;
    }

    public void setAmountOverDue(BigDecimal amountOverDue) {
	this.amountOverDue = amountOverDue;
    }

    public BigDecimal getPointBalance() {
	return pointBalance;
    }

    public void setPointBalance(BigDecimal pointBalance) {
	this.pointBalance = pointBalance;
    }

    public boolean isRequestFlg() {
	return requestFlg;
    }

    public void setRequestFlg(boolean requestFlg) {
	this.requestFlg = requestFlg;
    }

    public boolean currentBalancePositive() {
	if (getCurrentBalance() == null) {
	    // Add Start Change the CurrentBalance Cr Issue
	    return false;
	    // Add End
	}
	// Modify Start Change the CurrentBalance Cr Issue
	return getCurrentBalance().doubleValue() > 0;
	// Modify End
    }

    public BigDecimal getPositiveCurrentBalance() {
	if (getCurrentBalance() == null) {
	    return null;
	}
	return getCurrentBalance().abs();
    }

    @Override
    public int hashCode() {
	return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
	return super.equals(obj);
    }

	public BigDecimal getKadiKopeOutstandingBalanceAmount() {
		return kadiKopeOutstandingBalanceAmount;
	}

	public void setKadiKopeOutstandingBalanceAmount(
			BigDecimal kadiKopeOutstandingBalanceAmount) {
		this.kadiKopeOutstandingBalanceAmount = kadiKopeOutstandingBalanceAmount;
	}

}
