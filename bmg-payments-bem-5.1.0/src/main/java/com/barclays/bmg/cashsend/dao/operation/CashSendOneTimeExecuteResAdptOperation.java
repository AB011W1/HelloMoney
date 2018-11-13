package com.barclays.bmg.cashsend.dao.operation;

import java.util.Date;

import com.barclays.bem.SendCash.SendCashResponse;
import com.barclays.bmg.cashsend.service.request.CashSendOneTimeExecuteServiceRequest;
import com.barclays.bmg.cashsend.service.response.CashSendOneTimeExecuteServiceResponse;
import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.dao.operation.accountservices.AbstractResAdptOperation;

public class CashSendOneTimeExecuteResAdptOperation extends AbstractResAdptOperation {

    public CashSendOneTimeExecuteServiceResponse adaptResponse(WorkContext workContext, Object obj) {

	SendCashResponse response = (SendCashResponse) obj;
	CashSendOneTimeExecuteServiceResponse cashSendExecuteCashSendOneTimeExecuteServiceResponse = new CashSendOneTimeExecuteServiceResponse();
	String resCode = response.getResponseHeader().getServiceResStatus().getServiceResCode();

	DAOContext daoContext = (DAOContext) workContext;
	Object[] args = daoContext.getArguments();
	CashSendOneTimeExecuteServiceRequest request = (CashSendOneTimeExecuteServiceRequest) args[0];

	cashSendExecuteCashSendOneTimeExecuteServiceResponse.setResCde(resCode);
	cashSendExecuteCashSendOneTimeExecuteServiceResponse.setServiceResponse(response.getResponseHeader().getServiceResStatus()
		.getServiceResCode());

	if (response != null && checkResponseHeader(response.getResponseHeader())) {
	    cashSendExecuteCashSendOneTimeExecuteServiceResponse.setSendCashResInfo(response.getSendCashResInfo());
	    if (ResponseCodeConstants.SUCCESS_RES_CODE.equals(resCode)) {
		cashSendExecuteCashSendOneTimeExecuteServiceResponse.setResMsg(ResponseCodeConstants.SUCCESS_TXN_RESPONSE_MESSAGE);
	    } else {
		cashSendExecuteCashSendOneTimeExecuteServiceResponse.setResMsg(ResponseCodeConstants.SUBMITTED_TXN_RESPONSE_MESSAGE);
	    }
	    cashSendExecuteCashSendOneTimeExecuteServiceResponse.setTxnRefNo(request.getContext().getActivityRefNo());
	    cashSendExecuteCashSendOneTimeExecuteServiceResponse.setTxnDt(new Date());
	} else {
	    cashSendExecuteCashSendOneTimeExecuteServiceResponse.setSuccess(Boolean.FALSE);
	}

	return cashSendExecuteCashSendOneTimeExecuteServiceResponse;
    }

}
