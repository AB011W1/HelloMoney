package com.barclays.bmg.operation.response.fundtransfer.external;

import java.util.List;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.BeneficiaryDTO;

public class MergeBarclayCardPayeeInfoOperationResponse extends ResponseContext {

    private static final long serialVersionUID = -6895908422454684732L;

    private BeneficiaryDTO beneficiaryDTO;
    private List<String> currLst;

    public BeneficiaryDTO getBeneficiaryDTO() {
	return beneficiaryDTO;
    }

    public void setBeneficiaryDTO(BeneficiaryDTO beneficiaryDTO) {
	this.beneficiaryDTO = beneficiaryDTO;
    }

    public List<String> getCurrLst() {
	return currLst;
    }

    public void setCurrLst(List<String> currLst) {
	this.currLst = currLst;
    }

}
