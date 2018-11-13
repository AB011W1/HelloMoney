package com.barclays.bmg.operation.accountdetails.response;

import java.util.List;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.InvestmentAccountDTO;

public class BondDetailsOperationResponse extends ResponseContext {

    private List<InvestmentAccountDTO> investmentAccountDTOList;

    public List<InvestmentAccountDTO> getInvestmentAccountDTOList() {
	return investmentAccountDTOList;
    }

    public void setInvestmentAccountDTOList(List<InvestmentAccountDTO> investmentAccountDTOList) {
	this.investmentAccountDTOList = investmentAccountDTOList;
    }

}
