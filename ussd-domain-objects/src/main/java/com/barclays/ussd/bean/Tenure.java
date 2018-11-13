package com.barclays.ussd.bean;

// TODO: Auto-generated Javadoc
/**
 * The Class Tenure.
 */
public class Tenure {
	
	/** The ten type. */
	private String tenType;
	
	/** The ten month. */
	private int tenMonth;
	
	/** The ten day. */
	private int tenDay;
	
	/** The intrate. */
	private double intrate;

	/**
     * Instantiates a new tenure.
     * 
     * @param tenType
     *            the ten type
     * @param tenMonth
     *            the ten month
     * @param tenDay
     *            the ten day
     * @param intrate
     *            the intrate
     */
	public Tenure(String tenType, int tenMonth, int tenDay, double intrate) {
		this.tenType = tenType;
		this.tenMonth = tenMonth;
		this.tenDay = tenDay;
		this.intrate = intrate;
	}

	/**
     * Gets the ten type.
     * 
     * @return the ten type
     */
	public String getTenType() {
		return tenType;
	}

	/**
     * Sets the ten type.
     * 
     * @param tenType
     *            the new ten type
     */
	public void setTenType(String tenType) {
		this.tenType = tenType;
	}

	/**
     * Gets the ten month.
     * 
     * @return the ten month
     */
	public int getTenMonth() {
		return tenMonth;
	}

	/**
     * Sets the ten month.
     * 
     * @param tenMonth
     *            the new ten month
     */
	public void setTenMonth(int tenMonth) {
		this.tenMonth = tenMonth;
	}

	/**
     * Gets the ten day.
     * 
     * @return the ten day
     */
	public int getTenDay() {
		return tenDay;
	}

	/**
     * Sets the ten day.
     * 
     * @param tenDay
     *            the new ten day
     */
	public void setTenDay(int tenDay) {
		this.tenDay = tenDay;
	}

	/**
     * Gets the intrate.
     * 
     * @return the intrate
     */
	public double getIntrate() {
		return intrate;
	}

	/**
     * Sets the intrate.
     * 
     * @param intrate
     *            the new intrate
     */
	public void setIntrate(double intrate) {
		this.intrate = intrate;
	}
}
