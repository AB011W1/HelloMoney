package com.barclays.bmg.json.model.builder.cashsend;

import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.context.BMBContextHolder;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.CASAAccountDTO;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.json.model.AmountJSONModel;
import com.barclays.bmg.json.model.CasaAccountJSONModel;
import com.barclays.bmg.json.model.CashSendOneTimeValidateJSONModel;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadHeader;
import com.barclays.bmg.operation.response.fundtransfer.external.FormValidateOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.external.GetSelectedAccountOperationResponse;
import com.barclays.bmg.utils.BMGFormatUtility;
public class CashSendOneTimeValidateJsonBldr extends
		BMBMultipleResponseJSONBuilder implements BMBJSONBuilder {


	@Override
	public Object createMultipleJSONResponse(
			ResponseContext... responseContexts) {
		BMBPayloadHeader bmbPayloadHeader = null;
		BMBPayload bmbPayload = null;
		for (ResponseContext response : responseContexts) {
			if (response != null && !response.isSuccess()) {
				bmbPayloadHeader = createHeader(
						response,
						ResponseIdConstants.CASH_SEND_VALIDATE_RESPONSE_ID);
				break;
			}
		}

		if (bmbPayloadHeader != null) {
			bmbPayload = new BMBPayload(bmbPayloadHeader);
		} else {
			bmbPayload = new BMBPayload(createHeader(responseContexts[0],
					ResponseIdConstants.CASH_SEND_VALIDATE_RESPONSE_ID));
			populatePayLoad(bmbPayload, responseContexts);
		}

		return bmbPayload;
	}

	protected void populatePayLoad(BMBPayload bmbPayload,
			ResponseContext... responseContexts) {

		CashSendOneTimeValidateJSONModel responseModel = new CashSendOneTimeValidateJSONModel();
		GetSelectedAccountOperationResponse getSelectedAccountOperationResponse = (GetSelectedAccountOperationResponse) responseContexts[0];

		FormValidateOperationResponse formValidateOperationResponse = (FormValidateOperationResponse) responseContexts[1];





		CustomerAccountDTO srcAcct = getSelectedAccountOperationResponse.getSelectedAcct();

		responseModel.setFrmAct(getCASAAccount(srcAcct, getSelectedAccountOperationResponse));


		if (formValidateOperationResponse != null
				&& formValidateOperationResponse.isSuccess()) {


			AmountJSONModel txnAmt = new AmountJSONModel();
			txnAmt.setAmt(BMGFormatUtility
					.getFormattedAmount(formValidateOperationResponse
							.getTxnAmt().getAmount()));
			txnAmt.setCurr(formValidateOperationResponse.getTxnAmt()
					.getCurrency());
			responseModel.setTxnAmt(txnAmt);

			responseModel.setTxnRefNo(formValidateOperationResponse.getContext().getOrgRefNo());
			responseModel.setMobileNo(formValidateOperationResponse.getMobileNo());

			//CPB change 18/04/2017
			AmountJSONModel jsonModel= new AmountJSONModel();
			if(formValidateOperationResponse.getTranFee()!=null){
				jsonModel.setCurr(formValidateOperationResponse.getTranFee().getCurrency()); // jsonModel.setCurr("KES");
				jsonModel.setAmt(formValidateOperationResponse.getTranFee().getAmount().toString()); // jsonModel.setAmt("15.00");
				responseModel.setTransactionFeeAmount(jsonModel); //formValidateOperationResponse.getTranFee()
				responseModel.setTaxAmount(formValidateOperationResponse.getTaxAmount());
			}
		}

		bmbPayload.setPayData(responseModel);
	}



	private CasaAccountJSONModel getCASAAccount(
			CustomerAccountDTO account, ResponseContext response) {
		CasaAccountJSONModel accountJSONModel = null;
		if (account != null) {
				CASAAccountDTO casaAcct = (CASAAccountDTO) account;
				accountJSONModel = new CasaAccountJSONModel(
						casaAcct);
				// CHECK added masking
				/*accountJSONModel.setMkdActNo(getMaskedAccountNumber(null
								, casaAcct.getAccountNumber()));
*/

				if(branchCodeCountryList.contains(BMBContextHolder.getContext().getBusinessId())){
					accountJSONModel.setMkdActNo(getMaskedAccountNumber(casaAcct.getBranchCode(), casaAcct.getAccountNumber()));
				    }
				    else {
					accountJSONModel.setMkdActNo(getMaskedAccountNumber(null, casaAcct.getAccountNumber()));
				    }

				accountJSONModel.setActNo(getCorrelationId(casaAcct.getAccountNumber(), response));
				//accountJSONModel.setActNo("6000021006");

			}
		return accountJSONModel;

	}


}