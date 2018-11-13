package com.barclays.bmg.json.model.builder.fundtransfer.internal;

import java.util.ArrayList;
import java.util.List;

import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.context.BMBContextHolder;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.dto.CASAAccountDTO;
import com.barclays.bmg.dto.CategorizedPayeeListDTO;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.dto.TransferFacadeDTO;
import com.barclays.bmg.json.model.AmountJSONModel;
import com.barclays.bmg.json.model.CasaAccountJSONModel;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.model.fundtransfer.CategorizedPayeeJSONModel;
import com.barclays.bmg.json.model.fundtransfer.PayeeJSONModel;
import com.barclays.bmg.json.model.internalfundtransfer.InternalFTInitJSONResponseModel;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadHeader;
import com.barclays.bmg.operation.response.fundtransfer.external.RetrieveAcctListOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.external.RetrievePayeeListOperationResponse;

public class InternalFundTransferInitJSONBldr extends BMBMultipleResponseJSONBuilder {

    @Override
    public Object createMultipleJSONResponse(ResponseContext... responseContexts) {
	BMBPayloadHeader bmbPayloadHeader = null;
	BMBPayload bmbPayload = null;
	for (ResponseContext response : responseContexts) {
	    if (response != null && !response.isSuccess()) {
		bmbPayloadHeader = createHeader(response, ResponseIdConstants.INTERNAL_FUND_TRANSFER_INIT_RESPONSE_ID);
		break;
	    }
	}

	if (bmbPayloadHeader != null) {
	    bmbPayload = new BMBPayload(bmbPayloadHeader);
	} else {
	    bmbPayload = new BMBPayload(createHeader(responseContexts[0], ResponseIdConstants.INTERNAL_FUND_TRANSFER_INIT_RESPONSE_ID));
	    populatePayLoad(bmbPayload, responseContexts);
	}

	return bmbPayload;
    }

    protected void populatePayLoad(BMBPayload bmbPayload, ResponseContext... responseContexts) {

	InternalFTInitJSONResponseModel responseModel = null;
	RetrievePayeeListOperationResponse retrievePayeeListOperationResponse = (RetrievePayeeListOperationResponse) responseContexts[0];
	RetrieveAcctListOperationResponse srcActLstOperationResponse = (RetrieveAcctListOperationResponse) responseContexts[1];
	// TransactionLimitOperationResponse transactionLimitOperationResponse = (TransactionLimitOperationResponse)responseContexts[2];
	if (retrievePayeeListOperationResponse != null) {
	    responseModel = new InternalFTInitJSONResponseModel();
	    List<CategorizedPayeeListDTO> categorizedPayeeList = retrievePayeeListOperationResponse.getCategorizedPayeeList();

	    for (CategorizedPayeeListDTO categorizedPayee : categorizedPayeeList) {
		CategorizedPayeeJSONModel categorizedPayeeJSONModel = new CategorizedPayeeJSONModel();
		categorizedPayeeJSONModel.setPayCat(categorizedPayee.getPayeeCategory());

		List<BeneficiaryDTO> payeeList = categorizedPayee.getPayeeList();
		if (payeeList != null) {
		    for (BeneficiaryDTO payee : payeeList) {
			PayeeJSONModel beneficiary = new PayeeJSONModel();
			beneficiary.setDisLbl(payee.getPayeeNickname());
			beneficiary.setActNo(getMaskedAccountNumber(null, payee.getDestinationAccountNumber()));

			TransferFacadeDTO facadeDTO = new TransferFacadeDTO();
			facadeDTO.setBeneficiary(payee);
			facadeDTO.setTransactionFacadeRoutineType(categorizedPayee.getTransactionFacadeRoutineType());

			beneficiary.setTransferFacadeDTO(facadeDTO);
			categorizedPayeeJSONModel.add(beneficiary);
		    }
		}

		responseModel.addCategorizedPayeeBean(categorizedPayeeJSONModel);
	    }
	    responseModel.setSrcLst(getCASAAccountList(srcActLstOperationResponse.getAcctList(), srcActLstOperationResponse));
	    AmountJSONModel txnLmt = null;
	    /*
	     * if(transactionLimitOperationResponse.getAValidDailyLimit()!=null){ txnLmt = new AmountJSONModel();
	     * txnLmt.setAmt(BMGFormatUtility.getFormattedAmount(transactionLimitOperationResponse.getAValidDailyLimit()));
	     * txnLmt.setCurr(transactionLimitOperationResponse.getContext().getLocalCurrency()); }
	     */responseModel.setTxnLmt(txnLmt);
	}
	bmbPayload.setPayData(responseModel);
    }

    private List<CasaAccountJSONModel> getCASAAccountList(List<? extends CustomerAccountDTO> accounts, ResponseContext response) {

	List<CasaAccountJSONModel> casaAccountList = new ArrayList<CasaAccountJSONModel>();
	if (accounts != null) {
	    for (int i = 0; i < accounts.size(); i++) {
		CASAAccountDTO account = (CASAAccountDTO) accounts.get(i);
		CasaAccountJSONModel accountJSONModel = new CasaAccountJSONModel(account);
		// CHECK added masking
		/*
		 * accountJSONModel.setMkdActNo(getMaskedAccountNumber(account.getBranchCode() , account.getAccountNumber()));
		 */

		if (branchCodeCountryList.contains(BMBContextHolder.getContext().getBusinessId())) {
		    accountJSONModel.setMkdActNo(getMaskedAccountNumber(account.getBranchCode(), account.getAccountNumber()));
		} else {
		    accountJSONModel.setMkdActNo(getMaskedAccountNumber(null, account.getAccountNumber()));
		}
		accountJSONModel.setActNo(getCorrelationId(account.getAccountNumber(), response));
		casaAccountList.add(accountJSONModel);

	    }
	}
	return casaAccountList;

    }
}
