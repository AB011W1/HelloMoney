/**
 *
 */
package com.barclays.ussd.utils.jsonparsers.bean.airtime;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author BTCI
 * 
 */
public class Biller {

    @JsonProperty
    private String billerName;

    @JsonProperty
    private String billerId;

    @JsonProperty
    private String billerCatId;

    @JsonProperty
    private String billerCatName;

    @JsonProperty
    private String billerRefNo;

    @JsonProperty
    private String billerType;

    /**
     * @return the billerName
     */
    public String getBillerName() {
	return billerName;
    }

    /**
     * @param billerName
     *            the billerName to set
     */
    public void setBillerName(String billerName) {
	this.billerName = billerName;
    }

    /**
     * @return the billerId
     */
    public String getBillerId() {
	return billerId;
    }

    /**
     * @param billerId
     *            the billerId to set
     */
    public void setBillerId(String billerId) {
	this.billerId = billerId;
    }

    /**
     * @return the billerCatId
     */
    public String getBillerCatId() {
	return billerCatId;
    }

    /**
     * @param billerCatId
     *            the billerCatId to set
     */
    public void setBillerCatId(String billerCatId) {
	this.billerCatId = billerCatId;
    }

    /**
     * @return the billerCatName
     */
    public String getBillerCatName() {
	return billerCatName;
    }

    /**
     * @param billerCatName
     *            the billerCatName to set
     */
    public void setBillerCatName(String billerCatName) {
	this.billerCatName = billerCatName;
    }

    /**
     * @return the billerRefNo
     */
    public String getBillerRefNo() {
	return billerRefNo;
    }

    /**
     * @param billerRefNo
     *            the billerRefNo to set
     */
    public void setBillerRefNo(String billerRefNo) {
	this.billerRefNo = billerRefNo;
    }

    public void setBillerType(String billerType) {
	this.billerType = billerType;
    }

    public String getBillerType() {
	return billerType;
    }
}
