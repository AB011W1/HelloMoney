package com.barclays.bmg.operation.accountdetails;

import java.util.ArrayList;
import java.util.List;

import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dto.InvestmentAccountDTO;
import com.barclays.bmg.operation.BMBCommonOperation;
import com.barclays.bmg.operation.accountdetails.request.BondDetailsOperationRequest;
import com.barclays.bmg.operation.accountdetails.response.BondDetailsOperationResponse;
import com.barclays.bmg.service.accountdetails.InvestmentAccountDetailsService;
import com.barclays.bmg.service.accountdetails.request.BondDetailsServiceRequest;
import com.barclays.bmg.service.accountdetails.response.BondDetailsServiceResponse;

public class BondDetailsOperation extends BMBCommonOperation {

    private InvestmentAccountDetailsService investmentAccountDetailsService;

    /**
     * 1. Loads the system parameter into context 2. Retrieve the Bond Account Details. BEM request
     */

    public BondDetailsOperationResponse retrieveBondListByAssetClass(BondDetailsOperationRequest request) {

	Context context = request.getContext();

	loadParameters(context, ActivityConstant.COMMON_ID, ActivityConstant.SEC_COMMON_ID, context.getActivityId());

	BondDetailsOperationResponse bondDetailsOperationResponse = new BondDetailsOperationResponse();

	/*
	 * ---------- Get the Bond Detail Service Request to set Context and AssetClass property -------------
	 */
	BondDetailsServiceRequest bondDetailsServiceRequest = new BondDetailsServiceRequest();

	bondDetailsServiceRequest.setContext(context);
	bondDetailsServiceRequest.setAssetClass(request.getAsssetClass());

	/*
	 * ---------- Get the Bond Detail Service Response to call Retrieve Bond List through a common service i.e. investment Account Detail Service
	 * -------------
	 */

	BondDetailsServiceResponse bondDetailsServiceResponse = null;

	bondDetailsServiceResponse = investmentAccountDetailsService.retrieveBondListByAssetClass(bondDetailsServiceRequest);

	List<InvestmentAccountDTO> investmentAccountDTOList = new ArrayList<InvestmentAccountDTO>();

	if (bondDetailsServiceResponse != null) {

	    investmentAccountDTOList = bondDetailsServiceResponse.getInvestmentAccountDTOList();

	    // ---- To set Asset Type Name i.e. Bond, in investmentDTOList to
	    // make it available in JSON Response
	    if (investmentAccountDTOList != null && investmentAccountDTOList.size() > 0) {
		for (InvestmentAccountDTO invAccDTOList : investmentAccountDTOList) {
		    invAccDTOList.setAssetTypeName(request.getAsssetClass());
		}
	    }
	}
	bondDetailsOperationResponse.setInvestmentAccountDTOList(investmentAccountDTOList);

	return bondDetailsOperationResponse;
    }

    public InvestmentAccountDetailsService getInvestmentAccountDetailsService() {
	return investmentAccountDetailsService;
    }

    public void setInvestmentAccountDetailsService(InvestmentAccountDetailsService investmentAccountDetailsService) {
	this.investmentAccountDetailsService = investmentAccountDetailsService;
    }

}
