package com.barclays.bmg.dao.operation.accountservices.investment;

import java.util.List;

import com.barclays.bem.RetrieveInvestmentAcctDetailsByCustNo.RetrieveInvestmentAccountDetailsByCustomerNoResponse;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dto.InvestmentAccountDTO;
import com.barclays.bmg.mapper.InvestAccountListToInvestAccountDTOMapper;
import com.barclays.bmg.service.accountdetails.response.MutualFundDetailsServiceResponse;

public class MutualFundDetailsRespAdptOperation {

    public MutualFundDetailsServiceResponse adaptResponseForMutualFundDetails(WorkContext workContext, Object obj) {

	MutualFundDetailsServiceResponse mfDetailsServiceResponse = new MutualFundDetailsServiceResponse();

	RetrieveInvestmentAccountDetailsByCustomerNoResponse retInvAccDetByCusNoRes = (RetrieveInvestmentAccountDetailsByCustomerNoResponse) obj;

	// ------------------ Investment Mapper Section --------------
	InvestAccountListToInvestAccountDTOMapper invAccListToInvAccDToMapper = new InvestAccountListToInvestAccountDTOMapper();

	List<InvestmentAccountDTO> investmentAccountDTOList = invAccListToInvAccDToMapper.mapCollection(retInvAccDetByCusNoRes
		.getInvestmentAccountDetails().getInvestmentAccount());

	mfDetailsServiceResponse.setInvestmentAccountDTOList(investmentAccountDTOList);

	mfDetailsServiceResponse.setSuccess(true);

	return mfDetailsServiceResponse;

    }

}
