package com.barclays.bmg.service.response;

import org.codehaus.jackson.annotate.JsonProperty;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.Amount;

public class RetrieveBillDetailsServiceResponse extends ResponseContext {

	private static final long serialVersionUID = -1699114389201116017L;

	/**
	 * acntData
	 */
	@JsonProperty
	private Amount feeAmount = new Amount();

	@JsonProperty
    private String paymentType = "";

	@JsonProperty
    private String primaryReferenceNumber = "";

	@JsonProperty
    private String secondaryReferenceNumber = "";

	@JsonProperty
    private String BillDueDate = "";

	/**
	 * @return the feeAmount
	 */
	public Amount getFeeAmount() {
		return feeAmount;
	}

	/**
	 * @param amount the feeAmount to set
	 */
	public void setFeeAmount(Amount amount) {
		this.feeAmount = amount;
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