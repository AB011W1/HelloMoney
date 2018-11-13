package com.barclays.bmg.service.request;

import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.dto.BeneficiaryDTO;

public class AddBeneficiaryServiceRequest extends RequestContext {

    private String payeeId;
    private String payeeType;
    private BeneficiaryDTO beneficiaryDTO;

    public BeneficiaryDTO getBeneficiaryDTO() {
	return beneficiaryDTO;
    }

    public void setBeneficiaryDTO(BeneficiaryDTO beneficiaryDTO) {
	this.beneficiaryDTO = beneficiaryDTO;
    }

    public String getPayeeId() {
	return payeeId;
    }

    public void setPayeeId(String payeeId) {
	this.payeeId = payeeId;
    }

    public String getPayeeType() {
	return payeeType;
    }

    public void setPayeeType(String payeeType) {
	this.payeeType = payeeType;
    }

}
