package com.barclays.bmg.operation.request;

public class RetailTxnCheckHeaderReq {

    private String frmAcctNo;
    private String frmAcctPrdCode;

    public String getFrmAcctNo() {
	return frmAcctNo;
    }

    public void setFrmAcctNo(String frmAcctNo) {
	this.frmAcctNo = frmAcctNo;
    }

    public String getFrmAcctPrdCode() {
	return frmAcctPrdCode;
    }

    public void setFrmAcctPrdCode(String frmAcctPrdCode) {
	this.frmAcctPrdCode = frmAcctPrdCode;
    }
}
