package com.barclays.bmg.context;

public class ResponseContext {
    private Context context;
    private boolean success = true;
    private String resCde;
    private String resMsg;
    private String txnTyp;
    private String errTyp;

    public Context getContext() {
	return context;
    }

    public void setContext(Context context) {

	this.context = context;
    }

    public String getTxnTyp() {
	return txnTyp;
    }

    public void setTxnTyp(String txnTyp) {
	this.txnTyp = txnTyp;
    }

    public boolean isSuccess() {
	return success;
    }

    public void setSuccess(boolean success) {
	this.success = success;
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

    public String getErrTyp() {
	return errTyp;
    }

    public void setErrTyp(String errTyp) {
	this.errTyp = errTyp;
    }

}
