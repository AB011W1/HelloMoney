package com.barclays.bmg.mvc.command.billpayment;

public class RetrieveBillDetailsCommand {

	private String controlNumber;
	private String billerID;
	private String billerName;
	/**
	 * @return the controlNumber
	 */
	public String getControlNumber() {
		return controlNumber;
	}
	/**
	 * @param controlNumber the controlNumber to set
	 */
	public void setControlNumber(String controlNumber) {
		this.controlNumber = controlNumber;
	}
	/**
	 * @return the billerID
	 */
	public String getBillerID() {
		return billerID;
	}
	/**
	 * @param billerID the billerID to set
	 */
	public void setBillerID(String billerID) {
		this.billerID = billerID;
	}
	/**
	 * @return the billerName
	 */
	public String getBillerName() {
		return billerName;
	}
	/**
	 * @param billerName the billerName to set
	 */
	public void setBillerName(String billerName) {
		this.billerName = billerName;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BillerID : " + billerID + "\tBiller Name : "+ billerName+"\tControl Number :"+controlNumber ;
	}


}
