package com.barclays.ussd.utils.jsonparsers.bean.pesalink;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NonPersonalAccountDetailsPayData {
	@JsonProperty
	List<AccountAdditionalInfo> nonPersonalCustAcctList;

	public List<AccountAdditionalInfo> getNonPersonalCustAcctList() {
		return nonPersonalCustAcctList;
	}

	public void setNonPersonalCustAcctList(
			List<AccountAdditionalInfo> nonPersonalCustAcctList) {
		this.nonPersonalCustAcctList = nonPersonalCustAcctList;
	}

}
