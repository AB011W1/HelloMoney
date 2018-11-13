package com.barclays.bmg.service.accountdetails.response;

import java.util.List;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.InvestmentAccountDTO;

public class MutualFundDetailsServiceResponse extends ResponseContext {

    /**
	 *
	 */
    private static final long serialVersionUID = -6537226862945949575L;
    private List<InvestmentAccountDTO> investmentAccountDTOList;

    public List<InvestmentAccountDTO> getInvestmentAccountDTOList() {
	return investmentAccountDTOList;
    }

    public void setInvestmentAccountDTOList(List<InvestmentAccountDTO> investmentAccountDTOList) {
	this.investmentAccountDTOList = investmentAccountDTOList;
    }

}