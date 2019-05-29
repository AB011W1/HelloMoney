package com.barclays.bmg.dao.operation.pesalink;

import com.barclays.bem.CreateIndividualCustomer.CreateIndividualCustomerRequest;
import com.barclays.bmg.dao.adapter.pesalink.CreateIndividualCustomerHeaderAdapter;
import com.barclays.bmg.dao.adapter.pesalink.CreateIndividualCustomerPayloadAdapter;
import com.barclays.bmg.dao.core.context.WorkContext;

public class CreateIndividualCustomerReqAdptOperation  {


	private CreateIndividualCustomerHeaderAdapter createIndividualCustomerHeaderAdapter;
    private CreateIndividualCustomerPayloadAdapter createIndividualCustomerPayloadAdapter;

    //TODO Request type from BEM stub.jar
	public CreateIndividualCustomerRequest adaptRequest(WorkContext workContext){

		CreateIndividualCustomerRequest request =new CreateIndividualCustomerRequest();
		request.setRequestHeader(createIndividualCustomerHeaderAdapter.buildRequestHeader(workContext));

		//TODO change implementation of  adaptPayLoad
		request.setCustomerInfo(createIndividualCustomerPayloadAdapter.customerInfoAdaptPayload(workContext));
		request.setCustomerDetails(createIndividualCustomerPayloadAdapter.customerDetailsAdaptPayload(workContext));

		return request;

	}

	public CreateIndividualCustomerHeaderAdapter getCreateIndividualCustomerHeaderAdapter() {
		return createIndividualCustomerHeaderAdapter;
	}

	public void setCreateIndividualCustomerHeaderAdapter(
			CreateIndividualCustomerHeaderAdapter createIndividualCustomerHeaderAdapter) {
		this.createIndividualCustomerHeaderAdapter = createIndividualCustomerHeaderAdapter;
	}

	public CreateIndividualCustomerPayloadAdapter getCreateIndividualCustomerPayloadAdapter() {
		return createIndividualCustomerPayloadAdapter;
	}

	public void setCreateIndividualCustomerPayloadAdapter(
			CreateIndividualCustomerPayloadAdapter createIndividualCustomerPayloadAdapter) {
		this.createIndividualCustomerPayloadAdapter = createIndividualCustomerPayloadAdapter;
	}


}
