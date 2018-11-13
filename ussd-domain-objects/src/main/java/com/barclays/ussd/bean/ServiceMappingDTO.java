package com.barclays.ussd.bean;

import java.util.Set;

// TODO: Auto-generated Javadoc
/**
 * The Class ServiceMappingDTO.
 */
public class ServiceMappingDTO {
	
	/** The tran id. */
	private String tranId;
	
	/** The value. */
	private String value;
	
	/** The skipped nodes. */
	private Set<SkipNodeDTO> skippedNodes;
	
	/**
     * Gets the tran id.
     * 
     * @return the tran id
     */
	public String getTranId() {
		return tranId;
	}
	
	/**
     * Sets the tran id.
     * 
     * @param tranId
     *            the new tran id
     */
	public void setTranId(String tranId) {
		this.tranId = tranId;
	}
	
	/**
     * Gets the value.
     * 
     * @return the value
     */
	public String getValue() {
		return value;
	}
	
	/**
     * Sets the value.
     * 
     * @param value
     *            the new value
     */
	public void setValue(String value) {
		this.value = value;
	}
	
	/**
     * Gets the skipped nodes.
     * 
     * @return the skipped nodes
     */
	public Set<SkipNodeDTO> getSkippedNodes() {
		return skippedNodes;
	}
	
	/**
     * Sets the skipped nodes.
     * 
     * @param skippedNodes
     *            the new skipped nodes
     */
	public void setSkippedNodes(Set<SkipNodeDTO> skippedNodes) {
		this.skippedNodes = skippedNodes;
	}

}
