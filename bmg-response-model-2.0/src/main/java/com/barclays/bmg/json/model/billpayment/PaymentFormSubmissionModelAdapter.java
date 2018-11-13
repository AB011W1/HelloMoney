package com.barclays.bmg.json.model.billpayment;

import org.apache.commons.lang.StringUtils;

import com.barclays.bmg.dto.Amount;
import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.dto.CreditCardAccountDTO;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.json.model.AmountJSONModel;
import com.barclays.bmg.json.model.BMGJSONAdapter;
import com.barclays.bmg.json.response.BMBPayloadData;
import com.barclays.bmg.operation.response.billpayment.PaymentFormSubmissionOperationResponse;
import com.barclays.bmg.utils.BMGFormatUtility;

public class PaymentFormSubmissionModelAdapter implements BMGJSONAdapter {

	@Override
	public BMBPayloadData adaptToJSONModel(Object obj) {
		PaymentTransferJSONBean paymentTransferJSONBean = new PaymentTransferJSONBean();

		PaymentFormSubmissionOperationResponse response = (PaymentFormSubmissionOperationResponse) obj;

		CustomerAccountDTO account = response.getFromAccount();
		String currency = account.getCurrency();

		AccountJSONBean accountJSONBean = new AccountJSONBean();
		accountJSONBean.setPrdTyp(account.getDesc());
		accountJSONBean.setActNo(account.getAccountNumber());
		// Set Masked account number.
		accountJSONBean.setMkdActNo(account.getAccountNumber());
		accountJSONBean.setCurr(account.getCurrency());
		accountJSONBean.setAvlbBal(BMGFormatUtility.getJSONAmount(currency,
				BMGFormatUtility.getFormattedAmount(account
						.getAvailableBalance())));
		// paymentTransferJSONBean.add(accountJSONBean);

		paymentTransferJSONBean.setFrActNo(accountJSONBean);

		BeneficiaryDTO beneficiaryDTO = response.getBeneficiaryDTO();
		PayeeInformationJSONModel benefiModel = null;
		if (beneficiaryDTO != null) {
			paymentTransferJSONBean.setCurr(beneficiaryDTO.getCurrency());
			benefiModel = getBeneficiaryJSONModel(beneficiaryDTO, response);
		} else {
			benefiModel = getCCPayeeJSONModel(response.getDestCreditCardAcct(),
					response);
		}
		// benefiModel.setTransactionLmt(BMGFormatUtility.getFormattedAmount(response.getAValidDailyLimit()));
		paymentTransferJSONBean.setPay(benefiModel);
		paymentTransferJSONBean.setTxnRefNo(response.getTxnRefNo());

		return paymentTransferJSONBean;

	}

	private BeneficiaryJSONModel getBeneficiaryJSONModel(
			BeneficiaryDTO beneficiaryDTO,
			PaymentFormSubmissionOperationResponse response) {
		BeneficiaryJSONModel benficiBean = new BeneficiaryJSONModel();
		if (beneficiaryDTO != null) {

			benficiBean.setPayNckNam(beneficiaryDTO.getPayeeNickname());
			// BillerType
			// benficiBean.setBilrTyp(beneficiaryDTO.getBillerCategoryId());
			benficiBean.setBilrTyp(beneficiaryDTO.getBillerCategoryName());
			// Biller
			benficiBean.setBilrNam(beneficiaryDTO.getBillerName());
			ReferenceNumber refNo = new ReferenceNumber();

			if ("Landline".equalsIgnoreCase(beneficiaryDTO.getBillerId())) {
				refNo.setLndLnNo(beneficiaryDTO.getBillRefNo1() + "-"
						+ beneficiaryDTO.getBillRefNo2());
			}
			if (beneficiaryDTO.isMobileProvider()
					&& !"Landline".equalsIgnoreCase(beneficiaryDTO
							.getBillerId())) {
				refNo.setPhNo(beneficiaryDTO.getBillRefNo1() + "-"
						+ beneficiaryDTO.getBillRefNo2());
			}
			if (!beneficiaryDTO.isMobileProvider()) {
				refNo.setPhNo(beneficiaryDTO.getBillRefNo1());
			}
			benficiBean.setRefNo(refNo);

			StringBuffer benefiStringBuffer = new StringBuffer();

			String adrln1 = beneficiaryDTO.getDestinationAddressLine1();
			String adrln2 = beneficiaryDTO.getDestinationAddressLine2();
			String adrln3 = beneficiaryDTO.getDestinationAddressLine3();

			if (StringUtils.isNotEmpty(adrln1)) {
				benefiStringBuffer.append(adrln1);
			}

			if (StringUtils.isNotEmpty(adrln2)) {
				benefiStringBuffer.append(", ").append(adrln2);
			}

			if (StringUtils.isNotEmpty(adrln3)) {
				benefiStringBuffer.append(", ").append(adrln3);
			}

			benficiBean.setBillHldAdd(benefiStringBuffer.toString());
			benficiBean.setPayId(beneficiaryDTO.getPayeeId());
			benficiBean.setBilrId(beneficiaryDTO.getBillerId());

		}

		AmountJSONModel usAmt = new AmountJSONModel();
		usAmt.setAmt(BMGFormatUtility.getFormattedAmount(response.getTxnAmt()));
		// TODO
		// usAmt.setCurrency(currency);
		benficiBean.setAmt(usAmt);

		// Set transaction fee and charge details

		if (response.getTranFee() != null) {
			Amount tranFee = response.getTranFee();
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
			PaymentFormSubmissionOperationResponse response) {
		CCPayeeJSONModel ccPayeeJSONModel = new CCPayeeJSONModel();

		if (customerAccountDTO != null
				&& customerAccountDTO instanceof CreditCardAccountDTO) {

			CreditCardAccountDTO creditCardDestAcct = (CreditCardAccountDTO) customerAccountDTO;
			CreditCardAccountJSONBean creditAccountJSONBean = new CreditCardAccountJSONBean();
			AmountJSONModel curBal = new AmountJSONModel();
			curBal.setCurr(creditCardDestAcct.getCurrency());
			curBal.setAmt(creditCardDestAcct.getCurrentBalance().toString());
			creditAccountJSONBean.setCurBal(curBal);
			creditAccountJSONBean.setCurr(creditCardDestAcct.getCurrency());

			AmountJSONModel minPayAmt = new AmountJSONModel();
			minPayAmt.setCurr(creditCardDestAcct.getCurrency());
			minPayAmt.setAmt(creditCardDestAcct.getMinPaymentAmount()
					.toString());
			creditAccountJSONBean.setMinPayAmt(minPayAmt);

			AmountJSONModel outStanBal = new AmountJSONModel();
			outStanBal.setCurr(creditCardDestAcct.getCurrency());
			outStanBal.setAmt(creditCardDestAcct.getOutstandingBalance()
					.toString());
			creditAccountJSONBean.setOutStndBal(outStanBal);

			creditAccountJSONBean.setPymntDueDt(creditCardDestAcct
					.getPaymentDueDate());
			// TODO MAsked Account number
			creditAccountJSONBean.setToAcctDisName(creditCardDestAcct
					.getAccountNumber());
			creditAccountJSONBean.setToAcct(creditCardDestAcct
					.getAccountNumber());
			ccPayeeJSONModel
					.setCreditCardAccountJSONBean(creditAccountJSONBean);

		}
		TransactionInformationRestBean trBean = new TransactionInformationRestBean();
		AmountJSONModel usAmt = new AmountJSONModel();
		usAmt.setAmt(BMGFormatUtility.getFormattedAmount(response.getTxnAmt()));
		// TODO
		// usAmt.setCurrency(currency);
		trBean.setTxnAmt(usAmt);

		// Set transaction fee and charge details

		ccPayeeJSONModel.setTrRestBean(trBean);

		return ccPayeeJSONModel;
	}

}
