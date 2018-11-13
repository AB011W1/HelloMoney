package com.barclays.bmg.json.model.builder.fundtransfer.nonregistered.internal;

import com.barclays.bmg.constants.AccountConstants;
import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.context.BMBContextHolder;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.dto.CASAAccountDTO;
import com.barclays.bmg.dto.CreditCardAccountDTO;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.json.model.AmountJSONModel;
import com.barclays.bmg.json.model.CasaAccountJSONModel;
import com.barclays.bmg.json.model.CreditCardAccountJSONModel;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.model.fundtransfer.nonregistered.internal.InternalNonRegisteredFTValidateJSONResponseModel;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadHeader;
import com.barclays.bmg.operation.response.fundtransfer.external.GetSelectedAccountOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.nonregistered.internal.FormValidateOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.nonregistered.internal.RetrieveInternalNonRegisteredPayeeInfoOperationResponse;
import com.barclays.bmg.utils.BMGFormatUtility;

public class InternalNonRegisteredFundTransferFormSubmissionJSONBldr extends BMBMultipleResponseJSONBuilder implements BMBJSONBuilder {


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
		} else if(response != null){
			header.setResCde(response.getResCde());
			header.setResMsg(response.getResMsg());
		}
		header.setSerVer(ResponseIdConstants.RESPONSE_SERVICE_VERSION);
		header.setResId(ResponseIdConstants.EXTERNAL_FUND_TRANSFER_FORM_SUBMIT);

		return header;
	}

	protected void populatePayLoad(BMBPayload bmbPayload,
			ResponseContext... responses
			) {

		InternalNonRegisteredFTValidateJSONResponseModel responseModel = new InternalNonRegisteredFTValidateJSONResponseModel();
		GetSelectedAccountOperationResponse selectedAccountOperationResponse = (GetSelectedAccountOperationResponse)responses[0];
		RetrieveInternalNonRegisteredPayeeInfoOperationResponse internalNonRegisteredPayeeInfoOperationResponse = (RetrieveInternalNonRegisteredPayeeInfoOperationResponse)responses[1];
		FormValidateOperationResponse formValidateOperationResponse = (FormValidateOperationResponse)responses[2];

		String casaStrFlag = null;
		BeneficiaryDTO beneficiaryDTO = internalNonRegisteredPayeeInfoOperationResponse.getBeneficiaryDTO();

		//Context context = internalNonRegisteredPayeeInfoOperationResponse.getContext();

		CustomerAccountDTO srcAcct = selectedAccountOperationResponse.getSelectedAcct();

		if(srcAcct instanceof CreditCardAccountDTO){
		    CreditCardAccountDTO account = (CreditCardAccountDTO) srcAcct;
		    CreditCardAccountJSONModel ccActJson = new CreditCardAccountJSONModel(account);
		    //ccActJson.setTyp(AccountConstants.CC_ACCOUNTS);
		    //ccActJson.setDesc(account.getProductCode());
		    ccActJson.setMkdCrdNo(getMaskedCreditCardNumber(ccActJson.getCrdNo()));
			responseModel.setCreditcard(ccActJson);
		} else {
		// Set CASA identifier for CBP 26/05/2017
		casaStrFlag = "CASA Account";
		responseModel.setFrmAct(getCASAAccount(srcAcct, internalNonRegisteredPayeeInfoOperationResponse));
		}

		responseModel.setBeneAccountNumber(beneficiaryDTO.getDestinationAccountNumber());
		responseModel.setBeneName(beneficiaryDTO.getBeneficiaryName());
		responseModel.setBeneBrnCde(beneficiaryDTO.getDestinationBranchCode());

		AmountJSONModel txnAmt = new AmountJSONModel();
		txnAmt.setAmt(BMGFormatUtility.getFormattedAmount(formValidateOperationResponse.getTxnAmt().getAmount()));
		txnAmt.setCurr(formValidateOperationResponse.getTxnAmt().getCurrency());
		responseModel.setTxnAmt(txnAmt);

		if(formValidateOperationResponse.getFxRateDTO()!=null){
			responseModel.setFxRt(BMGFormatUtility.getFormattedAmount(formValidateOperationResponse.getFxRateDTO().getEffectiveFXRate()));
			AmountJSONModel eqAmt = new AmountJSONModel();
			eqAmt.setAmt(BMGFormatUtility.getFormattedAmount(formValidateOperationResponse.getFxRateDTO().getEquivalentAmount()));
			eqAmt.setCurr(srcAcct.getCurrency());
			responseModel.setEqAmt(eqAmt);
		}


		responseModel.setTxnRefNo(formValidateOperationResponse.getTxnRefNo());
		responseModel.setTxnNot(formValidateOperationResponse.getTxnNot());

		// OtherBarclays FundTransfer for CASA - CBP 31/05/2017
		if(casaStrFlag != null && casaStrFlag.equals("CASA Account")){
			FormValidateOperationResponse formValidationOperationResponse = (FormValidateOperationResponse) responses[2];
			AmountJSONModel jsonModel= new AmountJSONModel();
			if(formValidationOperationResponse.getTranFee()!=null){
				jsonModel.setCurr(formValidationOperationResponse.getTranFee().getCurrency()); // jsonModel.setCurr("KES");
				jsonModel.setAmt(formValidationOperationResponse.getTranFee().getAmount().toString()); // jsonModel.setAmt("15.00");
			}
			// Fields to be passed for MakeDomesticFundTransferRequest CPB
			responseModel.setTxnChargeAmt(jsonModel);	//responseModel.setChargeAmount(pesaLinkRetrievChargeOperationResponse.getChargeAmount());
			responseModel.setFeeGLAccount(formValidationOperationResponse.getFeeGLAccount());
			responseModel.setChargeNarration(formValidationOperationResponse.getChargeNarration());
			responseModel.setTaxAmount(formValidationOperationResponse.getTaxAmount());
			responseModel.setTaxGLAccount(formValidationOperationResponse.getTaxGLAccount());
			responseModel.setExciseDutyNarration(formValidationOperationResponse.getExciseDutyNarration());
			responseModel.setTypeCode(formValidationOperationResponse.getTypeCode());
			responseModel.setValue(formValidationOperationResponse.getValue());
		}

		bmbPayload.setPayData(responseModel);

	}

	private CasaAccountJSONModel getCASAAccount(
			CustomerAccountDTO account, ResponseContext response) {
		CasaAccountJSONModel accountJSONModel = null;
		if (account != null) {
				CASAAccountDTO casaAcct = (CASAAccountDTO) account;
				accountJSONModel = new CasaAccountJSONModel(casaAcct);

				// CHECK added masking
				/*accountJSONModel.setMkdActNo(getMaskedAccountNumber(casaAcct.getBranchCode()
								, casaAcct.getAccountNumber()));*/


				 if(branchCodeCountryList.contains(BMBContextHolder.getContext().getBusinessId())){
					accountJSONModel.setMkdActNo(getMaskedAccountNumber(casaAcct.getBranchCode(), casaAcct.getAccountNumber()));
				    }
				    else {
					accountJSONModel.setMkdActNo(getMaskedAccountNumber(null, casaAcct.getAccountNumber()));
				    }

				accountJSONModel.setActNo(getCorrelationId(casaAcct.getAccountNumber(), response));

			}
		return accountJSONModel;

	}

}
