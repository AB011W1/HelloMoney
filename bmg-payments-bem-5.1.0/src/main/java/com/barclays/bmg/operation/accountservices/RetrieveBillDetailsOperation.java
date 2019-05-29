package com.barclays.bmg.operation.accountservices;

import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.operation.payments.BMBPaymentsOperation;
import com.barclays.bmg.service.RetrieveBillDetailsService;
import com.barclays.bmg.service.request.RetrieveBillDetailsServiceRequest;
import com.barclays.bmg.service.response.RetrieveBillDetailsServiceResponse;

public class RetrieveBillDetailsOperation extends BMBPaymentsOperation {

	private RetrieveBillDetailsService retrieveBillDetailsService;

    /**
     * Returns Bill Details based on Control Number.
     *
     * @param request
     * @return
     */
    public RetrieveBillDetailsServiceResponse getBillDetails(RetrieveBillDetailsServiceRequest request) {
    	loadParameters(request.getContext(), ActivityConstant.COMMON_ID, ActivityConstant.SEC_COMMON_ID);
    	return retrieveBillDetailsService.retreiveBillDetails(request);
    }

	/**
	 * @return the retrieveBillDetailsService
	 */
	public RetrieveBillDetailsService getRetrieveBillDetailsService() {
		return retrieveBillDetailsService;
	}

	/**
	 * @param retrieveBillDetailsService the retrieveBillDetailsService to set
	 */
	public void setRetrieveBillDetailsService(
			RetrieveBillDetailsService retrieveBillDetailsService) {
		this.retrieveBillDetailsService = retrieveBillDetailsService;
	}

}
