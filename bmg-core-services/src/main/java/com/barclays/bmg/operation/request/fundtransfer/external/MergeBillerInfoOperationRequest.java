package com.barclays.bmg.operation.request.fundtransfer.external;

import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.dto.BeneficiaryDTO;

public class MergeBillerInfoOperationRequest extends RequestContext {

    private BeneficiaryDTO beneficiaryDTO;
    private String payeeType;

    public BeneficiaryDTO getBeneficiaryDTO() {
	return beneficiaryDTO;
    }

    public void setBeneficiaryDTO(BeneficiaryDTO beneficiaryDTO) {
	this.beneficiaryDTO = beneficiaryDTO;
    }

    public String getPayeeType() {
	return payeeType;
    }

    public void setPayeeType(String payeeType) {
	this.payeeType = payeeType;
    }

}
