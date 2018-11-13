package com.barclays.bmg.dao.processing.bankdraft;

import com.barclays.bem.MakeDomesticDemandDraftByAccount.MakeDomesticDemandDraftByAccountRequest;
import com.barclays.bem.MakeDomesticDemandDraftByAccount.MakeDomesticDemandDraftByAccountResponse;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.processing.Controller;
import com.barclays.bmg.dao.operation.TransmissionOperation;
import com.barclays.bmg.dao.operation.bankdraft.PurchaseBankDraftPayReqAdptOperation;
import com.barclays.bmg.dao.operation.bankdraft.PurchaseBankDraftPayRespAdptOperation;
import com.barclays.bmg.service.response.bankdraft.PurchaseBankDraftServiceResponse;

public class PurchaseBankDraftDAOController implements Controller {

	private PurchaseBankDraftPayReqAdptOperation bankDraftPayReqAdptOperation;
	private TransmissionOperation transmissionOperation;
	private PurchaseBankDraftPayRespAdptOperation bankDraftPayRespAdptOperation;

	@Override
	public Object handleRequest(WorkContext workContext) throws Exception {

		MakeDomesticDemandDraftByAccountRequest request = bankDraftPayReqAdptOperation.createBankDraftRequest(workContext);
		MakeDomesticDemandDraftByAccountResponse response = (MakeDomesticDemandDraftByAccountResponse)transmissionOperation.sendAndReceive(request);
		PurchaseBankDraftServiceResponse purchaseBankDraftServiceResponse = bankDraftPayRespAdptOperation.createPurchaseBankDraftServiceResponse(workContext,response);

		return purchaseBankDraftServiceResponse;
	}

	public PurchaseBankDraftPayReqAdptOperation getBankDraftPayReqAdptOperation() {
		return bankDraftPayReqAdptOperation;
	}

	public void setBankDraftPayReqAdptOperation(
			PurchaseBankDraftPayReqAdptOperation bankDraftPayReqAdptOperation) {
		this.bankDraftPayReqAdptOperation = bankDraftPayReqAdptOperation;
	}

	public TransmissionOperation getTransmissionOperation() {
		return transmissionOperation;
	}

	public void setTransmissionOperation(TransmissionOperation transmissionOperation) {
		this.transmissionOperation = transmissionOperation;
	}

	public PurchaseBankDraftPayRespAdptOperation getBankDraftPayRespAdptOperation() {
		return bankDraftPayRespAdptOperation;
	}

	public void setBankDraftPayRespAdptOperation(
			PurchaseBankDraftPayRespAdptOperation bankDraftPayRespAdptOperation) {
		this.bankDraftPayRespAdptOperation = bankDraftPayRespAdptOperation;
	}



}
