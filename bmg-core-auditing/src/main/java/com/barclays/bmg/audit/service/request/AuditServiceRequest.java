package com.barclays.bmg.audit.service.request;

import com.barclays.bmg.audit.dto.TransactionAuditDTO;
import com.barclays.bmg.context.RequestContext;

public class AuditServiceRequest extends RequestContext {

    TransactionAuditDTO transactionAuditDTO;
    String activityId;

    public TransactionAuditDTO getTransactionAuditDTO() {
	return transactionAuditDTO;
    }

    public void setTransactionAuditDTO(TransactionAuditDTO transactionAuditDTO) {
	this.transactionAuditDTO = transactionAuditDTO;
    }

    public String getActivityId() {
	return activityId;
    }

    public void setActivityId(String activityId) {
	this.activityId = activityId;
    }

}
