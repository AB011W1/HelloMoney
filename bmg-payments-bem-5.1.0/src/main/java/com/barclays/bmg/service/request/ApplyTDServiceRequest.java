package com.barclays.bmg.service.request;

import com.barclays.bem.BEMServiceHeader.BEMResHeader;
import com.barclays.bmg.context.RequestContext;

public class ApplyTDServiceRequest extends RequestContext {

	private String accountNumber;
	private String accountType;
	private BEMResHeader responseHeader;
	private String noteDescription;
	private String notesId;
	private String objectId;
	private String subject;

	public String getNotesId() {
		return notesId;
	}
	public void setNotesId(String notesId) {
		this.notesId = notesId;
	}
	public String getObjectId() {
		return objectId;
	}
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public void setNoteDescription(String noteDescription) {
		this.noteDescription = noteDescription;
	}
	public String getNoteDescription() {
		return noteDescription;
	}


	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public BEMResHeader getResponseHeader() {
		return responseHeader;
	}
	public void setResponseHeader(BEMResHeader responseHeader) {
		this.responseHeader = responseHeader;
	}


}
