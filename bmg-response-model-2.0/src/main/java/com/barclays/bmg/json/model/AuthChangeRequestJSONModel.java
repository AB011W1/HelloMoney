package com.barclays.bmg.json.model;

import java.util.Date;

import com.barclays.bmg.json.response.BMBPayloadData;

/**
 * Model class with the fields in json response
 *
 */
public class AuthChangeRequestJSONModel extends BMBPayloadData {

	private static final long serialVersionUID = -6427107219442162360L;

	private String serviceResponse;

	private Date txnDt;

	private String activityId;

	private Object documents;

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

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public Object getDocuments() {
		return documents;
	}

	public void setDocuments(Object documents) {
		this.documents = documents;
	}

}
