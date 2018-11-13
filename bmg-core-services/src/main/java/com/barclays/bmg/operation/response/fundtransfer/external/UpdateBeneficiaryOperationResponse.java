package com.barclays.bmg.operation.response.fundtransfer.external;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.BeneficiaryDTO;

public class UpdateBeneficiaryOperationResponse extends ResponseContext {

    /**
	 *
	 */
    private static final long serialVersionUID = -3698879118869215496L;
    private BeneficiaryDTO beneficiaryDTO;
    private boolean scndLvlAuthReq;

    public boolean isScndLvlAuthReq() {
	return scndLvlAuthReq;
    }

    public void setScndLvlAuthReq(boolean scndLvlAuthReq) {
	this.scndLvlAuthReq = scndLvlAuthReq;
    }

    public BeneficiaryDTO getBeneficiaryDTO() {
	return beneficiaryDTO;
    }

    public void setBeneficiaryDTO(BeneficiaryDTO beneficiaryDTO) {
	this.beneficiaryDTO = beneficiaryDTO;
    }
}