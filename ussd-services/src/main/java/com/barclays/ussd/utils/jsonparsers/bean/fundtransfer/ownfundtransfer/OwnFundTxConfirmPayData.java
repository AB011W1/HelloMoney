package com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OwnFundTxConfirmPayData
{
	@JsonProperty
	private String refNo;

	@JsonProperty
	private String txnDt;

	public String getRefNo() {
		return refNo;
	}

	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}

	public String getTxnDt() {
		return txnDt;
	}

	public void setTxnDt(String txnDt) {
		this.txnDt = txnDt;
	}



}
