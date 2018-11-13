package com.barclays.bmg.mvc.command.billpayment;

/**
 * @author BTCI
 * 
 */
public class DeleteBeneficiaryCommand {

	private String payeeId;

	/**
	 * @return String
	 */
	public String getPayeeId() {
		return payeeId;
	}

	/**
	 * @param payeeId
	 */
	public void setPayeeId(String payeeId) {
		this.payeeId = payeeId;
	}

}
