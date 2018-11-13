package com.barclays.bmg.dao.operation.accountservices.investment;

import java.util.List;

import com.barclays.bem.RetrieveInvestmentAcctDetailsByCustNo.RetrieveInvestmentAccountDetailsByCustomerNoResponse;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dto.InvestmentAccountDTO;
import com.barclays.bmg.mapper.InvestAccountListToInvestAccountDTOMapper;
import com.barclays.bmg.service.accountdetails.response.BondDetailsServiceResponse;

public class BondDetailsRespAdptOperation {

    public BondDetailsServiceResponse adaptResponseForBondDetails(WorkContext workContext, Object obj) {

	BondDetailsServiceResponse bondDetailsServiceResponse = new BondDetailsServiceResponse();

	RetrieveInvestmentAccountDetailsByCustomerNoResponse retInvAccDetByCusNoRes = (RetrieveInvestmentAccountDetailsByCustomerNoResponse) obj;

	// ------------------ Investment Mapper Section --------------
	InvestAccountListToInvestAccountDTOMapper invAccListToInvAccDToMapper = new InvestAccountListToInvestAccountDTOMapper();

	List<InvestmentAccountDTO> investmentAccountDTOList = invAccListToInvAccDToMapper.mapCollection(retInvAccDetByCusNoRes
		.getInvestmentAccountDetails().getInvestmentAccount());

	bondDetailsServiceResponse.setInvestmentAccountDTOList(investmentAccountDTOList);

	bondDetailsServiceResponse.setSuccess(true);

	return bondDetailsServiceResponse;
    }
}