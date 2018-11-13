package com.barclays.bmg.service.sessionactivity.builder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.barclays.bmg.service.request.InternationalFundTransferServiceRequest;
import com.barclays.bmg.service.response.InternationalFundTransferServiceResponse;
import com.barclays.bmg.service.sessionactivity.bean.KeyValuePairBean;
import com.barclays.bmg.service.sessionactivity.dto.SessionActivityDTO;
import com.barclays.bmg.service.utils.BMGFormatUtils;

public class InternationalFundTransferSessionActivityBuilder extends
		BMGFormatUtils implements BMGSessionActivityBuilder {

	@Override
	public SessionActivityDTO build(Object[] args, Object result) {
		SessionActivityDTO sessionActivityDTO = new SessionActivityDTO();

		InternationalFundTransferServiceRequest request = (InternationalFundTransferServiceRequest) args[0];

		InternationalFundTransferServiceResponse response = (InternationalFundTransferServiceResponse) result;

		String beneName = request.getBeneficiaryDTO().getBeneficiaryName();
		String fromActNo = request.getSourceAcct().getAccountNumber();
		String amount = request.getTxnAmt().getAmount().toString();
		String note = request.getRemPart1() + " " + request.getRemPart2() + " "
				+ request.getRemPart3() + " " + request.getRemPart4();

		String userId = request.getContext().getUserId();
		String refNo = request.getContext().getActivityRefNo();
		String sessionId = request.getContext().getSessionId();
		Date txnTmDt = response.getTrnDate();
		String txnType = request.getTxnType();

		List<KeyValuePairBean> details = new ArrayList<KeyValuePairBean>();

		details.add(new KeyValuePairBean("BeneficiaryName", beneName));
		details.add(new KeyValuePairBean("FromAccount", getMaskedAccountNumber(
				null, fromActNo)));
		details.add(new KeyValuePairBean("Amount", amount));
		details.add(new KeyValuePairBean("Note", note));

		sessionActivityDTO.setTxnTime(txnTmDt);
		sessionActivityDTO.setRefNo(refNo);
		sessionActivityDTO.setSessionId(sessionId);
		// sessionActivityDTO.setTxnTyp(SessionActivityType.FUND_TRANSFER_INTERNATIONAL);
		sessionActivityDTO.setTxnTyp(txnType);
		sessionActivityDTO.setUserId(userId);

		sessionActivityDTO.setTxnDetails(details);

		return sessionActivityDTO;
	}

}
