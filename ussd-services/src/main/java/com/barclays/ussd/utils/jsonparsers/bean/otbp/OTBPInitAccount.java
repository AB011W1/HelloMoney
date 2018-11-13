/**
 * AccountData.java
 */
package com.barclays.ussd.utils.jsonparsers.bean.otbp;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author BTCI
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OTBPInitAccount {

    /**
     * actNo
     */
    @JsonProperty
    private String actNo;
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
    /**
     * avlbBal
     */
    @JsonProperty
    private AvailBal avlbBal;

    @JsonProperty
    private String priInd;

    /**
     * @return the actNo
     */
    public String getActNo() {
	return actNo;
    }

    /**
     * @param actNo
     *            the actNo to set
     */
    public void setActNo(String actNo) {
	this.actNo = actNo;
    }

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

    /**
     * @return the avlbBal
     */
    public AvailBal getAvlbBal() {
	return avlbBal;
    }

    /**
     * @param avlbBal
     *            the avlbBal to set
     */
    public void setAvlbBal(AvailBal avlbBal) {
	this.avlbBal = avlbBal;
    }

    /**
     * @return the priInd
     */
    public String getPriInd() {
	return priInd;
    }

    /**
     * @param priInd
     *            the priInd to set
     */
    public void setPriInd(String priInd) {
	this.priInd = priInd;
    }

}
