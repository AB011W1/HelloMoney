package com.barclays.ussd.svc.context;

import java.util.Map;

// TODO: Auto-generated Javadoc
/**
 * The Class USSDBaseRequest.
 */
public class USSDBaseRequest {
	
	/** The msisdn no. */
	private String msisdnNo;
	
	/** The op cde. */
	private String opCde;
	
	/** The ser ver. */
	private String serVer;
	
	/** The request param map. */
	private Map<String, String> requestParamMap;

	/**
     * Gets the msisdn no.
     * 
     * @return the msisdn no
     */
	public String getMsisdnNo() {
		return msisdnNo;
	}

	/**
     * Sets the msisdn no.
     * 
     * @param msisdnNo
     *            the new msisdn no
     */
	public void setMsisdnNo(String msisdnNo) {
		this.msisdnNo = msisdnNo;
	}

	/**
     * Gets the op cde.
     * 
     * @return the op cde
     */
	public String getOpCde() {
		return opCde;
	}

	/**
     * Sets the op cde.
     * 
     * @param opCde
     *            the new op cde
     */
	public void setOpCde(String opCde) {
		this.opCde = opCde;
	}

	/**
     * Gets the ser ver.
     * 
     * @return the ser ver
     */
	public String getSerVer() {
		return serVer;
	}

	/**
     * Sets the ser ver.
     * 
     * @param serVer
     *            the new ser ver
     */
	public void setSerVer(String serVer) {
		this.serVer = serVer;
	}

	/**
     * Gets the request param map.
     * 
     * @return the request param map
     */
	public Map<String, String> getRequestParamMap() {
		return requestParamMap;
	}

	/**
     * Sets the request param map.
     * 
     * @param requestParamMap
     *            the request param map
     */
	public void setRequestParamMap(Map<String, String> requestParamMap) {
		this.requestParamMap = requestParamMap;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "msisdnNo " +msisdnNo+ " opCde : "+opCde + "serVer : " +serVer ;
	}


	
}
