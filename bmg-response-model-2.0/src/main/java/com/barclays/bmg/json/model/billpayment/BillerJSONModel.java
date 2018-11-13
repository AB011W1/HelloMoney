package com.barclays.bmg.json.model.billpayment;

import java.io.Serializable;

public class BillerJSONModel implements Serializable
{
	/**
	 *
	 */
	private static final long serialVersionUID = -2712821142893440931L;
	private String billerCatId;
	private String billerName;
	private String billerRefNo;
	private String billerCatName;
	private String billerId;
	private String billerType;

	public String getBillerName() {
		return billerName;
	}
	public void setBillerName(String billerName) {
		this.billerName = billerName;
	}
	public String getBillerRefNo() {
		return billerRefNo;
	}
	public void setBillerRefNo(String billerRefNo) {
		this.billerRefNo = billerRefNo;
	}
	/**
	 * @return the billerCatId
	 */
	public String getBillerCatId() {
		return billerCatId;
	}
	/**
	 * @param billerCatId the billerCatId to set
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
	 * @param billerCatName the billerCatName to set
	 */
	public void setBillerCatName(String billerCatName) {
		this.billerCatName = billerCatName;
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
	 * @return the Biller Type
	 */
	public String getBillerType() {
		return billerType;
	}
	/**
	 * @param billerType
	 */
	public void setBillerType(String billerType) {
		this.billerType = billerType;
	}

}