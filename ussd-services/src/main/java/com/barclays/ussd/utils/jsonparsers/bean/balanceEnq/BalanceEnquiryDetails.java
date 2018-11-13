/**
 * CasaAccntDetails.java
 */
package com.barclays.ussd.utils.jsonparsers.bean.balanceEnq;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author BTCI
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BalanceEnquiryDetails {

    /**
     * mkdActNo
     */
    @JsonProperty
    private String mkdActNo;
    /**
     * curr
     */
    @JsonProperty
    private String curr;

    /* Added for the Balance Enquiry */
    @JsonProperty
    private String actNo;

    @JsonProperty
    private CurrentBalance curBal;

    @JsonProperty
    private AvilableBalance avblBal;

    @JsonProperty
    private NetBalanceAmount netBalanceAmount;

    @JsonProperty
    private CurrentBookBalanceAmount currentBookBalanceAmount;


    /**
     * @return the mkdActNo
     */
    public String getMkdActNo() {
	return mkdActNo;
    }

    /**
     * @param mkdActNo
     *            the mkdActNo to set
     */
    public void setMkdActNo(String mkdActNo) {
	this.mkdActNo = mkdActNo;
    }

    /**
     * @return the curr
     */
    public String getCurr() {
	return curr;
    }

    /**
     * @param curr
     *            the curr to set
     */
    public void setCurr(String curr) {
	this.curr = curr;
    }

    public String getActNo() {
	return actNo;
    }

    public void setActNo(String actNo) {
	this.actNo = actNo;
    }

    /**
     * @return the curBal
     */
    public CurrentBalance getCurBal() {
	return curBal;
    }

    /**
     * @param curBal
     *            the curBal to set
     */
    public void setCurBal(CurrentBalance curBal) {
	this.curBal = curBal;
    }

    /**
     * @return the avblBal
     */
    public AvilableBalance getAvblBal() {
	return avblBal;
    }

    /**
     * @param avblBal
     *            the avblBal to set
     */
    public void setAvblBal(AvilableBalance avblBal) {
	this.avblBal = avblBal;
    }

    /**
     * @return the netBalanceAmount
     */
	public NetBalanceAmount getNetBalanceAmount() {
	return netBalanceAmount;
    }

    /**
	 * @param netBalanceAmount the netBalanceAmount to set
     */
	public void setNetBalanceAmount(NetBalanceAmount netBalanceAmount) {
	this.netBalanceAmount = netBalanceAmount;
    }

    /**
     * @return the currentBookBalanceAmount
     */
	public CurrentBookBalanceAmount getCurrentBookBalanceAmount() {
	return currentBookBalanceAmount;
    }

    /**
	 * @param currentBookBalanceAmount the currentBookBalanceAmount to set
     */
	public void setCurrentBookBalanceAmount(
			CurrentBookBalanceAmount currentBookBalanceAmount) {
	this.currentBookBalanceAmount = currentBookBalanceAmount;
    }



}
