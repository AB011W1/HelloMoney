/* Copyright 2008 Barclays PLC */

package com.barclays.bmg.dto;

import java.math.BigDecimal;
import java.util.Date;

public class CustomerAccountDTO extends BaseDomainDTO implements Comparable {

    private static final long serialVersionUID = 390038904648147294L;

    public static final String DORMANT = "DMT";

    public static final String ACTIVE = "ACT";

    private String accountNumber;

    private String productCode;

    private String productCategory;

    private String productName;

    private String currency;

    private String accountStatus;

    private String accountType;

    private String customerId;

    private String branchCode;

    private BigDecimal currentBalance;

    private BigDecimal availableBalance;

    protected BigDecimal netBalanceAmount;

    protected BigDecimal currentBookBalanceAmount;

    private String org;
    private String productPECode;
    private String desc;

    private String relationshipCode;

    private String customerSex;

    // IBAN Change ;Feb 20, 2012;
    private boolean ibanFlag = false;
    // IBAN Change ;Feb 20, 2012;

    // Update for casa is internet available
    private Boolean internetBankingAccessFlag = true;

    private Date statementStartDate;

    private Date statementEndDate;

    private String eStatementFlag;

    private String nib;

    private String iban;

    private String accountLevelBlockTypeCode1;

    private String accountLevelBlockTypeCode2;

    // branch address and flag
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String nominationRegistered;

    // Add for India Insurance & Investment PDF
    private String clientCode;
    private String relationship;
    private String residenceStatus;
    private String relationshipManager;

    // Add account nick name
    private String accountNickName;

    private BigDecimal asset = new BigDecimal(0);

    private BigDecimal debt = new BigDecimal(0);

    // Added for "verify user with pin"
    private String priInd;

    /**
     * @return the priInd
     */
    public String getPriInd() {
	return priInd;
    }

    /**
     * @param priInd
     *            the priInd to set
     */
    public void setPriInd(String priInd) {
	this.priInd = priInd;
    }

    public String getAddressLine1() {
	return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
	this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
	return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
	this.addressLine2 = addressLine2;
    }

    public String getAddressLine3() {
	return addressLine3;
    }

    public void setAddressLine3(String addressLine3) {
	this.addressLine3 = addressLine3;
    }

    public String getNominationRegistered() {
	return nominationRegistered;
    }

    public void setNominationRegistered(String nominationRegistered) {
	this.nominationRegistered = nominationRegistered;
    }

    // End

    public Boolean getInternetBankingAccessFlag() {
	return internetBankingAccessFlag;
    }

    public void setInternetBankingAccessFlag(Boolean internetBankingAccessFlag) {
	this.internetBankingAccessFlag = internetBankingAccessFlag;
    }

    public String getAccountLevelBlockTypeCode1() {
	return accountLevelBlockTypeCode1;
    }

    public void setAccountLevelBlockTypeCode1(String accountLevelBlockTypeCode1) {
	this.accountLevelBlockTypeCode1 = accountLevelBlockTypeCode1;
    }

    public String getAccountLevelBlockTypeCode2() {
	return accountLevelBlockTypeCode2;
    }

    public void setAccountLevelBlockTypeCode2(String accountLevelBlockTypeCode2) {
	this.accountLevelBlockTypeCode2 = accountLevelBlockTypeCode2;
    }

    public String getNib() {
	return nib;
    }

    public void setNib(String nib) {
	this.nib = nib;
    }

    public String getIban() {
	return iban;
    }

    public void setIban(String iban) {
	this.iban = iban;
    }

    public String getEStatementFlag() {
	return eStatementFlag;
    }

    public void setEStatementFlag(String statementFlag) {
	eStatementFlag = statementFlag;
    }

    /**
     * @return the statementStartDate
     */
    public Date getStatementStartDate() {
	if (statementStartDate != null) {
	    return new Date(statementStartDate.getTime());
	}
	return null;
    }

    /**
     * @param statementStartDate
     *            the statementStartDate to set
     */
    public void setStatementStartDate(Date statementStartDate) {
	if (statementStartDate != null) {
	    this.statementStartDate = new Date(statementStartDate.getTime());
	} else {
	    this.statementStartDate = null;
	}
    }

    /**
     * @return the statementEndDate
     */
    public Date getStatementEndDate() {
	if (statementEndDate != null) {
	    return new Date(statementEndDate.getTime());
	}
	return null;
    }

    /**
     * @param statementEndDate
     *            the statementEndDate to set
     */
    public void setStatementEndDate(Date statementEndDate) {
	if (statementEndDate != null) {
	    this.statementEndDate = new Date(statementEndDate.getTime());
	} else {
	    this.statementEndDate = null;
	}
    }

    /**
     * @return the relationshipCode
     */
    public final String getRelationshipCode() {
	return relationshipCode;
    }

    /**
     * @param relationshipCode
     *            the relationshipCode to set
     */
    public final void setRelationshipCode(String relationshipCode) {
	this.relationshipCode = relationshipCode;
    }

    public BigDecimal getCurrentBalance() {
	return currentBalance;
    }

    public void setCurrentBalance(BigDecimal currentBalance) {
	this.currentBalance = currentBalance;
    }

    public BigDecimal getAvailableBalance() {
	return availableBalance;
    }

    public void setAvailableBalance(BigDecimal availableBalance) {
	this.availableBalance = availableBalance;
    }

    /**
     * @return the accountNumber
     */
    public String getAccountNumber() {
	return accountNumber == null ? accountNumber : accountNumber.trim();
    }

    /**
     * @param accountNumberParam
     *            the accountNumber to set
     */
    public void setAccountNumber(String accountNumberParam) {
	this.accountNumber = accountNumberParam;
    }

    /**
     * @return the productCode
     */
    public String getProductCode() {
	return productCode;
    }

    /**
     * @param productCodeParam
     *            the productCode to set
     */
    public void setProductCode(String productCodeParam) {
	this.productCode = productCodeParam;
    }

    public String getProductCategory() {
	return productCategory;
    }

    public void setProductCategory(String productCategory) {
	this.productCategory = productCategory;
    }

    /**
     * @return the productName
     */
    public String getProductName() {
	return productName;
    }

    /**
     * @param productNameParam
     *            the productName to set
     */
    public void setProductName(String productNameParam) {
	this.productName = productNameParam;
    }

    /**
     * @return the currency
     */
    public String getCurrency() {
	return currency;
    }

    /**
     * @param currencyParam
     *            the currency to set
     */
    public void setCurrency(String currencyParam) {
	this.currency = currencyParam;
    }

    /**
     * @return the accountType
     */
    public String getAccountType() {
	return accountType;
    }

    /**
     * @param accountTypeParam
     *            the accountType to set
     */
    public void setAccountType(String accountTypeParam) {
	this.accountType = accountTypeParam;
    }

    /**
     * @return the customerId
     */
    public String getCustomerId() {
	return customerId;
    }

    /**
     * @param customerIdParam
     *            the customerId to set
     */
    public void setCustomerId(String customerIdParam) {
	this.customerId = customerIdParam;
    }

    /**
     * @return the accountStatus
     */
    public String getAccountStatus() {
	return accountStatus;
    }

    /**
     * @return the branchCode
     */
    public String getBranchCode() {
	return branchCode;
    }

    /**
     * @param branchCode
     *            the branchCode to set
     */
    public void setBranchCode(String branchCode) {
	this.branchCode = branchCode;
    }

    /**
     * @return the desc
     */
    public String getDesc() {
	return desc;
    }

    /**
     * @param desc
     *            the desc to set
     */
    public void setDesc(String desc) {
	this.desc = desc;
    }

    /**
     * @param accountStatus
     *            the accountStatus to set
     */
    public void setAccountStatus(String accountStatus) {
	this.accountStatus = accountStatus;
    }

    public String getCustomerSex() {
	return customerSex;
    }

    public void setCustomerSex(String customerSex) {
	this.customerSex = customerSex;
    }

    public String getClientCode() {
	return clientCode;
    }

    public void setClientCode(String clientCode) {
	this.clientCode = clientCode;
    }

    public String getRelationship() {
	return relationship;
    }

    public void setRelationship(String relationship) {
	this.relationship = relationship;
    }

    public String getResidenceStatus() {
	return residenceStatus;
    }

    public void setResidenceStatus(String residenceStatus) {
	this.residenceStatus = residenceStatus;
    }

    public String getRelationshipManager() {
	return relationshipManager;
    }

    public void setRelationshipManager(String relationshipManager) {
	this.relationshipManager = relationshipManager;
    }

    public BigDecimal getAsset() {
	return asset;
    }

    public void setAsset(BigDecimal asset) {
	this.asset = asset;
    }

    public BigDecimal getDebt() {
	return debt;
    }

    public void setDebt(BigDecimal debt) {
	this.debt = debt;
    }

    public String getAccountNickName() {
	return accountNickName;
    }

    public void setAccountNickName(String accountNickName) {
	this.accountNickName = accountNickName;
    }

    public boolean isIbanFlag() {
	return ibanFlag;
    }

    public void setIbanFlag(boolean ibanFlag) {
	this.ibanFlag = ibanFlag;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((accountNumber == null) ? 0 : accountNumber.hashCode());
	result = prime * result + ((productCode == null) ? 0 : productCode.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (!(obj instanceof CustomerAccountDTO))
	    return false;
	CustomerAccountDTO other = (CustomerAccountDTO) obj;
	if (accountNumber == null) {
	    if (other.accountNumber != null)
		return false;
	} else if (!accountNumber.equals(other.accountNumber))
	    return false;
	if (productCode == null) {
	    if (other.productCode != null)
		return false;
	} else if (!productCode.equals(other.productCode))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	StringBuilder sb = new StringBuilder();
	sb.append("CustomerAccountDTO[accountNumber=").append(accountNumber);
	sb.append(",productCode=").append(productCode);
	sb.append(",productName=").append(productName);
	sb.append(",accountStatus=").append(accountStatus);
	sb.append(",accountType=").append(accountType);
	sb.append(",customerId=").append(customerId);
	sb.append("]");
	return sb.toString();
    }

    public int compareTo(Object o) {
	if (Long.parseLong(accountNumber) == Long.parseLong(((CustomerAccountDTO) o).getAccountNumber())) {
	    if (this instanceof CreditCardAccountDTO) {
		if (((CreditCardAccountDTO) this).getPrimary() != null && ((CreditCardAccountDTO) this).getPrimary().getCardNumber() != null) {
		    return -1;
		} else {
		    return 1;
		}
	    }
	    return 0;
	} else if (Long.parseLong(accountNumber) > Long.parseLong(((CustomerAccountDTO) o).getAccountNumber())) {
	    return 1;
	} else {
	    return -1;
	}

    }

    /**
     * @return the netBalanceAmount
     */
    public BigDecimal getNetBalanceAmount() {
	return netBalanceAmount;
    }

    /**
     * @param netBalanceAmount
     *            the netBalanceAmount to set
     */
    public void setNetBalanceAmount(BigDecimal netBalanceAmount) {
	this.netBalanceAmount = netBalanceAmount;
    }

    /**
     * @return the currentBookBalanceAmount
     */
    public BigDecimal getCurrentBookBalanceAmount() {
	return currentBookBalanceAmount;
    }

    /**
     * @param currentBookBalanceAmount
     *            the currentBookBalanceAmount to set
     */
    public void setCurrentBookBalanceAmount(BigDecimal currentBookBalanceAmount) {
	this.currentBookBalanceAmount = currentBookBalanceAmount;
    }

    public String getOrg() {
	return org;
    }

    public void setOrg(String org) {
	this.org = org;
    }

    public String getProductPECode() {
	return productPECode;
    }

    public void setProductPECode(String productPECode) {
	this.productPECode = productPECode;
    }

}
