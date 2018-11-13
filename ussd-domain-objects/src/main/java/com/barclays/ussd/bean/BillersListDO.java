/**
 * BillersListDO.java
 */
package com.barclays.ussd.bean;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * The Class BillersListDO.
 *
 * @author BTCI
 */
public class BillersListDO implements Serializable {

    /** serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** businessId. */
    private String businessId;

    /** billerId. */
    private String billerId;

    /** billerNm. */
    private String billerNm;

    /** billerCategoryId. */
    private String billerCategoryId;

    /** billerCategoryNm. */
    private String billerCategoryNm;

    private String onlineBillerFlg;

    private String billerAttributes;

	public String getBillerAttributes() {
	return billerAttributes;
    }

    public void setBillerAttributes(String billerAttributes) {
	this.billerAttributes = billerAttributes;
    }

    public String getOnlineBillerFlg() {
	return onlineBillerFlg;
    }

    public void setOnlineBillerFlg(String onlineBillerFlg) {
	this.onlineBillerFlg = onlineBillerFlg;
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
     * Gets the biller id.
     *
     * @return the billerId
     */
    public String getBillerId() {
	return billerId;
    }

    /**
     * Sets the biller id.
     *
     * @param billerId
     *            the billerId to set
     */
    public void setBillerId(String billerId) {
	this.billerId = billerId;
    }

    /**
     * Gets the biller nm.
     *
     * @return the billerNm
     */
    public String getBillerNm() {
	return billerNm;
    }

    /**
     * Sets the biller nm.
     *
     * @param billerNm
     *            the billerNm to set
     */
    public void setBillerNm(String billerNm) {
	this.billerNm = billerNm;
    }

    /**
     * Gets the biller category id.
     *
     * @return the billerCategoryId
     */
    public String getBillerCategoryId() {
	return billerCategoryId;
    }

    /**
     * Sets the biller category id.
     *
     * @param billerCategoryId
     *            the billerCategoryId to set
     */
    public void setBillerCategoryId(String billerCategoryId) {
	this.billerCategoryId = billerCategoryId;
    }

    /**
     * Gets the biller category nm.
     *
     * @return the billerCategoryNm
     */
    public String getBillerCategoryNm() {
	return billerCategoryNm;
    }

    /**
     * Sets the biller category nm.
     *
     * @param billerCategoryNm
     *            the billerCategoryNm to set
     */
    public void setBillerCategoryNm(String billerCategoryNm) {
	this.billerCategoryNm = billerCategoryNm;
    }

}
