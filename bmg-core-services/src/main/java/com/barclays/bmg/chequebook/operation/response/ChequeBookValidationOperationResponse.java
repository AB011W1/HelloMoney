package com.barclays.bmg.chequebook.operation.response;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.CustomerAccountDTO;

public class ChequeBookValidationOperationResponse extends ResponseContext {

    private static final long serialVersionUID = -6290236435090955511L;

    private CustomerAccountDTO actNo;
    private String txnRefNo;
    private String bkSize;
    private String bkTyp;
    private String brnCde;
    private String brnNam;

    public CustomerAccountDTO getActNo() {
	return actNo;
    }

    public void setActNo(CustomerAccountDTO actNo) {
	this.actNo = actNo;
    }

    public String getTxnRefNo() {
	return txnRefNo;
    }

    public void setTxnRefNo(String txnRefNo) {
	this.txnRefNo = txnRefNo;
    }

    public String getBkSize() {
	return bkSize;
    }

    public void setBkSize(String bkSize) {
	this.bkSize = bkSize;
    }

    public String getBkTyp() {
	return bkTyp;
    }

    public void setBkTyp(String bkTyp) {
	this.bkTyp = bkTyp;
    }

    public String getBrnCde() {
	return brnCde;
    }

    public void setBrnCde(String brnCde) {
	this.brnCde = brnCde;
    }

    public String getBrnNam() {
	return brnNam;
    }

    public void setBrnNam(String brnNam) {
	this.brnNam = brnNam;
    }

}
