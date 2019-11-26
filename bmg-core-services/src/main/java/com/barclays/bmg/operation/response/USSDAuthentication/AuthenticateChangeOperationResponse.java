package com.barclays.bmg.operation.response.USSDAuthentication;

import java.util.Date;

import com.barclays.bmg.context.ResponseContext;

/**
 * @author Hiral Pannchal 
 * This is the response mapping class for Authentication Change Request
 *
 */
public class AuthenticateChangeOperationResponse extends ResponseContext {

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
