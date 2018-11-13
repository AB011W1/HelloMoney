package com.barclays.ussd.utils.jsonparsers.bean.regbenf.internal;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import com.barclays.ussd.utils.jsonparsers.bean.request.chqueBookReq.PayHeader;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RegBenfIntBean 
{
	@JsonProperty
	private ValidateRegBenfIntPayData payData;

	@JsonProperty
	private PayHeader payHdr;

	public ValidateRegBenfIntPayData getPayData() {
		return payData;
	}

	public void setPayData(ValidateRegBenfIntPayData payData) {
		this.payData = payData;
	}

	public PayHeader getPayHdr() {
		return payHdr;
	}

	public void setPayHdr(PayHeader payHdr) {
		this.payHdr = payHdr;
	}


}
