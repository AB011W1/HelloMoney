/**
 * BMGResponseContainer.java
 */
package com.barclays.ussd.bean;

import java.io.Serializable;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class BMGResponseContainer.
 * 
 * @author BTCI
 */
public class BMGResponseContainer implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** bmgResponseStack. */
	private List<Screen> bmgResponse = null;

	/**
     * Gets the bmg response.
     * 
     * @return the bmg response
     */
	public List<Screen> getBmgResponse() {
		return bmgResponse;
	}

	/**
     * Sets the bmg response.
     * 
     * @param bmgResponse
     *            the new bmg response
     */
	public void setBmgResponse(List<Screen> bmgResponse) {
		this.bmgResponse = bmgResponse;
	}

}
