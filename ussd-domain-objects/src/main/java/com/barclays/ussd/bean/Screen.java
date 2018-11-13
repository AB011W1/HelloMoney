package com.barclays.ussd.bean;

import java.io.Serializable;
import java.util.Map;

// TODO: Auto-generated Javadoc
/**
 * The Class Screen.
 */
public class Screen implements Serializable
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The screen data. */
	private Map<String,String> screenData;

	/**
     * Gets the screen data.
     * 
     * @return the screen data
     */
	public Map<String, String> getScreenData() {
		return screenData;
	}

	/**
     * Sets the screen data.
     * 
     * @param screenData
     *            the screen data
     */
	public void setScreenData(Map<String, String> screenData) {
		this.screenData = screenData;
	}


}
