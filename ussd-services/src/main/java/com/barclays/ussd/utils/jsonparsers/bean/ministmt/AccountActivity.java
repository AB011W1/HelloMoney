/**
 * AccountActivity.java
 */
package com.barclays.ussd.utils.jsonparsers.bean.ministmt;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import com.barclays.ussd.bean.Amount;

/**
 * @author BTCI
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountActivity {
	/**
	 * dt
	 */
	@JsonProperty
	private String dt;
	/**
	 * txnPrt
	 */
	@JsonProperty
	private String txnPrt;

	@JsonProperty
	private String refNo;
	/**
	 * drAmt
	 */
	@JsonProperty
	private Amount drAmt;
	/**
	 * crAmt
	 */
	@JsonProperty
	private Amount crAmt;

	@JsonProperty
	private String curr;

	@JsonProperty
	private String amount;

	@JsonProperty
	private String txnType;


	@JsonProperty
	private String bal;

	/**
	 * @return the dt
	 */
	public String getDt() {
		return dt;
	}

	/**
	 * @param dt
	 *            the dt to set
	 */
	public void setDt(String dt) {
		this.dt = dt;
	}

	/**
	 * @return the txnPrt
	 */
	public String getTxnPrt() {
		return txnPrt;
	}

	/**
	 * @param txnPrt
	 *            the txnPrt to set
	 */
	public void setTxnPrt(String txnPrt) {
		this.txnPrt = txnPrt;
	}

	public Amount getDrAmt() {
		return drAmt;
	}

	public void setDrAmt(Amount drAmt) {
		this.drAmt = drAmt;
	}

	public Amount getCrAmt() {
		return crAmt;
	}

	public void setCrAmt(Amount crAmt) {
		this.crAmt = crAmt;
	}

	public String getBal() {
		return bal;
	}

	public void setBal(String bal) {
		this.bal = bal;
	}

	public String getRefNo() {
		return refNo;
	}

	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}

	/**
	 * @return the curr
	 */
	public String getCurr() {
		return curr;
	}

	/**
	 * @param curr the curr to set
	 */
	public void setCurr(String curr) {
		this.curr = curr;
	}

	/**
	 * @return the amount
	 */
	public String getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}

	/**
	 * @return the txnType
	 */
	public String getTxnType() {
		return txnType;
	}

	/**
	 * @param txnType the txnType to set
	 */
	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}



}
