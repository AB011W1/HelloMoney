package com.barclays.bmg.service.response.USSDAuthentication;

import java.util.Date;
import java.util.List;

import com.barclays.bmg.context.ResponseContext;

public class AuthenticateChangeServiceResponse extends ResponseContext {

	private String  serviceResponse;

	private Date txnDt;

	private String activityId;

	private List<GetRecordResDocumentInfo> documents;

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

	public List<GetRecordResDocumentInfo> getDocuments() {
		return documents;
	}

	public void setDocuments(List<GetRecordResDocumentInfo> documents) {
		this.documents = documents;
	}

}
