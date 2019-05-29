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

    private String billAggregatorId;

    /* Presentment Flag*/
    private String presentmentFlg;

    private String refNoValidation_1;
	/**
	 * @return the businessId
	 */
	public String getBusinessId() {
		return businessId;
	}

	/**
	 * @param businessId the businessId to set
	 */
	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	/**
	 * @return the billerId
	 */
	public String getBillerId() {
		return billerId;
	}

	/**
	 * @param billerId the billerId to set
	 */
	public void setBillerId(String billerId) {
		this.billerId = billerId;
	}

	/**
	 * @return the billerNm
	 */
	public String getBillerNm() {
		return billerNm;
	}

	/**
	 * @param billerNm the billerNm to set
	 */
	public void setBillerNm(String billerNm) {
		this.billerNm = billerNm;
	}

	/**
	 * @return the billerCategoryId
	 */
	public String getBillerCategoryId() {
		return billerCategoryId;
	}

	/**
	 * @param billerCategoryId the billerCategoryId to set
	 */
	public void setBillerCategoryId(String billerCategoryId) {
		this.billerCategoryId = billerCategoryId;
	}

	/**
	 * @return the billerCategoryNm
	 */
	public String getBillerCategoryNm() {
		return billerCategoryNm;
	}

	/**
	 * @param billerCategoryNm the billerCategoryNm to set
	 */
	public void setBillerCategoryNm(String billerCategoryNm) {
		this.billerCategoryNm = billerCategoryNm;
	}

	/**
	 * @return the onlineBillerFlg
	 */
	public String getOnlineBillerFlg() {
		return onlineBillerFlg;
	}

	/**
	 * @param onlineBillerFlg the onlineBillerFlg to set
	 */
	public void setOnlineBillerFlg(String onlineBillerFlg) {
		this.onlineBillerFlg = onlineBillerFlg;
	}

	/**
	 * @return the billerAttributes
	 */
	public String getBillerAttributes() {
		return billerAttributes;
	}

	/**
	 * @param billerAttributes the billerAttributes to set
	 */
	public void setBillerAttributes(String billerAttributes) {
		this.billerAttributes = billerAttributes;
	}

	/**
	 * @return the billAggregatorId
	 */
	public String getBillAggregatorId() {
		return billAggregatorId;
	}

	/**
	 * @param billAggregatorId the billAggregatorId to set
	 */
	public void setBillAggregatorId(String billAggregatorId) {
		this.billAggregatorId = billAggregatorId;
	}

	/**
	 * @return the presentmentFlg
	 */
	public String getPresentmentFlg() {
		return presentmentFlg;
	}

	/**
	 * @param presentmentFlg the presentmentFlg to set
	 */
	public void setPresentmentFlg(String presentmentFlg) {
		this.presentmentFlg = presentmentFlg;
	}

	/**
	 * @return the refNoValidation_1
	 */
	public String getRefNoValidation_1() {
		return refNoValidation_1;
	}

	/**
	 * @param refNoValidation_1 the refNoValidation_1 to set
	 */
	public void setRefNoValidation_1(String refNoValidation_1) {
		this.refNoValidation_1 = refNoValidation_1;
	}
}
