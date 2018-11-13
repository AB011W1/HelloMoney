package com.barclays.bmg.service.accountdetails.response;

import java.util.List;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.InvestmentAccountDTO;

public class StructuredNoteDetailsServiceResponse extends ResponseContext {

    /**
	 *
	 */
    private static final long serialVersionUID = -1770001966155459620L;
    private List<InvestmentAccountDTO> investmentAccountDTOList;

    public List<InvestmentAccountDTO> getInvestmentAccountDTOList() {
	return investmentAccountDTOList;
    }

    public void setInvestmentAccountDTOList(List<InvestmentAccountDTO> investmentAccountDTOList) {
	this.investmentAccountDTOList = investmentAccountDTOList;
    }
}