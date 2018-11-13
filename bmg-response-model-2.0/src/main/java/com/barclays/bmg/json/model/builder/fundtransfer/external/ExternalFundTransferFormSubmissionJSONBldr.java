package com.barclays.bmg.json.model.builder.fundtransfer.external;

import java.util.List;

import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.context.BMBContextHolder;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.dto.CASAAccountDTO;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.json.model.AmountJSONModel;
import com.barclays.bmg.json.model.CasaAccountJSONModel;
import com.barclays.bmg.json.model.billpayment.ChargeDescJSONModel;
import com.barclays.bmg.json.model.billpayment.ExternalBankJSONModel;
import com.barclays.bmg.json.model.billpayment.ExternalBeneficiaryJSONModel;
import com.barclays.bmg.json.model.billpayment.RemittanceInfoJSONModel;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.model.fundtransfer.ExternalFTFormSubmissionJSONResponseModel;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadHeader;
import com.barclays.bmg.operation.response.fundtransfer.external.ExternalFTFormSubmissionOperationResponse;
import com.barclays.bmg.utils.BMGFormatUtility;

public class ExternalFundTransferFormSubmissionJSONBldr extends BMBMultipleResponseJSONBuilder implements BMBJSONBuilder {

	  private static final String CMN_COUNTRY = "CMN_COUNTRY";
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
		} else if(response != null) {
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
		ExternalFTFormSubmissionJSONResponseModel responseModel = new ExternalFTFormSubmissionJSONResponseModel();
		ExternalFTFormSubmissionOperationResponse response = (ExternalFTFormSubmissionOperationResponse)responses[0];

		BeneficiaryDTO beneficiaryDTO = response.getBeneficiaryDTO();

		//Changes for GHIPPS
		if(beneficiaryDTO!=null){
			ExternalBeneficiaryJSONModel beneficiaryJSONModel = new ExternalBeneficiaryJSONModel(beneficiaryDTO);

			Context context = response.getContext();
			beneficiaryJSONModel.setCntry(getListValueLabel(context, CMN_COUNTRY, beneficiaryJSONModel.getCntry()));
			resolveCountry(beneficiaryJSONModel.getBeneBank(),context);
			resolveCountryList(beneficiaryJSONModel.getIntBankLst(), context);
			beneficiaryJSONModel.setMkdActNo(beneficiaryDTO.getDestinationAccountNumber());
			responseModel.setPayInfo(beneficiaryJSONModel);
		}
		CustomerAccountDTO srcAcct = response.getSourceAcct();
		responseModel.setFrmAct(getCASAAccount(srcAcct, response));

		ChargeDescJSONModel chargeDescJSONModel = new ChargeDescJSONModel();
		chargeDescJSONModel.setKey(response.getChargeKey());
		chargeDescJSONModel.setDesc(response.getCharDtls());
		responseModel.setChrgesDesc(chargeDescJSONModel);

		AmountJSONModel txnAmt = new AmountJSONModel();
		txnAmt.setAmt(BMGFormatUtility.getFormattedAmount(response.getTxnAmt().getAmount()));
		txnAmt.setCurr(response.getTxnAmt().getCurrency());
		responseModel.setTxnAmt(txnAmt);

		responseModel.setPayRson(response.getPayRsonValue());
		responseModel.setPayDtls(response.getPayDtlsValue());

		if(response.getFxRateDTO()!=null){
			responseModel.setFxRate(BMGFormatUtility.getFormattedAmount(response.getFxRateDTO().getEffectiveFXRate()));
			AmountJSONModel eqAmt = new AmountJSONModel();
			eqAmt.setAmt(BMGFormatUtility.getFormattedAmount(response.getFxRateDTO().getEquivalentAmount()));
			eqAmt.setCurr(srcAcct.getCurrency());
			responseModel.setEqAmt(eqAmt);
		}



		RemittanceInfoJSONModel remInfo = new RemittanceInfoJSONModel();
		//remInfo.setPart1(convertStringToUnicode(response.getRem1())); //Added convertStringToUnicode() to convert to unicode
		remInfo.setPart1(response.getRem1());
		remInfo.setPart2(response.getRem2());
		remInfo.setPart3(response.getRem3());
		responseModel.setRemInfo(remInfo);

		responseModel.setTxnRefNo(response.getTxnRefNo());
		responseModel.setTxnNot(response.getTxnNot());

		bmbPayload.setPayData(responseModel);

	}

	private void resolveCountryList(List<ExternalBankJSONModel> banks, Context context){
		if(banks!=null && !banks.isEmpty()){
			for(ExternalBankJSONModel bank : banks){
				resolveCountry(bank, context);
			}
		}
	}

	private void resolveCountry(ExternalBankJSONModel bank, Context context){
		if(bank!=null){
			bank.setCntry(getListValueLabel(context, CMN_COUNTRY,bank.getCntry()));
		}
	}

	private CasaAccountJSONModel getCASAAccount(
			CustomerAccountDTO account, ResponseContext response) {
		CasaAccountJSONModel accountJSONModel = null;
		if (account != null) {
				CASAAccountDTO casaAcct = (CASAAccountDTO) account;
				accountJSONModel = new CasaAccountJSONModel(
						casaAcct);
				// CHECK added masking
				/*accountJSONModel.setMkdActNo(getMaskedAccountNumber(casaAcct.getBranchCode()
								, casaAcct.getAccountNumber()));*/


				if(branchCodeCountryList.contains(BMBContextHolder.getContext().getBusinessId())){
					accountJSONModel.setMkdActNo(getMaskedAccountNumber(casaAcct.getBranchCode(), casaAcct.getAccountNumber()));
				    }
				    else {
					accountJSONModel.setMkdActNo(getMaskedAccountNumber(null, casaAcct.getAccountNumber()));
				    }


				//accountJSONModel.setActNo(getCorrelationId(casaAcct.getAccountNumber(), response));

			}
		return accountJSONModel;

	}

}
