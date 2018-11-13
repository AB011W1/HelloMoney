package com.barclays.bmg.operation.response.billpayment;

import java.math.BigDecimal;
import java.util.List;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.dto.BillerDTO;
import com.barclays.bmg.dto.CustomerAccountDTO;

public class RetreivePayeeInformationOperationResponse extends ResponseContext {

    private static final long serialVersionUID = 8731633168361112229L;

    private BeneficiaryDTO beneficiaryDTO;
    private List<? extends CustomerAccountDTO> sourceAcctList;
    private BigDecimal aValidDailyLimit;
    private String activityId;
    private BillerDTO billerDTO;
    private BigDecimal minBillAmt;
    private BigDecimal maxBilAmt;
    private BigDecimal outBalAmt;
    private String billPayTxnType;
    private BigDecimal intervalAmt;

    public BeneficiaryDTO getBeneficiaryDTO() {
	return beneficiaryDTO;
    }

    public void setBeneficiaryDTO(BeneficiaryDTO beneficiaryDTO) {
	this.beneficiaryDTO = beneficiaryDTO;
    }

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

    public BigDecimal getMinBillAmt() {
	return minBillAmt;
    }

    public void setMinBillAmt(BigDecimal minBillAmt) {
	this.minBillAmt = minBillAmt;
    }

    public BigDecimal getMaxBilAmt() {
	return maxBilAmt;
    }

    public void setMaxBilAmt(BigDecimal maxBilAmt) {
	this.maxBilAmt = maxBilAmt;
    }

    public BillerDTO getBillerDTO() {
	return billerDTO;
    }

    public void setBillerDTO(BillerDTO billerDTO) {
	this.billerDTO = billerDTO;
    }

    public String getBillPayTxnType() {
	return billPayTxnType;
    }

    public void setBillPayTxnType(String billPayTxnType) {
	this.billPayTxnType = billPayTxnType;
    }

    public BigDecimal getOutBalAmt() {
	return outBalAmt;
    }

    public void setOutBalAmt(BigDecimal outBalAmt) {
	this.outBalAmt = outBalAmt;
    }

    public BigDecimal getIntervalAmt() {
	return intervalAmt;
    }

    public void setIntervalAmt(BigDecimal intervalAmt) {
	this.intervalAmt = intervalAmt;
    }

}
