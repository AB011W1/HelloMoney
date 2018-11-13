package com.barclays.bmg.operation.response.fundtransfer.external;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.dto.CASAAccountDTO;

public class RetrievePayeeInfoOperationResponse extends ResponseContext {

    private static final long serialVersionUID = -9048237789390853954L;

    private BeneficiaryDTO beneficiaryDTO;
    private CASAAccountDTO casaAccountDTO;

    public BeneficiaryDTO getBeneficiaryDTO() {
	return beneficiaryDTO;
    }

    public void setBeneficiaryDTO(BeneficiaryDTO beneficiaryDTO) {
	this.beneficiaryDTO = beneficiaryDTO;
    }

    public CASAAccountDTO getCasaAccountDTO() {
	return casaAccountDTO;
    }

    public void setCasaAccountDTO(CASAAccountDTO casaAccountDTO) {
	this.casaAccountDTO = casaAccountDTO;
    }

}
