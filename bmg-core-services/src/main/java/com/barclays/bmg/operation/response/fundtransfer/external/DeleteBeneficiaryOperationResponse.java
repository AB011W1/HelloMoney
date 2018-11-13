package com.barclays.bmg.operation.response.fundtransfer.external;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.BeneficiaryDTO;

/**
 * @author BTCI
 * 
 */
public class DeleteBeneficiaryOperationResponse extends ResponseContext {

    /**
	 *
	 */
    private static final long serialVersionUID = 1L;
    private BeneficiaryDTO beneficiaryDTO;

    /**
     * @return BeneficiaryDTO
     */
    public BeneficiaryDTO getBeneficiaryDTO() {
	return beneficiaryDTO;
    }

    /**
     * @param beneficiaryDTO
     */
    public void setBeneficiaryDTO(BeneficiaryDTO beneficiaryDTO) {
	this.beneficiaryDTO = beneficiaryDTO;
    }

}
