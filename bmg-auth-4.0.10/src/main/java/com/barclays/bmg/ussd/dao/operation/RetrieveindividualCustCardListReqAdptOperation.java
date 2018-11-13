package com.barclays.bmg.ussd.dao.operation;

import com.barclays.bem.BEMServiceHeader.RoutingIndicator;
import com.barclays.bem.RetrieveIndividualCustCardList.RetrieveIndividualCustCardListRequest;
import com.barclays.bmg.constants.CommonConstants;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.ussd.dao.adapter.RetrieveindividualCustCardListHeaderAdapter;
import com.barclays.bmg.ussd.dao.adapter.RetrieveindividualCustCardListPayloadAdapter;

public class RetrieveindividualCustCardListReqAdptOperation {
	private RetrieveindividualCustCardListHeaderAdapter retrieveindividualCustCardListHeaderAdapter;

    private RetrieveindividualCustCardListPayloadAdapter retrieveindividualCustCardListPayloadAdapter;



    public void setRetrieveindividualCustCardListHeaderAdapter(
			RetrieveindividualCustCardListHeaderAdapter retrieveindividualCustCardListHeaderAdapter) {
		this.retrieveindividualCustCardListHeaderAdapter = retrieveindividualCustCardListHeaderAdapter;
	}



	public void setRetrieveindividualCustCardListPayloadAdapter(
			RetrieveindividualCustCardListPayloadAdapter retrieveindividualCustCardListPayloadAdapter) {
		this.retrieveindividualCustCardListPayloadAdapter = retrieveindividualCustCardListPayloadAdapter;
	}

	public RetrieveindividualCustCardListHeaderAdapter getRetrieveindividualCustCardListHeaderAdapter() {
		return retrieveindividualCustCardListHeaderAdapter;
	}



	public RetrieveindividualCustCardListPayloadAdapter getRetrieveindividualCustCardListPayloadAdapter() {
		return retrieveindividualCustCardListPayloadAdapter;
	}
//TODO Request type from BEM stub.jar
	public RetrieveIndividualCustCardListRequest adaptRequestForIndvCustCardList(WorkContext workContext){

		RetrieveIndividualCustCardListRequest request =
					new RetrieveIndividualCustCardListRequest();
		request.setRequestHeader(retrieveindividualCustCardListHeaderAdapter.buildRequestHeader(workContext));

		RoutingIndicator routInd = new RoutingIndicator();
	    routInd.setIndicator(CommonConstants.SPRW_ROUTING_INDICATOR);
	    request.getRequestHeader().setRoutingIndicator(routInd);

		//TODO change implementation of  adaptPayLoad
		request.setCustomerCardList(retrieveindividualCustCardListPayloadAdapter.adaptPayLoad(workContext));
		return request;
	}




}
