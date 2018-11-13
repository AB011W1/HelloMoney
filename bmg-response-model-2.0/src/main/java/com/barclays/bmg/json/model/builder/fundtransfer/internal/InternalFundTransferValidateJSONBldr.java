package com.barclays.bmg.json.model.builder.fundtransfer.internal;

import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.context.BMBContextHolder;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.dto.CreditCardAccountDTO;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.dto.FxRateDTO;
import com.barclays.bmg.json.model.AmountJSONModel;
import com.barclays.bmg.json.model.CreditCardAccountJSONModel;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.model.fundtransfer.InternalFTValidateJSONResponseModel;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadHeader;
import com.barclays.bmg.operation.response.fundtransfer.external.FormValidateOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.external.GetSelectedAccountOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.external.RetrievePayeeInfoOperationResponse;
import com.barclays.bmg.utils.BMGFormatUtility;

public class InternalFundTransferValidateJSONBldr extends
		BMBMultipleResponseJSONBuilder {

	@Override
	public Object createMultipleJSONResponse(
			ResponseContext... responseContexts) {
		BMBPayloadHeader bmbPayloadHeader = null;
		BMBPayload bmbPayload = null;
		for (ResponseContext response : responseContexts) {
			if (response != null && !response.isSuccess()) {
				bmbPayloadHeader = createHeader(
						response,
						ResponseIdConstants.INTERNAL_TRANSFER_VALIDATE_RESPONSE_ID);
				break;
			}
		}

		if (bmbPayloadHeader != null) {
			bmbPayload = new BMBPayload(bmbPayloadHeader);
		} else {
			bmbPayload = new BMBPayload(createHeader(responseContexts[0],
					ResponseIdConstants.INTERNAL_TRANSFER_VALIDATE_RESPONSE_ID));
			populatePayLoad(bmbPayload, responseContexts);
		}

		return bmbPayload;
	}

	protected void populatePayLoad(BMBPayload bmbPayload,
			ResponseContext... responseContexts) {

		InternalFTValidateJSONResponseModel responseModel = null;
		GetSelectedAccountOperationResponse getSelectedAccountOperationResponse = (GetSelectedAccountOperationResponse) responseContexts[0];
		RetrievePayeeInfoOperationResponse retrievePayeeInfoOperationResponse = (RetrievePayeeInfoOperationResponse) responseContexts[1];
		FormValidateOperationResponse formValidateOperationResponse = (FormValidateOperationResponse) responseContexts[2];

		String casaStrFlag = null;
		if (formValidateOperationResponse != null
				&& formValidateOperationResponse.isSuccess()) {

			responseModel = new InternalFTValidateJSONResponseModel();
			// String currency = response.getCurreny();
			AmountJSONModel txnAmt = new AmountJSONModel();
			txnAmt.setAmt(BMGFormatUtility
					.getFormattedAmount(formValidateOperationResponse
							.getTxnAmt().getAmount()));
			txnAmt.setCurr(formValidateOperationResponse.getTxnAmt()
					.getCurrency());
			responseModel.setTxnAmt(txnAmt);

			// responseModel.setTxnDt(response.getTxnDate());
			CustomerAccountDTO frmAcct = getSelectedAccountOperationResponse
					.getSelectedAcct();
			CustomerAccountDTO toAcct = retrievePayeeInfoOperationResponse
					.getCasaAccountDTO();
			BeneficiaryDTO beneficiaryDTO = retrievePayeeInfoOperationResponse
					.getBeneficiaryDTO();
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
					CreditCardAccountDTO account = (CreditCardAccountDTO) frmAcct;
					responseModel.setMkdCrdNo(getMaskedCreditCardNumber(account.getPrimary().getCardNumber()));
				} else {
					// Set CASA identifier for CBP 26/05/2017
					casaStrFlag = "CASA Account";

					responseModel.setFrMskActNo(getMaskedAccountNumber(null,
							frmAcct.getAccountNumber()));
				}
				responseModel.setToMskActNo(getMaskedAccountNumber(null, toAcct
						.getAccountNumber()));
			}


			responseModel.setBeneBrnCde(getFormattedBranchCode(beneficiaryDTO.getDestinationBranchCode()));


			FxRateDTO fxRateDTO = formValidateOperationResponse.getFxRateDTO();
			// Fixed Rate and equivalen amont pending
			if (fxRateDTO != null && fxRateDTO.getEffectiveFXRate() != null
					&& fxRateDTO.getEquivalentAmount() != null) {
				responseModel
						.setFxRt(fxRateDTO.getEffectiveFXRate().toString());
				AmountJSONModel eqAmt = new AmountJSONModel();
				eqAmt.setAmt(BMGFormatUtility.getFormattedAmount(fxRateDTO
						.getEquivalentAmount()));
				eqAmt.setCurr(frmAcct.getCurrency());

				responseModel.setEqAmt(eqAmt);
			}
			/*
			 * AmountJSONModel txnLimit = new AmountJSONModel(); if
			 * (formValidateOperationResponse.get != null) {
			 * txnLimit.setAmt(BMGFormatUtility
			 * .getFormattedAmount(formValidateOperationResponse
			 * .getAValidDailyLimit()));
			 * txnLimit.setCurr(response.getContext().getLocalCurrency()); }
			 * responseModel.setTxnLmt(txnLimit);
			 */
			// responseModel.setTxnRefNo(response.getTxnRef());
			if (formValidateOperationResponse.getTranFee() != null) {
				responseModel.setTotFee(BMGFormatUtility
						.getFormattedAmount(formValidateOperationResponse
								.getTranFee().getAmount()));
			}

			responseModel.setCurr(formValidateOperationResponse.getContext()
					.getLocalCurrency());

			responseModel.setToActCurr(toAcct.getCurrency());
			responseModel.setFrActCurr(frmAcct.getCurrency());
			responseModel.setPayId(beneficiaryDTO.getPayeeId());
			responseModel.setPayName(beneficiaryDTO.getPayeeNickname());
			responseModel.setBeneName(beneficiaryDTO.getBeneficiaryName());

			/**
			 * Retrofitting changes
			 */
			responseModel.setToActDesc(toAcct.getDesc());
			responseModel.setFrmActDesc(frmAcct.getDesc());

			responseModel.setTxnDt(formValidateOperationResponse.getTxnDt());
			//Added convertStringToUnicode() to convert Character from Unicode
			//responseModel.setTxnNot(convertStringToUnicode(formValidateOperationResponse.getTxnNot()));
			responseModel.setTxnNot(formValidateOperationResponse.getTxnNot());
			responseModel.setTxnRefNo(formValidateOperationResponse.getContext().getOrgRefNo());

		}

		// PayBills for CASA - CBP 29/05/2017
		if(casaStrFlag != null && casaStrFlag.equals("CASA Account")){
			// MWallet change for CPB - 12/05/2017
			FormValidateOperationResponse formValidationOperationResponse = (FormValidateOperationResponse) responseContexts[2];
			AmountJSONModel jsonModel= new AmountJSONModel();
			if(formValidationOperationResponse.getTranFee()!=null){
				jsonModel.setCurr(formValidationOperationResponse.getTranFee().getCurrency()); // jsonModel.setCurr("KES");
				jsonModel.setAmt(formValidationOperationResponse.getTranFee().getAmount().toString()); // jsonModel.setAmt("15.00");
			}
			//Fields to be passed for MakeBillPaymentRequest CPB
			responseModel.setTxnChargeAmt(jsonModel);	//responseModel.setChargeAmount(pesaLinkRetrievChargeOperationResponse.getChargeAmount());
			responseModel.setFeeGLAccount(formValidationOperationResponse.getFeeGLAccount());
			responseModel.setChargeNarration(formValidationOperationResponse.getChargeNarration());
			responseModel.setTaxAmount(formValidationOperationResponse.getTaxAmount());
			responseModel.setTaxGLAccount(formValidationOperationResponse.getTaxGLAccount());
			responseModel.setExciseDutyNarration(formValidationOperationResponse.getExciseDutyNarration());
			responseModel.setTypeCode(formValidationOperationResponse.getTypeCode());
			responseModel.setValue(formValidationOperationResponse.getValue());

			// xelerate offline error codes
			responseModel.setXelerateOfflineError(formValidateOperationResponse.getXelerateOfflineError());
			responseModel.setXelerateOfflineDesc(formValidationOperationResponse.getXelerateOfflineDesc());
		}

		bmbPayload.setPayData(responseModel);
	}
}
