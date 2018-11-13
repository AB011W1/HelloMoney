package com.barclays.bmg.operation.accountdetails;

import java.util.ArrayList;
import java.util.List;

import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dto.InvestmentAccountDTO;
import com.barclays.bmg.operation.BMBCommonOperation;
import com.barclays.bmg.operation.accountdetails.request.MutualFundDetailsOperationRequest;
import com.barclays.bmg.operation.accountdetails.response.MutualFundDetailsOperationResponse;
import com.barclays.bmg.service.accountdetails.InvestmentAccountDetailsService;
import com.barclays.bmg.service.accountdetails.request.MutualFundDetailsServiceRequest;
import com.barclays.bmg.service.accountdetails.response.MutualFundDetailsServiceResponse;

public class MutualFundDetailsOperation extends BMBCommonOperation {

    private InvestmentAccountDetailsService investmentAccountDetailsService;

    /**
     * 1. Loads the system parameter into context 2. Retrieve the Mutual Fund Account Details. BEM request
     */

    public MutualFundDetailsOperationResponse retrieveMutualFundListByAssetClass(MutualFundDetailsOperationRequest request) {

	Context context = request.getContext();

	loadParameters(context, ActivityConstant.COMMON_ID, ActivityConstant.SEC_COMMON_ID, context.getActivityId());

	MutualFundDetailsOperationResponse mutualDetailsOperationResponse = new MutualFundDetailsOperationResponse();

	/*
	 * ---------- Get the Mutual Fund Detail Service Request to set Context and AssetClass property -------------
	 */
	MutualFundDetailsServiceRequest mutualFundDetailsServiceRequest = new MutualFundDetailsServiceRequest();

	mutualFundDetailsServiceRequest.setContext(context);
	mutualFundDetailsServiceRequest.setAssetClass(request.getAsssetClass());

	/*
	 * ---------- Get the Mutual Fund Detail Service Response to call Retrieve MutualFund List through a common service i.e. investment Account
	 * Detail Service -------------
	 */

	MutualFundDetailsServiceResponse mutualFundDetailsServiceResponse = null;

	mutualFundDetailsServiceResponse = investmentAccountDetailsService.retrieveMutualFundListByAssetClass(mutualFundDetailsServiceRequest);

	List<InvestmentAccountDTO> investmentAccountDTOList = new ArrayList<InvestmentAccountDTO>();

	if (mutualFundDetailsServiceResponse != null) {

	    investmentAccountDTOList = mutualFundDetailsServiceResponse.getInvestmentAccountDTOList();

	    /*
	     * ---- To set Asset Type Name i.e. Mutual Fund, in investmentDTOList to make it available in JSON Response
	     */
	    if (investmentAccountDTOList != null && investmentAccountDTOList.size() > 0) {
		for (InvestmentAccountDTO invAccDTOList : investmentAccountDTOList) {
		    invAccDTOList.setAssetTypeName(request.getAsssetClass());
		}
	    }
	}

	mutualDetailsOperationResponse.setInvestmentAccountDTOList(investmentAccountDTOList);

	return mutualDetailsOperationResponse;
    }

    public InvestmentAccountDetailsService getInvestmentAccountDetailsService() {
	return investmentAccountDetailsService;
    }

    public void setInvestmentAccountDetailsService(InvestmentAccountDetailsService investmentAccountDetailsService) {
	this.investmentAccountDetailsService = investmentAccountDetailsService;
    }

}
