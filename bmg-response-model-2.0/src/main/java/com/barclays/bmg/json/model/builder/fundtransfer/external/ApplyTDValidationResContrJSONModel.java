package com.barclays.bmg.json.model.builder.fundtransfer.external;

import java.io.Serializable;

import com.barclays.bmg.json.model.CasaAccountJSONModel;
import com.barclays.bmg.json.response.BMBPayloadData;

public class ApplyTDValidationResContrJSONModel extends BMBPayloadData
		implements Serializable {

	private static final long serialVersionUID = 1695401155047913117L;
/*	private String acctNo;
*/ 	private String depositAmount;
    private String tenureMonths;
    private String tenureDays;
	private String txnRefNo;
	private String resMsg;
    private String resCode;
	private CasaAccountJSONModel srcAct;

	public String getResMsg() {
		return resMsg;
	}
	public void setResMsg(String resMsg) {
		this.resMsg = resMsg;
	}
	public String getResCode() {
		return resCode;
	}
	public void setResCode(String resCode) {
		this.resCode = resCode;
	}
	/*public String getAcctNo() {
		return acctNo;
	}
	public void setAcctNo(String acctNo) {
		this.acctNo = acctNo;
	}*/
	public String getDepositAmount() {
		return depositAmount;
	}
	public void setDepositAmount(String depositAmount) {
		this.depositAmount = depositAmount;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public void setTxnRefNo(String txnRefNo) {
		this.txnRefNo = txnRefNo;
	}
	public String getTxnRefNo() {
		return txnRefNo;
	}
	public void setSrcAct(CasaAccountJSONModel accountJSONModel) {
		this.srcAct = accountJSONModel;
	}
	public CasaAccountJSONModel getSrcAct() {
		return srcAct;
	}
	public String getTenureMonths() {
		return tenureMonths;
	}
	public void setTenureMonths(String tenureMonths) {
		this.tenureMonths = tenureMonths;
	}
	public String getTenureDays() {
		return tenureDays;
	}
	public void setTenureDays(String tenureDays) {
		this.tenureDays = tenureDays;
	}


}
