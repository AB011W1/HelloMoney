package com.barclays.ussd.utils.jsonparsers.bean.mobilewallettopup;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.PayHdr;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MobileWalletTxValidate {

	@JsonProperty
	private PayHdr payHdr;

	@JsonProperty
	private MobileWalletTxValidatePayData payData;

	public PayHdr getPayHdr() {
		return this.payHdr;
	}

	public void setPayHdr(PayHdr payHdr) {
		this.payHdr = payHdr;
	}

	public MobileWalletTxValidatePayData getPayData() {
		return this.payData;
	}

	public void setPayData(MobileWalletTxValidatePayData payData) {
		this.payData = payData;
	}
}
