package com.barclays.bmg.dto;

import com.barclays.bmg.constants.TransactionHistoryConstants;

/**
 * @author BTCI
 * 
 */
public class BillPaymentHistory extends TransactionHistoryDTO {

    /**
	 *
	 */
    private static final long serialVersionUID = 1L;

    private String billReferenceNumber;

    private String billerType;

    /*
     * (non-Javadoc)
     * 
     * @see com.barclays.bmg.dto.TransactionHistoryDTO#getType()
     */
    @Override
    public String getType() {
	return TransactionHistoryConstants.BILL_PAYMENT;
    }

    /**
     * @return String
     */
    public String getBillReferenceNumber() {
	return billReferenceNumber;
    }

    /**
     * @param billReferenceNumber
     */
    public void setBillReferenceNumber(String billReferenceNumber) {
	this.billReferenceNumber = billReferenceNumber;
    }

    /**
     * @return String
     */
    public String getBillerType() {
	return billerType;
    }

    /**
     * @param billerType
     */
    public void setBillerType(String billerType) {
	this.billerType = billerType;
    }

}
