package com.barclays.ussd.utils.jsonparsers.bean.regbenf.internal;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BeneficiaryInfo {
	@JsonProperty
	private String payNckNam;

	@JsonProperty
	private String actNo;

	@JsonProperty
	private BranchInfo branchInfo;

	@JsonProperty
	private String bnkCde;

	public String getBnkCde() {
		return bnkCde;
	}

	public void setBnkCde(String bnkCde) {
		this.bnkCde = bnkCde;
	}

	public String getBeneNam() {
		return beneNam;
	}

	public void setBeneNam(String beneNam) {
		this.beneNam = beneNam;
	}

	@JsonProperty
	private String beneNam;

	public String getPayNckNam() {
		return payNckNam;
	}

	public void setPayNckNam(String payNckNam) {
		this.payNckNam = payNckNam;
	}

	public String getActNo() {
		return actNo;
	}

	public void setActNo(String actNo) {
		this.actNo = actNo;
	}

	public BranchInfo getBranchInfo() {
		return branchInfo;
	}

	public void setBranchInfo(BranchInfo branchInfo) {
		this.branchInfo = branchInfo;
	}



}
