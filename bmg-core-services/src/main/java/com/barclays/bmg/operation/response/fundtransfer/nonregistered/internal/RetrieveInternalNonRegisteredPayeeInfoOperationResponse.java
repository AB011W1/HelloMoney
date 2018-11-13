package com.barclays.bmg.operation.response.fundtransfer.nonregistered.internal;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.dto.CASAAccountDTO;

public class RetrieveInternalNonRegisteredPayeeInfoOperationResponse extends ResponseContext {

    private static final long serialVersionUID = 1495853235900717869L;

    private BeneficiaryDTO beneficiaryDTO;
    private CASAAccountDTO casaAccountDTO;

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

    public CASAAccountDTO getCasaAccountDTO() {
	return casaAccountDTO;
    }

    public void setCasaAccountDTO(CASAAccountDTO casaAccountDTO) {
	this.casaAccountDTO = casaAccountDTO;
    }

}
