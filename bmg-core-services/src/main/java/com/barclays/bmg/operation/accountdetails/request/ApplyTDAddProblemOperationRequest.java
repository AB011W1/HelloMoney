package com.barclays.bmg.operation.accountdetails.request;

import com.barclays.bmg.context.RequestContext;

public class ApplyTDAddProblemOperationRequest extends RequestContext {

    private String acctNo;

    private String depositAmount;

    private String tenureTerm;

    private String noteDescription;
    private String notesId;
    private String objectId;
    private String subject;

    public String getNoteDescription() {
	return noteDescription;
    }

    public void setNoteDescription(String noteDescription) {
	this.noteDescription = noteDescription;
    }

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

    public String getAcctNo() {
	return acctNo;
    }

    public void setAcctNo(String acctNo2) {
	this.acctNo = acctNo2;
    }

    public String getDepositAmount() {
	return depositAmount;
    }

    public void setDepositAmount(String depositAmount) {
	this.depositAmount = depositAmount;
    }

    public String getTenureTerm() {
	return tenureTerm;
    }

    public void setTenureTerm(String tenureTerm) {
	this.tenureTerm = tenureTerm;
    }

}
