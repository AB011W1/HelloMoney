/**
 *
 */
package com.barclays.ussd.utils.jsonparsers.bean.fdrates;

/**
 * @author BTCI
 * 
 */
public class SelectedFDProduct {

    private String selectedTenure;
    private String selProductDesc;
    private String selProductID;
    private String intRate;
    private String amount;
    private String tenMon;
    private String tenDays;

    public SelectedFDProduct(String selectedTenure, String selProductDesc, String selProductID, String intRate, String amount, String tenMon,
	    String tenDays) {
	this.selectedTenure = selectedTenure;
	this.selProductDesc = selProductDesc;
	this.selProductID = selProductID;
	this.intRate = intRate;
	this.amount = amount;
	this.tenMon = tenMon;
	this.tenDays = tenDays;
    }

    /**
     * @return the selectedTenure
     */
    public String getSelectedTenure() {
	return selectedTenure;
    }

    /**
     * @param selectedTenure
     *            the selectedTenure to set
     */
    public void setSelectedTenure(String selectedTenure) {
	this.selectedTenure = selectedTenure;
    }

    /**
     * @return the selProductDesc
     */
    public String getSelProductDesc() {
	return selProductDesc;
    }

    /**
     * @param selProductDesc
     *            the selProductDesc to set
     */
    public void setSelProductDesc(String selProductDesc) {
	this.selProductDesc = selProductDesc;
    }

    /**
     * @return the selProductID
     */
    public String getSelProductID() {
	return selProductID;
    }

    /**
     * @param selProductID
     *            the selProductID to set
     */
    public void setSelProductID(String selProductID) {
	this.selProductID = selProductID;
    }

    /**
     * @return the intRate
     */
    public String getIntRate() {
	return intRate;
    }

    /**
     * @param intRate
     *            the intRate to set
     */
    public void setIntRate(String intRate) {
	this.intRate = intRate;
    }

    /**
     * @return the amount
     */
    public String getAmount() {
	return amount;
    }

    /**
     * @param amount
     *            the amount to set
     */
    public void setAmount(String amount) {
	this.amount = amount;
    }

    /**
     * @return the tenMon
     */
    public String getTenMon() {
	return tenMon;
    }

    /**
     * @param tenMon
     *            the tenMon to set
     */
    public void setTenMon(String tenMon) {
	this.tenMon = tenMon;
    }

    /**
     * @return the tenDays
     */
    public String getTenDays() {
	return tenDays;
    }

    /**
     * @param tenDays
     *            the tenDays to set
     */
    public void setTenDays(String tenDays) {
	this.tenDays = tenDays;
    }
}