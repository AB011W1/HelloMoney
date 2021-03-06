package com.barclays.ussd.bmg.auth.request;

import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import com.barclays.bmg.service.response.USSDAuthentication.GetRecordResDocumentInfo;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthRequestDetailsPayData {

	@JsonProperty
    private String serviceResponse;

	@JsonProperty
	private Date txnDt;

	@JsonProperty
	private String activityId;

	@JsonProperty
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
