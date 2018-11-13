package com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BeneficiaryList
{
	@JsonProperty
	private List<BeneficiaryDetails> bnfLst;

	public List<BeneficiaryDetails> getBnfLst() {
		return bnfLst;
	}

	public void setBnfLst(List<BeneficiaryDetails> bnfLst) {
		this.bnfLst = bnfLst;
	}

}
