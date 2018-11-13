package com.barclays.bmg.cashsend.service.response;

import java.util.Date;

import com.barclays.bem.SendCash.SendCashResInfo;
import com.barclays.bmg.context.ResponseContext;

public class CashSendOneTimeExecuteServiceResponse extends ResponseContext {

    private static final long serialVersionUID = -5955669287603440504L;

    private SendCashResInfo sendCashResInfo;
    private String txnRefNo;
    private Date txnDt;
    private String txnMsg;
    private String pin;
    private String serviceResponse;

    public String getServiceResponse() {
        return serviceResponse;
    }

    public void setServiceResponse(String serviceResponse) {
        this.serviceResponse = serviceResponse;
    }

    public String getTxnRefNo() {
	return txnRefNo;
    }

    public void setTxnRefNo(String txnRefNo) {
	this.txnRefNo = txnRefNo;
    }

    public Date getTxnDt() {
	return new Date(txnDt.getTime());
    }

    public void setTxnDt(Date txnDt) {
	if (txnDt == null) {
	    this.txnDt = null;
	} else {
	    this.txnDt = new Date(txnDt.getTime());
	}
    }

    public SendCashResInfo getSendCashResInfo() {
        return sendCashResInfo;
    }

    public void setSendCashResInfo(SendCashResInfo sendCashResInfo) {
        this.sendCashResInfo = sendCashResInfo;
    }

    public String getTxnMsg() {
	return txnMsg;
    }

    public void setTxnMsg(String txnMsg) {
	this.txnMsg = txnMsg;
    }

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

}
