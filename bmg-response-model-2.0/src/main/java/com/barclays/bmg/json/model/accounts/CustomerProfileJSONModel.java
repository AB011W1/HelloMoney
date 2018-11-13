package com.barclays.bmg.json.model.accounts;

import java.io.Serializable;
import java.util.Date;

import com.barclays.bmg.utils.BMGFormatUtility;


public class CustomerProfileJSONModel implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = -7003189391413076516L;
	private String custNam = "";
	private String lastLgnTm = "";
	private String unrdMsgCnt = "";
	private String prefLang = "";
	private String usrSta = "";
	private String pinSta = "";
	/**
	 * @return the prefLang
	 */
	public String getPrefLang() {
		return prefLang;
	}

	/**
	 *
	 */
	public void setPrefLang(String prefLang) {
		this.prefLang = prefLang;
	}

	/**
	 * @return the usrSta
	 */
	public String getUsrSta() {
		return usrSta;
	}

	/**
	 *
	 */
	public void setUsrSta(String usrSta) {
		this.usrSta = usrSta;
	}

	/**
	 * @return the pinSta
	 */
	public String getPinSta() {
		return pinSta;
	}

	/**
	 *
	 */
	public void setPinSta(String pinSta) {
		this.pinSta = pinSta;
	}

	public String getCustNam() {
		return custNam;
	}

	public void setCustNam(String custNam) {
		this.custNam = custNam;
	}

	public String getLastLgnTm() {
		return lastLgnTm;
	}

	public void setLastLgnTm(String lastLgnTm) {
		this.lastLgnTm = lastLgnTm;
	}

	public void setLastLoginTm(Date lastLoginTm) {
		this.lastLgnTm = BMGFormatUtility.getLongDate(lastLoginTm);
	}

	public String getUnrdMsgCnt() {
		return unrdMsgCnt;
	}

	public void setUnrdMsgCnt(String unrdMsgCnt) {
		this.unrdMsgCnt = unrdMsgCnt;
	}

}
