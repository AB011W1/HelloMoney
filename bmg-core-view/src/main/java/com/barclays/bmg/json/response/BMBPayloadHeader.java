package com.barclays.bmg.json.response;

import java.io.Serializable;

public class BMBPayloadHeader implements Serializable {

    private static final long serialVersionUID = -7357168153303862496L;

    private String resId;
    private String resCde;
    private String resMsg;
    private String serVer;
    private String expTrace;
    private String txnRefNo;

    public String getResId() {
	return resId;
    }

    public void setResId(String resId) {
	this.resId = resId;
    }

    public String getResCde() {
	return resCde;
    }

    public void setResCde(String resCde) {
	this.resCde = resCde;
    }

    public String getResMsg() {
	return resMsg;
    }

    public void setResMsg(String resMsg) {
	this.resMsg = resMsg;
    }

    public String getSerVer() {
	return serVer;
    }

    public void setSerVer(String serVer) {
	this.serVer = serVer;
    }

    public String getExpTrace() {
	return expTrace;
    }

    public void setExpTrace(String expTrace) {
	this.expTrace = expTrace;
    }

    public String getTxnRefNo() {
	return txnRefNo;
    }

    public void setTxnRefNo(String txnRefNo) {
	this.txnRefNo = txnRefNo;
    }

}
