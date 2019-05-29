/**
 * FromAcntLst.java
 */
package com.barclays.ussd.utils.jsonparsers.bean.billpay;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import com.barclays.bmg.dto.Amount;

/**
 * @author BTCI
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BillDetails {
	/**
	 * acntData
	 */
	@JsonProperty
	private Amount feeAmount;

	@JsonProperty
    private String paymentType;

	@JsonProperty
    private String primaryReferenceNumber;

	@JsonProperty
    private String secondaryReferenceNumber;

	@JsonProperty
    private String BillDueDate;

	/**
	 * @return the feeAmount
	 */
	public Amount getFeeAmount() {
		return feeAmount;
	}

	/**
	 * @param feeAmount the feeAmount to set
	 */
	public void setFeeAmount(Amount feeAmount) {
		this.feeAmount = feeAmount;
	}

	/**
	 * @return the paymentType
	 */
	public String getPaymentType() {
		return paymentType;
	}

	/**
	 * @param paymentType the paymentType to set
	 */
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	/**
	 * @return the primaryReferenceNumber
	 */
	public String getPrimaryReferenceNumber() {
		return primaryReferenceNumber;
	}

	/**
	 * @param primaryReferenceNumber the primaryReferenceNumber to set
	 */
	public void setPrimaryReferenceNumber(String primaryReferenceNumber) {
		this.primaryReferenceNumber = primaryReferenceNumber;
	}

	/**
	 * @return the billDueDate
	 */
	public String getBillDueDate() {
		return BillDueDate;
	}

	/**
	 * @param billDueDate the billDueDate to set
	 */
	public void setBillDueDate(String billDueDate) {
		BillDueDate = billDueDate;
	}

	/**
	 * @return the secondaryReferenceNumber
	 */
	public String getSecondaryReferenceNumber() {
		return secondaryReferenceNumber;
	}

	/**
	 * @param secondaryReferenceNumber the secondaryReferenceNumber to set
	 */
	public void setSecondaryReferenceNumber(String secondaryReferenceNumber) {
		this.secondaryReferenceNumber = secondaryReferenceNumber;
	}

}
