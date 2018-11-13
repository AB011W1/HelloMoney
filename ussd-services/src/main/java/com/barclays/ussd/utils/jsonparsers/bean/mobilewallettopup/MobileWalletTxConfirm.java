package com.barclays.ussd.utils.jsonparsers.bean.mobilewallettopup;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.PayHdr;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MobileWalletTxConfirm {
	@JsonProperty
	private PayHdr payHdr;

	@JsonProperty
	private MobileWalletTxConfirmPayData payData;

	public PayHdr getPayHdr() {
		return payHdr;
	}

	public void setPayHdr(PayHdr payHdr) {
		this.payHdr = payHdr;
	}

	public MobileWalletTxConfirmPayData getPayData() {
		return payData;
	}

	public void setPayData(MobileWalletTxConfirmPayData payData) {
		this.payData = payData;
	}

}
