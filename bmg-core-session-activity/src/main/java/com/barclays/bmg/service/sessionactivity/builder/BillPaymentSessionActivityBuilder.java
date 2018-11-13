package com.barclays.bmg.service.sessionactivity.builder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.barclays.bmg.constants.SessionActivityType;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.dto.CreditCardAccountDTO;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.service.request.PayBillServiceRequest;
import com.barclays.bmg.service.sessionactivity.bean.KeyValuePairBean;
import com.barclays.bmg.service.sessionactivity.dto.SessionActivityDTO;
import com.barclays.bmg.service.utils.BMGFormatUtils;

public class BillPaymentSessionActivityBuilder extends BMGFormatUtils implements
		BMGSessionActivityBuilder {

	@Override
	public SessionActivityDTO build(Object args[], Object result) {

		SessionActivityDTO sessionActivityDTO = new SessionActivityDTO();

		PayBillServiceRequest payBillServiceRequest = (PayBillServiceRequest) args[0];

		Context context = payBillServiceRequest.getContext();

		BeneficiaryDTO benDTO = (BeneficiaryDTO) payBillServiceRequest
				.getBeneficiaryDTO();

		String actNo = payBillServiceRequest.getFromAccount()
				.getAccountNumber();
		String billerName = benDTO.getBillerName();
		String amount = payBillServiceRequest.getBillAmount().toString();
		String note = payBillServiceRequest.getTxnNote();
		String refNo = context.getActivityRefNo();
		List<KeyValuePairBean> details = new ArrayList<KeyValuePairBean>();

		String payeeNickName = benDTO.getPayeeNickname();
		String benfName = benDTO.getBeneficiaryName();

		String transactionRoutineType = payBillServiceRequest.getBillPayTxnTyp();
		if (SessionActivityType.BILL_PAYMENT.equals(transactionRoutineType)) {
			details.add(new KeyValuePairBean("Biller", billerName));
			details.add(new KeyValuePairBean("FromAccount", getMaskedAccountNumber(null,actNo)));
			details.add(new KeyValuePairBean("Amount", amount));
			details.add(new KeyValuePairBean("Note", note));
			if (benDTO.getPayeeId() != null) {
				details
						.add(new KeyValuePairBean("PayeeNickname",
								payeeNickName));
			}
			//sessionActivityDTO.setTxnTyp("Pay bill");
		}

		else if (SessionActivityType.BARCLAY_CARD_PAYMENT
				.equals(transactionRoutineType)) {
			details.add(new KeyValuePairBean("FromAccount", getMaskedAccountNumber(null,actNo)));
			details.add(new KeyValuePairBean("CardNumber", benDTO
					.getCardNumber()));
			details.add(new KeyValuePairBean("CardHolder", benfName));
			if (null != payeeNickName) {
				details
						.add(new KeyValuePairBean("PayeeNickname",
								payeeNickName));
			}
			details.add(new KeyValuePairBean("Amount", amount));
			//sessionActivityDTO.setTxnTyp("Payment to third party Barclaycard");

		}else if (SessionActivityType.FUND_TRANSFER_CARD_PAYMENT
				.equals(transactionRoutineType)){
			details.add(new KeyValuePairBean("FromAccount", getMaskedAccountNumber(null,actNo)));
			CustomerAccountDTO custAcct = benDTO.getDestinationAccount();
			if(custAcct instanceof CreditCardAccountDTO){
				CreditCardAccountDTO creditAcct= (CreditCardAccountDTO) custAcct;
				details.add(new KeyValuePairBean("CardNumber", creditAcct
						.getPrimary().getCardNumber()));
			}
			details.add(new KeyValuePairBean("Amount", amount));
			//sessionActivityDTO.setTxnTyp("Payment to own Barclaycard");

		}else if (SessionActivityType.MOBILE_TOPUP.equals(transactionRoutineType)) {
			details.add(new KeyValuePairBean("Biller", billerName));
			details.add(new KeyValuePairBean("FromAccount", getMaskedAccountNumber(null,actNo)));
			details.add(new KeyValuePairBean("Amount", amount));
			details.add(new KeyValuePairBean("Note", note));
			if (benDTO.getPayeeId() != null) {
				details
						.add(new KeyValuePairBean("PayeeNickname",
								payeeNickName));
			}
			//sessionActivityDTO.setTxnTyp("Prepaid Mobile Recharge");
		}


		sessionActivityDTO.setTxnDetails(details);
		sessionActivityDTO.setUserId(context.getUserId());
		sessionActivityDTO.setBusinessId(context.getBusinessId());
		sessionActivityDTO.setSystemId(context.getSystemId());
		sessionActivityDTO.setTxnTyp(transactionRoutineType);
		sessionActivityDTO.setSessionId(context.getSessionId());
		sessionActivityDTO.setTxnTime(new Date());
		sessionActivityDTO.setRefNo(refNo);
		sessionActivityDTO.setAmount(payBillServiceRequest.getBillAmount());
		return sessionActivityDTO;
	}

}
