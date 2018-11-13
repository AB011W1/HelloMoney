package com.barclays.bmg.service.sessionactivity.builder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.barclays.bmg.context.Context;
import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.service.request.DomesticFundTransferServiceRequest;
import com.barclays.bmg.service.sessionactivity.bean.KeyValuePairBean;
import com.barclays.bmg.service.sessionactivity.dto.SessionActivityDTO;
import com.barclays.bmg.service.utils.BMGFormatUtils;
import com.barclays.bmg.utils.BMGFormatUtility;

public class FundTransferSessionActivityBuilder extends BMGFormatUtils implements
		BMGSessionActivityBuilder {

	@Override
	public SessionActivityDTO build(Object args[], Object result) {

		SessionActivityDTO sessionActivityDTO = new SessionActivityDTO();

		DomesticFundTransferServiceRequest domesticFundTransferServiceRequest = (DomesticFundTransferServiceRequest) args[0];

		Context context = domesticFundTransferServiceRequest.getContext();

		String fromActNo = domesticFundTransferServiceRequest.getSourceAcct().getAccountNumber();
		BeneficiaryDTO beneficiaryDTO = domesticFundTransferServiceRequest.getBeneficiaryDTO();
		String toActNo = beneficiaryDTO.getDestinationAccountNumber();
		String amount = BMGFormatUtility.getFormattedAmount(domesticFundTransferServiceRequest.getTxnAmt().getAmount());
		String note = domesticFundTransferServiceRequest.getTxnNot();
		String txnType = domesticFundTransferServiceRequest.getTxnTyp();
		List<KeyValuePairBean> details = new ArrayList<KeyValuePairBean>();
		String refNo = context.getActivityRefNo();
		// FIXME Need to implement this once we have the internal transfer
		// working
		// String transactionRoutineType = fundExecuteServiceRequest.get();
		// else
		// if(SessionActivityType.FUND_TRANSFER_OWN.equals(dto.getTransactionFacadeRoutineType())){
		details.add(new KeyValuePairBean("ToAccount", getMaskedAccountNumber(null,toActNo)));
		details.add(new KeyValuePairBean("FromAccount", getMaskedAccountNumber(null,fromActNo)));
		details.add(new KeyValuePairBean("Amount", amount));
		details.add(new KeyValuePairBean("Note", note));

		// * }
		/*
		 * elseif(SessionActivityType.FUND_TRANSFER_INTERNAL.equals(dto.
		 * getTransactionFacadeRoutineType())){ details.add(new
		 * KeyValuePairBean("BeneficiaryAccount", benActNo)); details.add(new
		 * KeyValuePairBean("BeneficiaryName", benName)); details.add(new
		 * KeyValuePairBean("ToAccount", toActNo)); details.add(new
		 * KeyValuePairBean("FromAccount", fromActNo)); details.add(new
		 * KeyValuePairBean("Amount", amount)); details.add(new
		 * KeyValuePairBean("Note", note)); }
		 */
		sessionActivityDTO.setTxnDetails(details);
		sessionActivityDTO.setUserId(context.getUserId());
		sessionActivityDTO.setBusinessId(context.getBusinessId());
		sessionActivityDTO.setSystemId(context.getSystemId());
		sessionActivityDTO.setTxnTyp(txnType);
		/*if(SessionActivityType.FUND_TRANSFER_OWN.equals(txnType)){
			sessionActivityDTO.setTxnTyp("Fund Transfer Between my CASA accounts");
		}else if(SessionActivityType.FUND_TRANSFER_INTERNAL.equals(txnType)){
			sessionActivityDTO.setTxnTyp("Fund Transfer to Domestic Barclays account");
		}*/

		sessionActivityDTO.setSessionId(context.getSessionId());

		sessionActivityDTO.setTxnTime(new Date());
		sessionActivityDTO.setRefNo(refNo);

		return sessionActivityDTO;
	}

}
