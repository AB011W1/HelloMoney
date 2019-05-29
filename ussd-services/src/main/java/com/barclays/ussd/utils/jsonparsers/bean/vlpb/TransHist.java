/**
 * TransHist.java
 */
package com.barclays.ussd.utils.jsonparsers.bean.vlpb;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import com.barclays.ussd.utils.jsonparsers.bean.airtime.Account;

/**
 * @author BTCI
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransHist {
	/**
	 * status
	 */
	@JsonProperty
	private String status;
	/**
	 * transactionDate
	 */
	@JsonProperty
	private String transactionDate;
	/**
	 * billerInfo
	 */
	@JsonProperty
	private BillerInfo billerInfo;

	/**
	 * transactionRefNumber
	 */
	@JsonProperty
	private String transactionRefNumber;
	/**
	 * amountInfo
	 */
	@JsonProperty
	private AmountInfo amountInfo;

	@JsonProperty
	private Account fromAccountInfo;

	public Account getFromAccountInfo() {
		return fromAccountInfo;
	}

	public void setFromAccountInfo(Account fromAccountInfo) {
		this.fromAccountInfo = fromAccountInfo;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the transactionDate
	 */
	public String getTransactionDate() {
		return transactionDate;
	}
	/**
	 * @param transactionDate the transactionDate to set
	 */
	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}
	/**
	 * @return the billerInfo
	 */
	public BillerInfo getBillerInfo() {
		return billerInfo;
	}
	/**
	 * @param billerInfo the billerInfo to set
	 */
	public void setBillerInfo(BillerInfo billerInfo) {
		this.billerInfo = billerInfo;
	}
	/**
	 * @param transactionRefNumber the transactionRefNumber to set
	 */
	public void setTransactionRefNumber(String transactionRefNumber) {
		this.transactionRefNumber = transactionRefNumber;
	}
	/**
	 * @return the transactionRefNumber
	 */
	public String getTransactionRefNumber() {
		return transactionRefNumber;
	}
	/**
	 * @param amountInfo the amountInfo to set
	 */
	public void setAmountInfo(AmountInfo amountInfo) {
		this.amountInfo = amountInfo;
	}
	/**
	 * @return the amountInfo
	 */
	public AmountInfo getAmountInfo() {
		return amountInfo;
	}



}
