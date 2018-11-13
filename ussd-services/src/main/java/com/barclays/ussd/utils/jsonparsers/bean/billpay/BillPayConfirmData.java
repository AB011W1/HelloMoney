/**
 * BillPayConfirmData.java
 */
package com.barclays.ussd.utils.jsonparsers.bean.billpay;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author BTCI
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BillPayConfirmData {
	/**
	 * txnRefNo
	 */
	@JsonProperty
	private String txnRefNo;
	/**
	 * pay
	 */
	@JsonProperty
	private BillerData pay;
	/**
	 * amt
	 */
	@JsonProperty
	private PayBillConfirmAmount amt;
	/**
	 * frActNo
	 */
	@JsonProperty
	private AccountData frActNo;
	/**
	 * curr
	 */
	@JsonProperty
	private String curr;

	/**
	 * @param txnRefNo the txnRefNo to set
	 */
	public void setTxnRefNo(String txnRefNo) {
		this.txnRefNo = txnRefNo;
	}

	/**
	 * @return the txnRefNo
	 */
	public String getTxnRefNo() {
		return txnRefNo;
	}

	/**
	 * @return the pay
	 */
	public BillerData getPay() {
		return pay;
	}

	/**
	 * @param pay the pay to set
	 */
	public void setPay(BillerData pay) {
		this.pay = pay;
	}

	/**
	 * @return the amt
	 */
	public PayBillConfirmAmount getAmt() {
		return amt;
	}

	/**
	 * @param amt the amt to set
	 */
	public void setAmt(PayBillConfirmAmount amt) {
		this.amt = amt;
	}

	/**
	 * @return the frActNo
	 */
	public AccountData getFrActNo() {
		return frActNo;
	}

	/**
	 * @param frActNo the frActNo to set
	 */
	public void setFrActNo(AccountData frActNo) {
		this.frActNo = frActNo;
	}

	/**
	 * @param curr the curr to set
	 */
	public void setCurr(String curr) {
		this.curr = curr;
	}

	/**
	 * @return the curr
	 */
	public String getCurr() {
		return curr;
	}


}
