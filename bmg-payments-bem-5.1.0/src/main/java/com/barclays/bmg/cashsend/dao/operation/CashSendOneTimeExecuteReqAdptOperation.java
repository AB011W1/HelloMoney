package com.barclays.bmg.cashsend.dao.operation;

import com.barclays.bem.SendCash.SendCashRequest;
import com.barclays.bmg.cashsend.dao.adapter.CashSendOneTimeExecutePayloadAdapter;
import com.barclays.bmg.cashsend.dao.adapter.CashSendOneTimeExecuteRequestHeaderAdapter;
import com.barclays.bmg.dao.core.context.WorkContext;

public class CashSendOneTimeExecuteReqAdptOperation {

    CashSendOneTimeExecuteRequestHeaderAdapter cashSendOneTimeExecuteRequestHeaderAdapter;
    CashSendOneTimeExecutePayloadAdapter cashSendOneTimeExecutePayloadAdapter;

    public SendCashRequest adaptRequest(WorkContext workContext) {
	SendCashRequest sendCashRequest = new SendCashRequest();

	sendCashRequest.setRequestHeader(cashSendOneTimeExecuteRequestHeaderAdapter.buildRequestHeader(workContext));
	sendCashRequest.setSendCashInfo(cashSendOneTimeExecutePayloadAdapter.adaptPayLoad(workContext));
	return sendCashRequest;

    }

    public CashSendOneTimeExecutePayloadAdapter getCashSendOneTimeExecutePayloadAdapter() {
	return cashSendOneTimeExecutePayloadAdapter;
    }

    public CashSendOneTimeExecuteRequestHeaderAdapter getCashSendOneTimeExecuteRequestHeaderAdapter() {
        return cashSendOneTimeExecuteRequestHeaderAdapter;
    }

    public void setCashSendOneTimeExecuteRequestHeaderAdapter(CashSendOneTimeExecuteRequestHeaderAdapter cashSendOneTimeExecuteRequestHeaderAdapter) {
        this.cashSendOneTimeExecuteRequestHeaderAdapter = cashSendOneTimeExecuteRequestHeaderAdapter;
    }

    public void setCashSendOneTimeExecutePayloadAdapter(CashSendOneTimeExecutePayloadAdapter cashSendOneTimeExecutePayloadAdapter) {
	this.cashSendOneTimeExecutePayloadAdapter = cashSendOneTimeExecutePayloadAdapter;
    }

}
