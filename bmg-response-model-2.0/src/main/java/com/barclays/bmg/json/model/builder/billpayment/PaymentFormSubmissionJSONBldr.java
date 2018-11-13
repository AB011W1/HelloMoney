package com.barclays.bmg.json.model.builder.billpayment;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.barclays.bmg.constants.BillPaymentConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.Amount;
import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.dto.CreditCardAccountDTO;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.dto.FxRateDTO;
import com.barclays.bmg.json.model.AmountJSONModel;
import com.barclays.bmg.json.model.billpayment.AccountJSONBean;
import com.barclays.bmg.json.model.billpayment.BeneficiaryJSONModel;
import com.barclays.bmg.json.model.billpayment.CCAccountJSONBean;
import com.barclays.bmg.json.model.billpayment.CCPayeeJSONModel;
import com.barclays.bmg.json.model.billpayment.CreditCardAccountJSONBean;
import com.barclays.bmg.json.model.billpayment.PayeeInformationJSONModel;
import com.barclays.bmg.json.model.billpayment.PaymentTransferJSONBean;
import com.barclays.bmg.json.model.billpayment.ReferenceNumber;
import com.barclays.bmg.json.model.billpayment.TransactionInformationRestBean;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadHeader;
import com.barclays.bmg.operation.response.fundtransfer.external.BillPayAmountValidationOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.external.FormValidateOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.external.GetSelectedAccountOperationResponse;
import com.barclays.bmg.utils.BMGFormatUtility;

public class PaymentFormSubmissionJSONBldr extends
		BMBMultipleResponseJSONBuilder implements BMBJSONBuilder {

	private static final String SCREEN_ID = "PMT_BP_FACADE";
	private static final String LANDLINE = "Landline";
	private static final String PHONE_NO = "ReferenceNumber";
	private static final String MOBILE = "PhoneNumberInputText";

	/**
	 * @param payeeType
	 * @return Get the response id as per payee type
	 */
	private String getResponseId(String payeeType) {

		String resId = ResponseIdConstants.BP_PAYMENT_TRANSFER_FORM_SUBMIT_RESPONSE_ID;
		if (BillPaymentConstants.PAYEE_TYPE_BILL_PAYMENT.equals(payeeType)) {
			resId = ResponseIdConstants.BP_PAYMENT_TRANSFER_FORM_SUBMIT_RESPONSE_ID;
		}
		if (BillPaymentConstants.PAYEE_TYPE_MOBILE_TOPUP.equals(payeeType)) {
			resId = ResponseIdConstants.MTP_PAYMENT_TRANSFER_FORM_SUBMIT_RESPONSE_ID;
		}

		if (BillPaymentConstants.PAYEE_TYPE_BARCLAY_CARD.equals(payeeType)) {
			resId = ResponseIdConstants.BCD_PAYMENT_TRANSFER_FORM_SUBMIT_RESPONSE_ID;
		}

		if (BillPaymentConstants.PAYEE_TYPE_CREDIT_CARD_PAYMENT
				.equals(payeeType)) {
			resId = ResponseIdConstants.CCP_PAYMENT_TRANSFER_FORM_SUBMIT_RESPONSE_ID;
		}

		return resId;
	}

	protected void populatePayLoad(BMBPayload bmbPayload,
			ResponseContext... responseContexts) {
		PaymentTransferJSONBean paymentTransferJSONBean = null;
		GetSelectedAccountOperationResponse selSourceAcctOpResp = (GetSelectedAccountOperationResponse) responseContexts[0];
		BillPayAmountValidationOperationResponse billAmountValidationOperationResponse = (BillPayAmountValidationOperationResponse) responseContexts[1];
		FormValidateOperationResponse formValidateOperationResponse = (FormValidateOperationResponse) responseContexts[2];

		String casaStrFlag = null;
		if (selSourceAcctOpResp != null && selSourceAcctOpResp.isSuccess()) {
			Context context = selSourceAcctOpResp.getContext();
			Map<String, Object> contextMap = context.getContextMap();
			paymentTransferJSONBean = new PaymentTransferJSONBean();
			CustomerAccountDTO account = selSourceAcctOpResp.getSelectedAcct();

			if (account instanceof CreditCardAccountDTO) {
				String currency = account.getCurrency();

				CreditCardAccountDTO ccaccount = (CreditCardAccountDTO) account;

				CCAccountJSONBean accountJSONBean = new CCAccountJSONBean();
				accountJSONBean.setPrdTyp(account.getProductCode());
				accountJSONBean.setActNo(account.getAccountNumber());

				// Set Masked account number.
				// accountJSONBean.setMkdActNo(getMaskedAccountNumber(account.getBranchCode(),account.getAccountNumber()));

				accountJSONBean
						.setMkdCrdNo(getMaskedCardNumber(((CreditCardAccountDTO) account)
								.getPrimary().getCardNumber()));

				accountJSONBean.setCurr(account.getCurrency());
				accountJSONBean.setCrLmt(BMGFormatUtility.getJSONAmount(
						currency, BMGFormatUtility.getFormattedAmount(ccaccount
								.getCreditLimit())));
				accountJSONBean.setAvlCrLmt(BMGFormatUtility.getJSONAmount(
						currency, BMGFormatUtility.getFormattedAmount(ccaccount
								.getAvailableCreditLimit())));
				accountJSONBean.setCurBal(BMGFormatUtility.getJSONAmount(
						currency, getPositiveCurrentBalance(ccaccount
								.getCurrentBalance())));
				paymentTransferJSONBean.setFrActNo(accountJSONBean);
			} else {
				// Set CASA identifier for CBP 26/05/2017
				casaStrFlag = "CASA Account";

				String currency = account.getCurrency();

				AccountJSONBean accountJSONBean = new AccountJSONBean();
				accountJSONBean.setPrdTyp(account.getDesc());
				accountJSONBean.setActNo(account.getAccountNumber());
				// Set Masked account number.
				accountJSONBean.setMkdActNo(getMaskedAccountNumber(account
						.getBranchCode(), account.getAccountNumber()));
				accountJSONBean.setCurr(account.getCurrency());
				accountJSONBean.setAvlbBal(BMGFormatUtility.getJSONAmount(
						currency, BMGFormatUtility.getFormattedAmount(account
								.getAvailableBalance())));
				paymentTransferJSONBean.setFrActNo(accountJSONBean);
			}

			String txnNote = contextMap.get("txnNot").toString();

			if (StringUtils.isNotBlank(txnNote)) {
				// Added convertStringToUnicode() to convert to unicode
				//paymentTransferJSONBean
				//		.setTxnNot(convertStringToUnicode(txnNote));
				paymentTransferJSONBean
				.setTxnNot(txnNote);
			}

			BeneficiaryDTO beneficiaryDTO = billAmountValidationOperationResponse
					.getBeneficiaryDTO();
			PayeeInformationJSONModel benefiModel = null;
			paymentTransferJSONBean.setCurr(context.getLocalCurrency());
			if (!BillPaymentConstants.PAYEE_TYPE_CREDIT_CARD_PAYMENT
					.equals(formValidateOperationResponse.getTxnTyp())) {

				benefiModel = getBeneficiaryJSONModel(beneficiaryDTO,
						formValidateOperationResponse);
			} else {
				benefiModel = getCCPayeeJSONModel(beneficiaryDTO
						.getDestinationAccount(), formValidateOperationResponse);
			}
			// benefiModel.setTransactionLmt(BMGFormatUtility.getFormattedAmount(response.getAValidDailyLimit()));
			paymentTransferJSONBean.setPay(benefiModel);
			paymentTransferJSONBean.setTxnRefNo(context.getOrgRefNo());
			paymentTransferJSONBean
					.setPaySer((String) contextMap.get("paySer"));

			FxRateDTO fxRateDTO = formValidateOperationResponse.getFxRateDTO();
			if (fxRateDTO != null) {
				paymentTransferJSONBean.setFxRate(BMGFormatUtility
						.getFormattedAmount(fxRateDTO.getEffectiveFXRate()));
				AmountJSONModel eqAmt = new AmountJSONModel();
				eqAmt.setAmt(BMGFormatUtility.getFormattedAmount(fxRateDTO
						.getEquivalentAmount()));
				eqAmt.setCurr(account.getCurrency());
				paymentTransferJSONBean.setEqAmt(eqAmt);
			}
		}

		// PayBills for CASA - CBP 26/05/2017
		if(casaStrFlag != null && casaStrFlag.equals("CASA Account")){
			// MWallet change for CPB - 12/05/2017
			FormValidateOperationResponse formValidationOperationResponse = (FormValidateOperationResponse) responseContexts[2];
			AmountJSONModel jsonModel= new AmountJSONModel();
			if(formValidationOperationResponse.getTranFee()!=null){
				jsonModel.setCurr(formValidationOperationResponse.getTranFee().getCurrency()); 	//jsonModel.setCurr("KES")
				jsonModel.setAmt(formValidationOperationResponse.getTranFee().getAmount().toString());  //jsonModel.setAmt("15.00")
			}
			//Fields to be passed for MakeBillPaymentRequest CPB
			paymentTransferJSONBean.setTxnChargeAmt(jsonModel);	//responseModel.setChargeAmount(pesaLinkRetrievChargeOperationResponse.getChargeAmount());
			paymentTransferJSONBean.setFeeGLAccount(formValidationOperationResponse.getFeeGLAccount());
			paymentTransferJSONBean.setChargeNarration(formValidationOperationResponse.getChargeNarration());
			paymentTransferJSONBean.setTaxAmount(formValidationOperationResponse.getTaxAmount());
			paymentTransferJSONBean.setTaxGLAccount(formValidationOperationResponse.getTaxGLAccount());
			paymentTransferJSONBean.setExciseDutyNarration(formValidationOperationResponse.getExciseDutyNarration());
			paymentTransferJSONBean.setTypeCode(formValidationOperationResponse.getTypeCode());
			paymentTransferJSONBean.setValue(formValidationOperationResponse.getValue());
		}

		bmbPayload.setPayData(paymentTransferJSONBean);
	}

	private BeneficiaryJSONModel getBeneficiaryJSONModel(
			BeneficiaryDTO beneficiaryDTO,
			FormValidateOperationResponse formValidateOperationResponse) {
		BeneficiaryJSONModel benficiBean = new BeneficiaryJSONModel();
		if (beneficiaryDTO != null) {

			Context context = formValidateOperationResponse.getContext();
			benficiBean.setPayNckNam(beneficiaryDTO.getPayeeNickname());
			// BillerType
			// benficiBean.setBilrTyp(beneficiaryDTO.getBillerCategoryId());
			benficiBean.setBilrTyp(beneficiaryDTO.getBillerCategoryName());
			// Biller
			benficiBean.setBilrNam(beneficiaryDTO.getBillerName());
			ReferenceNumber refNo = new ReferenceNumber();
			ReferenceNumber wucContractRefNo = new ReferenceNumber();
			if ("Landline".equalsIgnoreCase(beneficiaryDTO.getBillerId())) {
				if (beneficiaryDTO.getBillRefNo1() != null
						&& beneficiaryDTO.getBillRefNo2() != null) {
					refNo.setLndLnNo(beneficiaryDTO.getBillRefNo1() + "-"
							+ beneficiaryDTO.getBillRefNo2());
					refNo.setDisplayLabel(getComponentLable(context, LANDLINE,
							SCREEN_ID));
				}

			}
			if (beneficiaryDTO.isMobileProvider()
					&& !"Landline".equalsIgnoreCase(beneficiaryDTO
							.getBillerId())) {
				if (beneficiaryDTO.getBillRefNo1() != null
						&& beneficiaryDTO.getBillRefNo2() != null) {
					refNo.setMobNo(beneficiaryDTO.getBillRefNo1() + "-"
							+ beneficiaryDTO.getBillRefNo2());
					refNo.setDisplayLabel(getComponentLable(context, MOBILE,
							SCREEN_ID));
				}
			}
			if (!beneficiaryDTO.isMobileProvider()) {
				if (beneficiaryDTO.getBillRefNo1() != null) {
					refNo.setPhNo(beneficiaryDTO.getBillRefNo1());
					refNo.setDisplayLabel(getComponentLable(context, PHONE_NO,
							SCREEN_ID));
				}

			}
			if (beneficiaryDTO.getRefNoKey1() != null) {
				refNo.setRefNo(beneficiaryDTO.getBillRefNo());
				refNo.setDisplayLabel(getComponentLable(context, beneficiaryDTO
						.getRefNoKey1(), SCREEN_ID));
			}
			// WUC-2 botswana biller change - 03/07/2017
			if(beneficiaryDTO.getBillerId() !=null && beneficiaryDTO.getBillerId().equals("WUC-2")){
				wucContractRefNo.setRefNo(beneficiaryDTO.getBillRefNo2());
				wucContractRefNo.setDisplayLabel("Contract Reference Number:");
				benficiBean.setWucContractRefNo(wucContractRefNo);
			}


			benficiBean.setRefNo(refNo);

			StringBuffer benefiStringBuffer = null;

			String adrln1 = beneficiaryDTO.getDestinationAddressLine1();
			String adrln2 = beneficiaryDTO.getDestinationAddressLine2();

			String adrln3 = beneficiaryDTO.getDestinationAddressLine3();

			if (StringUtils.isNotEmpty(adrln1)) {
				benefiStringBuffer = new StringBuffer();
				benefiStringBuffer.append(adrln1);
			}

			if (StringUtils.isNotEmpty(adrln2)) {
				benefiStringBuffer.append(", ").append(adrln2);
			}

			if (StringUtils.isNotEmpty(adrln3)) {
				benefiStringBuffer.append(", ").append(adrln3);
			}

			if (benefiStringBuffer != null) {
				benficiBean.setBillHldAdd(benefiStringBuffer.toString());
			}
			benficiBean.setPayId(beneficiaryDTO.getPayeeId());
			benficiBean.setBilrId(beneficiaryDTO.getBillerId());
			benficiBean.setBeneNam(beneficiaryDTO.getBeneficiaryName());
			benficiBean.setCrdNo(beneficiaryDTO.getCardNumber());

		}

		AmountJSONModel usAmt = new AmountJSONModel();
		usAmt.setAmt(BMGFormatUtility
				.getFormattedAmount(formValidateOperationResponse.getTxnAmt()
						.getAmount()));
		usAmt.setCurr(formValidateOperationResponse.getTxnAmt().getCurrency());
		benficiBean.setAmt(usAmt);

		// Set transaction fee and charge details

		if (formValidateOperationResponse.getTranFee() != null) {
			Amount tranFee = formValidateOperationResponse.getTranFee();
			AmountJSONModel feeAmt = new AmountJSONModel();
			feeAmt.setAmt(BMGFormatUtility.getFormattedAmount(tranFee
					.getAmount()));
			feeAmt.setCurr(tranFee.getCurrency());
			benficiBean.setFee(feeAmt);
		}
		return benficiBean;
	}

	private CCPayeeJSONModel getCCPayeeJSONModel(
			CustomerAccountDTO customerAccountDTO,
			FormValidateOperationResponse formValidateOperationResponse) {
		CCPayeeJSONModel ccPayeeJSONModel = new CCPayeeJSONModel();

		if (customerAccountDTO != null
				&& customerAccountDTO instanceof CreditCardAccountDTO) {

			CreditCardAccountDTO creditCardDestAcct = (CreditCardAccountDTO) customerAccountDTO;
			CreditCardAccountJSONBean creditAccountJSONBean = new CreditCardAccountJSONBean();
			AmountJSONModel curBal = new AmountJSONModel();
			curBal.setCurr(creditCardDestAcct.getCurrency());
			curBal.setAmt(getPositiveCurrentBalance(creditCardDestAcct
					.getCurrentBalance()));
			creditAccountJSONBean.setCurBal(curBal);
			creditAccountJSONBean.setCurr(creditCardDestAcct.getCurrency());

			AmountJSONModel minPayAmt = new AmountJSONModel();
			minPayAmt.setCurr(creditCardDestAcct.getCurrency());
			minPayAmt.setAmt(BMGFormatUtility
					.getFormattedAmount(creditCardDestAcct
							.getMinPaymentAmount()));
			creditAccountJSONBean.setMinPayAmt(minPayAmt);

			AmountJSONModel outStanBal = new AmountJSONModel();
			outStanBal.setCurr(creditCardDestAcct.getCurrency());
			outStanBal.setAmt(getPositiveCurrentBalance(creditCardDestAcct
					.getOutstandingBalance()));
			creditAccountJSONBean.setOutStndBal(outStanBal);

			creditAccountJSONBean.setPymntDueDt(creditCardDestAcct
					.getPaymentDueDate());
			creditAccountJSONBean
					.setToAcctDisName(getMaskedCardNumber(creditCardDestAcct
							.getPrimary().getCardNumber()));
			creditAccountJSONBean.setToAcct(creditCardDestAcct
					.getAccountNumber());
			ccPayeeJSONModel
					.setCreditCardAccountJSONBean(creditAccountJSONBean);

		}
		TransactionInformationRestBean trBean = new TransactionInformationRestBean();
		AmountJSONModel usAmt = new AmountJSONModel();
		usAmt.setAmt(BMGFormatUtility
				.getFormattedAmount(formValidateOperationResponse.getTxnAmt()
						.getAmount()));
		usAmt.setCurr(formValidateOperationResponse.getTxnAmt().getCurrency());

		trBean.setTxnAmt(usAmt);

		ccPayeeJSONModel.setTrRestBean(trBean);

		return ccPayeeJSONModel;
	}

	@Override
	public Object createMultipleJSONResponse(
			ResponseContext... responseContexts) {
		BMBPayloadHeader bmbPayloadHeader = null;
		BMBPayload bmbPayload = null;
		for (ResponseContext response : responseContexts) {
			if (response != null && !response.isSuccess()) {
				bmbPayloadHeader = createHeader(response,
						getResponseId(response.getTxnTyp()));
				break;
			}
		}

		if (bmbPayloadHeader != null) {
			bmbPayload = new BMBPayload(bmbPayloadHeader);
		} else {
			ResponseContext response = responseContexts[0];
			bmbPayload = new BMBPayload(createHeader(response,
					getResponseId(response.getTxnTyp())));
			populatePayLoad(bmbPayload, responseContexts);
		}

		return bmbPayload;
	}

}
