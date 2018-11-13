package com.barclays.bmg.mvc.command.billpayment;

public class AddBeneficiaryFormSubmissionCommand {

	private String payeeId;
	private String payeeType;
	private String accountNumber;
	private String branchCode;
	private String bankName;
	private String beneficiaryName;
	private String beneficiaryNickName;
	private boolean isCheckBeneficiaryName;
	private boolean isBarclaysAccount;
	private String bankCode;
	private String branchName;
	private String address;
	private String businessId;
	private String nib;

	public String getNib() {
		return nib;
	}

	public void setNib(String nib) {
		this.nib = nib;
	}

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	private String city;


	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getPayeeId() {
		return payeeId;
	}

	public void setPayeeId(String payeeId) {
		this.payeeId = payeeId;
	}

	public String getPayeeType() {
		return payeeType;
	}

	public void setPayeeType(String payeeType) {
		this.payeeType = payeeType;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBeneficiaryName() {
		return beneficiaryName;
	}

	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}

	public String getBeneficiaryNickName() {
		return beneficiaryNickName;
	}

	public void setBeneficiaryNickName(String beneficiaryNickName) {
		this.beneficiaryNickName = beneficiaryNickName;
	}

	public boolean isCheckBeneficiaryName() {
		return isCheckBeneficiaryName;
	}

	public void setCheckBeneficiaryName(boolean isCheckBeneficiaryName) {
		this.isCheckBeneficiaryName = isCheckBeneficiaryName;
	}

	public boolean isBarclaysAccount() {
		return isBarclaysAccount;
	}

	public void setBarclaysAccount(boolean isBarclaysAccount) {
		this.isBarclaysAccount = isBarclaysAccount;
	}


}
