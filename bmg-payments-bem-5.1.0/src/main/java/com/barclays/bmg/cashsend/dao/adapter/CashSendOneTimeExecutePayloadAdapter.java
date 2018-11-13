package com.barclays.bmg.cashsend.dao.adapter;

import com.barclays.bem.SendCash.SendCashDetails;
import com.barclays.bem.SendCash.SendCashInfo;
import com.barclays.bem.SendCash.SendCashRecipientInfo;
import com.barclays.bem.SendCash.SendCashSenderInfo;
import com.barclays.bmg.cashsend.service.request.CashSendOneTimeExecuteServiceRequest;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.dto.CashSendRequestDTO;

public class CashSendOneTimeExecutePayloadAdapter {

    public SendCashInfo adaptPayLoad(WorkContext workContext) {
	DAOContext daoContext = (DAOContext) workContext;

	Object[] args = daoContext.getArguments();

	SendCashInfo sendCashInfo = new SendCashInfo();

	CashSendOneTimeExecuteServiceRequest request = (CashSendOneTimeExecuteServiceRequest) args[0];

	CashSendRequestDTO cashSendRequestDTO = request.getCashSendRequestDTO();

	SendCashSenderInfo sendCashSenderInfo = new SendCashSenderInfo();
	sendCashSenderInfo.setSenderAccountNumber(cashSendRequestDTO.getActNo());
	sendCashSenderInfo.setSenderMobileNumber(cashSendRequestDTO.getSenderMobileNo());
	sendCashInfo.setSendCashSenderInfo(sendCashSenderInfo);

	SendCashRecipientInfo sendCashRecipientInfo = new SendCashRecipientInfo();
	sendCashRecipientInfo.setRecipientMobileNumber(cashSendRequestDTO.getRecipientMobileNo());
	sendCashInfo.setSendCashRecipientInfo(sendCashRecipientInfo);

	SendCashDetails sendCashDetails = new SendCashDetails();
	sendCashDetails.setVoucherAmount(Double.parseDouble(cashSendRequestDTO.getTxnAmt()));
	sendCashDetails.setVoucherCurrency(cashSendRequestDTO.getCurr());
	sendCashDetails.setVoucherPinBlock(cashSendRequestDTO.getVPin());
	sendCashDetails.setNarrationText(cashSendRequestDTO.getTxnNot());

	sendCashInfo.setSendCashDetails(sendCashDetails);

	return sendCashInfo;
    }

}
