package com.barclays.bmg.json.model.billpayment;

import com.barclays.bmg.json.model.CasaAccountJSONModel;
import com.barclays.bmg.json.response.BMBPayloadData;

public class StatementValidationJsonModel extends BMBPayloadData {

	private CasaAccountJSONModel srcAct;
	// private String actNo;
	// private String brnCde;
	// private String brnNam;*/
	private String fromDate; // start Date
	private String toDate; // End Date
	private String txnRefNo;

	/** reference date*/
	private String refDate ;

	/**
	 * @return the refDate
	 */
	public String getRefDate() {
		return refDate;
	}

	/**
	 * @param refDate the refDate to set
	 */
	public void setRefDate(String refDate) {
		this.refDate = refDate;
	}

	/**
	 * @return the srcAct
	 */
	public CasaAccountJSONModel getSrcAct() {
		return srcAct;
	}

	/**
	 * @param srcAct
	 *            the srcAct to set
	 */
	public void setSrcAct(CasaAccountJSONModel srcAct) {
		this.srcAct = srcAct;
	}

	/**
	 * @return the fromDate
	 */
	public String getFromDate() {
		return fromDate;
	}

	/**
	 * @param fromDate the fromDate to set
	 */
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	/**
	 * @return the toDate
	 */
	public String getToDate() {
		return toDate;
	}

	/**
	 * @param toDate the toDate to set
	 */
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	/**
	 * @return the txnRefNo
	 */
	public String getTxnRefNo() {
		return txnRefNo;
	}

	/**
	 * @param txnRefNo
	 *            the txnRefNo to set
	 */
	public void setTxnRefNo(String txnRefNo) {
		this.txnRefNo = txnRefNo;
	}

}
