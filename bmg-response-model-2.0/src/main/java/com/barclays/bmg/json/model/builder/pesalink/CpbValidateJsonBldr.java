package com.barclays.bmg.json.model.builder.pesalink;

import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.context.BMBContextHolder;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.CASAAccountDTO;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.json.model.AmountJSONModel;
import com.barclays.bmg.json.model.CasaAccountJSONModel;
import com.barclays.bmg.json.model.CpbValidateJSONModel;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadHeader;
import com.barclays.bmg.operation.response.fundtransfer.external.GetSelectedAccountOperationResponse;
import com.barclays.bmg.operation.response.pesalink.PesaLinkRetrievChargeOperationResponse;
import com.barclays.bmg.utils.BMGFormatUtility;
public class CpbValidateJsonBldr extends
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
						ResponseIdConstants.CPB_PESALINK_RESPONSE_ID);
				break;
			}
		}

		if (bmbPayloadHeader != null) {
			bmbPayload = new BMBPayload(bmbPayloadHeader);
		} else {
			bmbPayload = new BMBPayload(createHeader(responseContexts[0],
					ResponseIdConstants.CPB_PESALINK_RESPONSE_ID));
			populatePayLoad(bmbPayload, responseContexts);
		}

		return bmbPayload;
	}

	protected void populatePayLoad(BMBPayload bmbPayload,
			ResponseContext... responseContexts) {

		CpbValidateJSONModel responseModel = new CpbValidateJSONModel();
		GetSelectedAccountOperationResponse getSelectedAccountOperationResponse = (GetSelectedAccountOperationResponse) responseContexts[0];

		PesaLinkRetrievChargeOperationResponse pesaLinkRetrievChargeOperationResponse = (PesaLinkRetrievChargeOperationResponse) responseContexts[1];


		CustomerAccountDTO srcAcct = getSelectedAccountOperationResponse.getSelectedAcct();

		responseModel.setFrmAct(getCASAAccount(srcAcct, getSelectedAccountOperationResponse));


		if (pesaLinkRetrievChargeOperationResponse != null
				&& pesaLinkRetrievChargeOperationResponse.isSuccess()) {


			AmountJSONModel txnAmt = new AmountJSONModel();
			txnAmt.setAmt(BMGFormatUtility
					.getFormattedAmount(pesaLinkRetrievChargeOperationResponse
							.getTxnAmt().getAmount()));
			txnAmt.setCurr(pesaLinkRetrievChargeOperationResponse.getTxnAmt()
					.getCurrency());
			responseModel.setTxnAmt(txnAmt);

			responseModel.setTxnRefNo(pesaLinkRetrievChargeOperationResponse.getContext().getOrgRefNo());
			responseModel.setMobileNo(pesaLinkRetrievChargeOperationResponse.getMobileNo());

			//CPB change 30/08/2017
			AmountJSONModel jsonModel= new AmountJSONModel();
			if(pesaLinkRetrievChargeOperationResponse.getTransactionFee()!=null){
				jsonModel.setCurr(pesaLinkRetrievChargeOperationResponse.getTransactionFee().getCurrency()); // jsonModel.setCurr("KES");
				jsonModel.setAmt(pesaLinkRetrievChargeOperationResponse.getTransactionFee().getAmount().toString()); //jsonModel.setAmt("15.00");
			}
			responseModel.setTransactionFeeAmount(jsonModel);

			//Fields to be passed for MakeBillPaymentRequest 09/05/2017
			responseModel.setTransactionFeeAmount(jsonModel);	//responseModel.setChargeAmount(pesaLinkRetrievChargeOperationResponse.getChargeAmount());
			responseModel.setFeeGLAccount(pesaLinkRetrievChargeOperationResponse.getFeeGLAccount());
			responseModel.setChargeNarration(pesaLinkRetrievChargeOperationResponse.getChargeNarration());
			responseModel.setTaxAmount(pesaLinkRetrievChargeOperationResponse.getTaxAmount());
			responseModel.setTaxGLAccount(pesaLinkRetrievChargeOperationResponse.getTaxGLAccount());
			responseModel.setExciseDutyNarration(pesaLinkRetrievChargeOperationResponse.getExciseDutyNarration());
			responseModel.setTypeCode(pesaLinkRetrievChargeOperationResponse.getTypeCode());
			responseModel.setValue(pesaLinkRetrievChargeOperationResponse.getValue());

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