package com.barclays.bmg.dao.operation.fundtransfer.domestic;

import com.barclays.bem.MakeDomesticFundTransfer.MakeDomesticFundTransferRequest;
import com.barclays.bmg.constants.ServiceIdConstants;
import com.barclays.bmg.dao.adapter.fundtransfer.domestic.DomesticFundTransferPayloadAdapter;
import com.barclays.bmg.dao.adapter.request.AbstractReqAdptOperation;
import com.barclays.bmg.dao.core.context.WorkContext;

public class DomesticFundTransferReqAdptOperation extends AbstractReqAdptOperation {
	private DomesticFundTransferPayloadAdapter domesticFundTransferPayloadAdapter;

	public MakeDomesticFundTransferRequest adaptRequestForFundTransfer(WorkContext workContext){

		MakeDomesticFundTransferRequest fundTransferRequest = new MakeDomesticFundTransferRequest();

		fundTransferRequest.setRequestHeader(buildRequestHeader(workContext, ServiceIdConstants.SERVICE_MAKE_DOMESTIC_FUND_TRANSFER));
		fundTransferRequest.setDomesticFundTransferInfo(domesticFundTransferPayloadAdapter.adaptPayload(workContext));

		return fundTransferRequest;
	}

	public DomesticFundTransferPayloadAdapter getDomesticFundTransferPayloadAdapter() {
		return domesticFundTransferPayloadAdapter;
	}

	public void setDomesticFundTransferPayloadAdapter(
			DomesticFundTransferPayloadAdapter domesticFundTransferPayloadAdapter) {
		this.domesticFundTransferPayloadAdapter = domesticFundTransferPayloadAdapter;
	}

}
