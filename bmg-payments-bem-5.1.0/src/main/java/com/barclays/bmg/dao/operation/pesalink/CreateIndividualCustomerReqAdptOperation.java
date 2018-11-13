package com.barclays.bmg.dao.operation.pesalink;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.barclays.bem.BEMServiceHeader.RoutingIndicator;
import com.barclays.bem.BEMServiceHeader.ServiceContext;
import com.barclays.bem.CreateIndividualCustomer.CreateIndividualCustomerRequest;
import com.barclays.bem.FundTransferResponseStatus.FundTransferResponseStatus;
import com.barclays.bem.MakeDomesticDemandDraftByAccount.MakeDomesticDemandDraftByAccountResponse;
import com.barclays.bem.RetrieveIndividualCustCardList.RetrieveIndividualCustCardListRequest;
import com.barclays.bem.SearchIndividualCustInformation.SearchIndividualCustomerInformationRequest;
import com.barclays.bmg.constants.CommonConstants;
import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.dao.adapter.pesalink.CreateIndividualCustomerHeaderAdapter;
import com.barclays.bmg.dao.adapter.pesalink.CreateIndividualCustomerPayloadAdapter;
import com.barclays.bmg.dao.adapter.pesalink.SearchIndividualCustforDeDupCheckHeaderAdapter;
import com.barclays.bmg.dao.adapter.pesalink.SearchIndividualCustforDeDupCheckPayloadAdapter;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.dao.operation.payments.BMBRetailTxnRespAdptOperation;
import com.barclays.bmg.service.request.bankdraft.PurchaseBankDraftServiceRequest;
import com.barclays.bmg.service.response.bankdraft.PurchaseBankDraftServiceResponse;
import com.barclays.bmg.ussd.dao.adapter.RetrieveindividualCustCardListHeaderAdapter;
import com.barclays.bmg.ussd.dao.adapter.RetrieveindividualCustCardListPayloadAdapter;

public class CreateIndividualCustomerReqAdptOperation  {


	private CreateIndividualCustomerHeaderAdapter createIndividualCustomerHeaderAdapter;
    private CreateIndividualCustomerPayloadAdapter createIndividualCustomerPayloadAdapter;

    //TODO Request type from BEM stub.jar
	public CreateIndividualCustomerRequest adaptRequest(WorkContext workContext){

		CreateIndividualCustomerRequest request =new CreateIndividualCustomerRequest();
		request.setRequestHeader(createIndividualCustomerHeaderAdapter.buildRequestHeader(workContext));

		//TODO change implementation of  adaptPayLoad
		request.setCustomerInfo(createIndividualCustomerPayloadAdapter.customerInfoAdaptPayload(workContext));
		request.setCustomerDetails(createIndividualCustomerPayloadAdapter.CustomerDetailsAdaptPayload(workContext));

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
