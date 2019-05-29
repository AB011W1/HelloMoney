package com.barclays.bmg.json.model.builder.fundtransfer.own;

import java.util.ArrayList;
import java.util.List;

import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.context.BMBContextHolder;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.CASAAccountDTO;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.json.model.AmountJSONModel;
import com.barclays.bmg.json.model.CasaAccountJSONModel;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.model.fundtransfer.OwnFundTransferInitJSONResponseModel;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadHeader;
import com.barclays.bmg.operation.response.fundtransfer.external.RetrieveAcctListOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.external.TransactionLimitOperationResponse;
import com.barclays.bmg.utils.BMGFormatUtility;

public class OwnFundTransferInitJSONBldr extends BMBMultipleResponseJSONBuilder  {

	@Override
	public Object createMultipleJSONResponse(
			ResponseContext... responseContexts) {
		BMBPayloadHeader bmbPayloadHeader = null;
		BMBPayload bmbPayload = null;
		for(ResponseContext response: responseContexts){
			if(response!=null && !response.isSuccess()){
				bmbPayloadHeader = createHeader(response, ResponseIdConstants.OWN_FUND_TRANSFER_INIT_RESPONSE_ID);
				break;
			}
		}

		if(bmbPayloadHeader!=null){
			bmbPayload = new BMBPayload(bmbPayloadHeader);
		}else{
			bmbPayload = new BMBPayload(createHeader(responseContexts[0],ResponseIdConstants.OWN_FUND_TRANSFER_INIT_RESPONSE_ID));
			populatePayLoad(bmbPayload, responseContexts);
		}

		return bmbPayload;
	}


	protected void populatePayLoad(
			BMBPayload bmbPayload,ResponseContext... responseContexts) {

		OwnFundTransferInitJSONResponseModel responseModel = null;
		RetrieveAcctListOperationResponse srcActLstOperationResponse  = null;
		RetrieveAcctListOperationResponse destActLstOperationResponse  = null;
		TransactionLimitOperationResponse transactionLimitOperationResponse = null;

		int size = 0;
		for(ResponseContext response: responseContexts){
			if(response!=null ){
				size = size +1;
				}
			}

		if(size == 1){
			 destActLstOperationResponse  = (RetrieveAcctListOperationResponse)responseContexts[0];
		} else {
			srcActLstOperationResponse  = (RetrieveAcctListOperationResponse)responseContexts[0];
			destActLstOperationResponse  = (RetrieveAcctListOperationResponse)responseContexts[1];
			transactionLimitOperationResponse = (TransactionLimitOperationResponse)responseContexts[3];
		}

		if(srcActLstOperationResponse != null && destActLstOperationResponse!=null){
			responseModel = new OwnFundTransferInitJSONResponseModel();
			responseModel.setSrcLst(getCASAAccountList(srcActLstOperationResponse.getAcctList(),srcActLstOperationResponse));
			responseModel.setPayLst(getCASAAccountList(destActLstOperationResponse.getAcctList(),destActLstOperationResponse));
			AmountJSONModel txnLmt = null;
			if(transactionLimitOperationResponse.getAValidDailyLimit()!=null){
				txnLmt = new AmountJSONModel();
				txnLmt.setAmt(BMGFormatUtility.getFormattedAmount(transactionLimitOperationResponse.getAValidDailyLimit()));
				txnLmt.setCurr(transactionLimitOperationResponse.getContext().getLocalCurrency());
			}
			responseModel.setTxnLmt(txnLmt);

		} else if(null != destActLstOperationResponse){
			responseModel = new OwnFundTransferInitJSONResponseModel();
			responseModel.setPayLst(getCASAAccountList(destActLstOperationResponse.getAcctList(),destActLstOperationResponse));
		}

		bmbPayload.setPayData(responseModel);
	}

	private List<CasaAccountJSONModel> getCASAAccountList(
			List<? extends CustomerAccountDTO> accounts,ResponseContext response) {

		List<CasaAccountJSONModel> casaAccountList = new ArrayList<CasaAccountJSONModel>();
		if (accounts != null) {
			for (int i = 0; i < accounts.size(); i++) {
				CASAAccountDTO account = (CASAAccountDTO) accounts.get(i);
				CasaAccountJSONModel accountJSONModel = new CasaAccountJSONModel(
						account);
				// CHECK added masking
				/*accountJSONModel.setMkdActNo(getMaskedAccountNumber(account.getBranchCode(),
																		account.getAccountNumber()));


				*/

				 if(branchCodeCountryList.contains(BMBContextHolder.getContext().getBusinessId())){
					accountJSONModel.setMkdActNo(getMaskedAccountNumber(account.getBranchCode(), account.getAccountNumber()));
				    }
				    else {
					accountJSONModel.setMkdActNo(getMaskedAccountNumber(null, account.getAccountNumber()));
				    }
				accountJSONModel.setActNo(getCorrelationId(account.getAccountNumber(), response));
				casaAccountList.add(accountJSONModel);

			}
		}
		return casaAccountList;

	}
}
