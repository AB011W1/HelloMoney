package com.barclays.bmg.operation.response.fundtransfer.external;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.dto.BillerDTO;

public class MergeBillerInfoOperationResponse extends ResponseContext {

    private static final long serialVersionUID = 8449729428943858000L;

    private BeneficiaryDTO beneficiaryDTO;
    private BillerDTO billerDTO;
    private String intervalAmt;

    public BeneficiaryDTO getBeneficiaryDTO() {
	return beneficiaryDTO;
    }

    public void setBeneficiaryDTO(BeneficiaryDTO beneficiaryDTO) {
	this.beneficiaryDTO = beneficiaryDTO;
    }

    public BillerDTO getBillerDTO() {
	return billerDTO;
    }

    public void setBillerDTO(BillerDTO billerDTO) {
	this.billerDTO = billerDTO;
    }

    public String getIntervalAmt() {
	return intervalAmt;
    }

    public void setIntervalAmt(String intervalAmt) {
	this.intervalAmt = intervalAmt;
    }

}
