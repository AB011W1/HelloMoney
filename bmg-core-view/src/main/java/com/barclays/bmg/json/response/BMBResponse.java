package com.barclays.bmg.json.response;

import java.io.Serializable;

import com.barclays.bmg.json.response.model.BMBBaseResponseModel;

public class BMBResponse extends BMBBaseResponseModel implements Serializable {

	private static final long serialVersionUID = 2557168853858866633L;

	private BMBHeader hdr;
	private BMBPayload pay;

	public BMBResponse() {
		super();
	}

	public BMBResponse(BMBHeader hdr) {
		super();
		this.hdr = hdr;
	}

	public BMBHeader getHdr() {
		return hdr;
	}

	public void setHdr(BMBHeader hdr) {
		this.hdr = hdr;
	}

	public BMBPayload getPay() {
		return pay;
	}

	public void setPay(BMBPayload pay) {
		this.pay = pay;
	}

}
