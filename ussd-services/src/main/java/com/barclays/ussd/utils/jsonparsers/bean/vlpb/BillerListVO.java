/**
 * BillerListVO.java
 */
package com.barclays.ussd.utils.jsonparsers.bean.vlpb;


/**
 * @author BTCI
 *
 */
public class BillerListVO {
	/**
	 * billerName
	 */
	private String billerName;
	/**
	 * billDate
	 */
	private String billDate;
	/**
	 * transRefNo
	 */
	private String transRefNo;
	/**
	 * billerRefNo
	 */
	private String billerRefNo;
	/**
	 * @return the billerName
	 */
	public String getBillerName() {
		return billerName;
	}
	/**
	 * @param billerName the billerName to set
	 */
	public void setBillerName(String billerName) {
		this.billerName = billerName;
	}
	/**
	 * @return the billDate
	 */
	public String getBillDate() {
		return billDate;
	}
	/**
	 * @param billDate the billDate to set
	 */
	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}
	/**
	 * @return the transRefNo
	 */
	public String getTransRefNo() {
		return transRefNo;
	}
	/**
	 * @param transRefNo the transRefNo to set
	 */
	public void setTransRefNo(String transRefNo) {
		this.transRefNo = transRefNo;
	}
	/**
	 * @param billerRefNo the billerRefNo to set
	 */
	public void setBillerRefNo(String billerRefNo) {
		this.billerRefNo = billerRefNo;
	}
	/**
	 * @return the billerRefNo
	 */
	public String getBillerRefNo() {
		return billerRefNo;
	}



}
