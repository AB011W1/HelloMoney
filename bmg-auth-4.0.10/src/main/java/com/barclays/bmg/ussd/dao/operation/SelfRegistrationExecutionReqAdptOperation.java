/*
 * Copyright (c) 2013 Barclays Bank Plc, All Rights Reserved.
 *
 * This code is confidential to Barclays Bank Plc and shall not be disclosed
 * outside the Bank without the prior written permission of the Director of
 * CIO
 *
 * In the event that such disclosure is permitted the code shall not be copied
 * or distributed other than on a need-to-know basis and any recipients may be
 * required to sign a confidentiality undertaking in favour of Barclays Bank
 * Plc.
 */
package com.barclays.bmg.ussd.dao.operation;

import com.barclays.bem.BEMServiceHeader.OverrideDetails;
import com.barclays.bem.SearchIndividualCustByAcct.SearchIndividualCustByAcctRequest;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.ussd.dao.adapter.SelfRegistrationExecutionHeaderAdapter;
import com.barclays.bmg.ussd.dao.adapter.SelfRegistrationExecutionPayloadAdapter;

/**
 * * ****************** Revision History ********************** </br> Project Name is <b>bmg-auth-4.0.10</b> </br> The file name is
 * <b>SelfRegistrationExecutionDAOController.java</b> </br> Description is <b>Initial Version</b> </br> Updated Date is <b>May 22, 2013</b> </br>
 * ***********************************************************
 *
 * @author E20043104
 *
 */
public class SelfRegistrationExecutionReqAdptOperation {

	/**
	 * The instance variable for selfRegistrationExecutionHeaderAdapter of type SelfRegistrationExecutionHeaderAdapter
	 */
	private SelfRegistrationExecutionHeaderAdapter selfRegistrationExecutionHeaderAdapter;
	/**
	 * The instance variable for selfRegistrationExecutionPayloadAdapter of type SelfRegistrationExecutionPayloadAdapter
	 */
	private SelfRegistrationExecutionPayloadAdapter selfRegistrationExecutionPayloadAdapter;

	/**
	 * Setter for SelfRegistrationExecutionHeaderAdapter
	 *
	 * @param SelfRegistrationExecutionHeaderAdapter
	 * @return void
	 */
	public void setSelfRegistrationExecutionHeaderAdapter(SelfRegistrationExecutionHeaderAdapter selfRegistrationExecutionHeaderAdapter) {
		this.selfRegistrationExecutionHeaderAdapter = selfRegistrationExecutionHeaderAdapter;
	}

	/**
	 * Setter for SelfRegistrationExecutionPayloadAdapter
	 *
	 * @param SelfRegistrationExecutionPayloadAdapter
	 * @return void
	 */
	public void setSelfRegistrationExecutionPayloadAdapter(SelfRegistrationExecutionPayloadAdapter selfRegistrationExecutionPayloadAdapter) {
		this.selfRegistrationExecutionPayloadAdapter = selfRegistrationExecutionPayloadAdapter;
	}

	/**
	 * This method adaptRequestForRegistrationExecution has the purpose to adapt the request for registration execution.
	 *
	 * @param WorkContext
	 * @return SelfRegistrationInitServiceRequest
	 */
	public final SearchIndividualCustByAcctRequest adaptRequestForRegistrationExecution(final WorkContext context) {

		SearchIndividualCustByAcctRequest searchIndividualCustByAcctRequest = new SearchIndividualCustByAcctRequest();
		searchIndividualCustByAcctRequest.setRequestHeader(selfRegistrationExecutionHeaderAdapter.buildAuthReqHeader(context));
		searchIndividualCustByAcctRequest.setIndividuaCustomerSearchInfo(selfRegistrationExecutionPayloadAdapter.adaptPayload(context));
		OverrideDetails[] overrideDetails=searchIndividualCustByAcctRequest.getRequestHeader().getOverrideList();
		if(overrideDetails!=null){
			OverrideDetails OverrideDetails=overrideDetails[0];
			if(OverrideDetails.getCode().equals("GHIPSS")){
				searchIndividualCustByAcctRequest.setSenderDetails(selfRegistrationExecutionPayloadAdapter.adaptPayloadTransactionAccount(context));
				searchIndividualCustByAcctRequest.setTransactionPurpose("credit");
				searchIndividualCustByAcctRequest.setRemarks("Name Query");
			}
		}
		return searchIndividualCustByAcctRequest;
	}
}
