package com.barclays.bmg.json.model.builder.billpayment;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.barclays.bmg.constants.BillPaymentConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.context.BMBContextHolder;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.dto.CreditCardAccountDTO;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.json.model.AmountJSONModel;
import com.barclays.bmg.json.model.billpayment.AccountJSONBean;
import com.barclays.bmg.json.model.billpayment.BeneficiaryJSONModel;
import com.barclays.bmg.json.model.billpayment.CCAccountJSONBean;
import com.barclays.bmg.json.model.billpayment.PaymentTransferJSONBean;
import com.barclays.bmg.json.model.billpayment.ReferenceNumber;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadHeader;
import com.barclays.bmg.operation.response.fundtransfer.external.CheckInqueryBillOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.external.MergeBillerInfoOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.external.RetrieveAcctListOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.external.RetrievePayeeInfoOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.external.TransactionLimitOperationResponse;
import com.barclays.bmg.utils.BMGFormatUtility;

public class RetreivePayeeInfoJSONBldr extends BMBMultipleResponseJSONBuilder {

	private static final String SCREEN_ID = "PMT_BP_FACADE";
	private static final String LANDLINE = "Landline";
	private static final String PHONE_NO = "ReferenceNumber";
	private static final String MOBILE = "PhoneNumberInputText";

	protected void populatePayLoad(BMBPayload bmbPayload,
			ResponseContext... responseContexts) {
		PaymentTransferJSONBean paymentTransferJSONBean = null;
		RetrievePayeeInfoOperationResponse retrievePayeeInfoOperationResponse = (RetrievePayeeInfoOperationResponse) responseContexts[0];
		RetrieveAcctListOperationResponse retrieveAcctListOperationResponse = (RetrieveAcctListOperationResponse) responseContexts[1];
		MergeBillerInfoOperationResponse mergeBillerInfoOperationResponse = (MergeBillerInfoOperationResponse) responseContexts[2];
		CheckInqueryBillOperationResponse checkInqueryBillOperationResponse = (CheckInqueryBillOperationResponse) responseContexts[3];
		TransactionLimitOperationResponse transactionLimitOperationResponse = (TransactionLimitOperationResponse) responseContexts[4];
		if (mergeBillerInfoOperationResponse != null) {

			paymentTransferJSONBean = new PaymentTransferJSONBean();
			List<? extends CustomerAccountDTO> accountList = retrieveAcctListOperationResponse
					.getAcctList();
			if (accountList != null) {
				for (CustomerAccountDTO account : accountList) {

					if (account instanceof CreditCardAccountDTO) {
						String currency = account.getCurrency();

						CreditCardAccountDTO ccaccount = (CreditCardAccountDTO) account;

						CCAccountJSONBean accountJSONBean = new CCAccountJSONBean();
						accountJSONBean.setPrdTyp(account.getProductCode());
						accountJSONBean.setActNo(getCorrelationId(account
								.getAccountNumber(),
								retrieveAcctListOperationResponse));

						// Set Masked account number.
						// accountJSONBean.setMkdActNo(getMaskedAccountNumber(account.getBranchCode(),account.getAccountNumber()));



						if(branchCodeCountryList.contains(BMBContextHolder.getContext().getBusinessId())){
						    accountJSONBean.setMkdActNo(getMaskedAccountNumber(
								account.getBranchCode(), account
										.getAccountNumber()));
						    }
						    else {
							accountJSONBean.setMkdActNo(getMaskedAccountNumber(
								null, account
										.getAccountNumber()));
						    }


						accountJSONBean
								.setMkdCrdNo(getMaskedCardNumber(((CreditCardAccountDTO) account)
										.getPrimary().getCardNumber()));

						accountJSONBean.setCurr(account.getCurrency());
						accountJSONBean.setCrLmt(BMGFormatUtility
								.getJSONAmount(currency, BMGFormatUtility
										.getFormattedAmount(ccaccount
												.getCreditLimit())));
						accountJSONBean.setAvlCrLmt(BMGFormatUtility
								.getJSONAmount(currency, BMGFormatUtility
										.getFormattedAmount(ccaccount
												.getAvailableCreditLimit())));
						accountJSONBean.setCurBal(BMGFormatUtility
								.getJSONAmount(currency,
										getPositiveCurrentBalance(ccaccount
												.getCurrentBalance())));
						paymentTransferJSONBean.add(accountJSONBean);
					} else {
						String currency = account.getCurrency();

						AccountJSONBean accountJSONBean = new AccountJSONBean();
						accountJSONBean.setPrdTyp(account.getDesc());
						accountJSONBean.setActNo(getCorrelationId(account
								.getAccountNumber(),
								retrieveAcctListOperationResponse));
						// Set Masked account number.

						/*accountJSONBean.setMkdActNo(getMaskedAccountNumber(
								account.getBranchCode(), account
										.getAccountNumber()));*/
						if(branchCodeCountryList.contains(BMBContextHolder.getContext().getBusinessId())){
						accountJSONBean.setMkdActNo(getMaskedAccountNumber(
								account.getBranchCode(), account
										.getAccountNumber()));
						    }
						    else {
							accountJSONBean.setMkdActNo(getMaskedAccountNumber(
								null, account
										.getAccountNumber()));
						    }




						accountJSONBean.setCurr(account.getCurrency());
						accountJSONBean.setAvlbBal(BMGFormatUtility
								.getJSONAmount(currency, BMGFormatUtility
										.getFormattedAmount(account
												.getAvailableBalance())));
						accountJSONBean.setPriInd(account.getPriInd());

						paymentTransferJSONBean.add(accountJSONBean);
					}

				}
			}
			BeneficiaryDTO beneficiaryDTO = retrievePayeeInfoOperationResponse
					.getBeneficiaryDTO();

			paymentTransferJSONBean.setCurr(beneficiaryDTO.getCurrency());
			BeneficiaryJSONModel benefiModel = getBeneficiaryJSONModel(
					beneficiaryDTO, retrievePayeeInfoOperationResponse);

			if (checkInqueryBillOperationResponse.getMinBillAmt() != null) {
				AmountJSONModel bilrMinAmt = new AmountJSONModel();
				bilrMinAmt.setAmt(checkInqueryBillOperationResponse
						.getMinBillAmt().toString());
				bilrMinAmt.setCurr(checkInqueryBillOperationResponse
						.getContext().getLocalCurrency());
				benefiModel.setBilrMinAmt(bilrMinAmt);
			}
			if (checkInqueryBillOperationResponse.getMaxBilAmt() != null) {
				AmountJSONModel bilrMaxAmt = new AmountJSONModel();
				bilrMaxAmt.setAmt(checkInqueryBillOperationResponse
						.getMaxBilAmt().toString());
				bilrMaxAmt.setCurr(checkInqueryBillOperationResponse
						.getContext().getLocalCurrency());
				benefiModel.setBilrMaxAmt(bilrMaxAmt);
			}

			if (checkInqueryBillOperationResponse.getOutBalAmt() != null) {
				AmountJSONModel outBalAmt = new AmountJSONModel();
				outBalAmt.setAmt(BMGFormatUtility
						.getFormattedAmount(checkInqueryBillOperationResponse
								.getOutBalAmt()));
				outBalAmt.setCurr(checkInqueryBillOperationResponse
						.getContext().getLocalCurrency());
				benefiModel.setOutBalAmt(outBalAmt);
			}

			AmountJSONModel txnLimit = null;
			if (transactionLimitOperationResponse.getAValidDailyLimit() != null) {
				txnLimit = new AmountJSONModel();
				txnLimit.setAmt(BMGFormatUtility
						.getFormattedAmount(transactionLimitOperationResponse
								.getAValidDailyLimit()));
				txnLimit.setCurr(transactionLimitOperationResponse.getContext()
						.getLocalCurrency());
			}
			benefiModel.setTxnLmt(txnLimit);
			AmountJSONModel intAmt = null;
			if (mergeBillerInfoOperationResponse.getIntervalAmt() != null) {
				intAmt = new AmountJSONModel();
				intAmt
						.setAmt(mergeBillerInfoOperationResponse
								.getIntervalAmt());
				intAmt.setCurr(mergeBillerInfoOperationResponse.getContext()
						.getLocalCurrency());
			}

			paymentTransferJSONBean.setIntAmt(intAmt);
			benefiModel.setBillHldrNam(beneficiaryDTO.getBeneficiaryName());
			paymentTransferJSONBean.setPay(benefiModel);
		}

		bmbPayload.setPayData(paymentTransferJSONBean);
	}

	private BeneficiaryJSONModel getBeneficiaryJSONModel(
			BeneficiaryDTO beneficiaryDTO, ResponseContext response) {
		BeneficiaryJSONModel benficiBean = new BeneficiaryJSONModel();
		if (beneficiaryDTO != null) {

			benficiBean.setPayNckNam(beneficiaryDTO.getPayeeNickname());
			// BillerType
			// benficiBean.setBilrTyp(beneficiaryDTO.getBillerCategoryId());
			benficiBean.setBilrTyp(beneficiaryDTO.getBillerCategoryName());
			// Biller
			benficiBean.setBilrNam(beneficiaryDTO.getBillerName());
			benficiBean.setBillerAreaCode(beneficiaryDTO.getBillRefNo2());
			Context context = response.getContext();
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

			String localCurr = context.getLocalCurrency();
			if (beneficiaryDTO.getMobileDenominaiton() != null) {
				List<AmountJSONModel> bilrAmtLst = new ArrayList<AmountJSONModel>();
				BigDecimal[] amtLst = beneficiaryDTO
						.getMobileDenominaitonList();
				for (BigDecimal amt : amtLst) {
					bilrAmtLst.add(BMGFormatUtility.getJSONAmount(localCurr,
							BMGFormatUtility.getFormattedAmount(amt)));
				}
				benficiBean.setBilrAmtLst(bilrAmtLst);
			}

		}
		return benficiBean;
	}

	/**
	 * @param payeeType
	 * @return Get the response id as per payee type
	 */
	private String getResponseId(String txnType) {

		String resId = ResponseIdConstants.BP_PAYMENT_TRANSFER_FORM_SUBMIT_RESPONSE_ID;
		if (BillPaymentConstants.TXN_FACADE_RTN_TYPE_BILL_PAYMENT
				.equals(txnType)) {
			resId = ResponseIdConstants.RETRIEVE_BP_PAYEE_INFORMATION_RESPONSE_ID;
		}
		if (BillPaymentConstants.TXN_FACADE_RTN_TYPE_MOBILE_TOPUP
				.equals(txnType)) {
			resId = ResponseIdConstants.RETRIEVE_MTP_PAYEE_INFORMATION_RESPONSE_ID;
		}

		if (BillPaymentConstants.TXN_FACADE_RTN_TYPE_BARCLAY_CARD
				.equals(txnType)) {
			resId = ResponseIdConstants.RETRIEVE_BCD_PAYEE_INFORMATION_RESPONSE_ID;
		}

		if (BillPaymentConstants.TXN_FACADE_RTN_TYPE_CREDIT_CARD
				.equals(txnType)) {
			resId = ResponseIdConstants.RETRIEVE_CCP_PAYEE_INFORMATION_RESPONSE_ID;
		}

		return resId;
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
