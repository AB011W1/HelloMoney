package com.barclays.ussd.utils.jsonparsers.bean.request.statementReq;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.PayHdr;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StatementRequestConfirm
{
	@JsonProperty
	private PayHdr payHdr;

	@JsonProperty
	private StmtReqConfirmPayData payData;

	public PayHdr getPayHdr() {
		return payHdr;
	}

	public void setPayHdr(PayHdr payHdr) {
		this.payHdr = payHdr;
	}

	public StmtReqConfirmPayData getPayData() {
		return payData;
	}

	public void setPayData(StmtReqConfirmPayData payData) {
		this.payData = payData;
	}


}
