package com.barclays.bmg.service.sessionactivity.builder;

import java.util.ArrayList;
import java.util.List;

import com.barclays.bmg.chequebook.service.request.ChequeBookExecuteServiceRequest;
import com.barclays.bmg.chequebook.service.response.ChequeBookExecuteServiceResponse;
import com.barclays.bmg.constants.AuditConstant;
import com.barclays.bmg.constants.SessionActivityType;
import com.barclays.bmg.service.sessionactivity.bean.KeyValuePairBean;
import com.barclays.bmg.service.sessionactivity.dto.SessionActivityDTO;
import com.barclays.bmg.service.utils.BMGFormatUtils;

public class ChequeBookRequestSessionActivityBuilder extends BMGFormatUtils implements BMGSessionActivityBuilder{

	@Override
	public SessionActivityDTO build(Object[] args, Object result)
	{
		SessionActivityDTO sessionActivityDTO = new SessionActivityDTO();

		ChequeBookExecuteServiceRequest request = (ChequeBookExecuteServiceRequest)args[0];
		ChequeBookExecuteServiceResponse response = (ChequeBookExecuteServiceResponse)result;

		String actNo = request.getChequeBookRequestDTO().getAccount().getAccountNumber();
		String bkTyp = request.getChequeBookRequestDTO().getBookType();
		String bkSize = request.getChequeBookRequestDTO().getBookSize();

		String userId = request.getContext().getUserId();
		String refNo = request.getContext().getActivityRefNo();
		String sessionId = request.getContext().getSessionId();

		List<KeyValuePairBean> details = new ArrayList<KeyValuePairBean>();

		details.add(new KeyValuePairBean(AuditConstant.ACCTNO, getMaskedAccountNumber(null, actNo)));
		details.add(new KeyValuePairBean(AuditConstant.BKTYPE, bkTyp));
		details.add(new KeyValuePairBean(AuditConstant.BKSIZE, bkSize));

		if(response != null)
		{
			sessionActivityDTO.setTxnTime(response.getTxnDt());
		}
		sessionActivityDTO.setRefNo(refNo);
		sessionActivityDTO.setSessionId(sessionId);
		sessionActivityDTO.setTxnTyp(SessionActivityType.CHEQUE_BOOK_REQUEST);
		sessionActivityDTO.setUserId(userId);

		sessionActivityDTO.setTxnDetails(details);

		return sessionActivityDTO;
	}
}