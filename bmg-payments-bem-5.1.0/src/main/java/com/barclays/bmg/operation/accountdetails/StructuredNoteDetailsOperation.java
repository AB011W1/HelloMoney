package com.barclays.bmg.operation.accountdetails;

import java.util.ArrayList;
import java.util.List;

import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dto.InvestmentAccountDTO;
import com.barclays.bmg.operation.BMBCommonOperation;
import com.barclays.bmg.operation.accountdetails.request.StructuredNoteDetailsOperationRequest;
import com.barclays.bmg.operation.accountdetails.response.StructuredNoteDetailsOperationResponse;
import com.barclays.bmg.service.accountdetails.InvestmentAccountDetailsService;
import com.barclays.bmg.service.accountdetails.request.StructuredNoteDetailsServiceRequest;
import com.barclays.bmg.service.accountdetails.response.StructuredNoteDetailsServiceResponse;

public class StructuredNoteDetailsOperation extends BMBCommonOperation {

    private InvestmentAccountDetailsService investmentAccountDetailsService;

    /**
     * 1. Loads the system parameter into context 2. Retrieve the Structured Note Details
     * 
     */

    public StructuredNoteDetailsOperationResponse retrieveStructureNoteListByAssetClass(StructuredNoteDetailsOperationRequest request) {

	Context context = request.getContext();

	loadParameters(context, ActivityConstant.COMMON_ID, ActivityConstant.SEC_COMMON_ID, context.getActivityId());

	/*
	 * Get Structured Note Operation object to set and return investmentAccountDTOList
	 */
	StructuredNoteDetailsOperationResponse strutNoteDetailsOperationResponse = new StructuredNoteDetailsOperationResponse();

	/*
	 * ---------- Get the Structured Note Detail Service Request to set Context and AssetClass property -------------
	 */
	StructuredNoteDetailsServiceRequest StrutdNoteDetailsServiceRequest = new StructuredNoteDetailsServiceRequest();

	StrutdNoteDetailsServiceRequest.setContext(context);
	StrutdNoteDetailsServiceRequest.setAssetClass(request.getAsssetClass());

	/*
	 * ---------- Get the Structured Note Detail Service Response to Retrieve Structured Note List through a common service i.e. investment
	 * Account Detail Service -------------
	 */

	StructuredNoteDetailsServiceResponse StrutNoteDetailsServiceResponse = null;

	StrutNoteDetailsServiceResponse = investmentAccountDetailsService.retrieveStructuredNoteListByAssetClass(StrutdNoteDetailsServiceRequest);

	List<InvestmentAccountDTO> investmentAccountDTOList = new ArrayList<InvestmentAccountDTO>();

	if (StrutNoteDetailsServiceResponse != null) {

	    investmentAccountDTOList = StrutNoteDetailsServiceResponse.getInvestmentAccountDTOList();

	    // ---- To set Asset Type Name i.e. Structured Note, in
	    // investmentDTOList to make it available in JSON Response
	    if (investmentAccountDTOList != null && investmentAccountDTOList.size() > 0) {
		for (InvestmentAccountDTO invAccDTOList : investmentAccountDTOList) {
		    invAccDTOList.setAssetTypeName(request.getAsssetClass());
		}
	    }

	}
	strutNoteDetailsOperationResponse.setInvestmentAccountDTOList(investmentAccountDTOList);

	return strutNoteDetailsOperationResponse;
    }

    public InvestmentAccountDetailsService getInvestmentAccountDetailsService() {
	return investmentAccountDetailsService;
    }

    public void setInvestmentAccountDetailsService(InvestmentAccountDetailsService investmentAccountDetailsService) {
	this.investmentAccountDetailsService = investmentAccountDetailsService;
    }

}
