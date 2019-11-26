package com.barclays.bmg.json.model;

import java.util.Date;

import com.barclays.bmg.json.response.BMBPayloadData;

public class UssdUpdateRecordsJSONModel extends BMBPayloadData {

	private static final long serialVersionUID = -2767201846910444322L;

	private String serviceResponse;

	private Date txnDt;

	public String getServiceResponse() {
		return serviceResponse;
	}

	public void setServiceResponse(String serviceResponse) {
		this.serviceResponse = serviceResponse;
	}

	public Date getTxnDt() {
		return txnDt;
	}

	public void setTxnDt(Date txnDt) {
		this.txnDt = txnDt;
	}

}
