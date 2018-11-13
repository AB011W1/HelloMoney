package com.barclays.bmg.operation.response.fundtransfer.external;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.BeneficiaryDTO;

public class MergeOwnCreditcardInfoOperationResponse extends ResponseContext {

    private static final long serialVersionUID = 3728793200534165363L;

    private BeneficiaryDTO beneficiaryDTO;

    public BeneficiaryDTO getBeneficiaryDTO() {
	return beneficiaryDTO;
    }

    public void setBeneficiaryDTO(BeneficiaryDTO beneficiaryDTO) {
	this.beneficiaryDTO = beneficiaryDTO;
    }

}
