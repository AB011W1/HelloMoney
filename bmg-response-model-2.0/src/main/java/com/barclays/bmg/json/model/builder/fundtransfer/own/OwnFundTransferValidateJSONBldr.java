package com.barclays.bmg.json.model.builder.fundtransfer.own;

import java.util.Map;

import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.context.BMBContextHolder;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.CreditCardAccountDTO;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.dto.FxRateDTO;
import com.barclays.bmg.json.model.AmountJSONModel;
import com.barclays.bmg.json.model.billpayment.CCAccountJSONBean;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.model.fundtransfer.OwnFundTransferValidateJSONResponseModel;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadHeader;
import com.barclays.bmg.operation.response.fundtransfer.external.FormValidateOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.external.GetSelectedAccountOperationResponse;
import com.barclays.bmg.utils.BMGFormatUtility;

public class OwnFundTransferValidateJSONBldr  extends BMBMultipleResponseJSONBuilder {

	@Override
	public Object createMultipleJSONResponse(
			ResponseContext... responseContexts) {
		BMBPayloadHeader bmbPayloadHeader = null;
		BMBPayload bmbPayload = null;
		for(ResponseContext response: responseContexts){
			if(response!=null && !response.isSuccess()){
				bmbPayloadHeader = createHeader(response, ResponseIdConstants.OWN_FUND_TRANSFER_VALIDATE_RESPONSE_ID);
				break;
			}
		}

		if(bmbPayloadHeader!=null){
			bmbPayload = new BMBPayload(bmbPayloadHeader);
		}else{
			bmbPayload = new BMBPayload(createHeader(responseContexts[0],ResponseIdConstants.OWN_FUND_TRANSFER_VALIDATE_RESPONSE_ID));
			populatePayLoad(bmbPayload, responseContexts);
		}

		return bmbPayload;
	}


	protected void populatePayLoad(BMBPayload bmbPayload,
			ResponseContext... responseContexts	) {

		OwnFundTransferValidateJSONResponseModel responseModel = null;

		GetSelectedAccountOperationResponse selSourceAcctOpResp = (GetSelectedAccountOperationResponse) responseContexts[0];
		GetSelectedAccountOperationResponse selDestAcctOpResp = (GetSelectedAccountOperationResponse) responseContexts[1];
		FormValidateOperationResponse formValidateOperationResponse = (FormValidateOperationResponse) responseContexts[3];
		if (formValidateOperationResponse != null && formValidateOperationResponse.isSuccess()) {
			Context context = formValidateOperationResponse.getContext();
			Map<String, Object> contextMap = context.getContextMap();
			responseModel = new OwnFundTransferValidateJSONResponseModel();
			AmountJSONModel txnAmt = new AmountJSONModel();

			txnAmt.setAmt(BMGFormatUtility.getFormattedAmount(formValidateOperationResponse.getTxnAmt().getAmount()));
			txnAmt.setCurr(formValidateOperationResponse.getTxnAmt().getCurrency());
			responseModel.setTxnAmt(txnAmt);
			responseModel.setTxnDt((String)contextMap.get("txnDt"));

			CustomerAccountDTO toAcct = selDestAcctOpResp.getSelectedAcct();
			CustomerAccountDTO frmAcct = selSourceAcctOpResp.getSelectedAcct();


			// CHECK Account Number masked
			// MAsked Acccount Number is pending
			/*responseModel.setFrMskActNo(getMaskedAccountNumber(frmAcct
					.getBranchCode(), frmAcct.getAccountNumber()));

			responseModel.setToMskActNo(getMaskedAccountNumber(toAcct
					.getBranchCode(), toAcct.getAccountNumber()));*/





		if (branchCodeCountryList.contains(BMBContextHolder.getContext()
					.getBusinessId())) {
			responseModel.setFrMskActNo(getMaskedAccountNumber(frmAcct
						.getBranchCode(), frmAcct.getAccountNumber()));

			responseModel.setToMskActNo(getMaskedAccountNumber(toAcct
						.getBranchCode(), toAcct.getAccountNumber()));
			} else {
				if (frmAcct instanceof CreditCardAccountDTO) {
					CreditCardAccountDTO ccaccount = (CreditCardAccountDTO) frmAcct;
					responseModel.setMkdCrdNo(getMaskedCreditCardNumber(ccaccount
							.getPrimary().getCardNumber()));
				} else {
					responseModel.setFrMskActNo(getMaskedAccountNumber(null,
							frmAcct.getAccountNumber()));
				}
				responseModel.setToMskActNo(getMaskedAccountNumber(null, toAcct
						.getAccountNumber()));
			}




			/*
			 * responseModel .setFrActNo(frmAcct.getAccountNumber());
			 * responseModel .setToActNo(toAcct.getAccountNumber());
			 */

			//responseModel.setTxnNot(convertStringToUnicode((String)contextMap.get("txnNot"))); //Added convertStringToUnicode() to convert to unicode
			responseModel.setTxnNot((String)contextMap.get("txnNot"));

			// Fixed Rate and equivalen amont pending
			FxRateDTO fxRateDTO = formValidateOperationResponse.getFxRateDTO();
			if (fxRateDTO != null && fxRateDTO.getEffectiveFXRate() != null) {
				responseModel
						.setFxRt(fxRateDTO.getEffectiveFXRate().toString());
			}
			if (fxRateDTO != null && fxRateDTO.getEquivalentAmount() != null) {
				AmountJSONModel eqAmt = new AmountJSONModel();

				eqAmt.setAmt(BMGFormatUtility.getFormattedAmount(fxRateDTO
						.getEquivalentAmount()));
				eqAmt.setCurr(frmAcct.getCurrency());
				responseModel.setEqAmt(eqAmt);
			}
			responseModel.setTxnRefNo(context.getOrgRefNo());
			if(formValidateOperationResponse.getTranFee()!=null){
				responseModel.setTotFee(BMGFormatUtility.getFormattedAmount(formValidateOperationResponse.getTranFee().getAmount()));
			}


			responseModel.setCurr(formValidateOperationResponse.getContext().getLocalCurrency());

			responseModel.setToActCurr(toAcct.getCurrency());
			responseModel.setFrActCurr(frmAcct.getCurrency());

			/**
			 * Retrofitting changes
			 */
			responseModel.setToActDesc(toAcct.getDesc());
			responseModel.setFrmActDesc(frmAcct.getDesc());

/*			AmountJSONModel txnLimit = null;
			if (formValidateOperationResponse.getAValidDailyLimit() != null) {
				txnLimit = new AmountJSONModel();
				txnLimit.setAmt(BMGFormatUtility.getFormattedAmount(response
						.getAValidDailyLimit()));
				txnLimit.setCurr(response.getContext().getLocalCurrency());
			}

			responseModel.setTxnLmt(txnLimit);
*/
		}

		bmbPayload.setPayData(responseModel);
	}

}
