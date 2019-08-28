package com.barclays.bmg.json.model.billpayment;

import java.io.Serializable;

import com.barclays.bmg.dto.Amount;
import com.barclays.bmg.dto.InvoiceDetails;
import com.barclays.bmg.json.response.BMBPayloadData;

public class BillDetailsJSONBean extends BMBPayloadData implements
		Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 3303270007959862290L;

	private Amount feeAmount;

    private String paymentType;
    private String primaryReferenceNumber;
    private String BillDueDate;
    private String secondaryReferenceNumber;
//    private BillInvoiceDetails billInvoiceDetails;
    private InvoiceDetails billInvoiceDetails;

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

	public void setBillInvoiceDetails(InvoiceDetails billInvoiceDetails) {
		this.billInvoiceDetails = billInvoiceDetails;
	}

	public InvoiceDetails getBillInvoiceDetails() {
		return billInvoiceDetails;
	}
}