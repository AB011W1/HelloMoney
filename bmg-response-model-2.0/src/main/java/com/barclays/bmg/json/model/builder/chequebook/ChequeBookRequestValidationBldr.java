package com.barclays.bmg.json.model.builder.chequebook;


import com.barclays.bmg.chequebook.operation.response.ChequeBookValidationOperationResponse;
import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.context.BMBContextHolder;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.CASAAccountDTO;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.json.model.CasaAccountJSONModel;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.model.chequebook.ChequeBookValidationJsonModel;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadHeader;
import com.barclays.bmg.operation.response.fundtransfer.external.GetSelectedAccountOperationResponse;

public class ChequeBookRequestValidationBldr extends BMBMultipleResponseJSONBuilder implements BMBJSONBuilder{

	@Override
	public Object createMultipleJSONResponse(
			ResponseContext... responseContexts) {
		BMBPayloadHeader bmbPayloadHeader = null;
		BMBPayload bmbPayload = null;
		for(ResponseContext response: responseContexts){
			if(response!=null && !response.isSuccess()){
				bmbPayloadHeader = createHeader(response);
				break;
			}
		}

		if(bmbPayloadHeader!=null){
			bmbPayload = new BMBPayload(bmbPayloadHeader);
		}else{
			bmbPayload = new BMBPayload(createHeader(responseContexts[0]));
			populatePayLoad(bmbPayload, responseContexts);
		}

		return bmbPayload;
	}

	protected BMBPayloadHeader createHeader(ResponseContext response) {

		BMBPayloadHeader header = new BMBPayloadHeader();
		if (response != null && response.isSuccess()) {
			header.setResCde(ResponseCodeConstants.SUCCESS_RES_CODE);
			header.setResMsg("");
		} else  if(response != null){
			header.setResCde(response.getResCde());
			header.setResMsg(response.getResMsg());
		}
		header.setSerVer(ResponseIdConstants.RESPONSE_SERVICE_VERSION);
		header.setResId(ResponseIdConstants.CHEQUE_BOOK_CONFIRM);

		return header;
	}

	protected void populatePayLoad(BMBPayload bmbPayload,
			ResponseContext... responses
			) {

		ChequeBookValidationJsonModel responseModel = new ChequeBookValidationJsonModel();

		GetSelectedAccountOperationResponse getSelectedAccountOperationResponse = (GetSelectedAccountOperationResponse)responses[0];

		responseModel.setSrcAct(getCASAAccount(getSelectedAccountOperationResponse.getSelectedAcct(), getSelectedAccountOperationResponse));


		ChequeBookValidationOperationResponse chequeBookValidationOperationResponse =
												(ChequeBookValidationOperationResponse)responses[1];

		responseModel.setBkSize(chequeBookValidationOperationResponse.getBkSize());
		responseModel.setBkTyp(chequeBookValidationOperationResponse.getBkTyp());

		responseModel.setTxnRefNo(chequeBookValidationOperationResponse.getTxnRefNo());
		responseModel.setBrnCde(chequeBookValidationOperationResponse.getBrnCde());
		responseModel.setBrnNam(chequeBookValidationOperationResponse.getBrnNam());

		bmbPayload.setPayData(responseModel);

	}

	private CasaAccountJSONModel getCASAAccount(
			CustomerAccountDTO accountDTO, ResponseContext response) {


				CASAAccountDTO account = (CASAAccountDTO) accountDTO;
				CasaAccountJSONModel accountJSONModel = new CasaAccountJSONModel(
						account);
				// CHECK added masking
				/*accountJSONModel.setMkdActNo(getMaskedAccountNumber(account.getBranchCode()
								, account.getAccountNumber()));
				*/
				 if(branchCodeCountryList.contains(BMBContextHolder.getContext().getBusinessId())){
					accountJSONModel.setMkdActNo(getMaskedAccountNumber(account.getBranchCode(), account.getAccountNumber()));
				    }
				    else {
					accountJSONModel.setMkdActNo(getMaskedAccountNumber(null, account.getAccountNumber()));
				    }
				accountJSONModel.setActNo(getCorrelationId(account.getAccountNumber(), response));
				return accountJSONModel;

	}


}
