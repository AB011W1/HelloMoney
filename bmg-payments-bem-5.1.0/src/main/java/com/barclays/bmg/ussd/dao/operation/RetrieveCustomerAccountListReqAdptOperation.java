package com.barclays.bmg.ussd.dao.operation;

import com.barclays.bem.RetrieveIndividualCustAcctList.RetrieveIndividualCustomerAccountListRequest;
import com.barclays.bem.SearchIndividualCustByAcct.SearchIndividualCustByAcctRequest;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.ussd.dao.adapter.RetrieveCustomerAccountListHeaderAdapter;
import com.barclays.bmg.ussd.dao.adapter.RetrieveCustomerAccountListPayloadAdapter;

public class RetrieveCustomerAccountListReqAdptOperation {

    /**
     * The instance variable for selfRegistrationExecutionHeaderAdapter of type SelfRegistrationExecutionHeaderAdapter
     */
    private RetrieveCustomerAccountListHeaderAdapter retrieveCustomerAccountListHeaderAdapter;
    /**
     * The instance variable for selfRegistrationExecutionPayloadAdapter of type SelfRegistrationExecutionPayloadAdapter
     */
    private RetrieveCustomerAccountListPayloadAdapter retrieveCustomerAccountListPayloadAdapter;

    public void setRetrieveCustomerAccountListHeaderAdapter(RetrieveCustomerAccountListHeaderAdapter retrieveCustomerAccountListHeaderAdapter) {
	this.retrieveCustomerAccountListHeaderAdapter = retrieveCustomerAccountListHeaderAdapter;
    }

    public void setRetrieveCustomerAccountListPayloadAdapter(RetrieveCustomerAccountListPayloadAdapter retrieveCustomerAccountListPayloadAdapter) {
	this.retrieveCustomerAccountListPayloadAdapter = retrieveCustomerAccountListPayloadAdapter;
    }

    /**
     * This method adaptRequestForRegistrationExecution has the purpose to adapt the request for registration execution.
     * 
     * @param WorkContext
     * @return SelfRegistrationInitServiceRequest
     */
    public final SearchIndividualCustByAcctRequest adaptRequestForAccountList(final WorkContext context) {

	RetrieveIndividualCustomerAccountListRequest request = new RetrieveIndividualCustomerAccountListRequest();
	request.setRequestHeader(retrieveCustomerAccountListHeaderAdapter.buildCustomerAccountHeader(context));
	request.setIndividualCustomerInfo(retrieveCustomerAccountListPayloadAdapter.adaptPayload(context));

	return null;
    }
}
