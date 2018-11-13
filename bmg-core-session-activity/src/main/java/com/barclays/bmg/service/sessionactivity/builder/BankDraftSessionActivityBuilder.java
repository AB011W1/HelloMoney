package com.barclays.bmg.service.sessionactivity.builder;

import java.util.ArrayList;
import java.util.List;

import com.barclays.bmg.dto.BankDraftTransactionDTO;
import com.barclays.bmg.service.request.bankdraft.PurchaseBankDraftServiceRequest;
import com.barclays.bmg.service.response.bankdraft.PurchaseBankDraftServiceResponse;
import com.barclays.bmg.service.sessionactivity.bean.KeyValuePairBean;
import com.barclays.bmg.service.sessionactivity.dto.SessionActivityDTO;
import com.barclays.bmg.service.utils.BMGFormatUtils;
import com.barclays.bmg.utils.BMGFormatUtility;

public class BankDraftSessionActivityBuilder extends BMGFormatUtils implements
		BMGSessionActivityBuilder {

	@Override
	public SessionActivityDTO build(Object[] args, Object result) {

		SessionActivityDTO sessionActivityDTO = new SessionActivityDTO();
		PurchaseBankDraftServiceRequest bankDraftServiceRequest = (PurchaseBankDraftServiceRequest) args[0];
		PurchaseBankDraftServiceResponse purBankDraftServiceResponse = (PurchaseBankDraftServiceResponse) result;

		BankDraftTransactionDTO bankDraftTransactionDTO = bankDraftServiceRequest
				.getBankDraftTransactionDTO();

		List<KeyValuePairBean> details = new ArrayList<KeyValuePairBean>();

		details.add(new KeyValuePairBean("accountNumber",
				getMaskedAccountNumber(null, bankDraftTransactionDTO
						.getSourceAcct().getAccountNumber())));
		details.add(new KeyValuePairBean("amount", BMGFormatUtility
				.getFormattedAmount(bankDraftTransactionDTO.getTxnAmt()
						.getAmount())));
		details.add(new KeyValuePairBean("beneficiaryName",
				bankDraftTransactionDTO.getBeneficiaryDTO()
						.getBeneficiaryName()));
		details.add(new KeyValuePairBean("deliveryType",
				bankDraftTransactionDTO.getDeliveryType()));
		sessionActivityDTO.setTxnTime(purBankDraftServiceResponse
				.getTransactionDate());
		sessionActivityDTO.setRefNo(bankDraftServiceRequest.getContext()
				.getActivityRefNo());
		sessionActivityDTO.setSessionId(bankDraftServiceRequest.getContext()
				.getSessionId());
		sessionActivityDTO.setTxnTyp(bankDraftTransactionDTO.getTxnType());
		sessionActivityDTO.setTxnDetails(details);
		sessionActivityDTO.setUserId(bankDraftServiceRequest.getContext()
				.getUserId());
		return sessionActivityDTO;
	}
}
