package com.barclays.bmg.service.sessionactivity;

import java.util.Date;

import com.barclays.bmg.context.RequestContext;

public class SessionSummaryServiceRequest extends RequestContext {

	private String tnxType;
	private Date txnTime;


	public String getTnxType() {
		return tnxType;
	}

	public void setTnxType(String tnxType) {
		this.tnxType = tnxType;
	}

	public Date getTxnTime() {
		return (Date) txnTime.clone();
	}

	public void setTxnTime(Date txnTime) {
		this.txnTime = (Date) txnTime.clone();
	}

}
