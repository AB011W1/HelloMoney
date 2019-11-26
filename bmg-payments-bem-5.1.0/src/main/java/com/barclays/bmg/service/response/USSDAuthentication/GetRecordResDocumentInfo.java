package com.barclays.bmg.service.response.USSDAuthentication;

public class GetRecordResDocumentInfo {

	private String optimisticLockID;

	private String ussdStatus;

	private String fieldName;

	private String oldValue;

	private String newValue;

	private String docId;

	private String caseType;

	private String action;

	public String getOptimisticLockID() {
		return optimisticLockID;
	}

	public void setOptimisticLockID(String optimisticLockID) {
		this.optimisticLockID = optimisticLockID;
	}

	public String getUssdStatus() {
		return ussdStatus;
	}

	public void setUssdStatus(String ussdStatus) {
		this.ussdStatus = ussdStatus;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getOldValue() {
		return oldValue;
	}

	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}

	public String getNewValue() {
		return newValue;
	}

	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}

	public String getDocId() {
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

	public String getCaseType() {
		return caseType;
	}

	public void setCaseType(String caseType) {
		this.caseType = caseType;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
}
