/**
 * TDRatesDO.java
 */
package com.barclays.ussd.bean;

import java.math.BigDecimal;

// TODO: Auto-generated Javadoc
/**
 * The Class TDRatesDO.
 * 
 * @author BTCI
 */
public class TDRatesDO {
	
	/** countryCd. */
	private String countryCd;
	
	/** businessId. */
	private String businessId;
	
	/** tenureMonth. */
	private Integer tenureMonth;
	
	/** tenureDay. */
	private Integer tenureDay;
	
	/** amtSlabFrom. */
	private BigDecimal amtSlabFrom;
	
	/** amtSlabTo. */
	private BigDecimal amtSlabTo;
	
	/** interestRate. */
	private BigDecimal interestRate;
	
	/**
     * Gets the country cd.
     * 
     * @return the countryCd
     */
	public String getCountryCd() {
		return countryCd;
	}
	
	/**
     * Sets the country cd.
     * 
     * @param countryCd
     *            the countryCd to set
     */
	public void setCountryCd(String countryCd) {
		this.countryCd = countryCd;
	}
	
	/**
     * Gets the business id.
     * 
     * @return the businessId
     */
	public String getBusinessId() {
		return businessId;
	}
	
	/**
     * Sets the business id.
     * 
     * @param businessId
     *            the businessId to set
     */
	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}
	
	/**
     * Gets the tenure month.
     * 
     * @return the tenureMonth
     */
	public Integer getTenureMonth() {
		return tenureMonth;
	}
	
	/**
     * Sets the tenure month.
     * 
     * @param tenureMonth
     *            the tenureMonth to set
     */
	public void setTenureMonth(Integer tenureMonth) {
		this.tenureMonth = tenureMonth;
	}
	
	/**
     * Gets the tenure day.
     * 
     * @return the tenureDay
     */
	public Integer getTenureDay() {
		return tenureDay;
	}
	
	/**
     * Sets the tenure day.
     * 
     * @param tenureDay
     *            the tenureDay to set
     */
	public void setTenureDay(Integer tenureDay) {
		this.tenureDay = tenureDay;
	}
	
	/**
     * Gets the amt slab from.
     * 
     * @return the amtSlabFrom
     */
	public BigDecimal getAmtSlabFrom() {
		return amtSlabFrom;
	}
	
	/**
     * Sets the amt slab from.
     * 
     * @param amtSlabFrom
     *            the amtSlabFrom to set
     */
	public void setAmtSlabFrom(BigDecimal amtSlabFrom) {
		this.amtSlabFrom = amtSlabFrom;
	}
	
	/**
     * Gets the amt slab to.
     * 
     * @return the amtSlabTo
     */
	public BigDecimal getAmtSlabTo() {
		return amtSlabTo;
	}
	
	/**
     * Sets the amt slab to.
     * 
     * @param amtSlabTo
     *            the amtSlabTo to set
     */
	public void setAmtSlabTo(BigDecimal amtSlabTo) {
		this.amtSlabTo = amtSlabTo;
	}
	
	/**
     * Gets the interest rate.
     * 
     * @return the interestRate
     */
	public BigDecimal getInterestRate() {
		return interestRate;
	}
	
	/**
     * Sets the interest rate.
     * 
     * @param interestRate
     *            the interestRate to set
     */
	public void setInterestRate(BigDecimal interestRate) {
		this.interestRate = interestRate;
	}



}
