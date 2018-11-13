package com.barclays.ussd.utils.jsonparsers.bean.otherbarclaysfundtx;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.otherbank.TransactionLimit;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OBAFTInitPayData
{
	@JsonProperty
	private List<OBAFTPayeeAcct> payLst;

	@JsonProperty
	private List<OBAFTSrcAcct> srcLst;

	@JsonProperty
	private TransactionLimit txnLmt;

	public List<OBAFTSrcAcct> getSrcLst() {
		return srcLst;
	}
	public void setSrcLst(List<OBAFTSrcAcct> srcLst) {
		this.srcLst = srcLst;
	}

	public List<OBAFTPayeeAcct> getPayLst() {
		return payLst;
	}
	public void setPayLst(List<OBAFTPayeeAcct> payLst) {
		this.payLst = payLst;
	}
	public TransactionLimit getTxnLmt() {
		return txnLmt;
	}
	public void setTxnLmt(TransactionLimit txnLmt) {
		this.txnLmt = txnLmt;
	}

}
