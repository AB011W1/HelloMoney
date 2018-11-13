package com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AccListPayData
{
	@JsonProperty
	private List<AccountDetails> payLst;
	//private List<BeneficiaryList> payLst;

	@JsonProperty
	private List<AccountDetails> srcLst;


	public List<AccountDetails> getSrcLst() {
		return srcLst;
	}

	public void setSrcLst(List<AccountDetails> srcLst) {
		this.srcLst = srcLst;
	}

	public List<AccountDetails> getPayLst() {
		return payLst;
	}

	public void setPayLst(List<AccountDetails> payLst) {
		this.payLst = payLst;
	}


}
