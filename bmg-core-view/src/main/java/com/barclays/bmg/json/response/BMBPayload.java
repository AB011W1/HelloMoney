package com.barclays.bmg.json.response;

import java.io.Serializable;

import com.barclays.bmg.json.response.model.BMBBaseResponseModel;

public class BMBPayload extends BMBBaseResponseModel implements Serializable{

	public BMBPayload() {
		super();
			}
	public BMBPayload(BMBPayloadHeader payHdr) {
		super();
		this.payHdr = payHdr;
	}
	private static final long serialVersionUID = -441019836886107814L;

	private BMBPayloadHeader payHdr;
	private BMBPayloadData payData;

	public BMBPayloadHeader getPayHdr() {
		return payHdr;
	}
	public void setPayHdr(BMBPayloadHeader payHdr) {
		this.payHdr = payHdr;
	}
	public BMBPayloadData getPayData() {
		return payData;
	}
	public void setPayData(BMBPayloadData payData) {
		this.payData = payData;
	}




}
