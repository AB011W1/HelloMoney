package com.barclays.bmg.service.response;

import com.barclays.bmg.context.ResponseContext;

public class ApplyTDServiceResponse extends ResponseContext {
    /**
	 *
	 */
    private static final long serialVersionUID = 7278242008637162467L;

    public String getServiceStatus() {
	return serviceStatus;
    }

    public void setServiceStatus(String serviceStatus) {
	this.serviceStatus = serviceStatus;
    }

    public String getTransactionRefNum() {
	return transactionRefNum;
    }

    public void setTransactionRefNum(String transactionRefNum) {
	this.transactionRefNum = transactionRefNum;
    }

    public String getTransDate() {
	return transDate;
    }

    public void setTransDate(String transDate) {
	this.transDate = transDate;
    }

    private String serviceStatus;
    private String transactionRefNum;
    private String transDate;

}
