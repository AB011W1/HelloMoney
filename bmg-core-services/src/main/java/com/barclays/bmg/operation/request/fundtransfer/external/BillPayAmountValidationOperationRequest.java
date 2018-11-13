package com.barclays.bmg.operation.request.fundtransfer.external;

import java.math.BigDecimal;

import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.dto.Amount;
import com.barclays.bmg.dto.BeneficiaryDTO;

public class BillPayAmountValidationOperationRequest extends RequestContext {

    private String txnType;
    private Amount txnAmount;
    private BeneficiaryDTO beneficiaryDTO;
    private BigDecimal minAmt;
    private BigDecimal maxAmt;

    public String getTxnType() {
	return txnType;
    }

    public void setTxnType(String txnType) {
	this.txnType = txnType;
    }

    public Amount getTxnAmount() {
	return txnAmount;
    }

    public void setTxnAmount(Amount txnAmount) {
	this.txnAmount = txnAmount;
    }

    public BigDecimal getMinAmt() {
	return minAmt;
    }

    public void setMinAmt(BigDecimal minAmt) {
	this.minAmt = minAmt;
    }

    public BigDecimal getMaxAmt() {
	return maxAmt;
    }

    public void setMaxAmt(BigDecimal maxAmt) {
	this.maxAmt = maxAmt;
    }

    public BeneficiaryDTO getBeneficiaryDTO() {
	return beneficiaryDTO;
    }

    public void setBeneficiaryDTO(BeneficiaryDTO beneficiaryDTO) {
	this.beneficiaryDTO = beneficiaryDTO;
    }

}
