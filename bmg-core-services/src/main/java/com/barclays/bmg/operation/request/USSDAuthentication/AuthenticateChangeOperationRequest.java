package com.barclays.bmg.operation.request.USSDAuthentication;

import com.barclays.bmg.context.RequestContext;

/**
 * @author Hiral Pannchal 
 * This is the request mapping class for Authentication
 *         Change Request parameters
 *
 */
public class AuthenticateChangeOperationRequest extends RequestContext {

	private String accountNumber;

	private String activityId;

	private Object documentDetails;

	private String ussdStatus;

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public Object getDocumentDetails() {
		return documentDetails;
	}

	public void setDocumentDetails(Object documentDetails) {
		this.documentDetails = documentDetails;
	}

	public String getUssdStatus() {
		return ussdStatus;
	}

	public void setUssdStatus(String ussdStatus) {
		this.ussdStatus = ussdStatus;
	}
}
