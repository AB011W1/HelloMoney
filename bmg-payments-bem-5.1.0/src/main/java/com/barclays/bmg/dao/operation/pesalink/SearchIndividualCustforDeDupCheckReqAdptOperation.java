package com.barclays.bmg.dao.operation.pesalink;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.barclays.bem.BEMServiceHeader.RoutingIndicator;
import com.barclays.bem.BEMServiceHeader.ServiceContext;
import com.barclays.bem.FundTransferResponseStatus.FundTransferResponseStatus;
import com.barclays.bem.MakeDomesticDemandDraftByAccount.MakeDomesticDemandDraftByAccountResponse;
import com.barclays.bem.RetrieveIndividualCustCardList.RetrieveIndividualCustCardListRequest;
import com.barclays.bem.SearchIndividualCustInformation.SearchIndividualCustomerInformationRequest;
import com.barclays.bmg.constants.CommonConstants;
import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.dao.adapter.pesalink.SearchIndividualCustforDeDupCheckHeaderAdapter;
import com.barclays.bmg.dao.adapter.pesalink.SearchIndividualCustforDeDupCheckPayloadAdapter;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.dao.operation.payments.BMBRetailTxnRespAdptOperation;
import com.barclays.bmg.service.request.bankdraft.PurchaseBankDraftServiceRequest;
import com.barclays.bmg.service.response.bankdraft.PurchaseBankDraftServiceResponse;
import com.barclays.bmg.ussd.dao.adapter.RetrieveindividualCustCardListHeaderAdapter;
import com.barclays.bmg.ussd.dao.adapter.RetrieveindividualCustCardListPayloadAdapter;

public class SearchIndividualCustforDeDupCheckReqAdptOperation  {


	private SearchIndividualCustforDeDupCheckHeaderAdapter searchIndividualCustforDeDupCheckHeaderAdapter;
    private SearchIndividualCustforDeDupCheckPayloadAdapter searchIndividualCustforDeDupCheckPayloadAdapter;

    //TODO Request type from BEM stub.jar
	public SearchIndividualCustomerInformationRequest adaptRequest(WorkContext workContext){

		SearchIndividualCustomerInformationRequest request =new SearchIndividualCustomerInformationRequest();
		request.setRequestHeader(searchIndividualCustforDeDupCheckHeaderAdapter.buildRequestHeader(workContext));

		//TODO change implementation of  adaptPayLoad
		request.setIndividuaCustomerSearch(searchIndividualCustforDeDupCheckPayloadAdapter.adaptPayload(workContext));
		return request;

	}

	public SearchIndividualCustforDeDupCheckHeaderAdapter getSearchIndividualCustforDeDupCheckHeaderAdapter() {
		return searchIndividualCustforDeDupCheckHeaderAdapter;
	}

	public void setSearchIndividualCustforDeDupCheckHeaderAdapter(
			SearchIndividualCustforDeDupCheckHeaderAdapter searchIndividualCustforDeDupCheckHeaderAdapter) {
		this.searchIndividualCustforDeDupCheckHeaderAdapter = searchIndividualCustforDeDupCheckHeaderAdapter;
	}

	public SearchIndividualCustforDeDupCheckPayloadAdapter getSearchIndividualCustforDeDupCheckPayloadAdapter() {
		return searchIndividualCustforDeDupCheckPayloadAdapter;
	}

	public void setSearchIndividualCustforDeDupCheckPayloadAdapter(
			SearchIndividualCustforDeDupCheckPayloadAdapter searchIndividualCustforDeDupCheckPayloadAdapter) {
		this.searchIndividualCustforDeDupCheckPayloadAdapter = searchIndividualCustforDeDupCheckPayloadAdapter;
	}



}
