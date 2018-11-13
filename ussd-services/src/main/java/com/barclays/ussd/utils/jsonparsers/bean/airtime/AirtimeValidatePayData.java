/**
 *
 */
package com.barclays.ussd.utils.jsonparsers.bean.airtime;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import com.barclays.ussd.utils.jsonparsers.bean.login.CustomerMobileRegAcct;

/**
 * @author BTCI
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AirtimeValidatePayData {

	public CustomerMobileRegAcct getCreditcardJsonModel() {
		return creditcardJsonModel;
	}

	public void setCreditcardJsonModel(CustomerMobileRegAcct creditcardJsonModel) {
		this.creditcardJsonModel = creditcardJsonModel;
	}

	@JsonProperty
	private Account srcAcct;

	@JsonProperty
	private Amount txnAmt;

	@JsonProperty
	private String txnRefNo;

	@JsonProperty
	private String mblNo;

	@JsonProperty
	private Biller prvder;

	@JsonProperty
	private CustomerMobileRegAcct creditcardJsonModel;


	/**
	 * @return the srcAcct
	 */
	public Account getSrcAcct() {
		return srcAcct;
	}

	/**
	 * @param srcAcct
	 *            the srcAcct to set
	 */
	public void setSrcAcct(Account srcAcct) {
		this.srcAcct = srcAcct;
	}

	/**
	 * @return the txnAmt
	 */
	public Amount getTxnAmt() {
		return txnAmt;
	}

	/**
	 * @param txnAmt
	 *            the txnAmt to set
	 */
	public void setTxnAmt(Amount txnAmt) {
		this.txnAmt = txnAmt;
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

	/**
	 * @return the mblNo
	 */
	public String getMblNo() {
		return mblNo;
	}

	/**
	 * @param mblNo
	 *            the mblNo to set
	 */
	public void setMblNo(String mblNo) {
		this.mblNo = mblNo;
	}

	/**
	 * @return the prvder
	 */
	public Biller getPrvder() {
		return prvder;
	}

	/**
	 * @param prvder
	 *            the prvder to set
	 */
	public void setPrvder(Biller prvder) {
		this.prvder = prvder;
	}

}
