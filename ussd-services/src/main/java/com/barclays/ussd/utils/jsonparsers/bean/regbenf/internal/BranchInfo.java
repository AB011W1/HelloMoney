package com.barclays.ussd.utils.jsonparsers.bean.regbenf.internal;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BranchInfo {
	@JsonProperty
	private String brnCde;

	@JsonProperty
	private String bnkCde;

	public String getBnkCde() {
		return bnkCde;
	}

	public void setBnkCde(String bnkCde) {
		this.bnkCde = bnkCde;
	}

	public String getBrnCde() {
		return brnCde;
	}

	public void setBrnCde(String brnCde) {
		this.brnCde = brnCde;
	}

}
