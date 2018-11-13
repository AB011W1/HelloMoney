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

import java.math.BigDecimal;
import java.util.Date;

import com.barclays.bmg.type.Currency;
import com.barclays.bmg.type.RateAmount;
import com.barclays.bmg.type.Tenure;

/* *************************** Revision History *********************************
 * Version        Author          Date                            Description
 * 0.1            Max Zhang       Jul 29, 2009                    Initial version
 *
 *
 ********************************************************************************/

/**
 * TermDepositDTO.
 * 
 * @author Max Zhang/Elicer Zheng
 * 
 */
public class TermDepositDTO extends BaseDomainDTO {

    /** serial UID. */
    private static final long serialVersionUID = -8083300872372567209L;

    private Currency currencyCode;
    private String customerId;

    private Date depositDate;
    private Date maturityDate;
    private Date nextInterestPaymentDate;
    private Date lastInterestPaymentDate;
    private BigDecimal tdPrincipalBalance;
    private BigDecimal yearToDateTax;
    private BigDecimal lienAmount;
    private BigDecimal availableForRedemption;
    private BigDecimal maturityAmount;
    private BigDecimal interestPaidToDate;
    private BigDecimal projectedInterestAmount;
    private Date valueDate;

    private RateAmount interestRate;

    private String maturityInstruction;

    // for setupTD and ChangeTD in 1.1
    private String depositNumber;

    private String serialNumber;

    private String originalDepositNumber;

    private Product product;

    private TdAccountDTO tdAccount;

    private CASAAccountDTO srcAccount;

    private Amount depositAmount;

    private Tenure tenureTerm;

    private Amount maturity;

    private Renewal renewal;

    private String refNo;

    private Date txnDate;

    private String residentStatus;

    private String fxRate;

    private String fromCurrency;

    private String toCurrency;

    private Amount equivalantAmount;

    private Amount balanceAfterDeposit;

    private Boolean asResult = Boolean.FALSE;

    private Boolean newAccount = Boolean.FALSE;

    /**
     * 
     * Constructor.
     * 
     */
    public TermDepositDTO() {
	super();

	this.depositAmount = new Amount();
	this.tenureTerm = new Tenure();
	this.renewal = new Renewal();
	this.renewal.setRenewalType(Renewal.RENEWAL_ALL);
	this.renewal.setDescription(Renewal.RENEWAL_CAPITAL_DESC);

	// for open td (deposit date)
	this.valueDate = new Date();

	// interestRate = new RateAmount();
	// interestRate.setAmount(BigDecimal.valueOf(0.024));
    }

    /**
     * getCurrencyCode.
     * 
     * @return the currencyCode
     */
    public Currency getCurrencyCode() {
	return this.currencyCode;
    }

    /**
     * setCurrencyCode.
     * 
     * @param currencyCode
     *            the currencyCode to set
     */
    public void setCurrencyCode(Currency currencyCode) {
	this.currencyCode = currencyCode;
    }

    /**
     * getCustomerId.
     * 
     * @return customerId String
     */
    public String getCustomerId() {
	return this.customerId;
    }

    /**
     * setCustomerId.
     * 
     * @param customerId
     *            String
     */
    public void setCustomerId(String customerId) {
	this.customerId = customerId;
    }

    /**
     * setCustomerId.
     * 
     * @return product
     */
    public Product getProduct() {
	return this.product;
    }

    /**
     * setProduct.
     * 
     * @param product
     *            Product
     */
    public void setProduct(Product product) {
	this.product = product;
    }

    /**
     * getTdAccount.
     * 
     * @return tdAccount
     */
    public TdAccountDTO getTdAccount() {
	return this.tdAccount;
    }

    /**
     * setTdAccount.
     * 
     * @param tdAccount
     *            CustomerAccount
     */
    public void setTdAccount(TdAccountDTO tdAccount) {
	this.tdAccount = tdAccount;
    }

    /**
     * getSrcAccount.
     * 
     * @return srcAccount
     */
    public CASAAccountDTO getSrcAccount() {
	return this.srcAccount;
    }

    /**
     * setSrcAccount.
     * 
     * @param srcAccount
     *            CustomerAccount
     */
    public void setSrcAccount(CASAAccountDTO srcAccount) {
	this.srcAccount = srcAccount;
    }

    /**
     * getDepositAmount.
     * 
     * @return depositAmount
     */
    public Amount getDepositAmount() {
	return this.depositAmount;
    }

    /**
     * setDepositAmount.
     * 
     * @param depositAmount
     *            Amount
     */
    public void setDepositAmount(Amount depositAmount) {
	this.depositAmount = depositAmount;
    }

    /**
     * getTenureTerm.
     * 
     * @return tenureTerm
     */
    public Tenure getTenureTerm() {
	return this.tenureTerm;
    }

    /**
     * setTenureTerm.
     * 
     * @param tenureTerm
     *            Tenure
     */
    public void setTenureTerm(Tenure tenureTerm) {
	this.tenureTerm = tenureTerm;
    }

    /**
     * getMaturity.
     * 
     * @return maturity
     */
    public Amount getMaturity() {
	return this.maturity;
    }

    /**
     * setMaturity.
     * 
     * @param maturity
     *            Amount
     */
    public void setMaturity(Amount maturity) {
	this.maturity = maturity;
    }

    /**
     * getRenewal.
     * 
     * @return renewal
     */
    public Renewal getRenewal() {
	return this.renewal;
    }

    /**
     * setRenewal.
     * 
     * @param renewal
     *            Renewal
     */
    public void setRenewal(Renewal renewal) {
	this.renewal = renewal;
    }

    /**
     * getRefNo.
     * 
     * @return refNo
     */
    public String getRefNo() {
	return this.refNo;
    }

    /**
     * setRefNo.
     * 
     * @param refNo
     *            String
     */
    public void setRefNo(String refNo) {
	this.refNo = refNo;
    }

    /**
     * getTxnDate.
     * 
     * @return txnDate
     */
    public Date getTxnDate() {
	if (txnDate != null) {
	    return new Date(txnDate.getTime());
	}
	return null;
    }

    /**
     * setTxnDate.
     * 
     * @param txnDate
     *            Date
     */
    public void setTxnDate(Date txnDate) {
	if (txnDate != null) {
	    this.txnDate = new Date(txnDate.getTime());
	} else {
	    this.txnDate = null;
	}
    }

    /**
     * getResidentStatus.
     * 
     * @return residentStatus
     */
    public String getResidentStatus() {
	return this.residentStatus;
    }

    /**
     * setResidentStatus.
     * 
     * @param residentStatus
     *            String
     */
    public void setResidentStatus(String residentStatus) {
	this.residentStatus = residentStatus;
    }

    /**
     * getFxRate.
     * 
     * @return the fxRate
     */
    public String getFxRate() {
	return this.fxRate;
    }

    /**
     * setFxRate.
     * 
     * @param fxRate
     *            the fxRate to set
     */
    public void setFxRate(String fxRate) {
	this.fxRate = fxRate;
    }

    /**
     * getFromCurrency.
     * 
     * @return the fromCurrency
     */
    public String getFromCurrency() {
	return this.fromCurrency;
    }

    /**
     * setFromCurrency.
     * 
     * @param fromCurrency
     *            the fromCurrency to set
     */
    public void setFromCurrency(String fromCurrency) {
	this.fromCurrency = fromCurrency;
    }

    /**
     * getToCurrency.
     * 
     * @return the toCurrency
     */
    public String getToCurrency() {
	return this.toCurrency;
    }

    /**
     * setToCurrency.
     * 
     * @param toCurrency
     *            the toCurrency to set
     */
    public void setToCurrency(String toCurrency) {
	this.toCurrency = toCurrency;
    }

    /**
     * getDepositNumber.
     * 
     * @return the depositNumber
     */
    public String getDepositNumber() {
	return this.depositNumber;
    }

    /**
     * setDepositNumber.
     * 
     * @param depositNumber
     *            the depositNumber to set
     */
    public void setDepositNumber(String depositNumber) {
	this.depositNumber = depositNumber;
    }

    /**
     * getSerialNumber.
     * 
     * @return the serialNumber
     */
    public String getSerialNumber() {
	return this.serialNumber;
    }

    /**
     * setSerialNumber.
     * 
     * @param serialNumber
     *            the serialNumber to set
     */
    public void setSerialNumber(String serialNumber) {
	this.serialNumber = serialNumber;
    }

    /**
     * getOriginalDepositNumber.
     * 
     * @return the originalDepositNumber
     */
    public String getOriginalDepositNumber() {
	return this.originalDepositNumber;
    }

    /**
     * setOriginalDepositNumber.
     * 
     * @param originalDepositNumber
     *            the originalDepositNumber to set
     */
    public void setOriginalDepositNumber(String originalDepositNumber) {
	this.originalDepositNumber = originalDepositNumber;
    }

    /**
     * getDepositDate.
     * 
     * @return the depositDate
     */
    public Date getDepositDate() {
	if (depositDate != null) {
	    return new Date(depositDate.getTime());
	}
	return null;
    }

    /**
     * setDepositDate.
     * 
     * @param depositDate
     *            the depositDate to set
     */
    public void setDepositDate(Date depositDate) {
	if (depositDate != null) {
	    this.depositDate = new Date(depositDate.getTime());
	} else {
	    this.depositDate = null;
	}
    }

    /**
     * getMaturityDate.
     * 
     * @return the maturityDate
     */
    public Date getMaturityDate() {
	if (maturityDate != null) {
	    return new Date(maturityDate.getTime());
	}
	return null;
    }

    /**
     * setMaturityDate.
     * 
     * @param maturityDate
     *            the maturityDate to set
     */
    public void setMaturityDate(Date maturityDate) {
	if (maturityDate != null) {
	    this.maturityDate = new Date(maturityDate.getTime());
	} else {
	    this.maturityDate = null;
	}
    }

    /**
     * getNextInterestPaymentDate.
     * 
     * @return the nextInterestPaymentDate
     */
    public Date getNextInterestPaymentDate() {
	if (nextInterestPaymentDate != null) {
	    return new Date(nextInterestPaymentDate.getTime());
	}
	return null;
    }

    /**
     * setNextInterestPaymentDate.
     * 
     * @param nextInterestPaymentDate
     *            the nextInterestPaymentDate to set
     */
    public void setNextInterestPaymentDate(Date nextInterestPaymentDate) {
	if (nextInterestPaymentDate != null) {
	    this.nextInterestPaymentDate = new Date(nextInterestPaymentDate.getTime());
	} else {
	    this.nextInterestPaymentDate = null;
	}
    }

    /**
     * getLastInterestPaymentDate.
     * 
     * @return the lastInterestPaymentDate
     */
    public Date getLastInterestPaymentDate() {
	if (lastInterestPaymentDate != null) {
	    return new Date(lastInterestPaymentDate.getTime());
	}
	return null;
    }

    /**
     * setLastInterestPaymentDate.
     * 
     * @param lastInterestPaymentDate
     *            the lastInterestPaymentDate to set
     */
    public void setLastInterestPaymentDate(Date lastInterestPaymentDate) {
	if (lastInterestPaymentDate != null) {
	    this.lastInterestPaymentDate = new Date(lastInterestPaymentDate.getTime());
	} else {
	    this.lastInterestPaymentDate = null;
	}
    }

    /**
     * getTdPrincipalBalance.
     * 
     * @return the tdPrincipalBalance
     */
    public BigDecimal getTdPrincipalBalance() {
	return this.tdPrincipalBalance;
    }

    /**
     * setTdPrincipalBalance.
     * 
     * @param tdPrincipalBalance
     *            the tdPrincipalBalance to set
     */
    public void setTdPrincipalBalance(BigDecimal tdPrincipalBalance) {
	this.tdPrincipalBalance = tdPrincipalBalance;
    }

    /**
     * getYearToDateTax.
     * 
     * @return the yearToDateTax
     */
    public BigDecimal getYearToDateTax() {
	return this.yearToDateTax;
    }

    /**
     * setYearToDateTax.
     * 
     * @param yearToDateTax
     *            the yearToDateTax to set
     */
    public void setYearToDateTax(BigDecimal yearToDateTax) {
	this.yearToDateTax = yearToDateTax;
    }

    /**
     * getLienAmount.
     * 
     * @return the lienAmount
     */
    public BigDecimal getLienAmount() {
	return this.lienAmount;
    }

    /**
     * setLienAmount.
     * 
     * @param lienAmount
     *            the lienAmount to set
     */
    public void setLienAmount(BigDecimal lienAmount) {
	this.lienAmount = lienAmount;
    }

    /**
     * getAvailableForRedemption.
     * 
     * @return the availableForRedemption
     */
    public BigDecimal getAvailableForRedemption() {
	return this.availableForRedemption;
    }

    /**
     * setAvailableForRedemption.
     * 
     * @param availableForRedemption
     *            the availableForRedemption to set
     */
    public void setAvailableForRedemption(BigDecimal availableForRedemption) {
	this.availableForRedemption = availableForRedemption;
    }

    /**
     * getMaturityAmount.
     * 
     * @return the maturityAmount
     */
    public BigDecimal getMaturityAmount() {
	return this.maturityAmount;
    }

    /**
     * setMaturityAmount.
     * 
     * @param maturityAmount
     *            the maturityAmount to set
     */
    public void setMaturityAmount(BigDecimal maturityAmount) {
	this.maturityAmount = maturityAmount;
    }

    /**
     * getInterestPaidToDate.
     * 
     * @return the interestPaidToDate
     */
    public BigDecimal getInterestPaidToDate() {
	return this.interestPaidToDate;
    }

    /**
     * setInterestPaidToDate.
     * 
     * @param interestPaidToDate
     *            the interestPaidToDate to set
     */
    public void setInterestPaidToDate(BigDecimal interestPaidToDate) {
	this.interestPaidToDate = interestPaidToDate;
    }

    /**
     * getProjectedInterestAmount.
     * 
     * @return the projectedInterestAmount
     */
    public BigDecimal getProjectedInterestAmount() {
	return this.projectedInterestAmount;
    }

    /**
     * setProjectedInterestAmount.
     * 
     * @param projectedInterestAmount
     *            the projectedInterestAmount to set
     */
    public void setProjectedInterestAmount(BigDecimal projectedInterestAmount) {
	this.projectedInterestAmount = projectedInterestAmount;
    }

    /**
     * getValueDate.
     * 
     * @return the valueDate
     */
    public Date getValueDate() {
	if (valueDate != null) {
	    return new Date(valueDate.getTime());
	}
	return null;
    }

    /**
     * setValueDate.
     * 
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

    /**
     * getInterestRate.
     * 
     * @return the interestRate
     */
    public RateAmount getInterestRate() {
	return this.interestRate;
    }

    /**
     * setInterestRate.
     * 
     * @param interestRate
     *            the interestRate to set
     */
    public void setInterestRate(RateAmount interestRate) {
	this.interestRate = interestRate;
    }

    /**
     * getMaturityInstruction.
     * 
     * @return the maturityInstruction
     */
    public String getMaturityInstruction() {
	return this.maturityInstruction;
    }

    /**
     * setMaturityInstruction.
     * 
     * @param maturityInstruction
     *            the maturityInstruction to set
     */
    public void setMaturityInstruction(String maturityInstruction) {
	this.maturityInstruction = maturityInstruction;
    }

    /**
     * getEquivalantAmount.
     * 
     * @return the equivalantAmount
     */
    public final Amount getEquivalantAmount() {
	return this.equivalantAmount;
    }

    /**
     * setEquivalantAmount.
     * 
     * @param equivalantAmount
     *            the equivalantAmount to set
     */
    public final void setEquivalantAmount(Amount equivalantAmount) {
	this.equivalantAmount = equivalantAmount;
    }

    /**
     * getBalanceAfterDeposit.
     * 
     * @return the balanceAfterDeposit
     */
    public final Amount getBalanceAfterDeposit() {
	return this.balanceAfterDeposit;
    }

    /**
     * setBalanceAfterDeposit.
     * 
     * @param balanceAfterDeposit
     *            the balanceAfterDeposit to set
     */
    public final void setBalanceAfterDeposit(Amount balanceAfterDeposit) {
	this.balanceAfterDeposit = balanceAfterDeposit;
    }

    /**
     * override hashCode.
     * 
     * @return int
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((this.depositNumber == null) ? 0 : this.depositNumber.hashCode());
	result = prime * result + ((this.originalDepositNumber == null) ? 0 : this.originalDepositNumber.hashCode());
	return result;
    }

    /**
     * override equals.
     * 
     * @param obj
     *            Object
     * @return boolean
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
	if (this == obj) {
	    return true;
	}
	if (obj == null) {
	    return false;
	}
	if (this.getClass() != obj.getClass()) {
	    return false;
	}
	TermDepositDTO other = (TermDepositDTO) obj;
	if (this.depositNumber == null) {
	    if (other.depositNumber != null) {
		return false;
	    }
	} else if (!this.depositNumber.equals(other.depositNumber)) {
	    return false;
	}
	if (this.originalDepositNumber == null) {
	    if (other.originalDepositNumber != null) {
		return false;
	    }
	} else if (!this.originalDepositNumber.equals(other.originalDepositNumber)) {
	    return false;
	}

	return true;
    }

    public Boolean getAsResult() {
	return asResult;
    }

    public void setAsResult(Boolean asResult) {
	this.asResult = asResult;
    }

    public Boolean getNewAccount() {
	return newAccount;
    }

    public void setNewAccount(Boolean newAccount) {
	this.newAccount = newAccount;
    }

}
