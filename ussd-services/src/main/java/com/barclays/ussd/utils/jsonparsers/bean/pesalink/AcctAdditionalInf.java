package com.barclays.ussd.utils.jsonparsers.bean.pesalink;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AcctAdditionalInf {
	@JsonProperty
	String authLevel;
	@JsonProperty
	String accountId;
	@JsonProperty
	String maskedAccountId;
	@JsonProperty
	String accountTitle;
	@JsonProperty
	String currentBalance;
	@JsonProperty
	String availableBalance;
	@JsonProperty
	String branchCode;
	@JsonProperty
	String branchName;
	@JsonProperty
	String currencyCode;
	@JsonProperty
	String currencyShortName;
	@JsonProperty
	String currentStatus;
	@JsonProperty
	String customerId;
	@JsonProperty
	String customerRelationship;
	@JsonProperty
	String productCode;
	@JsonProperty
	String productName;

	public String getMaskedAccountId() {
		return maskedAccountId;
	}

	public void setMaskedAccountId(String maskedAccountId) {
		this.maskedAccountId = maskedAccountId;
	}
	public String getAuthLevel() {
		return authLevel;
	}

	public void setAuthLevel(String authLevel) {
		this.authLevel = authLevel;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getAccountTitle() {
		return accountTitle;
	}

	public void setAccountTitle(String accountTitle) {
		this.accountTitle = accountTitle;
	}

	public String getCurrentBalance() {
		return currentBalance;
	}

	public void setCurrentBalance(String currentBalance) {
		this.currentBalance = currentBalance;
	}

	public String getAvailableBalance() {
		return availableBalance;
	}

	public void setAvailableBalance(String availableBalance) {
		this.availableBalance = availableBalance;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getCurrencyShortName() {
		return currencyShortName;
	}

	public void setCurrencyShortName(String currencyShortName) {
		this.currencyShortName = currencyShortName;
	}

	public String getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCustomerRelationship() {
		return customerRelationship;
	}

	public void setCustomerRelationship(String customerRelationship) {
		this.customerRelationship = customerRelationship;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

}
