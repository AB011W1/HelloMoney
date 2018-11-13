package com.barclays.bmg.operation.response.billpayment;

import java.math.BigDecimal;
import java.util.List;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.CustomerAccountDTO;

public class RetreiveCCPPayeeInformationOperationResponse extends ResponseContext {

    private static final long serialVersionUID = -1303250955653409100L;

    private List<? extends CustomerAccountDTO> sourceAcctList;
    private BigDecimal txnLimit;
    private BigDecimal aValidDailyLimit;
    private String activityId;
    private CustomerAccountDTO creditCardDestAcct;

    public List<? extends CustomerAccountDTO> getSourceAcctList() {
	return sourceAcctList;
    }

    public void setSourceAcctList(List<? extends CustomerAccountDTO> sourceAcctList) {
	this.sourceAcctList = sourceAcctList;
    }

    public BigDecimal getAValidDailyLimit() {
	return aValidDailyLimit;
    }

    public void setAValidDailyLimit(BigDecimal validDailyLimit) {
	aValidDailyLimit = validDailyLimit;
    }

    public String getActivityId() {
	return activityId;
    }

    public void setActivityId(String activityId) {
	this.activityId = activityId;
    }

    public CustomerAccountDTO getCreditCardDestAcct() {
	return creditCardDestAcct;
    }

    public void setCreditCardDestAcct(CustomerAccountDTO creditCardDestAcct) {
	this.creditCardDestAcct = creditCardDestAcct;
    }

    public BigDecimal getTxnLimit() {
	return txnLimit;
    }

    public void setTxnLimit(BigDecimal txnLimit) {
	this.txnLimit = txnLimit;
    }
}
