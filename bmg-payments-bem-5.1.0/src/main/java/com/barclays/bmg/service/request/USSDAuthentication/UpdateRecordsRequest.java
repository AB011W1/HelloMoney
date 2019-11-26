package com.barclays.bmg.service.request.USSDAuthentication;

import java.util.List;

import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.service.response.USSDAuthentication.GetRecordResDocumentInfo;

public class UpdateRecordsRequest extends RequestContext{

	private List<GetRecordResDocumentInfo> documents;

	private String activityId;

	private String ussdStatus;

	public List<GetRecordResDocumentInfo> getDocuments() {
		return documents;
	}

	public void setDocuments(List<GetRecordResDocumentInfo> documents) {
		this.documents = documents;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public String getUssdStatus() {
		return ussdStatus;
	}

	public void setUssdStatus(String ussdStatus) {
		this.ussdStatus = ussdStatus;
	}

}
