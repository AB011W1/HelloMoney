package com.barclays.bmg.cashsend.dao.adapter;

import com.barclays.bem.EncryptCreditCardATMPin.CreditCardATMPin;
import com.barclays.bmg.cashsend.service.request.CashSendOneTimeExecuteServiceRequest;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.dto.CashSendRequestDTO;

public class CashSendEncryptionPayloadAdapter {

    private static final String PIN_LENGTH = "05"; // Make this configurable
    private static final String PIN_PADDING = "FFFFFFFFF"; // Make this configurable

    public CreditCardATMPin adaptPayLoad(WorkContext workContext) {
	DAOContext daoContext = (DAOContext) workContext;

	Object[] args = daoContext.getArguments();

	CreditCardATMPin pinObject = new CreditCardATMPin();

	CashSendOneTimeExecuteServiceRequest request = (CashSendOneTimeExecuteServiceRequest) args[0];

	CashSendRequestDTO cashSendRequestDTO = request.getCashSendRequestDTO();

	StringBuilder pinBlockBuffer = new StringBuilder();
	pinBlockBuffer.append(PIN_LENGTH);
	pinBlockBuffer.append(cashSendRequestDTO.getVPin());
	pinBlockBuffer.append(PIN_PADDING);
	String pinBlock = pinBlockBuffer.toString();

	pinObject.setClearPin(pinBlock);

	return pinObject;
    }
}