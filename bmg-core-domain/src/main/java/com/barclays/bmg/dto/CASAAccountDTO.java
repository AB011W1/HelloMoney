/* Copyright 2008 Barclays PLC */

/**************************** Revision History ****************************************************
 * Version        Author          Date                        Description
 * 0.1            Elicer Zheng        2009/02/08                  Initial version
 *
 *
 **************************************************************************************************/

package com.barclays.bmg.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.barclays.bmg.type.Currency;

/**
 * @author Scott Li
 */
public class CASAAccountDTO extends CustomerAccountDTO {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    private String accountHolders;

    private Date accountOpenDate;

    private BigDecimal unclearedFunds;

    private BigDecimal onHoldAmount;

    private BigDecimal eligibleAdvance;

    private BigDecimal minimumBalanceRequired;

    private BigDecimal withdrawalBalance;

    private BigDecimal accuredInterestOfThisYear;

    private BigDecimal preYearAccruedInterest;

    private BigDecimal overDraftLimit;

    private Currency currencyCode;

    private boolean requestFlg = true;

    private Boolean checkSupportFlag;

    private boolean operativeFlag = false;

    //For groupwallet
    private String bankCif;
	private String groupWalletIndicator;


    public String getBankCif() {
		return bankCif;
	}

	public void setBankCif(String bankCif) {
		this.bankCif = bankCif;
	}

	public String getGroupWalletIndicator() {
		return groupWalletIndicator;
	}

	public void setGroupWalletIndicator(String groupWalletIndicator) {
		this.groupWalletIndicator = groupWalletIndicator;
	}

    @Override
    public String toString() {
	StringBuilder sb = new StringBuilder();
	sb.append("CASAAccountDTO[accountHolders=").append(accountHolders);
	sb.append(",accountOpenDate=").append(accountOpenDate);
	sb.append(",onHoldAmount=").append(onHoldAmount);
	sb.append(",withdrawalBalance=").append(withdrawalBalance);
	sb.append(",accuredInterestOfThisYear=").append(accuredInterestOfThisYear);
	sb.append(",currencyCode=").append(currencyCode);
	sb.append("]");
	return sb.toString();
    }

    /**
     * @return the checkSupportFlag
     */
    public Boolean isCheckSupportFlag() {
	return checkSupportFlag;
    }

    /**
     * @param checkSupportFlag
     *            the checkSupportFlag to set
     */
    public void setCheckSupportFlag(Boolean checkSupportFlag) {
	this.checkSupportFlag = checkSupportFlag;
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

    /**
     *
     */
    public CASAAccountDTO() {
	super();
	// TODO Auto-generated constructor stub
    }

    /**
     * @return the accountHolders
     */
    public String getAccountHolders() {
	return accountHolders;
    }

    /**
     * @param accountHoldersParam
     *            the accountHolders to set
     */
    public void setAccountHolders(String accountHoldersParam) {
	this.accountHolders = accountHoldersParam;
    }

    /**
     * @return the accountOpenDate
     */
    public Date getAccountOpenDate() {
	if (accountOpenDate != null) {
	    return new Date(accountOpenDate.getTime());
	}
	return null;
    }

    /**
     * @param accountOpenDateParam
     *            the accountOpenDate to set
     */
    public void setAccountOpenDate(Date accountOpenDateParam) {
	if (accountOpenDateParam != null) {
	    this.accountOpenDate = new Date(accountOpenDateParam.getTime());
	} else {
	    this.accountOpenDate = null;
	}
    }

    /**
     * @return the unclearedFunds
     */
    public BigDecimal getUnclearedFunds() {
	return unclearedFunds;
    }

    /**
     * @param unclearedFundsParam
     *            the unclearedFunds to set
     */
    public void setUnclearedFunds(BigDecimal unclearedFundsParam) {
	this.unclearedFunds = unclearedFundsParam;
    }

    /**
     * @return the onHoldAmount
     */
    public BigDecimal getOnHoldAmount() {
	return onHoldAmount;
    }

    /**
     * @param onHoldAmountParam
     *            the onHoldAmount to set
     */
    public void setOnHoldAmount(BigDecimal onHoldAmountParam) {
	this.onHoldAmount = onHoldAmountParam;
    }

    /**
     * @return the eligibleAdvance
     */
    public BigDecimal getEligibleAdvance() {
	return eligibleAdvance;
    }

    /**
     * @param eligibleAdvanceParam
     *            the eligibleAdvance to set
     */
    public void setEligibleAdvance(BigDecimal eligibleAdvanceParam) {
	this.eligibleAdvance = eligibleAdvanceParam;
    }

    /**
     * @return the minimumBalanceRequired
     */
    public BigDecimal getMinimumBalanceRequired() {
	return minimumBalanceRequired;
    }

    /**
     * @param minimumBalanceRequiredParam
     *            the minimumBalanceRequired to set
     */
    public void setMinimumBalanceRequired(BigDecimal minimumBalanceRequiredParam) {
	this.minimumBalanceRequired = minimumBalanceRequiredParam;
    }

    /**
     * @return the withdrawalBalance
     */
    public BigDecimal getWithdrawalBalance() {
	return withdrawalBalance;
    }

    /**
     * @param withdrawalBalanceParam
     *            the withdrawalBalance to set
     */
    public void setWithdrawalBalance(BigDecimal withdrawalBalanceParam) {
	this.withdrawalBalance = withdrawalBalanceParam;
    }

    /**
     * @return the accuredInterestOfThisYear
     */
    public BigDecimal getAccuredInterestOfThisYear() {
	return accuredInterestOfThisYear;
    }

    /**
     * @param accuredInterestOfThisYearParam
     *            the accuredInterestOfThisYear to set
     */
    public void setAccuredInterestOfThisYear(BigDecimal accuredInterestOfThisYearParam) {
	this.accuredInterestOfThisYear = accuredInterestOfThisYearParam;
    }

    /**
     * @return the preYearAccruedInterest
     */
    public BigDecimal getPreYearAccruedInterest() {
	return preYearAccruedInterest;
    }

    /**
     * @param preYearAccruedInterestParam
     *            the preYearAccruedInterest to set
     */
    public void setPreYearAccruedInterest(BigDecimal preYearAccruedInterestParam) {
	this.preYearAccruedInterest = preYearAccruedInterestParam;
    }

    /**
     * @return the overDraftLimit
     */
    public BigDecimal getOverDraftLimit() {
	return overDraftLimit;
    }

    /**
     * @param overDraftLimit
     *            the overDraftLimit to set
     */
    public void setOverDraftLimit(BigDecimal overDraftLimit) {
	this.overDraftLimit = overDraftLimit;
    }

    public boolean isRequestFlg() {
	return requestFlg;
    }

    public void setRequestFlg(boolean requestFlg) {
	this.requestFlg = requestFlg;
    }

    public boolean isOperativeFlag() {
	return operativeFlag;
    }

    public void setOperativeFlag(boolean operativeFlag) {
	this.operativeFlag = operativeFlag;
    }

    @Override
    public int hashCode() {
	return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
	return super.equals(obj);
    }

}
