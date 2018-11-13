/**
 * FromAcntLst.java
 */
package com.barclays.ussd.utils.jsonparsers.bean.otbp;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author BTCI
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OTBPInitPayData {

	@JsonProperty
	private OTBPInitAccount fromAccount;

	@JsonProperty
	private String amount;

	@JsonProperty
	private String txnRefNo;

	@JsonProperty
	private String txnDate;

	@JsonProperty
	private List<OTBPInitAccount> fromAcctList;

	@JsonProperty
	private BillerInfo billerInfo;

	/**
	 * @return the fromAccount
	 */
	public OTBPInitAccount getFromAccount() {
		return fromAccount;
	}

	/**
	 * @param fromAccount
	 *            the fromAccount to set
	 */
	public void setFromAccount(OTBPInitAccount fromAccount) {
		this.fromAccount = fromAccount;
	}

	/**
	 * @return the amount
	 */
	public String getAmount() {
		return amount;
	}

	/**
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(String amount) {
		this.amount = amount;
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
	 * @return the txnDate
	 */
	public String getTxnDate() {
		return txnDate;
	}

	/**
	 * @param txnDate
	 *            the txnDate to set
	 */
	public void setTxnDate(String txnDate) {
		this.txnDate = txnDate;
	}

	public void setFromAcctList(List<OTBPInitAccount> fromAcctList) {
		this.fromAcctList = fromAcctList;
	}

	public List<OTBPInitAccount> getFromAcctList() {
		return fromAcctList;
	}

	/**
	 * @return the billerInfo
	 */
	public BillerInfo getBillerInfo() {
		return billerInfo;
	}

	/**
	 * @param billerInfo
	 *            the billerInfo to set
	 */
	public void setBillerInfo(BillerInfo billerInfo) {
		this.billerInfo = billerInfo;
	}

}
