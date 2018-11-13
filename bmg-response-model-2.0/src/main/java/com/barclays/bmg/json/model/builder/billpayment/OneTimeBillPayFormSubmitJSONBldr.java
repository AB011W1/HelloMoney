package com.barclays.bmg.json.model.builder.billpayment;

import com.barclays.bmg.constants.AccountConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.context.BMBContextHolder;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.dto.CreditCardAccountDTO;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.json.model.AmountJSONModel;
import com.barclays.bmg.json.model.CasaAccountJSONModel;
import com.barclays.bmg.json.model.CreditCardAccountJSONModel;
import com.barclays.bmg.json.model.billpayment.BillerJSONModel;
import com.barclays.bmg.json.model.billpayment.OneTimeBillPayFormSubmitJSONModel;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadHeader;
import com.barclays.bmg.operation.response.billpayment.OneTimeBillPayOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.external.FormValidateOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.external.GetSelectedAccountOperationResponse;
import com.barclays.bmg.utils.BMGFormatUtility;
import com.barclays.bmg.utils.DateTimeUtil;

/**
 * @author BTCI JSON builder class for One Time Bill Pay Form Submit response
 */
public class OneTimeBillPayFormSubmitJSONBldr extends
		BMBMultipleResponseJSONBuilder implements BMBJSONBuilder {

	/**
	 * @param bmbPayload
	 * @param responseContexts
	 */
	protected void populatePayLoad(BMBPayload bmbPayload,
			ResponseContext... responseContexts) {
		OneTimeBillPayFormSubmitJSONModel oneTimeBillPayFormSubmitJSONModel = null;
		GetSelectedAccountOperationResponse selSourceAcctOpResp = (GetSelectedAccountOperationResponse) responseContexts[0];
		FormValidateOperationResponse formValidateOperationResponse = (FormValidateOperationResponse) responseContexts[1];
		OneTimeBillPayOperationResponse oneTimeBillPayOperationResponse = (OneTimeBillPayOperationResponse) responseContexts[2];

		String casaStrFlag = null;
		if (selSourceAcctOpResp != null && selSourceAcctOpResp.isSuccess()
				&& formValidateOperationResponse != null) {
			Context context = formValidateOperationResponse.getContext();
			oneTimeBillPayFormSubmitJSONModel = new OneTimeBillPayFormSubmitJSONModel();
			CustomerAccountDTO account = selSourceAcctOpResp.getSelectedAcct();
			AmountJSONModel amountJSONModel = new AmountJSONModel();
			if (account instanceof CreditCardAccountDTO) {
				CreditCardAccountDTO creditCardAccount = (CreditCardAccountDTO) account;
				CreditCardAccountJSONModel ccActJson = new CreditCardAccountJSONModel(
						creditCardAccount);
				ccActJson.setTyp(AccountConstants.CC_ACCOUNTS);
				ccActJson.setDesc(account.getProductCode());
				ccActJson.setMkdCrdNo(getMaskedCreditCardNumber(ccActJson
						.getCrdNo()));

				ccActJson.setCurr(account.getCurrency());
				amountJSONModel.setAmt(getPositiveCurrentBalance(account
						.getCurrentBalance()));
				amountJSONModel.setCurr(account.getCurrency());
				ccActJson.setAmtOvrDue(amountJSONModel);
				// ccActJson.setA
				oneTimeBillPayFormSubmitJSONModel
						.setCreditcardJsonModel(ccActJson);
			} else {
				// Set CASA identifier for CBP 26/05/2017
				casaStrFlag = "CASA Account";

				CasaAccountJSONModel accountJSONModel = new CasaAccountJSONModel();
				accountJSONModel.setDesc(account.getDesc());
				accountJSONModel.setActNo(getCorrelationId(account
						.getAccountNumber(), oneTimeBillPayOperationResponse));
				if (branchCodeCountryList.contains(BMBContextHolder
						.getContext().getBusinessId())) {
					accountJSONModel.setMkdActNo(getMaskedAccountNumber(account
							.getBranchCode(), account.getAccountNumber()));
				} else {
					accountJSONModel.setMkdActNo(getMaskedAccountNumber(null,
							account.getAccountNumber()));
				}
				accountJSONModel.setCurr(account.getCurrency());

				amountJSONModel.setAmt(getPositiveCurrentBalance(account
						.getCurrentBalance()));
				amountJSONModel.setCurr(account.getCurrency());
				accountJSONModel.setAvblBal(amountJSONModel);
				oneTimeBillPayFormSubmitJSONModel
						.setFromAccount(accountJSONModel);
			}



			BeneficiaryDTO beneficiaryDTO = oneTimeBillPayOperationResponse	.getBeneficiaryDTO();
			BillerJSONModel billerJSONModel = new BillerJSONModel();
			billerJSONModel.setBillerCatId(beneficiaryDTO
					.getBillerCategoryName());
			billerJSONModel.setBillerName(beneficiaryDTO.getBillerName());
			billerJSONModel.setBillerRefNo(beneficiaryDTO.getBillRefNo());
			oneTimeBillPayFormSubmitJSONModel.setBillerInfo(billerJSONModel);

			AmountJSONModel amount = new AmountJSONModel();
			if (formValidateOperationResponse.getTxnAmt() != null) {
				amount.setAmt(formValidateOperationResponse.getTxnAmt()
						.getAmount().toString());
				amount.setCurr(formValidateOperationResponse.getTxnAmt()
						.getCurrency());
			}
			oneTimeBillPayFormSubmitJSONModel.setAmount(amount);

			oneTimeBillPayFormSubmitJSONModel
					.setTxnRefNo(context.getOrgRefNo());
			oneTimeBillPayFormSubmitJSONModel.setTxnDate(BMGFormatUtility
					.getLongDate(DateTimeUtil.getCurrentBusinessCalendar(
							formValidateOperationResponse.getContext())
							.getTime()));

		}

		// PayBills for CASA - CBP 29/05/2017
		if(casaStrFlag != null && casaStrFlag.equals("CASA Account")){
			// MWallet change for CPB - 12/05/2017
			FormValidateOperationResponse formValidationOperationResponse = (FormValidateOperationResponse) responseContexts[1];
			AmountJSONModel jsonModel= new AmountJSONModel();
			if(formValidationOperationResponse.getTranFee()!=null){
				jsonModel.setCurr(formValidationOperationResponse.getTranFee().getCurrency()); // jsonModel.setCurr("KES");
				jsonModel.setAmt(formValidationOperationResponse.getTranFee().getAmount().toString()); // jsonModel.setAmt("15.00");
			}
			//Fields to be passed for MakeBillPaymentRequest CPB
			oneTimeBillPayFormSubmitJSONModel.setTxnChargeAmt(jsonModel);	//responseModel.setChargeAmount(pesaLinkRetrievChargeOperationResponse.getChargeAmount());
			oneTimeBillPayFormSubmitJSONModel.setFeeGLAccount(formValidationOperationResponse.getFeeGLAccount());
			oneTimeBillPayFormSubmitJSONModel.setChargeNarration(formValidationOperationResponse.getChargeNarration());
			oneTimeBillPayFormSubmitJSONModel.setTaxAmount(formValidationOperationResponse.getTaxAmount());
			oneTimeBillPayFormSubmitJSONModel.setTaxGLAccount(formValidationOperationResponse.getTaxGLAccount());
			oneTimeBillPayFormSubmitJSONModel.setExciseDutyNarration(formValidationOperationResponse.getExciseDutyNarration());
			oneTimeBillPayFormSubmitJSONModel.setTypeCode(formValidationOperationResponse.getTypeCode());
			oneTimeBillPayFormSubmitJSONModel.setValue(formValidationOperationResponse.getValue());
		}

		bmbPayload.setPayData(oneTimeBillPayFormSubmitJSONModel);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @seecom.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder#
	 * createMultipleJSONResponse(com.barclays.bmg.context.ResponseContext[])
	 */
	@Override
	public Object createMultipleJSONResponse(
			ResponseContext... responseContexts) {
		BMBPayloadHeader bmbPayloadHeader = null;
		BMBPayload bmbPayload = null;
		for (ResponseContext response : responseContexts) {
			if (response != null && !response.isSuccess()) {
				bmbPayloadHeader = createHeader(response,
						ResponseIdConstants.ONE_TIME_BILL_PAY_FORM_SUBMIT);
				break;
			}
		}

		if (bmbPayloadHeader != null) {
			bmbPayload = new BMBPayload(bmbPayloadHeader);
		} else {
			ResponseContext response = responseContexts[0];
			bmbPayload = new BMBPayload(createHeader(response,
					ResponseIdConstants.ONE_TIME_BILL_PAY_FORM_SUBMIT));
			populatePayLoad(bmbPayload, responseContexts);
		}

		return bmbPayload;
	}

}
