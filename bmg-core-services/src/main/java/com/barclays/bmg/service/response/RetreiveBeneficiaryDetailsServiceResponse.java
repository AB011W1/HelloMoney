package com.barclays.bmg.service.response;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.BeneficiaryDTO;

public class RetreiveBeneficiaryDetailsServiceResponse extends ResponseContext {
    /**
	 *
	 */
    private static final long serialVersionUID = 6781019909354401157L;
    private BeneficiaryDTO beneficiaryDTO;

    public BeneficiaryDTO getBeneficiaryDTO() {
	return beneficiaryDTO;
    }

    public void setBeneficiaryDTO(BeneficiaryDTO beneficiaryDTO) {
	this.beneficiaryDTO = beneficiaryDTO;
    }
}
