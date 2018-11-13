/**
 * AccountActivity.java
 */
package com.barclays.ussd.utils.jsonparsers.bean.balanceEnq;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

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
	/**
	 * drAmt
	 */
	@JsonProperty
	private String drAmt;
	/**
	 * crAmt
	 */
	@JsonProperty
	private String crAmt;
	/**
	 * @return the dt
	 */
	public String getDt() {
		return dt;
	}
	/**
	 * @param dt the dt to set
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
	 * @param txnPrt the txnPrt to set
	 */
	public void setTxnPrt(String txnPrt) {
		this.txnPrt = txnPrt;
	}
	/**
	 * @return the drAmt
	 */
	public String getDrAmt() {
		return drAmt;
	}
	/**
	 * @param drAmt the drAmt to set
	 */
	public void setDrAmt(String drAmt) {
		this.drAmt = drAmt;
	}
	/**
	 * @return the crAmt
	 */
	public String getCrAmt() {
		return crAmt;
	}
	/**
	 * @param crAmt the crAmt to set
	 */
	public void setCrAmt(String crAmt) {
		this.crAmt = crAmt;
	}



}
