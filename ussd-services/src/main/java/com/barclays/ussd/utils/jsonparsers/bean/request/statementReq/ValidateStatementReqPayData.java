package com.barclays.ussd.utils.jsonparsers.bean.request.statementReq;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ValidateStatementReqPayData
{
	@JsonProperty
	private String txnRefNo;

	public String getTxnRefNo() {
		return txnRefNo;
	}

	public void setTxnRefNo(String txnRefNo) {
		this.txnRefNo = txnRefNo;
	}


}
