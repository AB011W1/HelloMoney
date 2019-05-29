package com.barclays.bmg.service.request;

import com.barclays.bmg.context.RequestContext;

public class RetrieveBillDetailsServiceRequest extends RequestContext {

	 private String billerID;
    private String billerName;
    private String controlNumber;

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

}
