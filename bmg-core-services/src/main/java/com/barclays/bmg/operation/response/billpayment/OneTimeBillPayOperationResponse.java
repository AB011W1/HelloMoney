package com.barclays.bmg.operation.response.billpayment;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.dto.BillerDTO;

public class OneTimeBillPayOperationResponse extends ResponseContext {

    /**
	 *
	 */
    private static final long serialVersionUID = 956636140891130244L;
    private BillerDTO billerDTO;
    private BeneficiaryDTO beneficiaryDTO;

    public BillerDTO getBillerDTO() {
	return billerDTO;
    }

    public void setBillerDTO(BillerDTO billerDTO) {
	this.billerDTO = billerDTO;
    }

    public BeneficiaryDTO getBeneficiaryDTO() {
	return beneficiaryDTO;
    }

    public void setBeneficiaryDTO(BeneficiaryDTO beneficiaryDTO) {
	this.beneficiaryDTO = beneficiaryDTO;
    }

}