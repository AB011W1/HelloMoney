package com.barclays.bmg.json.model.builder.fundtransfer.external;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.barclays.bmg.constants.FundTransferConstants;
import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.context.BMBContextHolder;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.dto.CASAAccountDTO;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.dto.PaymentRsonDtlsDTO;
import com.barclays.bmg.json.model.AmountJSONModel;
import com.barclays.bmg.json.model.CasaAccountJSONModel;
import com.barclays.bmg.json.model.billpayment.ChargeDescJSONModel;
import com.barclays.bmg.json.model.billpayment.CurrencyJSONModel;
import com.barclays.bmg.json.model.billpayment.ExternalBankJSONModel;
import com.barclays.bmg.json.model.billpayment.ExternalBeneficiaryJSONModel;
import com.barclays.bmg.json.model.billpayment.PaymentReasonDetailsJSONModel;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.model.fundtransfer.ExternalFTPayInfoJSONResponseModel;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadHeader;
import com.barclays.bmg.operation.response.fundtransfer.external.ExternalFundTransferDataOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.external.GetPaymentReasonDetailsOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.external.GetSelectedAccountOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.external.RetrievePayeeInfoOperationResponse;

public class ExternalFundTransferPayInfoJSONBldr extends BMBMultipleResponseJSONBuilder implements BMBJSONBuilder{

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
		} else if(response != null){
			header.setResCde(response.getResCde());
			header.setResMsg(response.getResMsg());
		}
		header.setSerVer(ResponseIdConstants.RESPONSE_SERVICE_VERSION);
		header.setResId(ResponseIdConstants.EXTERNAL_FUND_TRANSFER_PAY_INFO);

		return header;
	}

	protected void populatePayLoad(BMBPayload bmbPayload,
			ResponseContext... responses
			) {
		ExternalFTPayInfoJSONResponseModel responseModel = new ExternalFTPayInfoJSONResponseModel();

		GetSelectedAccountOperationResponse getSelectedAccountOperationResponse = (GetSelectedAccountOperationResponse)responses[0];
		CustomerAccountDTO srcAcct = getSelectedAccountOperationResponse.getSelectedAcct();
		responseModel.setFrmAct(getCASAAccount(srcAcct, getSelectedAccountOperationResponse));

		RetrievePayeeInfoOperationResponse retrievePayeeInfoOperationResponse = (RetrievePayeeInfoOperationResponse)responses[1];
		Context context = retrievePayeeInfoOperationResponse.getContext();

			BeneficiaryDTO beneficiaryDTO = retrievePayeeInfoOperationResponse.getBeneficiaryDTO();

			ExternalBeneficiaryJSONModel beneficiaryJSONModel = new ExternalBeneficiaryJSONModel(beneficiaryDTO);

			beneficiaryJSONModel.setCntry(getListValueLabel(context, CMN_COUNTRY, beneficiaryJSONModel.getCntry()));
			resolveCountry(beneficiaryJSONModel.getBeneBank(),context);
			resolveCountryList(beneficiaryJSONModel.getIntBankLst(), context);
			beneficiaryJSONModel.setMkdActNo(beneficiaryDTO.getDestinationAccountNumber());

			responseModel.setPayInfo(beneficiaryJSONModel);
			AmountJSONModel txnLmt = new AmountJSONModel();

			ExternalFundTransferDataOperationResponse externalFundTransferDataOperationResponse =
							(ExternalFundTransferDataOperationResponse) responses[2];

/*			TransactionLimitOperationResponse transactionLimitOperationResponse = (TransactionLimitOperationResponse) responses[3];

			txnLmt.setAmt(BMGFormatUtility.getFormattedAmount(transactionLimitOperationResponse.getAValidDailyLimit()));
			txnLmt.setCurr(transactionLimitOperationResponse.getContext().getLocalCurrency());
*/
			responseModel.setTxnLmt(txnLmt);
			List<String> currLst = externalFundTransferDataOperationResponse.getCurrLst();
			for(String curr:currLst){
				CurrencyJSONModel currency = new CurrencyJSONModel(curr);
				responseModel.add(currency);
			}


			Map<String, String> chargesDesc = externalFundTransferDataOperationResponse.getChargeDesc();

			/* Below code commented and re-implemented according to find bugs defect */

			/*
		  	if(chargesDesc!=null){
				Set<String> chargesKeys = chargesDesc.keySet();
				for(String chargesKey: chargesKeys){
					ChargeDescJSONModel chargeDescJSONModel = new ChargeDescJSONModel();
					chargeDescJSONModel.setKey(chargesKey);
					chargeDescJSONModel.setDesc(chargesDesc.get(chargesKey));
					responseModel.add(chargeDescJSONModel);
				}
			}
			*/

			if(chargesDesc!=null){
				Set<Entry<String, String>> chargesKeys = chargesDesc.entrySet();
				for(Entry<String, String> chargesKey: chargesKeys){
					ChargeDescJSONModel chargeDescJSONModel = new ChargeDescJSONModel();
					chargeDescJSONModel.setKey(chargesKey.getKey());
					chargeDescJSONModel.setDesc(chargesKey.getValue());
					responseModel.add(chargeDescJSONModel);
				}
			}

// Changes for Payment Rson and Payment Details.

			GetPaymentReasonDetailsOperationResponse getPaymentReasonDetailsOperationResponse = (GetPaymentReasonDetailsOperationResponse) responses[3];
			if(FundTransferConstants.TXN_TYPE_FUND_TRANSFER_EXTERNAL.equals(getPaymentReasonDetailsOperationResponse.getTxnTyp())){
				List<PaymentRsonDtlsDTO> paymentRsonDtlsLst = getPaymentReasonDetailsOperationResponse.getPaymentRsonDtlsLst();
				if(paymentRsonDtlsLst!=null && !paymentRsonDtlsLst.isEmpty()){
					for(PaymentRsonDtlsDTO paymentRsonDtlsDTO : paymentRsonDtlsLst){
						PaymentReasonDetailsJSONModel paymentReasonDetailsJSONModel = new PaymentReasonDetailsJSONModel(paymentRsonDtlsDTO);
						responseModel.add(paymentReasonDetailsJSONModel);
					}
				}
			}


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
				accountJSONModel.setActNo(getCorrelationId(casaAcct.getAccountNumber(), response));

			}
		return accountJSONModel;

	}

}
