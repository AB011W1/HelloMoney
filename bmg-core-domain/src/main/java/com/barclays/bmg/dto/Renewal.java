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

import java.io.Serializable;

/* *************************** Revision History *********************************
 * Version        Author          Date                            Description
 * 0.1            Max Zhang       Jul 29, 2009                    Initial version
 *
 *
 ********************************************************************************/

/**
 * @author Max Zhang
 */
public class Renewal implements Serializable {

    // public static final String RENEWAL_ALL = "1010";
    //
    // public static final String RENEWAL_CAPITAL = "102";
    //
    // public static final String RENEWAL_NULL = "22";

    public static final String RENEWAL_ALL = "01";

    public static final String RENEWAL_CAPITAL = "02";

    public static final String RENEWAL_NULL = "03";
    // desc
    public static final String RENEWAL_ALL_DESC = "Roll over Principle & Interest on maturity.";

    public static final String RENEWAL_CAPITAL_DESC = "Roll over principle on maturity and credit interest to ";

    public static final String RENEWAL_NULL_DESC = "Credit principal and interest to ";

    /**
     *
     */
    private static final long serialVersionUID = 4955402482575399896L;

    private String renewalType = RENEWAL_CAPITAL;

    private String principalRenewalType;

    private String interestRenewalType;

    private String description;

    private CASAAccountDTO account;
    private CASAAccountDTO account2;
    private CASAAccountDTO account3;

    private CASAAccountDTO beneficiaryAccount;

    private CustomerAccountDTO tdBeneficiaryAcct;
    private CustomerAccountDTO tdPrincipleBeneficiaryAcct;
    private CustomerAccountDTO tdInterestRateBeneficiaryAcct;

    /**
     * @return the tdPrincipleBeneficiaryAcct
     */
    public CustomerAccountDTO getTdPrincipleBeneficiaryAcct() {
	return tdPrincipleBeneficiaryAcct;
    }

    /**
     * @param tdPrincipleBeneficiaryAcct
     *            the tdPrincipleBeneficiaryAcct to set
     */
    public void setTdPrincipleBeneficiaryAcct(CustomerAccountDTO tdPrincipleBeneficiaryAcct) {
	this.tdPrincipleBeneficiaryAcct = tdPrincipleBeneficiaryAcct;
    }

    /**
     * @return the tdInterestRateBeneficiaryAcct
     */
    public CustomerAccountDTO getTdInterestRateBeneficiaryAcct() {
	return tdInterestRateBeneficiaryAcct;
    }

    /**
     * @param tdInterestRateBeneficiaryAcct
     *            the tdInterestRateBeneficiaryAcct to set
     */
    public void setTdInterestRateBeneficiaryAcct(CustomerAccountDTO tdInterestRateBeneficiaryAcct) {
	this.tdInterestRateBeneficiaryAcct = tdInterestRateBeneficiaryAcct;
    }

    private String beneficiaryAcc;

    /**
     * @return the beneficiaryAcc
     */
    public String getBeneficiaryAcc() {
	return beneficiaryAcc;
    }

    /**
     * @param beneficiaryAcc
     *            the beneficiaryAcc to set
     */
    public void setBeneficiaryAcc(String beneficiaryAcc) {
	this.beneficiaryAcc = beneficiaryAcc;
    }

    /**
     * @return renewalType
     */
    public String getRenewalType() {
	return renewalType;
    }

    /**
     * @param renewalTypeParam
     *            String
     */
    public void setRenewalType(String renewalTypeParam) {
	this.renewalType = renewalTypeParam;
    }

    /**
     * @return description
     */
    public String getDescription() {
	return description;
    }

    /**
     * @param descriptionParam
     *            String
     */
    public void setDescription(String descriptionParam) {
	this.description = descriptionParam;
    }

    /**
     * @return account
     */
    public CASAAccountDTO getAccount() {
	if (RENEWAL_CAPITAL.equals(this.renewalType)) {
	    account = account2;
	} else if (RENEWAL_NULL.equals(this.renewalType)) {
	    account = account3;
	}

	return account;
    }

    /**
     * @return the beneficiaryAccount
     */
    public CASAAccountDTO getBeneficiaryAccount() {
	return beneficiaryAccount;
    }

    /**
     * @param beneficiaryAccount
     *            the beneficiaryAccount to set
     */
    public void setBeneficiaryAccount(CASAAccountDTO beneficiaryAccount) {
	this.beneficiaryAccount = beneficiaryAccount;
    }

    /**
     * @param accountParam
     *            CustomerAccount
     */
    public void setAccount(CASAAccountDTO accountParam) {
	this.account = accountParam;
	this.account2 = accountParam;
	this.account3 = accountParam;
    }

    /**
     * @return the account2
     */
    public final CASAAccountDTO getAccount2() {
	return account2;
    }

    /**
     * @param account2
     *            the account2 to set
     */
    public final void setAccount2(CASAAccountDTO account2Param) {
	this.account2 = account2Param;
    }

    /**
     * @return the account3
     */
    public final CASAAccountDTO getAccount3() {
	return account3;
    }

    /**
     * @param account3
     *            the account3 to set
     */
    public final void setAccount3(CASAAccountDTO account3Param) {
	this.account3 = account3Param;
    }

    /**
     * @return the principalRenewalType
     */
    public String getPrincipalRenewalType() {
	return principalRenewalType;
    }

    /**
     * @param principalRenewalType
     *            the principalRenewalType to set
     */
    public void setPrincipalRenewalType(String principalRenewalType) {
	this.principalRenewalType = principalRenewalType;
    }

    /**
     * @return the interestRenewalType
     */
    public String getInterestRenewalType() {
	return interestRenewalType;
    }

    /**
     * @param interestRenewalType
     *            the interestRenewalType to set
     */
    public void setInterestRenewalType(String interestRenewalType) {
	this.interestRenewalType = interestRenewalType;
    }

    public CustomerAccountDTO getTdBeneficiaryAcct() {
	return tdBeneficiaryAcct;
    }

    public void setTdBeneficiaryAcct(CustomerAccountDTO tdBeneficiaryAcct) {
	this.tdBeneficiaryAcct = tdBeneficiaryAcct;
    }

}
