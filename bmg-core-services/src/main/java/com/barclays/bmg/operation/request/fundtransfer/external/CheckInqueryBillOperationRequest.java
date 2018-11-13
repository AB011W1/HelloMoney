package com.barclays.bmg.operation.request.fundtransfer.external;

import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.dto.BeneficiaryDTO;

public class CheckInqueryBillOperationRequest extends RequestContext {

    private BeneficiaryDTO beneficiaryDTO;
    private String txnType;

    public BeneficiaryDTO getBeneficiaryDTO() {
	return beneficiaryDTO;
    }

    public void setBeneficiaryDTO(BeneficiaryDTO beneficiaryDTO) {
	this.beneficiaryDTO = beneficiaryDTO;
    }

    public String getTxnType() {
	return txnType;
    }

    public void setTxnType(String txnType) {
	this.txnType = txnType;
    }

}
