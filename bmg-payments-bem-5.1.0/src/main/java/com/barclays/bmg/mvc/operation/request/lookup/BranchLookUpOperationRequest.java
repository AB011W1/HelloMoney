package com.barclays.bmg.mvc.operation.request.lookup;

import com.barclays.bmg.context.RequestContext;

public class BranchLookUpOperationRequest extends RequestContext {

	private String bankName;
	private String branchName;
	private String cityName;

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

}
