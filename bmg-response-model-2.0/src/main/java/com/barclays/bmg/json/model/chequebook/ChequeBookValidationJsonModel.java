package com.barclays.bmg.json.model.chequebook;

import com.barclays.bmg.json.model.CasaAccountJSONModel;
import com.barclays.bmg.json.response.BMBPayloadData;

public class ChequeBookValidationJsonModel extends BMBPayloadData {

    private static final long serialVersionUID = 5809897187497250063L;
    private CasaAccountJSONModel srcAct;
    private String bkSize;
    private String bkTyp;
    private String txnRefNo;
    private String brnCde;
    private String brnNam;

    public CasaAccountJSONModel getSrcAct() {
	return srcAct;
    }

    public void setSrcAct(CasaAccountJSONModel srcAct) {
	this.srcAct = srcAct;
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

    public String getTxnRefNo() {
	return txnRefNo;
    }

    public void setTxnRefNo(String txnRefNo) {
	this.txnRefNo = txnRefNo;
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
