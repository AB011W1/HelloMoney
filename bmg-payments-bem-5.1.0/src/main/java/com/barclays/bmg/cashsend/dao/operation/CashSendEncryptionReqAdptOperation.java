package com.barclays.bmg.cashsend.dao.operation;

import com.barclays.bem.EncryptCreditCardATMPin.CreditCardATMPinRequest;
import com.barclays.bmg.cashsend.dao.adapter.CashSendEncryptionPayloadAdapter;
import com.barclays.bmg.cashsend.dao.adapter.CashSendEncryptionRequestHeaderAdapter;
import com.barclays.bmg.dao.core.context.WorkContext;

public class CashSendEncryptionReqAdptOperation {

	CashSendEncryptionRequestHeaderAdapter cashSendEncryptionRequestHeaderAdapter;
	CashSendEncryptionPayloadAdapter cashSendEncryptionPayloadAdapter;

    public CreditCardATMPinRequest adaptRequest(WorkContext workContext) {
    	CreditCardATMPinRequest encryptRequest = new CreditCardATMPinRequest();

    	encryptRequest.setRequestHeader(cashSendEncryptionRequestHeaderAdapter.buildRequestHeader(workContext));
    	encryptRequest.setCreditCardATMPin(cashSendEncryptionPayloadAdapter.adaptPayLoad(workContext));
	return encryptRequest;

    }

	public CashSendEncryptionRequestHeaderAdapter getCashSendEncryptionRequestHeaderAdapter() {
		return cashSendEncryptionRequestHeaderAdapter;
	}

	public void setCashSendEncryptionRequestHeaderAdapter(
			CashSendEncryptionRequestHeaderAdapter cashSendEncryptionRequestHeaderAdapter) {
		this.cashSendEncryptionRequestHeaderAdapter = cashSendEncryptionRequestHeaderAdapter;
	}

	public CashSendEncryptionPayloadAdapter getCashSendEncryptionPayloadAdapter() {
		return cashSendEncryptionPayloadAdapter;
	}

	public void setCashSendEncryptionPayloadAdapter(
			CashSendEncryptionPayloadAdapter cashSendEncryptionPayloadAdapter) {
		this.cashSendEncryptionPayloadAdapter = cashSendEncryptionPayloadAdapter;
	}


}
